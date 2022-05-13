import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RelationService } from '../service/relation.service';

import { RelationComponent } from './relation.component';

describe('Relation Management Component', () => {
  let comp: RelationComponent;
  let fixture: ComponentFixture<RelationComponent>;
  let service: RelationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RelationComponent],
    })
      .overrideTemplate(RelationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RelationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RelationService);

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
    expect(comp.relations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
