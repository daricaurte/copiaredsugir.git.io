import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRelation, getRelationIdentifier } from '../relation.model';

export type EntityResponseType = HttpResponse<IRelation>;
export type EntityArrayResponseType = HttpResponse<IRelation[]>;

@Injectable({ providedIn: 'root' })
export class RelationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/relations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(relation: IRelation): Observable<EntityResponseType> {
    return this.http.post<IRelation>(this.resourceUrl, relation, { observe: 'response' });
  }

  update(relation: IRelation): Observable<EntityResponseType> {
    return this.http.put<IRelation>(`${this.resourceUrl}/${getRelationIdentifier(relation) as number}`, relation, { observe: 'response' });
  }

  partialUpdate(relation: IRelation): Observable<EntityResponseType> {
    return this.http.patch<IRelation>(`${this.resourceUrl}/${getRelationIdentifier(relation) as number}`, relation, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRelation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRelationToCollectionIfMissing(relationCollection: IRelation[], ...relationsToCheck: (IRelation | null | undefined)[]): IRelation[] {
    const relations: IRelation[] = relationsToCheck.filter(isPresent);
    if (relations.length > 0) {
      const relationCollectionIdentifiers = relationCollection.map(relationItem => getRelationIdentifier(relationItem)!);
      const relationsToAdd = relations.filter(relationItem => {
        const relationIdentifier = getRelationIdentifier(relationItem);
        if (relationIdentifier == null || relationCollectionIdentifiers.includes(relationIdentifier)) {
          return false;
        }
        relationCollectionIdentifiers.push(relationIdentifier);
        return true;
      });
      return [...relationsToAdd, ...relationCollection];
    }
    return relationCollection;
  }
}
