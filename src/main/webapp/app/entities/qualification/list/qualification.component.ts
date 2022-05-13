import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQualification } from '../qualification.model';
import { QualificationService } from '../service/qualification.service';
import { QualificationDeleteDialogComponent } from '../delete/qualification-delete-dialog.component';

@Component({
  selector: 'redsurgir-qualification',
  templateUrl: './qualification.component.html',
})
export class QualificationComponent implements OnInit {
  qualifications?: IQualification[];
  isLoading = false;

  constructor(protected qualificationService: QualificationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.qualificationService.query().subscribe({
      next: (res: HttpResponse<IQualification[]>) => {
        this.isLoading = false;
        this.qualifications = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IQualification): number {
    return item.id!;
  }

  delete(qualification: IQualification): void {
    const modalRef = this.modalService.open(QualificationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.qualification = qualification;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
