import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompanyTypeComponent } from './list/company-type.component';
import { CompanyTypeDetailComponent } from './detail/company-type-detail.component';
import { CompanyTypeUpdateComponent } from './update/company-type-update.component';
import { CompanyTypeDeleteDialogComponent } from './delete/company-type-delete-dialog.component';
import { CompanyTypeRoutingModule } from './route/company-type-routing.module';

@NgModule({
  imports: [SharedModule, CompanyTypeRoutingModule],
  declarations: [CompanyTypeComponent, CompanyTypeDetailComponent, CompanyTypeUpdateComponent, CompanyTypeDeleteDialogComponent],
  entryComponents: [CompanyTypeDeleteDialogComponent],
})
export class CompanyTypeModule {}
