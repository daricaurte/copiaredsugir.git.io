import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAffiliate, getAffiliateIdentifier } from '../affiliate.model';

export type EntityResponseType = HttpResponse<IAffiliate>;
export type EntityArrayResponseType = HttpResponse<IAffiliate[]>;

@Injectable({ providedIn: 'root' })
export class AffiliateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/affiliates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(affiliate: IAffiliate): Observable<EntityResponseType> {
    return this.http.post<IAffiliate>(this.resourceUrl, affiliate, { observe: 'response' });
  }

  update(affiliate: IAffiliate): Observable<EntityResponseType> {
    return this.http.put<IAffiliate>(`${this.resourceUrl}/${getAffiliateIdentifier(affiliate) as number}`, affiliate, {
      observe: 'response',
    });
  }

  partialUpdate(affiliate: IAffiliate): Observable<EntityResponseType> {
    return this.http.patch<IAffiliate>(`${this.resourceUrl}/${getAffiliateIdentifier(affiliate) as number}`, affiliate, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAffiliate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAffiliate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAffiliateToCollectionIfMissing(
    affiliateCollection: IAffiliate[],
    ...affiliatesToCheck: (IAffiliate | null | undefined)[]
  ): IAffiliate[] {
    const affiliates: IAffiliate[] = affiliatesToCheck.filter(isPresent);
    if (affiliates.length > 0) {
      const affiliateCollectionIdentifiers = affiliateCollection.map(affiliateItem => getAffiliateIdentifier(affiliateItem)!);
      const affiliatesToAdd = affiliates.filter(affiliateItem => {
        const affiliateIdentifier = getAffiliateIdentifier(affiliateItem);
        if (affiliateIdentifier == null || affiliateCollectionIdentifiers.includes(affiliateIdentifier)) {
          return false;
        }
        affiliateCollectionIdentifiers.push(affiliateIdentifier);
        return true;
      });
      return [...affiliatesToAdd, ...affiliateCollection];
    }
    return affiliateCollection;
  }
}
