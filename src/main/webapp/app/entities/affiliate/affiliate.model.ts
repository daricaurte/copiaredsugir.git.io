import { IUser } from 'app/entities/user/user.model';
import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { IDocumentType } from 'app/entities/document-type/document-type.model';
import { Rol } from 'app/entities/enumerations/rol.model';

export interface IAffiliate {
  id?: number;
  documentNumber?: string;
  firstName?: string;
  secondName?: string | null;
  firstLastName?: string;
  secondLastName?: string | null;
  neighborhood?: string;
  cellPhoneNumber?: string;
  city?: string;
  department?: string;
  email?: string;
  callsign?: string;
  country?: string;
  phoneNumber?: string;
  rol?: Rol;
  user?: IUser | null;
  contactData?: IContactData[] | null;
  documentType?: IDocumentType | null;
}

export class Affiliate implements IAffiliate {
  constructor(
    public id?: number,
    public documentNumber?: string,
    public firstName?: string,
    public secondName?: string | null,
    public firstLastName?: string,
    public secondLastName?: string | null,
    public neighborhood?: string,
    public cellPhoneNumber?: string,
    public city?: string,
    public department?: string,
    public email?: string,
    public callsign?: string,
    public country?: string,
    public phoneNumber?: string,
    public rol?: Rol,
    public user?: IUser | null,
    public contactData?: IContactData[] | null,
    public documentType?: IDocumentType | null
  ) {}
}

export function getAffiliateIdentifier(affiliate: IAffiliate): number | undefined {
  return affiliate.id;
}
