import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPotential } from '../potential.model';
import { PotentialService } from '../service/potential.service';
import { PotentialDeleteDialogComponent } from '../delete/potential-delete-dialog.component';

@Component({
  selector: 'redsurgir-potential',
  templateUrl: './potential.component.html',
})
export class PotentialComponent implements OnInit {
  potentials?: IPotential[];
  isLoading = false;

  constructor(protected potentialService: PotentialService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.potentialService.query().subscribe({
      next: (res: HttpResponse<IPotential[]>) => {
        this.isLoading = false;
        this.potentials = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPotential): number {
    return item.id!;
  }

  delete(potential: IPotential): void {
    const modalRef = this.modalService.open(PotentialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.potential = potential;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
