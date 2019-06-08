import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { TestCompomentComponent } from './test-compoment/test-compoment.component';
import { AuthenticationService } from './services/authentication/authentication.service';
import { LoginGuardService } from './services/login-guard/login-guard.service';
import { RoleGuardService } from './services/role-guard/role-guard.service';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';
import { UtilService } from './services/util/util.service';
import { HomeComponent } from './home/home.component';
import { JournalsComponent } from './journals/journals.component';
import { JournalComponent } from './journal/journal.component';
import { EditionComponent } from './edition/edition.component';
import { EditionsComponent } from './editions/editions.component';
import { ArticleComponent } from './article/article.component';
import { ArticlesComponent } from './articles/articles.component';
import { JournalService } from './services/journal/journal.service';
import { EditionService } from './services/edition/edition.service';
import { ArticleService } from './services/article/article.service';
import { RegistrationService } from './services/registration/registration.service';
import { PublishService } from './services/publish/publish.service';
import { RegistrationComponent } from './registration-process/registration/registration.component';
import { ConfirmationEmailComponent } from './registration-process/confirmation-email/confirmation-email.component';
import { ChooseJournalComponent } from './publish-process-author/choose-journal/choose-journal.component';
import { SubmitArticleComponent } from './publish-process-author/submit-article/submit-article.component';
import { ResubmitArticleComponent } from './publish-process-author/resubmit-article/resubmit-article.component';
import { ReviewComponent } from './publish-process-reviewer/review/review.component';
import { ChooseReviewersComponent } from './publish-process-editor/choose-reviewers/choose-reviewers.component';
import { CorrectionComponent } from './publish-process-author/correction/correction.component';
import { AnalyzeReviewsComponent } from './publish-process-editor/analyze-reviews/analyze-reviews.component';
import { ReviewRequestComponent } from './publish-process-editor/review-request/review-request.component';
import { PayMembershipFeeComponent } from './publish-process-author/pay-membership-fee/pay-membership-fee.component';
import { ChooseNewReviewerComponent } from './publish-process-editor/choose-new-reviewer/choose-new-reviewer.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    TestCompomentComponent,
    RegistrationComponent,
    NotFoundPageComponent,
    HomeComponent,
    JournalsComponent,
    JournalComponent,
    EditionComponent,
    EditionsComponent,
    ArticleComponent,
    ArticlesComponent,
    ChooseJournalComponent,
    PayMembershipFeeComponent,
    ReviewRequestComponent,
    ConfirmationEmailComponent,
    SubmitArticleComponent,
    ResubmitArticleComponent,
    ReviewComponent,
    CorrectionComponent,
    ChooseReviewersComponent,
    AnalyzeReviewsComponent,
    ChooseNewReviewerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'}),
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-top-center',
      preventDuplicates: true,
    })
  ],
  providers: [AuthenticationService, LoginGuardService, RoleGuardService, UtilService,
    JournalService, EditionService, ArticleService, RegistrationService, PublishService, 
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
