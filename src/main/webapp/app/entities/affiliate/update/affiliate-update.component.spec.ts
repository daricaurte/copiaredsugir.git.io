import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AffiliateService } from '../service/affiliate.service';
import { IAffiliate, Affiliate } from '../affiliate.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDocumentType } from 'app/entities/document-type/document-type.model';
import { DocumentTypeService } from 'app/entities/document-type/service/document-type.service';

import { AffiliateUpdateComponent } from './affiliate-update.component';

describe('Affiliate Management Update Component', () => {
  let comp: AffiliateUpdateComponent;
  let fixture: ComponentFixture<AffiliateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let affiliateService: AffiliateService;
  let userService: UserService;
  let documentTypeService: DocumentTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AffiliateUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AffiliateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AffiliateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    affiliateService = TestBed.inject(AffiliateService);
    userService = TestBed.inject(UserService);
    documentTypeService = TestBed.inject(DocumentTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const affiliate: IAffiliate = { id: 456 };
      const user: IUser = { id: 76061 };
      affiliate.user = user;

      const userCollection: IUser[] = [{ id: 84364 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affiliate });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentType query and add missing value', () => {
      const affiliate: IAffiliate = { id: 456 };
      const documentType: IDocumentType = { id: 61384 };
      affiliate.documentType = documentType;

      const documentTypeCollection: IDocumentType[] = [{ id: 44413 }];
      jest.spyOn(documentTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: documentTypeCollection })));
      const additionalDocumentTypes = [documentType];
      const expectedCollection: IDocumentType[] = [...additionalDocumentTypes, ...documentTypeCollection];
      jest.spyOn(documentTypeService, 'addDocumentTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affiliate });
      comp.ngOnInit();

      expect(documentTypeService.query).toHaveBeenCalled();
      expect(documentTypeService.addDocumentTypeToCollectionIfMissing).toHaveBeenCalledWith(
        documentTypeCollection,
        ...additionalDocumentTypes
      );
      expect(comp.documentTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const affiliate: IAffiliate = { id: 456 };
      const user: IUser = { id: 19945 };
      affiliate.user = user;
      const documentType: IDocumentType = { id: 26870 };
      affiliate.documentType = documentType;

      activatedRoute.data = of({ affiliate });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(affiliate));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.documentTypesSharedCollection).toContain(documentType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Affiliate>>();
      const affiliate = { id: 123 };
      jest.spyOn(affiliateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affiliate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: affiliate }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(affiliateService.update).toHaveBeenCalledWith(affiliate);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Affiliate>>();
      const affiliate = new Affiliate();
      jest.spyOn(affiliateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affiliate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: affiliate }));
      saveSubject.complete();

      // THEN
      expect(affiliateService.create).toHaveBeenCalledWith(affiliate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Affiliate>>();
      const affiliate = { id: 123 };
      jest.spyOn(affiliateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affiliate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(affiliateService.update).toHaveBeenCalledWith(affiliate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDocumentTypeById', () => {
      it('Should return tracked DocumentType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDocumentTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
