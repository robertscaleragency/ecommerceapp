import { TestBed } from '@angular/core/testing';

import { Luv2ShopFormService } from './luv2-shop-form.service';
import { HttpClientModule } from '@angular/common/http';

describe('Luv2ShopFormService', () => {
  let service: Luv2ShopFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[ HttpClientModule]
    });
    service = TestBed.inject(Luv2ShopFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
