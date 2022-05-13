import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Rol } from 'app/entities/enumerations/rol.model';
import { IAffiliate, Affiliate } from '../affiliate.model';

import { AffiliateService } from './affiliate.service';

describe('Affiliate Service', () => {
  let service: AffiliateService;
  let httpMock: HttpTestingController;
  let elemDefault: IAffiliate;
  let expectedResult: IAffiliate | IAffiliate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AffiliateService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      documentNumber: 'AAAAAAA',
      firstName: 'AAAAAAA',
      secondName: 'AAAAAAA',
      firstLastName: 'AAAAAAA',
      secondLastName: 'AAAAAAA',
      neighborhood: 'AAAAAAA',
      cellPhoneNumber: 'AAAAAAA',
      city: 'AAAAAAA',
      department: 'AAAAAAA',
      email: 'AAAAAAA',
      callsign: 'AAAAAAA',
      country: 'AAAAAAA',
      phoneNumber: 'AAAAAAA',
      rol: Rol.AFFILIATE,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Affiliate', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Affiliate()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Affiliate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          documentNumber: 'BBBBBB',
          firstName: 'BBBBBB',
          secondName: 'BBBBBB',
          firstLastName: 'BBBBBB',
          secondLastName: 'BBBBBB',
          neighborhood: 'BBBBBB',
          cellPhoneNumber: 'BBBBBB',
          city: 'BBBBBB',
          department: 'BBBBBB',
          email: 'BBBBBB',
          callsign: 'BBBBBB',
          country: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          rol: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Affiliate', () => {
      const patchObject = Object.assign(
        {
          secondName: 'BBBBBB',
          secondLastName: 'BBBBBB',
          city: 'BBBBBB',
          country: 'BBBBBB',
          rol: 'BBBBBB',
        },
        new Affiliate()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Affiliate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          documentNumber: 'BBBBBB',
          firstName: 'BBBBBB',
          secondName: 'BBBBBB',
          firstLastName: 'BBBBBB',
          secondLastName: 'BBBBBB',
          neighborhood: 'BBBBBB',
          cellPhoneNumber: 'BBBBBB',
          city: 'BBBBBB',
          department: 'BBBBBB',
          email: 'BBBBBB',
          callsign: 'BBBBBB',
          country: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          rol: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Affiliate', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAffiliateToCollectionIfMissing', () => {
      it('should add a Affiliate to an empty array', () => {
        const affiliate: IAffiliate = { id: 123 };
        expectedResult = service.addAffiliateToCollectionIfMissing([], affiliate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(affiliate);
      });

      it('should not add a Affiliate to an array that contains it', () => {
        const affiliate: IAffiliate = { id: 123 };
        const affiliateCollection: IAffiliate[] = [
          {
            ...affiliate,
          },
          { id: 456 },
        ];
        expectedResult = service.addAffiliateToCollectionIfMissing(affiliateCollection, affiliate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Affiliate to an array that doesn't contain it", () => {
        const affiliate: IAffiliate = { id: 123 };
        const affiliateCollection: IAffiliate[] = [{ id: 456 }];
        expectedResult = service.addAffiliateToCollectionIfMissing(affiliateCollection, affiliate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(affiliate);
      });

      it('should add only unique Affiliate to an array', () => {
        const affiliateArray: IAffiliate[] = [{ id: 123 }, { id: 456 }, { id: 63609 }];
        const affiliateCollection: IAffiliate[] = [{ id: 123 }];
        expectedResult = service.addAffiliateToCollectionIfMissing(affiliateCollection, ...affiliateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const affiliate: IAffiliate = { id: 123 };
        const affiliate2: IAffiliate = { id: 456 };
        expectedResult = service.addAffiliateToCollectionIfMissing([], affiliate, affiliate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(affiliate);
        expect(expectedResult).toContain(affiliate2);
      });

      it('should accept null and undefined values', () => {
        const affiliate: IAffiliate = { id: 123 };
        expectedResult = service.addAffiliateToCollectionIfMissing([], null, affiliate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(affiliate);
      });

      it('should return initial array if no Affiliate is added', () => {
        const affiliateCollection: IAffiliate[] = [{ id: 123 }];
        expectedResult = service.addAffiliateToCollectionIfMissing(affiliateCollection, undefined, null);
        expect(expectedResult).toEqual(affiliateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
