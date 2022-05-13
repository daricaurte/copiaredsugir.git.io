import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContactDataDetailComponent } from './contact-data-detail.component';

describe('ContactData Management Detail Component', () => {
  let comp: ContactDataDetailComponent;
  let fixture: ComponentFixture<ContactDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ contactData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ContactDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContactDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contactData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.contactData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
