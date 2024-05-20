import { TestBed } from '@angular/core/testing';

import { ReadOnlyService } from './read-only.service';

describe('ReadOnlyService', () => {
  let service: ReadOnlyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReadOnlyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
