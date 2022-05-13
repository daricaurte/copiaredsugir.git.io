import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INetworks, Networks } from '../networks.model';

import { NetworksService } from './networks.service';

describe('Networks Service', () => {
  let service: NetworksService;
  let httpMock: HttpTestingController;
  let elemDefault: INetworks;
  let expectedResult: INetworks | INetworks[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NetworksService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      networks3rdSectorHl: false,
      academicNetworksHl: false,
      customerNetworksHl: false,
      employeeNetworksHl: false,
      networksEntFinanHl: false,
      stateNetworksHl: false,
      internationalNetworksHl: false,
      mediaNetworksComuncHl: false,
      communityOrgNetworksHl: false,
      regulatoryOrganismsNetworks: false,
      networksProvidersHl: false,
      socialNetworks: false,
      shareholderNetworksHl: false,
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

    it('should create a Networks', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Networks()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Networks', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          networks3rdSectorHl: true,
          academicNetworksHl: true,
          customerNetworksHl: true,
          employeeNetworksHl: true,
          networksEntFinanHl: true,
          stateNetworksHl: true,
          internationalNetworksHl: true,
          mediaNetworksComuncHl: true,
          communityOrgNetworksHl: true,
          regulatoryOrganismsNetworks: true,
          networksProvidersHl: true,
          socialNetworks: true,
          shareholderNetworksHl: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Networks', () => {
      const patchObject = Object.assign(
        {
          networks3rdSectorHl: true,
          employeeNetworksHl: true,
          networksEntFinanHl: true,
          internationalNetworksHl: true,
          networksProvidersHl: true,
        },
        new Networks()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Networks', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          networks3rdSectorHl: true,
          academicNetworksHl: true,
          customerNetworksHl: true,
          employeeNetworksHl: true,
          networksEntFinanHl: true,
          stateNetworksHl: true,
          internationalNetworksHl: true,
          mediaNetworksComuncHl: true,
          communityOrgNetworksHl: true,
          regulatoryOrganismsNetworks: true,
          networksProvidersHl: true,
          socialNetworks: true,
          shareholderNetworksHl: true,
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

    it('should delete a Networks', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNetworksToCollectionIfMissing', () => {
      it('should add a Networks to an empty array', () => {
        const networks: INetworks = { id: 123 };
        expectedResult = service.addNetworksToCollectionIfMissing([], networks);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(networks);
      });

      it('should not add a Networks to an array that contains it', () => {
        const networks: INetworks = { id: 123 };
        const networksCollection: INetworks[] = [
          {
            ...networks,
          },
          { id: 456 },
        ];
        expectedResult = service.addNetworksToCollectionIfMissing(networksCollection, networks);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Networks to an array that doesn't contain it", () => {
        const networks: INetworks = { id: 123 };
        const networksCollection: INetworks[] = [{ id: 456 }];
        expectedResult = service.addNetworksToCollectionIfMissing(networksCollection, networks);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(networks);
      });

      it('should add only unique Networks to an array', () => {
        const networksArray: INetworks[] = [{ id: 123 }, { id: 456 }, { id: 12117 }];
        const networksCollection: INetworks[] = [{ id: 123 }];
        expectedResult = service.addNetworksToCollectionIfMissing(networksCollection, ...networksArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const networks: INetworks = { id: 123 };
        const networks2: INetworks = { id: 456 };
        expectedResult = service.addNetworksToCollectionIfMissing([], networks, networks2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(networks);
        expect(expectedResult).toContain(networks2);
      });

      it('should accept null and undefined values', () => {
        const networks: INetworks = { id: 123 };
        expectedResult = service.addNetworksToCollectionIfMissing([], null, networks, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(networks);
      });

      it('should return initial array if no Networks is added', () => {
        const networksCollection: INetworks[] = [{ id: 123 }];
        expectedResult = service.addNetworksToCollectionIfMissing(networksCollection, undefined, null);
        expect(expectedResult).toEqual(networksCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
