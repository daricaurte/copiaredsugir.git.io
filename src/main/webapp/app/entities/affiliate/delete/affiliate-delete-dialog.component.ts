import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAffiliate } from '../affiliate.model';
import { AffiliateService } from '../service/affiliate.service';

@Component({
  templateUrl: './affiliate-delete-dialog.component.html',
})
export class AffiliateDeleteDialogComponent {
  affiliate?: IAffiliate;

  constructor(protected affiliateService: AffiliateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.affiliateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
