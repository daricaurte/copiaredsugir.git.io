import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRelation } from '../relation.model';
import { RelationService } from '../service/relation.service';
import { RelationDeleteDialogComponent } from '../delete/relation-delete-dialog.component';

@Component({
  selector: 'redsurgir-relation',
  templateUrl: './relation.component.html',
})
export class RelationComponent implements OnInit {
  relations?: IRelation[];
  isLoading = false;

  constructor(protected relationService: RelationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.relationService.query().subscribe({
      next: (res: HttpResponse<IRelation[]>) => {
        this.isLoading = false;
        this.relations = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IRelation): number {
    return item.id!;
  }

  delete(relation: IRelation): void {
    const modalRef = this.modalService.open(RelationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.relation = relation;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
