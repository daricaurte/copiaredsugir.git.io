import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PriorityOrderService } from '../service/priority-order.service';
import { IPriorityOrder, PriorityOrder } from '../priority-order.model';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

import { PriorityOrderUpdateComponent } from './priority-order-update.component';

describe('PriorityOrder Management Update Component', () => {
  let comp: PriorityOrderUpdateComponent;
  let fixture: ComponentFixture<PriorityOrderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let priorityOrderService: PriorityOrderService;
  let contactDataService: ContactDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PriorityOrderUpdateComponent],
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
      .overrideTemplate(PriorityOrderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PriorityOrderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    priorityOrderService = TestBed.inject(PriorityOrderService);
    contactDataService = TestBed.inject(ContactDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ContactData query and add missing value', () => {
      const priorityOrder: IPriorityOrder = { id: 456 };
      const contactData: IContactData = { id: 2258 };
      priorityOrder.contactData = contactData;

      const contactDataCollection: IContactData[] = [{ id: 54076 }];
      jest.spyOn(contactDataService, 'query').mockReturnValue(of(new HttpResponse({ body: contactDataCollection })));
      const additionalContactData = [contactData];
      const expectedCollection: IContactData[] = [...additionalContactData, ...contactDataCollection];
      jest.spyOn(contactDataService, 'addContactDataToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ priorityOrder });
      comp.ngOnInit();

      expect(contactDataService.query).toHaveBeenCalled();
      expect(contactDataService.addContactDataToCollectionIfMissing).toHaveBeenCalledWith(contactDataCollection, ...additionalContactData);
      expect(comp.contactDataSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const priorityOrder: IPriorityOrder = { id: 456 };
      const contactData: IContactData = { id: 79073 };
      priorityOrder.contactData = contactData;

      activatedRoute.data = of({ priorityOrder });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(priorityOrder));
      expect(comp.contactDataSharedCollection).toContain(contactData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PriorityOrder>>();
      const priorityOrder = { id: 123 };
      jest.spyOn(priorityOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priorityOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: priorityOrder }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(priorityOrderService.update).toHaveBeenCalledWith(priorityOrder);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PriorityOrder>>();
      const priorityOrder = new PriorityOrder();
      jest.spyOn(priorityOrderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priorityOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: priorityOrder }));
      saveSubject.complete();

      // THEN
      expect(priorityOrderService.create).toHaveBeenCalledWith(priorityOrder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PriorityOrder>>();
      const priorityOrder = { id: 123 };
      jest.spyOn(priorityOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priorityOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(priorityOrderService.update).toHaveBeenCalledWith(priorityOrder);
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
