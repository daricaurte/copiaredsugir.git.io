import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INetworks } from '../networks.model';

@Component({
  selector: 'redsurgir-networks-detail',
  templateUrl: './networks-detail.component.html',
})
export class NetworksDetailComponent implements OnInit {
  networks: INetworks | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ networks }) => {
      this.networks = networks;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
