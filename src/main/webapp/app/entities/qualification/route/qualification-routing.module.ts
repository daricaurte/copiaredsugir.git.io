import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QualificationComponent } from '../list/qualification.component';
import { QualificationDetailComponent } from '../detail/qualification-detail.component';
import { QualificationUpdateComponent } from '../update/qualification-update.component';
import { QualificationRoutingResolveService } from './qualification-routing-resolve.service';

const qualificationRoute: Routes = [
  {
    path: '',
    component: QualificationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QualificationDetailComponent,
    resolve: {
      qualification: QualificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QualificationUpdateComponent,
    resolve: {
      qualification: QualificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QualificationUpdateComponent,
    resolve: {
      qualification: QualificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qualificationRoute)],
  exports: [RouterModule],
})
export class QualificationRoutingModule {}
