import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublishService } from 'src/app/services/publish/publish.service';
import { ToastrService } from 'ngx-toastr';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-analyze-reviews',
  templateUrl: './analyze-reviews.component.html',
  styleUrls: ['./analyze-reviews.component.scss']
})
export class AnalyzeReviewsComponent implements OnInit {

  private taskId;

  article: any;
  reviews: any;
  authorMessage: any;
  firstCycle: boolean;

  constructor(
    private route: ActivatedRoute,
    private publishService: PublishService,
    private toastrService: ToastrService
  ) { }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
        this.taskId = params['taskId'];
      }
    );
    this.getArticle();
    this.getReviews();
    this.isFirstCycle();
  }

  getArticle() {
    this.publishService.getArticle(this.taskId).subscribe(
      result => this.article = result
    );
  }

  getReviews() {
    this.publishService.getReviewsForEditor(this.taskId).subscribe(
      result => this.reviews = result
    );
  }

  isFirstCycle() {
    this.publishService.isFirstCylce(this.taskId).subscribe(
      result => {
        this.firstCycle = result;
        if (this.firstCycle != true) {
          this.getAuthorMessage();
        }
      }
    );
  }

  getAuthorMessage() {
    this.publishService.getAuthorMessage(this.taskId).subscribe(
      result => {
        console.log("KOMENTAR" + result)
        this.authorMessage = result;
      }
    );
  }

  download() {
    this.publishService.downloadArticle(this.article.id).subscribe(
      data => {
        var blob = new Blob([data], { type: 'application/pdf' })
        var title = this.article.title + ".pdf";
        saveAs(blob, title);
      }
    )
  }

  reject() {
    let object = {
      "decision": "reject"
    }
    this.publishService.analyzeReviews(this.taskId, object).subscribe(
      result => {
        this.toastrService.success(result);
      }
    );
  }

  accept() {
    let object = {
      "decision": "accept"
    }
    this.publishService.analyzeReviews(this.taskId, object).subscribe(
      result => {
        this.toastrService.success(result);
      }
    );
  }

  smallCorrection() {
    let object = {
      "decision": "small correction"
    }
    this.publishService.analyzeReviews(this.taskId, object).subscribe(
      result => {
        this.toastrService.success(result);
      }
    );
  }

  bigCorrection() {
    let object = {
      "decision": "big correction"
    }
    this.publishService.analyzeReviews(this.taskId, object).subscribe(
      result => {
        this.toastrService.success(result);
      }
    );
  }

  additionalReview() {
    let object = {
      "decision": "additional review"
    }
    this.publishService.analyzeReviews(this.taskId, object).subscribe(
      result => {
        this.toastrService.success(result);
      }
    );
  }

}
