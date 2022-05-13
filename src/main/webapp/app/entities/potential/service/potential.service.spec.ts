import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPotential, Potential } from '../potential.model';

import { PotentialService } from './potential.service';

describe('Potential Service', () => {
  let service: PotentialService;
  let httpMock: HttpTestingController;
  let elemDefault: IPotential;
  let expectedResult: IPotential | IPotential[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PotentialService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      exchangePartner: false,
      consultant: false,
      client: false,
      collaborator: false,
      provider: false,
      referrer: false,
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

    it('should create a Potential', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Potential()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Potential', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          exchangePartner: true,
          consultant: true,
          client: true,
          collaborator: true,
          provider: true,
          referrer: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Potential', () => {
      const patchObject = Object.assign(
        {
          exchangePartner: true,
          consultant: true,
          provider: true,
        },
        new Potential()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Potential', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          exchangePartner: true,
          consultant: true,
          client: true,
          collaborator: true,
          provider: true,
          referrer: true,
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

    it('should delete a Potential', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPotentialToCollectionIfMissing', () => {
      it('should add a Potential to an empty array', () => {
        const potential: IPotential = { id: 123 };
        expectedResult = service.addPotentialToCollectionIfMissing([], potential);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(potential);
      });

      it('should not add a Potential to an array that contains it', () => {
        const potential: IPotential = { id: 123 };
        const potentialCollection: IPotential[] = [
          {
            ...potential,
          },
          { id: 456 },
        ];
        expectedResult = service.addPotentialToCollectionIfMissing(potentialCollection, potential);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Potential to an array that doesn't contain it", () => {
        const potential: IPotential = { id: 123 };
        const potentialCollection: IPotential[] = [{ id: 456 }];
        expectedResult = service.addPotentialToCollectionIfMissing(potentialCollection, potential);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(potential);
      });

      it('should add only unique Potential to an array', () => {
        const potentialArray: IPotential[] = [{ id: 123 }, { id: 456 }, { id: 90590 }];
        const potentialCollection: IPotential[] = [{ id: 123 }];
        expectedResult = service.addPotentialToCollectionIfMissing(potentialCollection, ...potentialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const potential: IPotential = { id: 123 };
        const potential2: IPotential = { id: 456 };
        expectedResult = service.addPotentialToCollectionIfMissing([], potential, potential2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(potential);
        expect(expectedResult).toContain(potential2);
      });

      it('should accept null and undefined values', () => {
        const potential: IPotential = { id: 123 };
        expectedResult = service.addPotentialToCollectionIfMissing([], null, potential, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(potential);
      });

      it('should return initial array if no Potential is added', () => {
        const potentialCollection: IPotential[] = [{ id: 123 }];
        expectedResult = service.addPotentialToCollectionIfMissing(potentialCollection, undefined, null);
        expect(expectedResult).toEqual(potentialCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
