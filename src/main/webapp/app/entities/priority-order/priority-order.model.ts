import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { Designation } from 'app/entities/enumerations/designation.model';

export interface IPriorityOrder {
  id?: number;
  qualifLogarit?: Designation;
  qualifAffiliate?: Designation;
  qualifDefinitive?: Designation;
  contactData?: IContactData | null;
}

export class PriorityOrder implements IPriorityOrder {
  constructor(
    public id?: number,
    public qualifLogarit?: Designation,
    public qualifAffiliate?: Designation,
    public qualifDefinitive?: Designation,
    public contactData?: IContactData | null
  ) {}
}

export function getPriorityOrderIdentifier(priorityOrder: IPriorityOrder): number | undefined {
  return priorityOrder.id;
}
