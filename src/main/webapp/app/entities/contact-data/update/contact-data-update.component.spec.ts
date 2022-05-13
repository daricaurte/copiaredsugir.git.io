import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContactDataService } from '../service/contact-data.service';
import { IContactData, ContactData } from '../contact-data.model';
import { IAffiliate } from 'app/entities/affiliate/affiliate.model';
import { AffiliateService } from 'app/entities/affiliate/service/affiliate.service';

import { ContactDataUpdateComponent } from './contact-data-update.component';

describe('ContactData Management Update Component', () => {
  let comp: ContactDataUpdateComponent;
  let fixture: ComponentFixture<ContactDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contactDataService: ContactDataService;
  let affiliateService: AffiliateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContactDataUpdateComponent],
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
      .overrideTemplate(ContactDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contactDataService = TestBed.inject(ContactDataService);
    affiliateService = TestBed.inject(AffiliateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Affiliate query and add missing value', () => {
      const contactData: IContactData = { id: 456 };
      const affiliate: IAffiliate = { id: 50047 };
      contactData.affiliate = affiliate;

      const affiliateCollection: IAffiliate[] = [{ id: 22209 }];
      jest.spyOn(affiliateService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliateCollection })));
      const additionalAffiliates = [affiliate];
      const expectedCollection: IAffiliate[] = [...additionalAffiliates, ...affiliateCollection];
      jest.spyOn(affiliateService, 'addAffiliateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contactData });
      comp.ngOnInit();

      expect(affiliateService.query).toHaveBeenCalled();
      expect(affiliateService.addAffiliateToCollectionIfMissing).toHaveBeenCalledWith(affiliateCollection, ...additionalAffiliates);
      expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contactData: IContactData = { id: 456 };
      const affiliate: IAffiliate = { id: 76751 };
      contactData.affiliate = affiliate;

      activatedRoute.data = of({ contactData });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(contactData));
      expect(comp.affiliatesSharedCollection).toContain(affiliate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactData>>();
      const contactData = { id: 123 };
      jest.spyOn(contactDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactData }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(contactDataService.update).toHaveBeenCalledWith(contactData);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactData>>();
      const contactData = new ContactData();
      jest.spyOn(contactDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactData }));
      saveSubject.complete();

      // THEN
      expect(contactDataService.create).toHaveBeenCalledWith(contactData);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactData>>();
      const contactData = { id: 123 };
      jest.spyOn(contactDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contactDataService.update).toHaveBeenCalledWith(contactData);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAffiliateById', () => {
      it('Should return tracked Affiliate primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAffiliateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
