import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AffiliateComponent } from './list/affiliate.component';
import { AffiliateDetailComponent } from './detail/affiliate-detail.component';
import { AffiliateUpdateComponent } from './update/affiliate-update.component';
import { AffiliateDeleteDialogComponent } from './delete/affiliate-delete-dialog.component';
import { AffiliateRoutingModule } from './route/affiliate-routing.module';

@NgModule({
  imports: [SharedModule, AffiliateRoutingModule],
  declarations: [AffiliateComponent, AffiliateDetailComponent, AffiliateUpdateComponent, AffiliateDeleteDialogComponent],
  entryComponents: [AffiliateDeleteDialogComponent],
})
export class AffiliateModule {}
