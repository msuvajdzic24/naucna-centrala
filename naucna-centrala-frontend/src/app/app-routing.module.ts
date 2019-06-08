import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LoginGuardService } from './services/login-guard/login-guard.service';
import { RoleGuardService } from './services/role-guard/role-guard.service';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';
import { HomeComponent } from './home/home.component';
import { JournalsComponent } from './journals/journals.component';
import { JournalComponent } from './journal/journal.component';
import { EditionsComponent } from './editions/editions.component';
import { EditionComponent } from './edition/edition.component';
import { ArticleComponent } from './article/article.component';
import { ArticlesComponent } from './articles/articles.component';
import { RegistrationComponent } from './registration-process/registration/registration.component';
import { ConfirmationEmailComponent } from './registration-process/confirmation-email/confirmation-email.component';
import { ChooseJournalComponent } from './publish-process-author/choose-journal/choose-journal.component';
import { SubmitArticleComponent } from './publish-process-author/submit-article/submit-article.component';
import { ResubmitArticleComponent } from './publish-process-author/resubmit-article/resubmit-article.component';
import { ChooseReviewersComponent } from './publish-process-editor/choose-reviewers/choose-reviewers.component';
import { ReviewComponent } from './publish-process-reviewer/review/review.component';
import { AnalyzeReviewsComponent } from './publish-process-editor/analyze-reviews/analyze-reviews.component';
import { CorrectionComponent } from './publish-process-author/correction/correction.component';
import { ReviewRequestComponent } from './publish-process-editor/review-request/review-request.component';
import { PayMembershipFeeComponent } from './publish-process-author/pay-membership-fee/pay-membership-fee.component';
import { ChooseNewReviewerComponent } from './publish-process-editor/choose-new-reviewer/choose-new-reviewer.component';

const routes: Routes = [
  { path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  { path: 'login',
    component: LoginComponent,
    canActivate: [LoginGuardService]
  },
  { path: 'registration',
    component: RegistrationComponent
  },
  { path: 'confirmEmail/:processId',
    component: ConfirmationEmailComponent
  },
  { path: 'home',
    component: HomeComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'admin|editor|reviewer|author|buyer'}
  },
  { path: 'journals',
    component: JournalsComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'author|buyer'}
  },
  { path: 'journal/:id',
    component: JournalComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'author|buyer'}
  },
  { path: 'editions',
    component: EditionsComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'buyer'}
  },
  { path: 'edition/:id',
    component: EditionComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'buyer'}
  },
  { path: 'articles',
    component: ArticlesComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'buyer'}
  },
  { path: 'article/:id',
    component: ArticleComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'buyer'}
  },
  { path: 'chooseJournal',
    component: ChooseJournalComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'author'}
  },
  { path: 'submitArticle/:taskId',
    component: SubmitArticleComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'author'}
  },
  { path: 'reviewRequest/:taskId',
    component: ReviewRequestComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'editor'}
  },
  { path: 'resubmitArticle/:taskId',
    component: ResubmitArticleComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'author'}
  },
  { path: 'chooseReviewers/:taskId',
    component: ChooseReviewersComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'editor'}
  },
  { path: 'chooseNewReviewer/:taskId',
    component: ChooseNewReviewerComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'editor'}
  },
  { path: 'review/:taskId',
    component: ReviewComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'editor|reviewer'}
  },
  { path: 'analyzeReviews/:taskId',
    component: AnalyzeReviewsComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'editor'}
  },
  { path: 'correction/:taskId',
    component: CorrectionComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'author'}
  },
  { path: 'payMembershipFee/:taskId',
    component: PayMembershipFeeComponent,
    canActivate: [RoleGuardService],
    data: {expectedRoles: 'author'}
  },
  { path: '**', 
    component: NotFoundPageComponent 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
