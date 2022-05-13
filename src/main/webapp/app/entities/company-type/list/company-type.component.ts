import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanyType } from '../company-type.model';
import { CompanyTypeService } from '../service/company-type.service';
import { CompanyTypeDeleteDialogComponent } from '../delete/company-type-delete-dialog.component';

@Component({
  selector: 'redsurgir-company-type',
  templateUrl: './company-type.component.html',
})
export class CompanyTypeComponent implements OnInit {
  companyTypes?: ICompanyType[];
  isLoading = false;

  constructor(protected companyTypeService: CompanyTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.companyTypeService.query().subscribe({
      next: (res: HttpResponse<ICompanyType[]>) => {
        this.isLoading = false;
        this.companyTypes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ICompanyType): number {
    return item.id!;
  }

  delete(companyType: ICompanyType): void {
    const modalRef = this.modalService.open(CompanyTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.companyType = companyType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
