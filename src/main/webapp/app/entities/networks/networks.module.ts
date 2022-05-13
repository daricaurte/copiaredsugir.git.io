import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NetworksComponent } from './list/networks.component';
import { NetworksDetailComponent } from './detail/networks-detail.component';
import { NetworksUpdateComponent } from './update/networks-update.component';
import { NetworksDeleteDialogComponent } from './delete/networks-delete-dialog.component';
import { NetworksRoutingModule } from './route/networks-routing.module';

@NgModule({
  imports: [SharedModule, NetworksRoutingModule],
  declarations: [NetworksComponent, NetworksDetailComponent, NetworksUpdateComponent, NetworksDeleteDialogComponent],
  entryComponents: [NetworksDeleteDialogComponent],
})
export class NetworksModule {}
