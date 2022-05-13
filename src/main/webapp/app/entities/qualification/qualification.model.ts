import { IContactData } from 'app/entities/contact-data/contact-data.model';
import { Designation } from 'app/entities/enumerations/designation.model';

export interface IQualification {
  id?: number;
  qualif1?: Designation;
  qualif2?: Designation;
  qualif3?: Designation;
  qualif4?: Designation;
  qualif1BussinessViability?: Designation;
  contactData?: IContactData | null;
}

export class Qualification implements IQualification {
  constructor(
    public id?: number,
    public qualif1?: Designation,
    public qualif2?: Designation,
    public qualif3?: Designation,
    public qualif4?: Designation,
    public qualif1BussinessViability?: Designation,
    public contactData?: IContactData | null
  ) {}
}

export function getQualificationIdentifier(qualification: IQualification): number | undefined {
  return qualification.id;
}
