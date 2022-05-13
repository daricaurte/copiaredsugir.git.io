import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQualification, Qualification } from '../qualification.model';
import { QualificationService } from '../service/qualification.service';

@Injectable({ providedIn: 'root' })
export class QualificationRoutingResolveService implements Resolve<IQualification> {
  constructor(protected service: QualificationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQualification> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qualification: HttpResponse<Qualification>) => {
          if (qualification.body) {
            return of(qualification.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Qualification());
  }
}
