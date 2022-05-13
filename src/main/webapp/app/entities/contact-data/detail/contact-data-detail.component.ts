import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactData } from '../contact-data.model';

@Component({
  selector: 'redsurgir-contact-data-detail',
  templateUrl: './contact-data-detail.component.html',
})
export class ContactDataDetailComponent implements OnInit {
  contactData: IContactData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactData }) => {
      this.contactData = contactData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
