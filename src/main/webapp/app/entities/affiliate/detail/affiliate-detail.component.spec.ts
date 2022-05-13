import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AffiliateDetailComponent } from './affiliate-detail.component';

describe('Affiliate Management Detail Component', () => {
  let comp: AffiliateDetailComponent;
  let fixture: ComponentFixture<AffiliateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AffiliateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ affiliate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AffiliateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AffiliateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load affiliate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.affiliate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
