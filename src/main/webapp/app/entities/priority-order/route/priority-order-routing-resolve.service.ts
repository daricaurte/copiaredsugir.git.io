import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPriorityOrder, PriorityOrder } from '../priority-order.model';
import { PriorityOrderService } from '../service/priority-order.service';

@Injectable({ providedIn: 'root' })
export class PriorityOrderRoutingResolveService implements Resolve<IPriorityOrder> {
  constructor(protected service: PriorityOrderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPriorityOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((priorityOrder: HttpResponse<PriorityOrder>) => {
          if (priorityOrder.body) {
            return of(priorityOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PriorityOrder());
  }
}
