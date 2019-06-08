import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestCompomentComponent } from './test-compoment.component';

describe('TestCompomentComponent', () => {
  let component: TestCompomentComponent;
  let fixture: ComponentFixture<TestCompomentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestCompomentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestCompomentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
