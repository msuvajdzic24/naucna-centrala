import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseNewReviewerComponent } from './choose-new-reviewer.component';

describe('ChooseNewReviewerComponent', () => {
  let component: ChooseNewReviewerComponent;
  let fixture: ComponentFixture<ChooseNewReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseNewReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseNewReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
