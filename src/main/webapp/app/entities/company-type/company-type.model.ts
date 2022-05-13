import { IContactData } from 'app/entities/contact-data/contact-data.model';

export interface ICompanyType {
  id?: number;
  primarySector?: boolean | null;
  secondarySector?: boolean | null;
  tertiarySector?: boolean | null;
  contactData?: IContactData | null;
}

export class CompanyType implements ICompanyType {
  constructor(
    public id?: number,
    public primarySector?: boolean | null,
    public secondarySector?: boolean | null,
    public tertiarySector?: boolean | null,
    public contactData?: IContactData | null
  ) {
    this.primarySector = this.primarySector ?? false;
    this.secondarySector = this.secondarySector ?? false;
    this.tertiarySector = this.tertiarySector ?? false;
  }
}

export function getCompanyTypeIdentifier(companyType: ICompanyType): number | undefined {
  return companyType.id;
}
