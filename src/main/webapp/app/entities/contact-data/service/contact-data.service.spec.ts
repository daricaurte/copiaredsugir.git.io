import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContactData, ContactData } from '../contact-data.model';

import { ContactDataService } from './contact-data.service';

describe('ContactData Service', () => {
  let service: ContactDataService;
  let httpMock: HttpTestingController;
  let elemDefault: IContactData;
  let expectedResult: IContactData | IContactData[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContactDataService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      documentNumberContact: 'AAAAAAA',
      cellPhoneNumber: 'AAAAAAA',
      country: 'AAAAAAA',
      city: 'AAAAAAA',
      email: 'AAAAAAA',
      department: 'AAAAAAA',
      firstName: 'AAAAAAA',
      secondName: 'AAAAAAA',
      firstLastName: 'AAAAAAA',
      secondLastName: 'AAAAAAA',
      neighborhood: 'AAAAAAA',
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

    it('should create a ContactData', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ContactData()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContactData', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          documentNumberContact: 'BBBBBB',
          cellPhoneNumber: 'BBBBBB',
          country: 'BBBBBB',
          city: 'BBBBBB',
          email: 'BBBBBB',
          department: 'BBBBBB',
          firstName: 'BBBBBB',
          secondName: 'BBBBBB',
          firstLastName: 'BBBBBB',
          secondLastName: 'BBBBBB',
          neighborhood: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContactData', () => {
      const patchObject = Object.assign(
        {
          documentNumberContact: 'BBBBBB',
          cellPhoneNumber: 'BBBBBB',
          city: 'BBBBBB',
          email: 'BBBBBB',
          firstName: 'BBBBBB',
          secondName: 'BBBBBB',
          neighborhood: 'BBBBBB',
        },
        new ContactData()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContactData', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          documentNumberContact: 'BBBBBB',
          cellPhoneNumber: 'BBBBBB',
          country: 'BBBBBB',
          city: 'BBBBBB',
          email: 'BBBBBB',
          department: 'BBBBBB',
          firstName: 'BBBBBB',
          secondName: 'BBBBBB',
          firstLastName: 'BBBBBB',
          secondLastName: 'BBBBBB',
          neighborhood: 'BBBBBB',
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

    it('should delete a ContactData', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addContactDataToCollectionIfMissing', () => {
      it('should add a ContactData to an empty array', () => {
        const contactData: IContactData = { id: 123 };
        expectedResult = service.addContactDataToCollectionIfMissing([], contactData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contactData);
      });

      it('should not add a ContactData to an array that contains it', () => {
        const contactData: IContactData = { id: 123 };
        const contactDataCollection: IContactData[] = [
          {
            ...contactData,
          },
          { id: 456 },
        ];
        expectedResult = service.addContactDataToCollectionIfMissing(contactDataCollection, contactData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContactData to an array that doesn't contain it", () => {
        const contactData: IContactData = { id: 123 };
        const contactDataCollection: IContactData[] = [{ id: 456 }];
        expectedResult = service.addContactDataToCollectionIfMissing(contactDataCollection, contactData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contactData);
      });

      it('should add only unique ContactData to an array', () => {
        const contactDataArray: IContactData[] = [{ id: 123 }, { id: 456 }, { id: 44815 }];
        const contactDataCollection: IContactData[] = [{ id: 123 }];
        expectedResult = service.addContactDataToCollectionIfMissing(contactDataCollection, ...contactDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contactData: IContactData = { id: 123 };
        const contactData2: IContactData = { id: 456 };
        expectedResult = service.addContactDataToCollectionIfMissing([], contactData, contactData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contactData);
        expect(expectedResult).toContain(contactData2);
      });

      it('should accept null and undefined values', () => {
        const contactData: IContactData = { id: 123 };
        expectedResult = service.addContactDataToCollectionIfMissing([], null, contactData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contactData);
      });

      it('should return initial array if no ContactData is added', () => {
        const contactDataCollection: IContactData[] = [{ id: 123 }];
        expectedResult = service.addContactDataToCollectionIfMissing(contactDataCollection, undefined, null);
        expect(expectedResult).toEqual(contactDataCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
