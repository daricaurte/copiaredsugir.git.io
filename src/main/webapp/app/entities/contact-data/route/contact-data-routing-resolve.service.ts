import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContactData, ContactData } from '../contact-data.model';
import { ContactDataService } from '../service/contact-data.service';

@Injectable({ providedIn: 'root' })
export class ContactDataRoutingResolveService implements Resolve<IContactData> {
  constructor(protected service: ContactDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contactData: HttpResponse<ContactData>) => {
          if (contactData.body) {
            return of(contactData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactData());
  }
}
