import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PayMembershipFeeComponent } from './pay-membership-fee.component';

describe('PayMembershipFeeComponent', () => {
  let component: PayMembershipFeeComponent;
  let fixture: ComponentFixture<PayMembershipFeeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PayMembershipFeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PayMembershipFeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
