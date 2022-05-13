import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPriorityOrder, PriorityOrder } from '../priority-order.model';
import { PriorityOrderService } from '../service/priority-order.service';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';
import { Designation } from 'app/entities/enumerations/designation.model';

@Component({
  selector: 'redsurgir-priority-order-update',
  templateUrl: './priority-order-update.component.html',
})
export class PriorityOrderUpdateComponent implements OnInit {
  isSaving = false;
  designationValues = Object.keys(Designation);

  contactDataSharedCollection: IContactData[] = [];

  editForm = this.fb.group({
    id: [],
    qualifLogarit: [null, [Validators.required]],
    qualifAffiliate: [null, [Validators.required]],
    qualifDefinitive: [null, [Validators.required]],
    contactData: [],
  });

  constructor(
    protected priorityOrderService: PriorityOrderService,
    protected contactDataService: ContactDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priorityOrder }) => {
      this.updateForm(priorityOrder);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const priorityOrder = this.createFromForm();
    if (priorityOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.priorityOrderService.update(priorityOrder));
    } else {
      this.subscribeToSaveResponse(this.priorityOrderService.create(priorityOrder));
    }
  }

  trackContactDataById(_index: number, item: IContactData): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriorityOrder>>): void {
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

  protected updateForm(priorityOrder: IPriorityOrder): void {
    this.editForm.patchValue({
      id: priorityOrder.id,
      qualifLogarit: priorityOrder.qualifLogarit,
      qualifAffiliate: priorityOrder.qualifAffiliate,
      qualifDefinitive: priorityOrder.qualifDefinitive,
      contactData: priorityOrder.contactData,
    });

    this.contactDataSharedCollection = this.contactDataService.addContactDataToCollectionIfMissing(
      this.contactDataSharedCollection,
      priorityOrder.contactData
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

  protected createFromForm(): IPriorityOrder {
    return {
      ...new PriorityOrder(),
      id: this.editForm.get(['id'])!.value,
      qualifLogarit: this.editForm.get(['qualifLogarit'])!.value,
      qualifAffiliate: this.editForm.get(['qualifAffiliate'])!.value,
      qualifDefinitive: this.editForm.get(['qualifDefinitive'])!.value,
      contactData: this.editForm.get(['contactData'])!.value,
    };
  }
}
