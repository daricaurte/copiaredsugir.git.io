import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactData } from '../contact-data.model';
import { ContactDataService } from '../service/contact-data.service';

@Component({
  templateUrl: './contact-data-delete-dialog.component.html',
})
export class ContactDataDeleteDialogComponent {
  contactData?: IContactData;

  constructor(protected contactDataService: ContactDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactDataService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
