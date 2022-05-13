import { IContactData } from 'app/entities/contact-data/contact-data.model';

export interface IPotential {
  id?: number;
  exchangePartner?: boolean;
  consultant?: boolean;
  client?: boolean;
  collaborator?: boolean;
  provider?: boolean;
  referrer?: boolean;
  contactData?: IContactData | null;
}

export class Potential implements IPotential {
  constructor(
    public id?: number,
    public exchangePartner?: boolean,
    public consultant?: boolean,
    public client?: boolean,
    public collaborator?: boolean,
    public provider?: boolean,
    public referrer?: boolean,
    public contactData?: IContactData | null
  ) {
    this.exchangePartner = this.exchangePartner ?? false;
    this.consultant = this.consultant ?? false;
    this.client = this.client ?? false;
    this.collaborator = this.collaborator ?? false;
    this.provider = this.provider ?? false;
    this.referrer = this.referrer ?? false;
  }
}

export function getPotentialIdentifier(potential: IPotential): number | undefined {
  return potential.id;
}
