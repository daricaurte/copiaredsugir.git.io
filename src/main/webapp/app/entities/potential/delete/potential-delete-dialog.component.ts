import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPotential } from '../potential.model';
import { PotentialService } from '../service/potential.service';

@Component({
  templateUrl: './potential-delete-dialog.component.html',
})
export class PotentialDeleteDialogComponent {
  potential?: IPotential;

  constructor(protected potentialService: PotentialService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.potentialService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
