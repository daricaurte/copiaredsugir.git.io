import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NetworksService } from '../service/networks.service';
import { INetworks, Networks } from '../networks.model';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { ContactDataService } from 'app/entities/contact-data/service/contact-data.service';

import { NetworksUpdateComponent } from './networks-update.component';

describe('Networks Management Update Component', () => {
  let comp: NetworksUpdateComponent;
  let fixture: ComponentFixture<NetworksUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let networksService: NetworksService;
  let contactDataService: ContactDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NetworksUpdateComponent],
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
      .overrideTemplate(NetworksUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NetworksUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    networksService = TestBed.inject(NetworksService);
    contactDataService = TestBed.inject(ContactDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ContactData query and add missing value', () => {
      const networks: INetworks = { id: 456 };
      const contactData: IContactData = { id: 35645 };
      networks.contactData = contactData;

      const contactDataCollection: IContactData[] = [{ id: 87203 }];
      jest.spyOn(contactDataService, 'query').mockReturnValue(of(new HttpResponse({ body: contactDataCollection })));
      const additionalContactData = [contactData];
      const expectedCollection: IContactData[] = [...additionalContactData, ...contactDataCollection];
      jest.spyOn(contactDataService, 'addContactDataToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ networks });
      comp.ngOnInit();

      expect(contactDataService.query).toHaveBeenCalled();
      expect(contactDataService.addContactDataToCollectionIfMissing).toHaveBeenCalledWith(contactDataCollection, ...additionalContactData);
      expect(comp.contactDataSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const networks: INetworks = { id: 456 };
      const contactData: IContactData = { id: 10558 };
      networks.contactData = contactData;

      activatedRoute.data = of({ networks });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(networks));
      expect(comp.contactDataSharedCollection).toContain(contactData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Networks>>();
      const networks = { id: 123 };
      jest.spyOn(networksService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ networks });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: networks }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(networksService.update).toHaveBeenCalledWith(networks);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Networks>>();
      const networks = new Networks();
      jest.spyOn(networksService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ networks });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: networks }));
      saveSubject.complete();

      // THEN
      expect(networksService.create).toHaveBeenCalledWith(networks);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Networks>>();
      const networks = { id: 123 };
      jest.spyOn(networksService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ networks });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(networksService.update).toHaveBeenCalledWith(networks);
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
