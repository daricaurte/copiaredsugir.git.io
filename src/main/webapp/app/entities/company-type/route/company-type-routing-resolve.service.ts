import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompanyType, CompanyType } from '../company-type.model';
import { CompanyTypeService } from '../service/company-type.service';

@Injectable({ providedIn: 'root' })
export class CompanyTypeRoutingResolveService implements Resolve<ICompanyType> {
  constructor(protected service: CompanyTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((companyType: HttpResponse<CompanyType>) => {
          if (companyType.body) {
            return of(companyType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompanyType());
  }
}
