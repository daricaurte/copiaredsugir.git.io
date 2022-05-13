import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IContact, Contact } from '../contact.model';
import { ContactService } from '../service/contact.service';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

@Component({
  selector: 'redsurgir-contact-update',
  templateUrl: './contact-update.component.html',
})
export class ContactUpdateComponent implements OnInit {
  isSaving = false;

  contactDataSharedCollection: IContactData[] = [];

  editForm = this.fb.group({
    id: [],
    contact3rdSectorHl: [null, [Validators.required]],
    contactAcademyHl: [null, [Validators.required]],
    contactTeethHl: [null, [Validators.required]],
    contactEmployeeHl: [null, [Validators.required]],
    contactEntFinanHl: [null, [Validators.required]],
    contactStatusHl: [null, [Validators.required]],
    internationalContactHl: [null, [Validators.required]],
    contactMediaComuncHl: [null, [Validators.required]],
    contactOrgCommunityHl: [null, [Validators.required]],
    contactRegulatoryOrganismsHl: [null, [Validators.required]],
    contactProveedoresHl: [null, [Validators.required]],
    contactSocialNetworks: [null, [Validators.required]],
    contactShareholdersHl: [null, [Validators.required]],
    contactData: [],
  });

  constructor(
    protected contactService: ContactService,
    protected contactDataService: ContactDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      this.updateForm(contact);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contact = this.createFromForm();
    if (contact.id !== undefined) {
      this.subscribeToSaveResponse(this.contactService.update(contact));
    } else {
      this.subscribeToSaveResponse(this.contactService.create(contact));
    }
  }

  trackContactDataById(_index: number, item: IContactData): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>): void {
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

  protected updateForm(contact: IContact): void {
    this.editForm.patchValue({
      id: contact.id,
      contact3rdSectorHl: contact.contact3rdSectorHl,
      contactAcademyHl: contact.contactAcademyHl,
      contactTeethHl: contact.contactTeethHl,
      contactEmployeeHl: contact.contactEmployeeHl,
      contactEntFinanHl: contact.contactEntFinanHl,
      contactStatusHl: contact.contactStatusHl,
      internationalContactHl: contact.internationalContactHl,
      contactMediaComuncHl: contact.contactMediaComuncHl,
      contactOrgCommunityHl: contact.contactOrgCommunityHl,
      contactRegulatoryOrganismsHl: contact.contactRegulatoryOrganismsHl,
      contactProveedoresHl: contact.contactProveedoresHl,
      contactSocialNetworks: contact.contactSocialNetworks,
      contactShareholdersHl: contact.contactShareholdersHl,
      contactData: contact.contactData,
    });

    this.contactDataSharedCollection = this.contactDataService.addContactDataToCollectionIfMissing(
      this.contactDataSharedCollection,
      contact.contactData
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

  protected createFromForm(): IContact {
    return {
      ...new Contact(),
      id: this.editForm.get(['id'])!.value,
      contact3rdSectorHl: this.editForm.get(['contact3rdSectorHl'])!.value,
      contactAcademyHl: this.editForm.get(['contactAcademyHl'])!.value,
      contactTeethHl: this.editForm.get(['contactTeethHl'])!.value,
      contactEmployeeHl: this.editForm.get(['contactEmployeeHl'])!.value,
      contactEntFinanHl: this.editForm.get(['contactEntFinanHl'])!.value,
      contactStatusHl: this.editForm.get(['contactStatusHl'])!.value,
      internationalContactHl: this.editForm.get(['internationalContactHl'])!.value,
      contactMediaComuncHl: this.editForm.get(['contactMediaComuncHl'])!.value,
      contactOrgCommunityHl: this.editForm.get(['contactOrgCommunityHl'])!.value,
      contactRegulatoryOrganismsHl: this.editForm.get(['contactRegulatoryOrganismsHl'])!.value,
      contactProveedoresHl: this.editForm.get(['contactProveedoresHl'])!.value,
      contactSocialNetworks: this.editForm.get(['contactSocialNetworks'])!.value,
      contactShareholdersHl: this.editForm.get(['contactShareholdersHl'])!.value,
      contactData: this.editForm.get(['contactData'])!.value,
    };
  }
}
