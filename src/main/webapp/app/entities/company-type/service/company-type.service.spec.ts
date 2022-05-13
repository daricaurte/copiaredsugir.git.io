import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICompanyType, CompanyType } from '../company-type.model';

import { CompanyTypeService } from './company-type.service';

describe('CompanyType Service', () => {
  let service: CompanyTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: ICompanyType;
  let expectedResult: ICompanyType | ICompanyType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CompanyTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      primarySector: false,
      secondarySector: false,
      tertiarySector: false,
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

    it('should create a CompanyType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CompanyType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CompanyType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          primarySector: true,
          secondarySector: true,
          tertiarySector: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CompanyType', () => {
      const patchObject = Object.assign(
        {
          primarySector: true,
          secondarySector: true,
          tertiarySector: true,
        },
        new CompanyType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CompanyType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          primarySector: true,
          secondarySector: true,
          tertiarySector: true,
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

    it('should delete a CompanyType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCompanyTypeToCollectionIfMissing', () => {
      it('should add a CompanyType to an empty array', () => {
        const companyType: ICompanyType = { id: 123 };
        expectedResult = service.addCompanyTypeToCollectionIfMissing([], companyType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companyType);
      });

      it('should not add a CompanyType to an array that contains it', () => {
        const companyType: ICompanyType = { id: 123 };
        const companyTypeCollection: ICompanyType[] = [
          {
            ...companyType,
          },
          { id: 456 },
        ];
        expectedResult = service.addCompanyTypeToCollectionIfMissing(companyTypeCollection, companyType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CompanyType to an array that doesn't contain it", () => {
        const companyType: ICompanyType = { id: 123 };
        const companyTypeCollection: ICompanyType[] = [{ id: 456 }];
        expectedResult = service.addCompanyTypeToCollectionIfMissing(companyTypeCollection, companyType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companyType);
      });

      it('should add only unique CompanyType to an array', () => {
        const companyTypeArray: ICompanyType[] = [{ id: 123 }, { id: 456 }, { id: 98763 }];
        const companyTypeCollection: ICompanyType[] = [{ id: 123 }];
        expectedResult = service.addCompanyTypeToCollectionIfMissing(companyTypeCollection, ...companyTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const companyType: ICompanyType = { id: 123 };
        const companyType2: ICompanyType = { id: 456 };
        expectedResult = service.addCompanyTypeToCollectionIfMissing([], companyType, companyType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companyType);
        expect(expectedResult).toContain(companyType2);
      });

      it('should accept null and undefined values', () => {
        const companyType: ICompanyType = { id: 123 };
        expectedResult = service.addCompanyTypeToCollectionIfMissing([], null, companyType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companyType);
      });

      it('should return initial array if no CompanyType is added', () => {
        const companyTypeCollection: ICompanyType[] = [{ id: 123 }];
        expectedResult = service.addCompanyTypeToCollectionIfMissing(companyTypeCollection, undefined, null);
        expect(expectedResult).toEqual(companyTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
