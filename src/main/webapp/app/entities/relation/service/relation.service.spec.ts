import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRelation, Relation } from '../relation.model';

import { RelationService } from './relation.service';

describe('Relation Service', () => {
  let service: RelationService;
  let httpMock: HttpTestingController;
  let elemDefault: IRelation;
  let expectedResult: IRelation | IRelation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RelationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      familyRelation: false,
      workRelation: false,
      personalRelation: false,
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

    it('should create a Relation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Relation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Relation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          familyRelation: true,
          workRelation: true,
          personalRelation: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Relation', () => {
      const patchObject = Object.assign(
        {
          familyRelation: true,
          workRelation: true,
          personalRelation: true,
        },
        new Relation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Relation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          familyRelation: true,
          workRelation: true,
          personalRelation: true,
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

    it('should delete a Relation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRelationToCollectionIfMissing', () => {
      it('should add a Relation to an empty array', () => {
        const relation: IRelation = { id: 123 };
        expectedResult = service.addRelationToCollectionIfMissing([], relation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(relation);
      });

      it('should not add a Relation to an array that contains it', () => {
        const relation: IRelation = { id: 123 };
        const relationCollection: IRelation[] = [
          {
            ...relation,
          },
          { id: 456 },
        ];
        expectedResult = service.addRelationToCollectionIfMissing(relationCollection, relation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Relation to an array that doesn't contain it", () => {
        const relation: IRelation = { id: 123 };
        const relationCollection: IRelation[] = [{ id: 456 }];
        expectedResult = service.addRelationToCollectionIfMissing(relationCollection, relation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(relation);
      });

      it('should add only unique Relation to an array', () => {
        const relationArray: IRelation[] = [{ id: 123 }, { id: 456 }, { id: 47657 }];
        const relationCollection: IRelation[] = [{ id: 123 }];
        expectedResult = service.addRelationToCollectionIfMissing(relationCollection, ...relationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const relation: IRelation = { id: 123 };
        const relation2: IRelation = { id: 456 };
        expectedResult = service.addRelationToCollectionIfMissing([], relation, relation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(relation);
        expect(expectedResult).toContain(relation2);
      });

      it('should accept null and undefined values', () => {
        const relation: IRelation = { id: 123 };
        expectedResult = service.addRelationToCollectionIfMissing([], null, relation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(relation);
      });

      it('should return initial array if no Relation is added', () => {
        const relationCollection: IRelation[] = [{ id: 123 }];
        expectedResult = service.addRelationToCollectionIfMissing(relationCollection, undefined, null);
        expect(expectedResult).toEqual(relationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
