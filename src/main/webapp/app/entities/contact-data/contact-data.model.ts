import { ICompanyType } from 'app/entities/company-type/company-type.model';
import { IRelation } from 'app/entities/relation/relation.model';
import { IContact } from 'app/entities/contact/contact.model';
import { INetworks } from 'app/entities/networks/networks.model';
import { IQualification } from 'app/entities/qualification/qualification.model';
import { IPriorityOrder } from 'app/entities/priority-order/priority-order.model';
import { IPotential } from 'app/entities/potential/potential.model';
import { IAffiliate } from 'app/entities/affiliate/affiliate.model';

export interface IContactData {
  id?: number;
  documentNumberContact?: string;
  cellPhoneNumber?: string;
  country?: string;
  city?: string;
  email?: string;
  department?: string;
  firstName?: string;
  secondName?: string | null;
  firstLastName?: string;
  secondLastName?: string | null;
  neighborhood?: string;
  companyTypes?: ICompanyType[] | null;
  relations?: IRelation[] | null;
  contacts?: IContact[] | null;
  networks?: INetworks[] | null;
  qualifications?: IQualification[] | null;
  priorityOrders?: IPriorityOrder[] | null;
  potentials?: IPotential[] | null;
  affiliate?: IAffiliate | null;
}

export class ContactData implements IContactData {
  constructor(
    public id?: number,
    public documentNumberContact?: string,
    public cellPhoneNumber?: string,
    public country?: string,
    public city?: string,
    public email?: string,
    public department?: string,
    public firstName?: string,
    public secondName?: string | null,
    public firstLastName?: string,
    public secondLastName?: string | null,
    public neighborhood?: string,
    public companyTypes?: ICompanyType[] | null,
    public relations?: IRelation[] | null,
    public contacts?: IContact[] | null,
    public networks?: INetworks[] | null,
    public qualifications?: IQualification[] | null,
    public priorityOrders?: IPriorityOrder[] | null,
    public potentials?: IPotential[] | null,
    public affiliate?: IAffiliate | null
  ) {}
}

export function getContactDataIdentifier(contactData: IContactData): number | undefined {
  return contactData.id;
}
