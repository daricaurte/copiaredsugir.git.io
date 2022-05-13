import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRelation, Relation } from '../relation.model';
import { RelationService } from '../service/relation.service';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

@Component({
  selector: 'redsurgir-relation-update',
  templateUrl: './relation-update.component.html',
})
export class RelationUpdateComponent implements OnInit {
  isSaving = false;

  contactDataSharedCollection: IContactData[] = [];

  editForm = this.fb.group({
    id: [],
    familyRelation: [null, [Validators.required]],
    workRelation: [null, [Validators.required]],
    personalRelation: [null, [Validators.required]],
    contactData: [],
  });

  constructor(
    protected relationService: RelationService,
    protected contactDataService: ContactDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relation }) => {
      this.updateForm(relation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const relation = this.createFromForm();
    if (relation.id !== undefined) {
      this.subscribeToSaveResponse(this.relationService.update(relation));
    } else {
      this.subscribeToSaveResponse(this.relationService.create(relation));
    }
  }

  trackContactDataById(_index: number, item: IContactData): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelation>>): void {
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

  protected updateForm(relation: IRelation): void {
    this.editForm.patchValue({
      id: relation.id,
      familyRelation: relation.familyRelation,
      workRelation: relation.workRelation,
      personalRelation: relation.personalRelation,
      contactData: relation.contactData,
    });

    this.contactDataSharedCollection = this.contactDataService.addContactDataToCollectionIfMissing(
      this.contactDataSharedCollection,
      relation.contactData
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

  protected createFromForm(): IRelation {
    return {
      ...new Relation(),
      id: this.editForm.get(['id'])!.value,
      familyRelation: this.editForm.get(['familyRelation'])!.value,
      workRelation: this.editForm.get(['workRelation'])!.value,
      personalRelation: this.editForm.get(['personalRelation'])!.value,
      contactData: this.editForm.get(['contactData'])!.value,
    };
  }
}
