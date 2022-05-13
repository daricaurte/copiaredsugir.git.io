import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { INetworks, Networks } from '../networks.model';
import { NetworksService } from '../service/networks.service';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

@Component({
  selector: 'redsurgir-networks-update',
  templateUrl: './networks-update.component.html',
})
export class NetworksUpdateComponent implements OnInit {
  isSaving = false;

  contactDataSharedCollection: IContactData[] = [];

  editForm = this.fb.group({
    id: [],
    networks3rdSectorHl: [null, [Validators.required]],
    academicNetworksHl: [null, [Validators.required]],
    customerNetworksHl: [null, [Validators.required]],
    employeeNetworksHl: [null, [Validators.required]],
    networksEntFinanHl: [null, [Validators.required]],
    stateNetworksHl: [null, [Validators.required]],
    internationalNetworksHl: [null, [Validators.required]],
    mediaNetworksComuncHl: [null, [Validators.required]],
    communityOrgNetworksHl: [null, [Validators.required]],
    regulatoryOrganismsNetworks: [null, [Validators.required]],
    networksProvidersHl: [null, [Validators.required]],
    socialNetworks: [null, [Validators.required]],
    shareholderNetworksHl: [null, [Validators.required]],
    contactData: [],
  });

  constructor(
    protected networksService: NetworksService,
    protected contactDataService: ContactDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ networks }) => {
      this.updateForm(networks);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const networks = this.createFromForm();
    if (networks.id !== undefined) {
      this.subscribeToSaveResponse(this.networksService.update(networks));
    } else {
      this.subscribeToSaveResponse(this.networksService.create(networks));
    }
  }

  trackContactDataById(_index: number, item: IContactData): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INetworks>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(networks: INetworks): void {
    this.editForm.patchValue({
      id: networks.id,
      networks3rdSectorHl: networks.networks3rdSectorHl,
      academicNetworksHl: networks.academicNetworksHl,
      customerNetworksHl: networks.customerNetworksHl,
      employeeNetworksHl: networks.employeeNetworksHl,
      networksEntFinanHl: networks.networksEntFinanHl,
      stateNetworksHl: networks.stateNetworksHl,
      internationalNetworksHl: networks.internationalNetworksHl,
      mediaNetworksComuncHl: networks.mediaNetworksComuncHl,
      communityOrgNetworksHl: networks.communityOrgNetworksHl,
      regulatoryOrganismsNetworks: networks.regulatoryOrganismsNetworks,
      networksProvidersHl: networks.networksProvidersHl,
      socialNetworks: networks.socialNetworks,
      shareholderNetworksHl: networks.shareholderNetworksHl,
      contactData: networks.contactData,
    });

    this.contactDataSharedCollection = this.contactDataService.addContactDataToCollectionIfMissing(
      this.contactDataSharedCollection,
      networks.contactData
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contactDataService
      .query()
      .pipe(map((res: HttpResponse<IContactData[]>) => res.body ?? []))
      .pipe(
        map((contactData: IContactData[]) =>
          this.contactDataService.addContactDataToCollectionIfMissing(contactData, this.editForm.get('contactData')!.value)
        )
      )
      .subscribe((contactData: IContactData[]) => (this.contactDataSharedCollection = contactData));
  }

  protected createFromForm(): INetworks {
    return {
      ...new Networks(),
      id: this.editForm.get(['id'])!.value,
      networks3rdSectorHl: this.editForm.get(['networks3rdSectorHl'])!.value,
      academicNetworksHl: this.editForm.get(['academicNetworksHl'])!.value,
      customerNetworksHl: this.editForm.get(['customerNetworksHl'])!.value,
      employeeNetworksHl: this.editForm.get(['employeeNetworksHl'])!.value,
      networksEntFinanHl: this.editForm.get(['networksEntFinanHl'])!.value,
      stateNetworksHl: this.editForm.get(['stateNetworksHl'])!.value,
      internationalNetworksHl: this.editForm.get(['internationalNetworksHl'])!.value,
      mediaNetworksComuncHl: this.editForm.get(['mediaNetworksComuncHl'])!.value,
      communityOrgNetworksHl: this.editForm.get(['communityOrgNetworksHl'])!.value,
      regulatoryOrganismsNetworks: this.editForm.get(['regulatoryOrganismsNetworks'])!.value,
      networksProvidersHl: this.editForm.get(['networksProvidersHl'])!.value,
      socialNetworks: this.editForm.get(['socialNetworks'])!.value,
      shareholderNetworksHl: this.editForm.get(['shareholderNetworksHl'])!.value,
      contactData: this.editForm.get(['contactData'])!.value,
    };
  }
}
