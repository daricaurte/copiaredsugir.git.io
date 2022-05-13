import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Designation } from 'app/entities/enumerations/designation.model';
import { IPriorityOrder, PriorityOrder } from '../priority-order.model';

import { PriorityOrderService } from './priority-order.service';

describe('PriorityOrder Service', () => {
  let service: PriorityOrderService;
  let httpMock: HttpTestingController;
  let elemDefault: IPriorityOrder;
  let expectedResult: IPriorityOrder | IPriorityOrder[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PriorityOrderService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      qualifLogarit: Designation.One,
      qualifAffiliate: Designation.One,
      qualifDefinitive: Designation.One,
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

    it('should create a PriorityOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PriorityOrder()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PriorityOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qualifLogarit: 'BBBBBB',
          qualifAffiliate: 'BBBBBB',
          qualifDefinitive: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PriorityOrder', () => {
      const patchObject = Object.assign({}, new PriorityOrder());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PriorityOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qualifLogarit: 'BBBBBB',
          qualifAffiliate: 'BBBBBB',
          qualifDefinitive: 'BBBBBB',
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

    it('should delete a PriorityOrder', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPriorityOrderToCollectionIfMissing', () => {
      it('should add a PriorityOrder to an empty array', () => {
        const priorityOrder: IPriorityOrder = { id: 123 };
        expectedResult = service.addPriorityOrderToCollectionIfMissing([], priorityOrder);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(priorityOrder);
      });

      it('should not add a PriorityOrder to an array that contains it', () => {
        const priorityOrder: IPriorityOrder = { id: 123 };
        const priorityOrderCollection: IPriorityOrder[] = [
          {
            ...priorityOrder,
          },
          { id: 456 },
        ];
        expectedResult = service.addPriorityOrderToCollectionIfMissing(priorityOrderCollection, priorityOrder);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PriorityOrder to an array that doesn't contain it", () => {
        const priorityOrder: IPriorityOrder = { id: 123 };
        const priorityOrderCollection: IPriorityOrder[] = [{ id: 456 }];
        expectedResult = service.addPriorityOrderToCollectionIfMissing(priorityOrderCollection, priorityOrder);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(priorityOrder);
      });

      it('should add only unique PriorityOrder to an array', () => {
        const priorityOrderArray: IPriorityOrder[] = [{ id: 123 }, { id: 456 }, { id: 19866 }];
        const priorityOrderCollection: IPriorityOrder[] = [{ id: 123 }];
        expectedResult = service.addPriorityOrderToCollectionIfMissing(priorityOrderCollection, ...priorityOrderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const priorityOrder: IPriorityOrder = { id: 123 };
        const priorityOrder2: IPriorityOrder = { id: 456 };
        expectedResult = service.addPriorityOrderToCollectionIfMissing([], priorityOrder, priorityOrder2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(priorityOrder);
        expect(expectedResult).toContain(priorityOrder2);
      });

      it('should accept null and undefined values', () => {
        const priorityOrder: IPriorityOrder = { id: 123 };
        expectedResult = service.addPriorityOrderToCollectionIfMissing([], null, priorityOrder, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(priorityOrder);
      });

      it('should return initial array if no PriorityOrder is added', () => {
        const priorityOrderCollection: IPriorityOrder[] = [{ id: 123 }];
        expectedResult = service.addPriorityOrderToCollectionIfMissing(priorityOrderCollection, undefined, null);
        expect(expectedResult).toEqual(priorityOrderCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
