import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PotentialService } from '../service/potential.service';

import { PotentialComponent } from './potential.component';

describe('Potential Management Component', () => {
  let comp: PotentialComponent;
  let fixture: ComponentFixture<PotentialComponent>;
  let service: PotentialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PotentialComponent],
    })
      .overrideTemplate(PotentialComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PotentialComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PotentialService);

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
    expect(comp.potentials?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
