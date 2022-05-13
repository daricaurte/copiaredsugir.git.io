import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INetworks } from '../networks.model';
import { NetworksService } from '../service/networks.service';
import { NetworksDeleteDialogComponent } from '../delete/networks-delete-dialog.component';

@Component({
  selector: 'redsurgir-networks',
  templateUrl: './networks.component.html',
})
export class NetworksComponent implements OnInit {
  networks?: INetworks[];
  isLoading = false;

  constructor(protected networksService: NetworksService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.networksService.query().subscribe({
      next: (res: HttpResponse<INetworks[]>) => {
        this.isLoading = false;
        this.networks = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: INetworks): number {
    return item.id!;
  }

  delete(networks: INetworks): void {
    const modalRef = this.modalService.open(NetworksDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.networks = networks;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
