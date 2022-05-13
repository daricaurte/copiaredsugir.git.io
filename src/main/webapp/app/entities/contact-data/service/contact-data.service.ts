import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContactData, getContactDataIdentifier } from '../contact-data.model';

export type EntityResponseType = HttpResponse<IContactData>;
export type EntityArrayResponseType = HttpResponse<IContactData[]>;

@Injectable({ providedIn: 'root' })
export class ContactDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contact-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contactData: IContactData): Observable<EntityResponseType> {
    return this.http.post<IContactData>(this.resourceUrl, contactData, { observe: 'response' });
  }

  update(contactData: IContactData): Observable<EntityResponseType> {
    return this.http.put<IContactData>(`${this.resourceUrl}/${getContactDataIdentifier(contactData) as number}`, contactData, {
      observe: 'response',
    });
  }

  partialUpdate(contactData: IContactData): Observable<EntityResponseType> {
    return this.http.patch<IContactData>(`${this.resourceUrl}/${getContactDataIdentifier(contactData) as number}`, contactData, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addContactDataToCollectionIfMissing(
    contactDataCollection: IContactData[],
    ...contactDataToCheck: (IContactData | null | undefined)[]
  ): IContactData[] {
    const contactData: IContactData[] = contactDataToCheck.filter(isPresent);
    if (contactData.length > 0) {
      const contactDataCollectionIdentifiers = contactDataCollection.map(contactDataItem => getContactDataIdentifier(contactDataItem)!);
      const contactDataToAdd = contactData.filter(contactDataItem => {
        const contactDataIdentifier = getContactDataIdentifier(contactDataItem);
        if (contactDataIdentifier == null || contactDataCollectionIdentifiers.includes(contactDataIdentifier)) {
          return false;
        }
        contactDataCollectionIdentifiers.push(contactDataIdentifier);
        return true;
      });
      return [...contactDataToAdd, ...contactDataCollection];
    }
    return contactDataCollection;
  }
}
