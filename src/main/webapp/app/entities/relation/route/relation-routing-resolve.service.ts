import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRelation, Relation } from '../relation.model';
import { RelationService } from '../service/relation.service';

@Injectable({ providedIn: 'root' })
export class RelationRoutingResolveService implements Resolve<IRelation> {
  constructor(protected service: RelationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRelation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((relation: HttpResponse<Relation>) => {
          if (relation.body) {
            return of(relation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Relation());
  }
}
