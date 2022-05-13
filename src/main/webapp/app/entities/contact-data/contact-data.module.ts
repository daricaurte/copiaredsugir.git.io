import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContactDataComponent } from './list/contact-data.component';
import { ContactDataDetailComponent } from './detail/contact-data-detail.component';
import { ContactDataUpdateComponent } from './update/contact-data-update.component';
import { ContactDataDeleteDialogComponent } from './delete/contact-data-delete-dialog.component';
import { ContactDataRoutingModule } from './route/contact-data-routing.module';

@NgModule({
  imports: [SharedModule, ContactDataRoutingModule],
  declarations: [ContactDataComponent, ContactDataDetailComponent, ContactDataUpdateComponent, ContactDataDeleteDialogComponent],
  entryComponents: [ContactDataDeleteDialogComponent],
})
export class ContactDataModule {}
