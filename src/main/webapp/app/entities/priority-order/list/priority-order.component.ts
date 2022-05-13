import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPriorityOrder } from '../priority-order.model';
import { PriorityOrderService } from '../service/priority-order.service';
import { PriorityOrderDeleteDialogComponent } from '../delete/priority-order-delete-dialog.component';

@Component({
  selector: 'redsurgir-priority-order',
  templateUrl: './priority-order.component.html',
})
export class PriorityOrderComponent implements OnInit {
  priorityOrders?: IPriorityOrder[];
  isLoading = false;

  constructor(protected priorityOrderService: PriorityOrderService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.priorityOrderService.query().subscribe({
      next: (res: HttpResponse<IPriorityOrder[]>) => {
        this.isLoading = false;
        this.priorityOrders = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPriorityOrder): number {
    return item.id!;
  }

  delete(priorityOrder: IPriorityOrder): void {
    const modalRef = this.modalService.open(PriorityOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.priorityOrder = priorityOrder;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
