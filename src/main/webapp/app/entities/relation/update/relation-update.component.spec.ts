import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RelationService } from '../service/relation.service';
import { IRelation, Relation } from '../relation.model';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

import { RelationUpdateComponent } from './relation-update.component';

describe('Relation Management Update Component', () => {
  let comp: RelationUpdateComponent;
  let fixture: ComponentFixture<RelationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let relationService: RelationService;
  let contactDataService: ContactDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RelationUpdateComponent],
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
      .overrideTemplate(RelationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RelationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    relationService = TestBed.inject(RelationService);
    contactDataService = TestBed.inject(ContactDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ContactData query and add missing value', () => {
      const relation: IRelation = { id: 456 };
      const contactData: IContactData = { id: 621 };
      relation.contactData = contactData;

      const contactDataCollection: IContactData[] = [{ id: 50652 }];
      jest.spyOn(contactDataService, 'query').mockReturnValue(of(new HttpResponse({ body: contactDataCollection })));
      const additionalContactData = [contactData];
      const expectedCollection: IContactData[] = [...additionalContactData, ...contactDataCollection];
      jest.spyOn(contactDataService, 'addContactDataToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ relation });
      comp.ngOnInit();

      expect(contactDataService.query).toHaveBeenCalled();
      expect(contactDataService.addContactDataToCollectionIfMissing).toHaveBeenCalledWith(contactDataCollection, ...additionalContactData);
      expect(comp.contactDataSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const relation: IRelation = { id: 456 };
      const contactData: IContactData = { id: 8969 };
      relation.contactData = contactData;

      activatedRoute.data = of({ relation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(relation));
      expect(comp.contactDataSharedCollection).toContain(contactData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Relation>>();
      const relation = { id: 123 };
      jest.spyOn(relationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: relation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(relationService.update).toHaveBeenCalledWith(relation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Relation>>();
      const relation = new Relation();
      jest.spyOn(relationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: relation }));
      saveSubject.complete();

      // THEN
      expect(relationService.create).toHaveBeenCalledWith(relation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Relation>>();
      const relation = { id: 123 };
      jest.spyOn(relationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(relationService.update).toHaveBeenCalledWith(relation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackContactDataById', () => {
      it('Should return tracked ContactData primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackContactDataById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
