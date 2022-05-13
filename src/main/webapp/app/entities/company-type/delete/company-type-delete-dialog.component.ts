import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanyType } from '../company-type.model';
import { CompanyTypeService } from '../service/company-type.service';

@Component({
  templateUrl: './company-type-delete-dialog.component.html',
})
export class CompanyTypeDeleteDialogComponent {
  companyType?: ICompanyType;

  constructor(protected companyTypeService: CompanyTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
