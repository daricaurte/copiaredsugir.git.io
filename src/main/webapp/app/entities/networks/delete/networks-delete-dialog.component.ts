import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INetworks } from '../networks.model';
import { NetworksService } from '../service/networks.service';

@Component({
  templateUrl: './networks-delete-dialog.component.html',
})
export class NetworksDeleteDialogComponent {
  networks?: INetworks;

  constructor(protected networksService: NetworksService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.networksService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
