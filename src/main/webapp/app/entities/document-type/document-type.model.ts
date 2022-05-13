import { IAffiliate } from 'app/entities/affiliate/affiliate.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IDocumentType {
  id?: number;
  initials?: string;
  documentName?: string;
  stateDocumentType?: State;
  affiliates?: IAffiliate[] | null;
}

export class DocumentType implements IDocumentType {
  constructor(
    public id?: number,
    public initials?: string,
    public documentName?: string,
    public stateDocumentType?: State,
    public affiliates?: IAffiliate[] | null
  ) {}
}

export function getDocumentTypeIdentifier(documentType: IDocumentType): number | undefined {
  return documentType.id;
}
