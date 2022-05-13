import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INetworks, getNetworksIdentifier } from '../networks.model';

export type EntityResponseType = HttpResponse<INetworks>;
export type EntityArrayResponseType = HttpResponse<INetworks[]>;

@Injectable({ providedIn: 'root' })
export class NetworksService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/networks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(networks: INetworks): Observable<EntityResponseType> {
    return this.http.post<INetworks>(this.resourceUrl, networks, { observe: 'response' });
  }

  update(networks: INetworks): Observable<EntityResponseType> {
    return this.http.put<INetworks>(`${this.resourceUrl}/${getNetworksIdentifier(networks) as number}`, networks, { observe: 'response' });
  }

  partialUpdate(networks: INetworks): Observable<EntityResponseType> {
    return this.http.patch<INetworks>(`${this.resourceUrl}/${getNetworksIdentifier(networks) as number}`, networks, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INetworks>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INetworks[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNetworksToCollectionIfMissing(networksCollection: INetworks[], ...networksToCheck: (INetworks | null | undefined)[]): INetworks[] {
    const networks: INetworks[] = networksToCheck.filter(isPresent);
    if (networks.length > 0) {
      const networksCollectionIdentifiers = networksCollection.map(networksItem => getNetworksIdentifier(networksItem)!);
      const networksToAdd = networks.filter(networksItem => {
        const networksIdentifier = getNetworksIdentifier(networksItem);
        if (networksIdentifier == null || networksCollectionIdentifiers.includes(networksIdentifier)) {
          return false;
        }
        networksCollectionIdentifiers.push(networksIdentifier);
        return true;
      });
      return [...networksToAdd, ...networksCollection];
    }
    return networksCollection;
  }
}
