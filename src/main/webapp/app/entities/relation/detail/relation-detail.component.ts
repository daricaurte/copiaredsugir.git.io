import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelation } from '../relation.model';

@Component({
  selector: 'redsurgir-relation-detail',
  templateUrl: './relation-detail.component.html',
})
export class RelationDetailComponent implements OnInit {
  relation: IRelation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relation }) => {
      this.relation = relation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
