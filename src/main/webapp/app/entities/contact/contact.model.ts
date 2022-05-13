import { IContactData } from 'app/entities/contact-data/contact-data.model';

export interface IContact {
  id?: number;
  contact3rdSectorHl?: boolean;
  contactAcademyHl?: boolean;
  contactTeethHl?: boolean;
  contactEmployeeHl?: boolean;
  contactEntFinanHl?: boolean;
  contactStatusHl?: boolean;
  internationalContactHl?: boolean;
  contactMediaComuncHl?: boolean;
  contactOrgCommunityHl?: boolean;
  contactRegulatoryOrganismsHl?: boolean;
  contactProveedoresHl?: boolean;
  contactSocialNetworks?: boolean;
  contactShareholdersHl?: boolean;
  contactData?: IContactData | null;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public contact3rdSectorHl?: boolean,
    public contactAcademyHl?: boolean,
    public contactTeethHl?: boolean,
    public contactEmployeeHl?: boolean,
    public contactEntFinanHl?: boolean,
    public contactStatusHl?: boolean,
    public internationalContactHl?: boolean,
    public contactMediaComuncHl?: boolean,
    public contactOrgCommunityHl?: boolean,
    public contactRegulatoryOrganismsHl?: boolean,
    public contactProveedoresHl?: boolean,
    public contactSocialNetworks?: boolean,
    public contactShareholdersHl?: boolean,
    public contactData?: IContactData | null
  ) {
    this.contact3rdSectorHl = this.contact3rdSectorHl ?? false;
    this.contactAcademyHl = this.contactAcademyHl ?? false;
    this.contactTeethHl = this.contactTeethHl ?? false;
    this.contactEmployeeHl = this.contactEmployeeHl ?? false;
    this.contactEntFinanHl = this.contactEntFinanHl ?? false;
    this.contactStatusHl = this.contactStatusHl ?? false;
    this.internationalContactHl = this.internationalContactHl ?? false;
    this.contactMediaComuncHl = this.contactMediaComuncHl ?? false;
    this.contactOrgCommunityHl = this.contactOrgCommunityHl ?? false;
    this.contactRegulatoryOrganismsHl = this.contactRegulatoryOrganismsHl ?? false;
    this.contactProveedoresHl = this.contactProveedoresHl ?? false;
    this.contactSocialNetworks = this.contactSocialNetworks ?? false;
    this.contactShareholdersHl = this.contactShareholdersHl ?? false;
  }
}

export function getContactIdentifier(contact: IContact): number | undefined {
  return contact.id;
}
