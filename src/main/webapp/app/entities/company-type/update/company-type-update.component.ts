import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICompanyType, CompanyType } from '../company-type.model';
import { CompanyTypeService } from '../service/company-type.service';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

@Component({
  selector: 'redsurgir-company-type-update',
  templateUrl: './company-type-update.component.html',
})
export class CompanyTypeUpdateComponent implements OnInit {
  isSaving = false;

  contactDataSharedCollection: IContactData[] = [];

  editForm = this.fb.group({
    id: [],
    primarySector: [],
    secondarySector: [],
    tertiarySector: [],
    contactData: [],
  });

  constructor(
    protected companyTypeService: CompanyTypeService,
    protected contactDataService: ContactDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyType }) => {
      this.updateForm(companyType);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyType = this.createFromForm();
    if (companyType.id !== undefined) {
      this.subscribeToSaveResponse(this.companyTypeService.update(companyType));
    } else {
      this.subscribeToSaveResponse(this.companyTypeService.create(companyType));
    }
  }

  trackContactDataById(_index: number, item: IContactData): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyType>>): void {
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

  protected updateForm(companyType: ICompanyType): void {
    this.editForm.patchValue({
      id: companyType.id,
      primarySector: companyType.primarySector,
      secondarySector: companyType.secondarySector,
      tertiarySector: companyType.tertiarySector,
      contactData: companyType.contactData,
    });

    this.contactDataSharedCollection = this.contactDataService.addContactDataToCollectionIfMissing(
      this.contactDataSharedCollection,
      companyType.contactData
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

  protected createFromForm(): ICompanyType {
    return {
      ...new CompanyType(),
      id: this.editForm.get(['id'])!.value,
      primarySector: this.editForm.get(['primarySector'])!.value,
      secondarySector: this.editForm.get(['secondarySector'])!.value,
      tertiarySector: this.editForm.get(['tertiarySector'])!.value,
      contactData: this.editForm.get(['contactData'])!.value,
    };
  }
}
