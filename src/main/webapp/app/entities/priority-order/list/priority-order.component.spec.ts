import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PriorityOrderService } from '../service/priority-order.service';

import { PriorityOrderComponent } from './priority-order.component';

describe('PriorityOrder Management Component', () => {
  let comp: PriorityOrderComponent;
  let fixture: ComponentFixture<PriorityOrderComponent>;
  let service: PriorityOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PriorityOrderComponent],
    })
      .overrideTemplate(PriorityOrderComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PriorityOrderComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PriorityOrderService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.priorityOrders?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
