import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RelationDetailComponent } from './relation-detail.component';

describe('Relation Management Detail Component', () => {
  let comp: RelationDetailComponent;
  let fixture: ComponentFixture<RelationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RelationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ relation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RelationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RelationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load relation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.relation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
