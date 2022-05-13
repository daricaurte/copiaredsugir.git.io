import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PotentialComponent } from './list/potential.component';
import { PotentialDetailComponent } from './detail/potential-detail.component';
import { PotentialUpdateComponent } from './update/potential-update.component';
import { PotentialDeleteDialogComponent } from './delete/potential-delete-dialog.component';
import { PotentialRoutingModule } from './route/potential-routing.module';

@NgModule({
  imports: [SharedModule, PotentialRoutingModule],
  declarations: [PotentialComponent, PotentialDetailComponent, PotentialUpdateComponent, PotentialDeleteDialogComponent],
  entryComponents: [PotentialDeleteDialogComponent],
})
export class PotentialModule {}
