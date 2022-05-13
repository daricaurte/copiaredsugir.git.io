import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AffiliateService } from '../service/affiliate.service';

import { AffiliateComponent } from './affiliate.component';

describe('Affiliate Management Component', () => {
  let comp: AffiliateComponent;
  let fixture: ComponentFixture<AffiliateComponent>;
  let service: AffiliateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AffiliateComponent],
    })
      .overrideTemplate(AffiliateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AffiliateComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AffiliateService);

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
    expect(comp.affiliates?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
