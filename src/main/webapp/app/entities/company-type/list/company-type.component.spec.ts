import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CompanyTypeService } from '../service/company-type.service';

import { CompanyTypeComponent } from './company-type.component';

describe('CompanyType Management Component', () => {
  let comp: CompanyTypeComponent;
  let fixture: ComponentFixture<CompanyTypeComponent>;
  let service: CompanyTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CompanyTypeComponent],
    })
      .overrideTemplate(CompanyTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CompanyTypeService);

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
    expect(comp.companyTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
