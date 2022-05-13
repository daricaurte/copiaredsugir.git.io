import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RelationComponent } from './list/relation.component';
import { RelationDetailComponent } from './detail/relation-detail.component';
import { RelationUpdateComponent } from './update/relation-update.component';
import { RelationDeleteDialogComponent } from './delete/relation-delete-dialog.component';
import { RelationRoutingModule } from './route/relation-routing.module';

@NgModule({
  imports: [SharedModule, RelationRoutingModule],
  declarations: [RelationComponent, RelationDetailComponent, RelationUpdateComponent, RelationDeleteDialogComponent],
  entryComponents: [RelationDeleteDialogComponent],
})
export class RelationModule {}
