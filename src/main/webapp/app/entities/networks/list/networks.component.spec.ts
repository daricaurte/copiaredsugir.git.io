import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NetworksService } from '../service/networks.service';

import { NetworksComponent } from './networks.component';

describe('Networks Management Component', () => {
  let comp: NetworksComponent;
  let fixture: ComponentFixture<NetworksComponent>;
  let service: NetworksService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NetworksComponent],
    })
      .overrideTemplate(NetworksComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NetworksComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NetworksService);

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
    expect(comp.networks?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
