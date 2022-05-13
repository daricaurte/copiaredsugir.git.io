import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'document-type',
        data: { pageTitle: 'redsurgirApp.documentType.home.title' },
        loadChildren: () => import('./document-type/document-type.module').then(m => m.DocumentTypeModule),
      },
      {
        path: 'affiliate',
        data: { pageTitle: 'redsurgirApp.affiliate.home.title' },
        loadChildren: () => import('./affiliate/affiliate.module').then(m => m.AffiliateModule),
      },
      {
        path: 'company-type',
        data: { pageTitle: 'redsurgirApp.companyType.home.title' },
        loadChildren: () => import('./company-type/company-type.module').then(m => m.CompanyTypeModule),
      },
      {
        path: 'contact-data',
        data: { pageTitle: 'redsurgirApp.contactData.home.title' },
        loadChildren: () => import('./contact-data/contact-data.module').then(m => m.ContactDataModule),
      },
      {
        path: 'relation',
        data: { pageTitle: 'redsurgirApp.relation.home.title' },
        loadChildren: () => import('./relation/relation.module').then(m => m.RelationModule),
      },
      {
        path: 'contact',
        data: { pageTitle: 'redsurgirApp.contact.home.title' },
        loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule),
      },
      {
        path: 'networks',
        data: { pageTitle: 'redsurgirApp.networks.home.title' },
        loadChildren: () => import('./networks/networks.module').then(m => m.NetworksModule),
      },
      {
        path: 'qualification',
        data: { pageTitle: 'redsurgirApp.qualification.home.title' },
        loadChildren: () => import('./qualification/qualification.module').then(m => m.QualificationModule),
      },
      {
        path: 'priority-order',
        data: { pageTitle: 'redsurgirApp.priorityOrder.home.title' },
        loadChildren: () => import('./priority-order/priority-order.module').then(m => m.PriorityOrderModule),
      },
      {
        path: 'potential',
        data: { pageTitle: 'redsurgirApp.potential.home.title' },
        loadChildren: () => import('./potential/potential.module').then(m => m.PotentialModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
