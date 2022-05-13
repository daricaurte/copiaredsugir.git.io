import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompanyTypeComponent } from '../list/company-type.component';
import { CompanyTypeDetailComponent } from '../detail/company-type-detail.component';
import { CompanyTypeUpdateComponent } from '../update/company-type-update.component';
import { CompanyTypeRoutingResolveService } from './company-type-routing-resolve.service';

const companyTypeRoute: Routes = [
  {
    path: '',
    component: CompanyTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyTypeDetailComponent,
    resolve: {
      companyType: CompanyTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyTypeUpdateComponent,
    resolve: {
      companyType: CompanyTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyTypeUpdateComponent,
    resolve: {
      companyType: CompanyTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(companyTypeRoute)],
  exports: [RouterModule],
})
export class CompanyTypeRoutingModule {}
