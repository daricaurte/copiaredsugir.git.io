import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPriorityOrder, getPriorityOrderIdentifier } from '../priority-order.model';

export type EntityResponseType = HttpResponse<IPriorityOrder>;
export type EntityArrayResponseType = HttpResponse<IPriorityOrder[]>;

@Injectable({ providedIn: 'root' })
export class PriorityOrderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/priority-orders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(priorityOrder: IPriorityOrder): Observable<EntityResponseType> {
    return this.http.post<IPriorityOrder>(this.resourceUrl, priorityOrder, { observe: 'response' });
  }

  update(priorityOrder: IPriorityOrder): Observable<EntityResponseType> {
    return this.http.put<IPriorityOrder>(`${this.resourceUrl}/${getPriorityOrderIdentifier(priorityOrder) as number}`, priorityOrder, {
      observe: 'response',
    });
  }

  partialUpdate(priorityOrder: IPriorityOrder): Observable<EntityResponseType> {
    return this.http.patch<IPriorityOrder>(`${this.resourceUrl}/${getPriorityOrderIdentifier(priorityOrder) as number}`, priorityOrder, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPriorityOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPriorityOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPriorityOrderToCollectionIfMissing(
    priorityOrderCollection: IPriorityOrder[],
    ...priorityOrdersToCheck: (IPriorityOrder | null | undefined)[]
  ): IPriorityOrder[] {
    const priorityOrders: IPriorityOrder[] = priorityOrdersToCheck.filter(isPresent);
    if (priorityOrders.length > 0) {
      const priorityOrderCollectionIdentifiers = priorityOrderCollection.map(
        priorityOrderItem => getPriorityOrderIdentifier(priorityOrderItem)!
      );
      const priorityOrdersToAdd = priorityOrders.filter(priorityOrderItem => {
        const priorityOrderIdentifier = getPriorityOrderIdentifier(priorityOrderItem);
        if (priorityOrderIdentifier == null || priorityOrderCollectionIdentifiers.includes(priorityOrderIdentifier)) {
          return false;
        }
        priorityOrderCollectionIdentifiers.push(priorityOrderIdentifier);
        return true;
      });
      return [...priorityOrdersToAdd, ...priorityOrderCollection];
    }
    return priorityOrderCollection;
  }
}
