import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseReviewersComponent } from './choose-reviewers.component';

describe('ChooseReviewersComponent', () => {
  let component: ChooseReviewersComponent;
  let fixture: ComponentFixture<ChooseReviewersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseReviewersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseReviewersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
