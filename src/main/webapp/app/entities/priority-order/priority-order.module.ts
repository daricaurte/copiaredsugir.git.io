import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PriorityOrderComponent } from './list/priority-order.component';
import { PriorityOrderDetailComponent } from './detail/priority-order-detail.component';
import { PriorityOrderUpdateComponent } from './update/priority-order-update.component';
import { PriorityOrderDeleteDialogComponent } from './delete/priority-order-delete-dialog.component';
import { PriorityOrderRoutingModule } from './route/priority-order-routing.module';

@NgModule({
  imports: [SharedModule, PriorityOrderRoutingModule],
  declarations: [PriorityOrderComponent, PriorityOrderDetailComponent, PriorityOrderUpdateComponent, PriorityOrderDeleteDialogComponent],
  entryComponents: [PriorityOrderDeleteDialogComponent],
})
export class PriorityOrderModule {}
