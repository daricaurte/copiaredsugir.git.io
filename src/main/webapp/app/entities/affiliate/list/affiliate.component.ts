import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAffiliate } from '../affiliate.model';
import { AffiliateService } from '../service/affiliate.service';
import { AffiliateDeleteDialogComponent } from '../delete/affiliate-delete-dialog.component';

@Component({
  selector: 'redsurgir-affiliate',
  templateUrl: './affiliate.component.html',
})
export class AffiliateComponent implements OnInit {
  affiliates?: IAffiliate[];
  isLoading = false;

  constructor(protected affiliateService: AffiliateService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.affiliateService.query().subscribe({
      next: (res: HttpResponse<IAffiliate[]>) => {
        this.isLoading = false;
        this.affiliates = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IAffiliate): number {
    return item.id!;
  }

  delete(affiliate: IAffiliate): void {
    const modalRef = this.modalService.open(AffiliateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.affiliate = affiliate;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
