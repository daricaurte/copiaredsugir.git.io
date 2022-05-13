import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PotentialService } from '../service/potential.service';
import { IPotential, Potential } from '../potential.model';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

import { PotentialUpdateComponent } from './potential-update.component';

describe('Potential Management Update Component', () => {
  let comp: PotentialUpdateComponent;
  let fixture: ComponentFixture<PotentialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let potentialService: PotentialService;
  let contactDataService: ContactDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PotentialUpdateComponent],
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
      .overrideTemplate(PotentialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PotentialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    potentialService = TestBed.inject(PotentialService);
    contactDataService = TestBed.inject(ContactDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ContactData query and add missing value', () => {
      const potential: IPotential = { id: 456 };
      const contactData: IContactData = { id: 34439 };
      potential.contactData = contactData;

      const contactDataCollection: IContactData[] = [{ id: 4596 }];
      jest.spyOn(contactDataService, 'query').mockReturnValue(of(new HttpResponse({ body: contactDataCollection })));
      const additionalContactData = [contactData];
      const expectedCollection: IContactData[] = [...additionalContactData, ...contactDataCollection];
      jest.spyOn(contactDataService, 'addContactDataToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ potential });
      comp.ngOnInit();

      expect(contactDataService.query).toHaveBeenCalled();
      expect(contactDataService.addContactDataToCollectionIfMissing).toHaveBeenCalledWith(contactDataCollection, ...additionalContactData);
      expect(comp.contactDataSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const potential: IPotential = { id: 456 };
      const contactData: IContactData = { id: 72680 };
      potential.contactData = contactData;

      activatedRoute.data = of({ potential });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(potential));
      expect(comp.contactDataSharedCollection).toContain(contactData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Potential>>();
      const potential = { id: 123 };
      jest.spyOn(potentialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ potential });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: potential }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(potentialService.update).toHaveBeenCalledWith(potential);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Potential>>();
      const potential = new Potential();
      jest.spyOn(potentialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ potential });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: potential }));
      saveSubject.complete();

      // THEN
      expect(potentialService.create).toHaveBeenCalledWith(potential);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Potential>>();
      const potential = { id: 123 };
      jest.spyOn(potentialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ potential });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(potentialService.update).toHaveBeenCalledWith(potential);
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
