import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalyzeReviewsComponent } from './analyze-reviews.component';

describe('AnalyzeReviewsComponent', () => {
  let component: AnalyzeReviewsComponent;
  let fixture: ComponentFixture<AnalyzeReviewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnalyzeReviewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalyzeReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
