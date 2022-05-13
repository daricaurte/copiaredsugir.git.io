import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PotentialComponent } from '../list/potential.component';
import { PotentialDetailComponent } from '../detail/potential-detail.component';
import { PotentialUpdateComponent } from '../update/potential-update.component';
import { PotentialRoutingResolveService } from './potential-routing-resolve.service';

const potentialRoute: Routes = [
  {
    path: '',
    component: PotentialComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PotentialDetailComponent,
    resolve: {
      potential: PotentialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PotentialUpdateComponent,
    resolve: {
      potential: PotentialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PotentialUpdateComponent,
    resolve: {
      potential: PotentialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(potentialRoute)],
  exports: [RouterModule],
})
export class PotentialRoutingModule {}
