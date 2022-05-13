import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyType } from '../company-type.model';

@Component({
  selector: 'redsurgir-company-type-detail',
  templateUrl: './company-type-detail.component.html',
})
export class CompanyTypeDetailComponent implements OnInit {
  companyType: ICompanyType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyType }) => {
      this.companyType = companyType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
