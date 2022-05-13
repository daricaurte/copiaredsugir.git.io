import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPotential, getPotentialIdentifier } from '../potential.model';

export type EntityResponseType = HttpResponse<IPotential>;
export type EntityArrayResponseType = HttpResponse<IPotential[]>;

@Injectable({ providedIn: 'root' })
export class PotentialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/potentials');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(potential: IPotential): Observable<EntityResponseType> {
    return this.http.post<IPotential>(this.resourceUrl, potential, { observe: 'response' });
  }

  update(potential: IPotential): Observable<EntityResponseType> {
    return this.http.put<IPotential>(`${this.resourceUrl}/${getPotentialIdentifier(potential) as number}`, potential, {
      observe: 'response',
    });
  }

  partialUpdate(potential: IPotential): Observable<EntityResponseType> {
    return this.http.patch<IPotential>(`${this.resourceUrl}/${getPotentialIdentifier(potential) as number}`, potential, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPotential>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPotential[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPotentialToCollectionIfMissing(
    potentialCollection: IPotential[],
    ...potentialsToCheck: (IPotential | null | undefined)[]
  ): IPotential[] {
    const potentials: IPotential[] = potentialsToCheck.filter(isPresent);
    if (potentials.length > 0) {
      const potentialCollectionIdentifiers = potentialCollection.map(potentialItem => getPotentialIdentifier(potentialItem)!);
      const potentialsToAdd = potentials.filter(potentialItem => {
        const potentialIdentifier = getPotentialIdentifier(potentialItem);
        if (potentialIdentifier == null || potentialCollectionIdentifiers.includes(potentialIdentifier)) {
          return false;
        }
        potentialCollectionIdentifiers.push(potentialIdentifier);
        return true;
      });
      return [...potentialsToAdd, ...potentialCollection];
    }
    return potentialCollection;
  }
}
