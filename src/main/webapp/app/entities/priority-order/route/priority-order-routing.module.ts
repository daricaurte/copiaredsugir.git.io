import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PriorityOrderComponent } from '../list/priority-order.component';
import { PriorityOrderDetailComponent } from '../detail/priority-order-detail.component';
import { PriorityOrderUpdateComponent } from '../update/priority-order-update.component';
import { PriorityOrderRoutingResolveService } from './priority-order-routing-resolve.service';

const priorityOrderRoute: Routes = [
  {
    path: '',
    component: PriorityOrderComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PriorityOrderDetailComponent,
    resolve: {
      priorityOrder: PriorityOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PriorityOrderUpdateComponent,
    resolve: {
      priorityOrder: PriorityOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PriorityOrderUpdateComponent,
    resolve: {
      priorityOrder: PriorityOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(priorityOrderRoute)],
  exports: [RouterModule],
})
export class PriorityOrderRoutingModule {}
