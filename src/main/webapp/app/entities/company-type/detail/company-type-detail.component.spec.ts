import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompanyTypeDetailComponent } from './company-type-detail.component';

describe('CompanyType Management Detail Component', () => {
  let comp: CompanyTypeDetailComponent;
  let fixture: ComponentFixture<CompanyTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ companyType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CompanyTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CompanyTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load companyType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.companyType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
