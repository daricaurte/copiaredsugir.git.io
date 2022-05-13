import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPotential, Potential } from '../potential.model';
import { PotentialService } from '../service/potential.service';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

@Component({
  selector: 'redsurgir-potential-update',
  templateUrl: './potential-update.component.html',
})
export class PotentialUpdateComponent implements OnInit {
  isSaving = false;

  contactDataSharedCollection: IContactData[] = [];

  editForm = this.fb.group({
    id: [],
    exchangePartner: [null, [Validators.required]],
    consultant: [null, [Validators.required]],
    client: [null, [Validators.required]],
    collaborator: [null, [Validators.required]],
    provider: [null, [Validators.required]],
    referrer: [null, [Validators.required]],
    contactData: [],
  });

  constructor(
    protected potentialService: PotentialService,
    protected contactDataService: ContactDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ potential }) => {
      this.updateForm(potential);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const potential = this.createFromForm();
    if (potential.id !== undefined) {
      this.subscribeToSaveResponse(this.potentialService.update(potential));
    } else {
      this.subscribeToSaveResponse(this.potentialService.create(potential));
    }
  }

  trackContactDataById(_index: number, item: IContactData): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPotential>>): void {
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

  protected updateForm(potential: IPotential): void {
    this.editForm.patchValue({
      id: potential.id,
      exchangePartner: potential.exchangePartner,
      consultant: potential.consultant,
      client: potential.client,
      collaborator: potential.collaborator,
      provider: potential.provider,
      referrer: potential.referrer,
      contactData: potential.contactData,
    });

    this.contactDataSharedCollection = this.contactDataService.addContactDataToCollectionIfMissing(
      this.contactDataSharedCollection,
      potential.contactData
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

  protected createFromForm(): IPotential {
    return {
      ...new Potential(),
      id: this.editForm.get(['id'])!.value,
      exchangePartner: this.editForm.get(['exchangePartner'])!.value,
      consultant: this.editForm.get(['consultant'])!.value,
      client: this.editForm.get(['client'])!.value,
      collaborator: this.editForm.get(['collaborator'])!.value,
      provider: this.editForm.get(['provider'])!.value,
      referrer: this.editForm.get(['referrer'])!.value,
      contactData: this.editForm.get(['contactData'])!.value,
    };
  }
}
