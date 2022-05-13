import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PriorityOrderDetailComponent } from './priority-order-detail.component';

describe('PriorityOrder Management Detail Component', () => {
  let comp: PriorityOrderDetailComponent;
  let fixture: ComponentFixture<PriorityOrderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PriorityOrderDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ priorityOrder: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PriorityOrderDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PriorityOrderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load priorityOrder on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.priorityOrder).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
