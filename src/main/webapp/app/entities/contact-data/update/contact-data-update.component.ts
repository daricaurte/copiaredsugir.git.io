import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IContactData, ContactData } from '../contact-data.model';
import { ContactDataService } from '../service/contact-data.service';
import { IAffiliate } from 'app/entities/affiliate/affiliate.model';
import { AffiliateService } from 'app/entities/affiliate/service/affiliate.service';

@Component({
  selector: 'redsurgir-contact-data-update',
  templateUrl: './contact-data-update.component.html',
})
export class ContactDataUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliate[] = [];

  editForm = this.fb.group({
    id: [],
    documentNumberContact: [null, [Validators.required, Validators.maxLength(50)]],
    cellPhoneNumber: [null, [Validators.required, Validators.maxLength(11)]],
    country: [null, [Validators.required, Validators.maxLength(50)]],
    city: [null, [Validators.required, Validators.maxLength(50)]],
    email: [null, [Validators.required, Validators.maxLength(50)]],
    department: [null, [Validators.required, Validators.maxLength(50)]],
    firstName: [null, [Validators.required, Validators.maxLength(50)]],
    secondName: [null, [Validators.maxLength(50)]],
    firstLastName: [null, [Validators.required, Validators.maxLength(50)]],
    secondLastName: [null, [Validators.maxLength(50)]],
    neighborhood: [null, [Validators.required, Validators.maxLength(50)]],
    affiliate: [],
  });

  constructor(
    protected contactDataService: ContactDataService,
    protected affiliateService: AffiliateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactData }) => {
      this.updateForm(contactData);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactData = this.createFromForm();
    if (contactData.id !== undefined) {
      this.subscribeToSaveResponse(this.contactDataService.update(contactData));
    } else {
      this.subscribeToSaveResponse(this.contactDataService.create(contactData));
    }
  }

  trackAffiliateById(_index: number, item: IAffiliate): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactData>>): void {
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

  protected updateForm(contactData: IContactData): void {
    this.editForm.patchValue({
      id: contactData.id,
      documentNumberContact: contactData.documentNumberContact,
      cellPhoneNumber: contactData.cellPhoneNumber,
      country: contactData.country,
      city: contactData.city,
      email: contactData.email,
      department: contactData.department,
      firstName: contactData.firstName,
      secondName: contactData.secondName,
      firstLastName: contactData.firstLastName,
      secondLastName: contactData.secondLastName,
      neighborhood: contactData.neighborhood,
      affiliate: contactData.affiliate,
    });

    this.affiliatesSharedCollection = this.affiliateService.addAffiliateToCollectionIfMissing(
      this.affiliatesSharedCollection,
      contactData.affiliate
    );
  }

  protected loadRelationshipsOptions(): void {
    this.affiliateService
      .query()
      .pipe(map((res: HttpResponse<IAffiliate[]>) => res.body ?? []))
      .pipe(
        map((affiliates: IAffiliate[]) =>
          this.affiliateService.addAffiliateToCollectionIfMissing(affiliates, this.editForm.get('affiliate')!.value)
        )
      )
      .subscribe((affiliates: IAffiliate[]) => (this.affiliatesSharedCollection = affiliates));
  }

  protected createFromForm(): IContactData {
    return {
      ...new ContactData(),
      id: this.editForm.get(['id'])!.value,
      documentNumberContact: this.editForm.get(['documentNumberContact'])!.value,
      cellPhoneNumber: this.editForm.get(['cellPhoneNumber'])!.value,
      country: this.editForm.get(['country'])!.value,
      city: this.editForm.get(['city'])!.value,
      email: this.editForm.get(['email'])!.value,
      department: this.editForm.get(['department'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      secondName: this.editForm.get(['secondName'])!.value,
      firstLastName: this.editForm.get(['firstLastName'])!.value,
      secondLastName: this.editForm.get(['secondLastName'])!.value,
      neighborhood: this.editForm.get(['neighborhood'])!.value,
      affiliate: this.editForm.get(['affiliate'])!.value,
    };
  }
}
