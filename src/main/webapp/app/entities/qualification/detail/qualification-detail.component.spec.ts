import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QualificationDetailComponent } from './qualification-detail.component';

describe('Qualification Management Detail Component', () => {
  let comp: QualificationDetailComponent;
  let fixture: ComponentFixture<QualificationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QualificationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qualification: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QualificationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QualificationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qualification on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qualification).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
