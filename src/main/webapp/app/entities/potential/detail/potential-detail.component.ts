import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPotential } from '../potential.model';

@Component({
  selector: 'redsurgir-potential-detail',
  templateUrl: './potential-detail.component.html',
})
export class PotentialDetailComponent implements OnInit {
  potential: IPotential | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ potential }) => {
      this.potential = potential;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
