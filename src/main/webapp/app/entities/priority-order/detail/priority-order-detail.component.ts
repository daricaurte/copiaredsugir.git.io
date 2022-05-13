import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriorityOrder } from '../priority-order.model';

@Component({
  selector: 'redsurgir-priority-order-detail',
  templateUrl: './priority-order-detail.component.html',
})
export class PriorityOrderDetailComponent implements OnInit {
  priorityOrder: IPriorityOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priorityOrder }) => {
      this.priorityOrder = priorityOrder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
