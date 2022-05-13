import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPriorityOrder } from '../priority-order.model';
import { PriorityOrderService } from '../service/priority-order.service';

@Component({
  templateUrl: './priority-order-delete-dialog.component.html',
})
export class PriorityOrderDeleteDialogComponent {
  priorityOrder?: IPriorityOrder;

  constructor(protected priorityOrderService: PriorityOrderService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.priorityOrderService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
