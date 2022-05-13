import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPotential, Potential } from '../potential.model';
import { PotentialService } from '../service/potential.service';

@Injectable({ providedIn: 'root' })
export class PotentialRoutingResolveService implements Resolve<IPotential> {
  constructor(protected service: PotentialService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPotential> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((potential: HttpResponse<Potential>) => {
          if (potential.body) {
            return of(potential.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Potential());
  }
}
