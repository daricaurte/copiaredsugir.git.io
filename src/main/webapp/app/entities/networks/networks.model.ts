import { IContactData } from 'app/entities/contact-data/contact-data.model';

export interface INetworks {
  id?: number;
  networks3rdSectorHl?: boolean;
  academicNetworksHl?: boolean;
  customerNetworksHl?: boolean;
  employeeNetworksHl?: boolean;
  networksEntFinanHl?: boolean;
  stateNetworksHl?: boolean;
  internationalNetworksHl?: boolean;
  mediaNetworksComuncHl?: boolean;
  communityOrgNetworksHl?: boolean;
  regulatoryOrganismsNetworks?: boolean;
  networksProvidersHl?: boolean;
  socialNetworks?: boolean;
  shareholderNetworksHl?: boolean;
  contactData?: IContactData | null;
}

export class Networks implements INetworks {
  constructor(
    public id?: number,
    public networks3rdSectorHl?: boolean,
    public academicNetworksHl?: boolean,
    public customerNetworksHl?: boolean,
    public employeeNetworksHl?: boolean,
    public networksEntFinanHl?: boolean,
    public stateNetworksHl?: boolean,
    public internationalNetworksHl?: boolean,
    public mediaNetworksComuncHl?: boolean,
    public communityOrgNetworksHl?: boolean,
    public regulatoryOrganismsNetworks?: boolean,
    public networksProvidersHl?: boolean,
    public socialNetworks?: boolean,
    public shareholderNetworksHl?: boolean,
    public contactData?: IContactData | null
  ) {
    this.networks3rdSectorHl = this.networks3rdSectorHl ?? false;
    this.academicNetworksHl = this.academicNetworksHl ?? false;
    this.customerNetworksHl = this.customerNetworksHl ?? false;
    this.employeeNetworksHl = this.employeeNetworksHl ?? false;
    this.networksEntFinanHl = this.networksEntFinanHl ?? false;
    this.stateNetworksHl = this.stateNetworksHl ?? false;
    this.internationalNetworksHl = this.internationalNetworksHl ?? false;
    this.mediaNetworksComuncHl = this.mediaNetworksComuncHl ?? false;
    this.communityOrgNetworksHl = this.communityOrgNetworksHl ?? false;
    this.regulatoryOrganismsNetworks = this.regulatoryOrganismsNetworks ?? false;
    this.networksProvidersHl = this.networksProvidersHl ?? false;
    this.socialNetworks = this.socialNetworks ?? false;
    this.shareholderNetworksHl = this.shareholderNetworksHl ?? false;
  }
}

export function getNetworksIdentifier(networks: INetworks): number | undefined {
  return networks.id;
}
