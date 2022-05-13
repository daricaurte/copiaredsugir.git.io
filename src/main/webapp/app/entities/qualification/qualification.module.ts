import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QualificationComponent } from './list/qualification.component';
import { QualificationDetailComponent } from './detail/qualification-detail.component';
import { QualificationUpdateComponent } from './update/qualification-update.component';
import { QualificationDeleteDialogComponent } from './delete/qualification-delete-dialog.component';
import { QualificationRoutingModule } from './route/qualification-routing.module';

@NgModule({
  imports: [SharedModule, QualificationRoutingModule],
  declarations: [QualificationComponent, QualificationDetailComponent, QualificationUpdateComponent, QualificationDeleteDialogComponent],
  entryComponents: [QualificationDeleteDialogComponent],
})
export class QualificationModule {}
