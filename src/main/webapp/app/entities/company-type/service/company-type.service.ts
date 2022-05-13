import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompanyType, getCompanyTypeIdentifier } from '../company-type.model';

export type EntityResponseType = HttpResponse<ICompanyType>;
export type EntityArrayResponseType = HttpResponse<ICompanyType[]>;

@Injectable({ providedIn: 'root' })
export class CompanyTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/company-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(companyType: ICompanyType): Observable<EntityResponseType> {
    return this.http.post<ICompanyType>(this.resourceUrl, companyType, { observe: 'response' });
  }

  update(companyType: ICompanyType): Observable<EntityResponseType> {
    return this.http.put<ICompanyType>(`${this.resourceUrl}/${getCompanyTypeIdentifier(companyType) as number}`, companyType, {
      observe: 'response',
    });
  }

  partialUpdate(companyType: ICompanyType): Observable<EntityResponseType> {
    return this.http.patch<ICompanyType>(`${this.resourceUrl}/${getCompanyTypeIdentifier(companyType) as number}`, companyType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCompanyTypeToCollectionIfMissing(
    companyTypeCollection: ICompanyType[],
    ...companyTypesToCheck: (ICompanyType | null | undefined)[]
  ): ICompanyType[] {
    const companyTypes: ICompanyType[] = companyTypesToCheck.filter(isPresent);
    if (companyTypes.length > 0) {
      const companyTypeCollectionIdentifiers = companyTypeCollection.map(companyTypeItem => getCompanyTypeIdentifier(companyTypeItem)!);
      const companyTypesToAdd = companyTypes.filter(companyTypeItem => {
        const companyTypeIdentifier = getCompanyTypeIdentifier(companyTypeItem);
        if (companyTypeIdentifier == null || companyTypeCollectionIdentifiers.includes(companyTypeIdentifier)) {
          return false;
        }
        companyTypeCollectionIdentifiers.push(companyTypeIdentifier);
        return true;
      });
      return [...companyTypesToAdd, ...companyTypeCollection];
    }
    return companyTypeCollection;
  }
}
