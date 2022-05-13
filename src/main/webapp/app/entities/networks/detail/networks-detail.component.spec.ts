import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NetworksDetailComponent } from './networks-detail.component';

describe('Networks Management Detail Component', () => {
  let comp: NetworksDetailComponent;
  let fixture: ComponentFixture<NetworksDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NetworksDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ networks: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NetworksDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NetworksDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load networks on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.networks).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
