import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAffiliate, Affiliate } from '../affiliate.model';
import { AffiliateService } from '../service/affiliate.service';

@Injectable({ providedIn: 'root' })
export class AffiliateRoutingResolveService implements Resolve<IAffiliate> {
  constructor(protected service: AffiliateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAffiliate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((affiliate: HttpResponse<Affiliate>) => {
          if (affiliate.body) {
            return of(affiliate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Affiliate());
  }
}
