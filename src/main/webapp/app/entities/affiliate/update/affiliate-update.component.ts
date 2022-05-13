import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAffiliate, Affiliate } from '../affiliate.model';
import { AffiliateService } from '../service/affiliate.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDocumentType } from 'app/entities/document-type/document-type.model';
import { DocumentTypeService } from 'app/entities/document-type/service/document-type.service';
import { Rol } from 'app/entities/enumerations/rol.model';

@Component({
  selector: 'redsurgir-affiliate-update',
  templateUrl: './affiliate-update.component.html',
})
export class AffiliateUpdateComponent implements OnInit {
  isSaving = false;
  rolValues = Object.keys(Rol);

  usersSharedCollection: IUser[] = [];
  documentTypesSharedCollection: IDocumentType[] = [];

  editForm = this.fb.group({
    id: [],
    documentNumber: [null, [Validators.required, Validators.maxLength(50)]],
    firstName: [null, [Validators.required, Validators.maxLength(50)]],
    secondName: [null, [Validators.maxLength(50)]],
    firstLastName: [null, [Validators.required, Validators.maxLength(50)]],
    secondLastName: [null, [Validators.maxLength(50)]],
    neighborhood: [null, [Validators.required, Validators.maxLength(50)]],
    cellPhoneNumber: [null, [Validators.required, Validators.maxLength(11)]],
    city: [null, [Validators.required, Validators.maxLength(50)]],
    department: [null, [Validators.required, Validators.maxLength(50)]],
    email: [null, [Validators.required, Validators.maxLength(50)]],
    callsign: [null, [Validators.required, Validators.maxLength(4)]],
    country: [null, [Validators.required, Validators.maxLength(50)]],
    phoneNumber: [null, [Validators.required, Validators.maxLength(11)]],
    rol: [null, [Validators.required]],
    user: [],
    documentType: [],
  });

  constructor(
    protected affiliateService: AffiliateService,
    protected userService: UserService,
    protected documentTypeService: DocumentTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affiliate }) => {
      this.updateForm(affiliate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const affiliate = this.createFromForm();
    if (affiliate.id !== undefined) {
      this.subscribeToSaveResponse(this.affiliateService.update(affiliate));
    } else {
      this.subscribeToSaveResponse(this.affiliateService.create(affiliate));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  trackDocumentTypeById(_index: number, item: IDocumentType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffiliate>>): void {
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

  protected updateForm(affiliate: IAffiliate): void {
    this.editForm.patchValue({
      id: affiliate.id,
      documentNumber: affiliate.documentNumber,
      firstName: affiliate.firstName,
      secondName: affiliate.secondName,
      firstLastName: affiliate.firstLastName,
      secondLastName: affiliate.secondLastName,
      neighborhood: affiliate.neighborhood,
      cellPhoneNumber: affiliate.cellPhoneNumber,
      city: affiliate.city,
      department: affiliate.department,
      email: affiliate.email,
      callsign: affiliate.callsign,
      country: affiliate.country,
      phoneNumber: affiliate.phoneNumber,
      rol: affiliate.rol,
      user: affiliate.user,
      documentType: affiliate.documentType,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, affiliate.user);
    this.documentTypesSharedCollection = this.documentTypeService.addDocumentTypeToCollectionIfMissing(
      this.documentTypesSharedCollection,
      affiliate.documentType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.documentTypeService
      .query()
      .pipe(map((res: HttpResponse<IDocumentType[]>) => res.body ?? []))
      .pipe(
        map((documentTypes: IDocumentType[]) =>
          this.documentTypeService.addDocumentTypeToCollectionIfMissing(documentTypes, this.editForm.get('documentType')!.value)
        )
      )
      .subscribe((documentTypes: IDocumentType[]) => (this.documentTypesSharedCollection = documentTypes));
  }

  protected createFromForm(): IAffiliate {
    return {
      ...new Affiliate(),
      id: this.editForm.get(['id'])!.value,
      documentNumber: this.editForm.get(['documentNumber'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      secondName: this.editForm.get(['secondName'])!.value,
      firstLastName: this.editForm.get(['firstLastName'])!.value,
      secondLastName: this.editForm.get(['secondLastName'])!.value,
      neighborhood: this.editForm.get(['neighborhood'])!.value,
      cellPhoneNumber: this.editForm.get(['cellPhoneNumber'])!.value,
      city: this.editForm.get(['city'])!.value,
      department: this.editForm.get(['department'])!.value,
      email: this.editForm.get(['email'])!.value,
      callsign: this.editForm.get(['callsign'])!.value,
      country: this.editForm.get(['country'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      rol: this.editForm.get(['rol'])!.value,
      user: this.editForm.get(['user'])!.value,
      documentType: this.editForm.get(['documentType'])!.value,
    };
  }
}
