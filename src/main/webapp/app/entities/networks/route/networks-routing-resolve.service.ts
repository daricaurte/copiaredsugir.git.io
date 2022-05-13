import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INetworks, Networks } from '../networks.model';
import { NetworksService } from '../service/networks.service';

@Injectable({ providedIn: 'root' })
export class NetworksRoutingResolveService implements Resolve<INetworks> {
  constructor(protected service: NetworksService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INetworks> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((networks: HttpResponse<Networks>) => {
          if (networks.body) {
            return of(networks.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Networks());
  }
}
