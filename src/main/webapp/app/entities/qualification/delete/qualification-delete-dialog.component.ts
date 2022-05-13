import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQualification } from '../qualification.model';
import { QualificationService } from '../service/qualification.service';

@Component({
  templateUrl: './qualification-delete-dialog.component.html',
})
export class QualificationDeleteDialogComponent {
  qualification?: IQualification;

  constructor(protected qualificationService: QualificationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qualificationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
