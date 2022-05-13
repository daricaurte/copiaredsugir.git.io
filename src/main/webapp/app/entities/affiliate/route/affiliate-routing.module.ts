import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AffiliateComponent } from '../list/affiliate.component';
import { AffiliateDetailComponent } from '../detail/affiliate-detail.component';
import { AffiliateUpdateComponent } from '../update/affiliate-update.component';
import { AffiliateRoutingResolveService } from './affiliate-routing-resolve.service';

const affiliateRoute: Routes = [
  {
    path: '',
    component: AffiliateComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AffiliateDetailComponent,
    resolve: {
      affiliate: AffiliateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AffiliateUpdateComponent,
    resolve: {
      affiliate: AffiliateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AffiliateUpdateComponent,
    resolve: {
      affiliate: AffiliateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(affiliateRoute)],
  exports: [RouterModule],
})
export class AffiliateRoutingModule {}
