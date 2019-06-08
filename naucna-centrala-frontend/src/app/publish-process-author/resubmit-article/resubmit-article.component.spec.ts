import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResubmitArticleComponent } from './resubmit-article.component';

describe('ResubmitArticleComponent', () => {
  let component: ResubmitArticleComponent;
  let fixture: ComponentFixture<ResubmitArticleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResubmitArticleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResubmitArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
