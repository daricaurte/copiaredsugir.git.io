import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Designation } from 'app/entities/enumerations/designation.model';
import { IQualification, Qualification } from '../qualification.model';

import { QualificationService } from './qualification.service';

describe('Qualification Service', () => {
  let service: QualificationService;
  let httpMock: HttpTestingController;
  let elemDefault: IQualification;
  let expectedResult: IQualification | IQualification[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QualificationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      qualif1: Designation.One,
      qualif2: Designation.One,
      qualif3: Designation.One,
      qualif4: Designation.One,
      qualif1BussinessViability: Designation.One,
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

    it('should create a Qualification', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Qualification()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Qualification', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qualif1: 'BBBBBB',
          qualif2: 'BBBBBB',
          qualif3: 'BBBBBB',
          qualif4: 'BBBBBB',
          qualif1BussinessViability: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Qualification', () => {
      const patchObject = Object.assign(
        {
          qualif1: 'BBBBBB',
          qualif3: 'BBBBBB',
          qualif4: 'BBBBBB',
          qualif1BussinessViability: 'BBBBBB',
        },
        new Qualification()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Qualification', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qualif1: 'BBBBBB',
          qualif2: 'BBBBBB',
          qualif3: 'BBBBBB',
          qualif4: 'BBBBBB',
          qualif1BussinessViability: 'BBBBBB',
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

    it('should delete a Qualification', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addQualificationToCollectionIfMissing', () => {
      it('should add a Qualification to an empty array', () => {
        const qualification: IQualification = { id: 123 };
        expectedResult = service.addQualificationToCollectionIfMissing([], qualification);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qualification);
      });

      it('should not add a Qualification to an array that contains it', () => {
        const qualification: IQualification = { id: 123 };
        const qualificationCollection: IQualification[] = [
          {
            ...qualification,
          },
          { id: 456 },
        ];
        expectedResult = service.addQualificationToCollectionIfMissing(qualificationCollection, qualification);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Qualification to an array that doesn't contain it", () => {
        const qualification: IQualification = { id: 123 };
        const qualificationCollection: IQualification[] = [{ id: 456 }];
        expectedResult = service.addQualificationToCollectionIfMissing(qualificationCollection, qualification);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qualification);
      });

      it('should add only unique Qualification to an array', () => {
        const qualificationArray: IQualification[] = [{ id: 123 }, { id: 456 }, { id: 88038 }];
        const qualificationCollection: IQualification[] = [{ id: 123 }];
        expectedResult = service.addQualificationToCollectionIfMissing(qualificationCollection, ...qualificationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qualification: IQualification = { id: 123 };
        const qualification2: IQualification = { id: 456 };
        expectedResult = service.addQualificationToCollectionIfMissing([], qualification, qualification2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qualification);
        expect(expectedResult).toContain(qualification2);
      });

      it('should accept null and undefined values', () => {
        const qualification: IQualification = { id: 123 };
        expectedResult = service.addQualificationToCollectionIfMissing([], null, qualification, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qualification);
      });

      it('should return initial array if no Qualification is added', () => {
        const qualificationCollection: IQualification[] = [{ id: 123 }];
        expectedResult = service.addQualificationToCollectionIfMissing(qualificationCollection, undefined, null);
        expect(expectedResult).toEqual(qualificationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
