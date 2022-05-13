import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAffiliate } from '../affiliate.model';

@Component({
  selector: 'redsurgir-affiliate-detail',
  templateUrl: './affiliate-detail.component.html',
})
export class AffiliateDetailComponent implements OnInit {
  affiliate: IAffiliate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affiliate }) => {
      this.affiliate = affiliate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
