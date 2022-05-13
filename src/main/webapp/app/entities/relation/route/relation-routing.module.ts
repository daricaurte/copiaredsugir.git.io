import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RelationComponent } from '../list/relation.component';
import { RelationDetailComponent } from '../detail/relation-detail.component';
import { RelationUpdateComponent } from '../update/relation-update.component';
import { RelationRoutingResolveService } from './relation-routing-resolve.service';

const relationRoute: Routes = [
  {
    path: '',
    component: RelationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RelationDetailComponent,
    resolve: {
      relation: RelationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RelationUpdateComponent,
    resolve: {
      relation: RelationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RelationUpdateComponent,
    resolve: {
      relation: RelationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(relationRoute)],
  exports: [RouterModule],
})
export class RelationRoutingModule {}
