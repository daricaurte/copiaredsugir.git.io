import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IQualification, Qualification } from '../qualification.model';
import { QualificationService } from '../service/qualification.service';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';
import { Designation } from 'app/entities/enumerations/designation.model';

@Component({
  selector: 'redsurgir-qualification-update',
  templateUrl: './qualification-update.component.html',
})
export class QualificationUpdateComponent implements OnInit {
  isSaving = false;
  designationValues = Object.keys(Designation);

  contactDataSharedCollection: IContactData[] = [];

  editForm = this.fb.group({
    id: [],
    qualif1: [null, [Validators.required]],
    qualif2: [null, [Validators.required]],
    qualif3: [null, [Validators.required]],
    qualif4: [null, [Validators.required]],
    qualif1BussinessViability: [null, [Validators.required]],
    contactData: [],
  });

  constructor(
    protected qualificationService: QualificationService,
    protected contactDataService: ContactDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qualification }) => {
      this.updateForm(qualification);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qualification = this.createFromForm();
    if (qualification.id !== undefined) {
      this.subscribeToSaveResponse(this.qualificationService.update(qualification));
    } else {
      this.subscribeToSaveResponse(this.qualificationService.create(qualification));
    }
  }

  trackContactDataById(_index: number, item: IContactData): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQualification>>): void {
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

  protected updateForm(qualification: IQualification): void {
    this.editForm.patchValue({
      id: qualification.id,
      qualif1: qualification.qualif1,
      qualif2: qualification.qualif2,
      qualif3: qualification.qualif3,
      qualif4: qualification.qualif4,
      qualif1BussinessViability: qualification.qualif1BussinessViability,
      contactData: qualification.contactData,
    });

    this.contactDataSharedCollection = this.contactDataService.addContactDataToCollectionIfMissing(
      this.contactDataSharedCollection,
      qualification.contactData
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

  protected createFromForm(): IQualification {
    return {
      ...new Qualification(),
      id: this.editForm.get(['id'])!.value,
      qualif1: this.editForm.get(['qualif1'])!.value,
      qualif2: this.editForm.get(['qualif2'])!.value,
      qualif3: this.editForm.get(['qualif3'])!.value,
      qualif4: this.editForm.get(['qualif4'])!.value,
      qualif1BussinessViability: this.editForm.get(['qualif1BussinessViability'])!.value,
      contactData: this.editForm.get(['contactData'])!.value,
    };
  }
}
