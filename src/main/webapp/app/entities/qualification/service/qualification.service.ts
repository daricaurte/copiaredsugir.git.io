import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQualification, getQualificationIdentifier } from '../qualification.model';

export type EntityResponseType = HttpResponse<IQualification>;
export type EntityArrayResponseType = HttpResponse<IQualification[]>;

@Injectable({ providedIn: 'root' })
export class QualificationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qualifications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qualification: IQualification): Observable<EntityResponseType> {
    return this.http.post<IQualification>(this.resourceUrl, qualification, { observe: 'response' });
  }

  update(qualification: IQualification): Observable<EntityResponseType> {
    return this.http.put<IQualification>(`${this.resourceUrl}/${getQualificationIdentifier(qualification) as number}`, qualification, {
      observe: 'response',
    });
  }

  partialUpdate(qualification: IQualification): Observable<EntityResponseType> {
    return this.http.patch<IQualification>(`${this.resourceUrl}/${getQualificationIdentifier(qualification) as number}`, qualification, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQualification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQualification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addQualificationToCollectionIfMissing(
    qualificationCollection: IQualification[],
    ...qualificationsToCheck: (IQualification | null | undefined)[]
  ): IQualification[] {
    const qualifications: IQualification[] = qualificationsToCheck.filter(isPresent);
    if (qualifications.length > 0) {
      const qualificationCollectionIdentifiers = qualificationCollection.map(
        qualificationItem => getQualificationIdentifier(qualificationItem)!
      );
      const qualificationsToAdd = qualifications.filter(qualificationItem => {
        const qualificationIdentifier = getQualificationIdentifier(qualificationItem);
        if (qualificationIdentifier == null || qualificationCollectionIdentifiers.includes(qualificationIdentifier)) {
          return false;
        }
        qualificationCollectionIdentifiers.push(qualificationIdentifier);
        return true;
      });
      return [...qualificationsToAdd, ...qualificationCollection];
    }
    return qualificationCollection;
  }
}
