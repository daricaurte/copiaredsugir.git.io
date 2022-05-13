import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NetworksComponent } from '../list/networks.component';
import { NetworksDetailComponent } from '../detail/networks-detail.component';
import { NetworksUpdateComponent } from '../update/networks-update.component';
import { NetworksRoutingResolveService } from './networks-routing-resolve.service';

const networksRoute: Routes = [
  {
    path: '',
    component: NetworksComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NetworksDetailComponent,
    resolve: {
      networks: NetworksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NetworksUpdateComponent,
    resolve: {
      networks: NetworksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NetworksUpdateComponent,
    resolve: {
      networks: NetworksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(networksRoute)],
  exports: [RouterModule],
})
export class NetworksRoutingModule {}
