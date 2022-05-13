import { IContactData } from 'app/entities/contact-data/contact-data.model';

export interface IRelation {
  id?: number;
  familyRelation?: boolean;
  workRelation?: boolean;
  personalRelation?: boolean;
  contactData?: IContactData | null;
}

export class Relation implements IRelation {
  constructor(
    public id?: number,
    public familyRelation?: boolean,
    public workRelation?: boolean,
    public personalRelation?: boolean,
    public contactData?: IContactData | null
  ) {
    this.familyRelation = this.familyRelation ?? false;
    this.workRelation = this.workRelation ?? false;
    this.personalRelation = this.personalRelation ?? false;
  }
}

export function getRelationIdentifier(relation: IRelation): number | undefined {
  return relation.id;
}
