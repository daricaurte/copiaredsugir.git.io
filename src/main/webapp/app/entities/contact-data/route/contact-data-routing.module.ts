import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContactDataComponent } from '../list/contact-data.component';
import { ContactDataDetailComponent } from '../detail/contact-data-detail.component';
import { ContactDataUpdateComponent } from '../update/contact-data-update.component';
import { ContactDataRoutingResolveService } from './contact-data-routing-resolve.service';

const contactDataRoute: Routes = [
  {
    path: '',
    component: ContactDataComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactDataDetailComponent,
    resolve: {
      contactData: ContactDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactDataUpdateComponent,
    resolve: {
      contactData: ContactDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactDataUpdateComponent,
    resolve: {
      contactData: ContactDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contactDataRoute)],
  exports: [RouterModule],
})
export class ContactDataRoutingModule {}
