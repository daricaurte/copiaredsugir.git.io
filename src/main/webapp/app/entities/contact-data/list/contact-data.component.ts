import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactData } from '../contact-data.model';
import { ContactDataService } from '../service/contact-data.service';
import { ContactDataDeleteDialogComponent } from '../delete/contact-data-delete-dialog.component';

@Component({
  selector: 'redsurgir-contact-data',
  templateUrl: './contact-data.component.html',
})
export class ContactDataComponent implements OnInit {
  contactData?: IContactData[];
  isLoading = false;

  constructor(protected contactDataService: ContactDataService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.contactDataService.query().subscribe({
      next: (res: HttpResponse<IContactData[]>) => {
        this.isLoading = false;
        this.contactData = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IContactData): number {
    return item.id!;
  }

  delete(contactData: IContactData): void {
    const modalRef = this.modalService.open(ContactDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactData = contactData;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
