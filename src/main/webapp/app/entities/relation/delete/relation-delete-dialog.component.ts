import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRelation } from '../relation.model';
import { RelationService } from '../service/relation.service';

@Component({
  templateUrl: './relation-delete-dialog.component.html',
})
export class RelationDeleteDialogComponent {
  relation?: IRelation;

  constructor(protected relationService: RelationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.relationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
