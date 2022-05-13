import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PotentialDetailComponent } from './potential-detail.component';

describe('Potential Management Detail Component', () => {
  let comp: PotentialDetailComponent;
  let fixture: ComponentFixture<PotentialDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PotentialDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ potential: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PotentialDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PotentialDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load potential on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.potential).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
