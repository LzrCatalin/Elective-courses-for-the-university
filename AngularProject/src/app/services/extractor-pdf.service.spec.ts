import { TestBed } from '@angular/core/testing';

import { ExtractorPdfService } from './extractor-pdf.service';

describe('ExtractorPdfService', () => {
  let service: ExtractorPdfService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExtractorPdfService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
