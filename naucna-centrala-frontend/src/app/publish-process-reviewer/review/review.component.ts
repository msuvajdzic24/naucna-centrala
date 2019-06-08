import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublishService } from 'src/app/services/publish/publish.service';
import { ToastrService } from 'ngx-toastr';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent implements OnInit {

  private taskId;

  article: any;
  commentEditor: any;
  commentAuthor: any;
  suggestion: any;

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
    this.commentEditor = "";
    this.commentAuthor = "";
    this.suggestion = "accept";
  }

  
  getArticle() {
    this.publishService.getArticle(this.taskId).subscribe(
      result => this.article = result
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

  review() {
    let object = {
      "commentEditor": this.commentEditor.trim(),
      "commentAuthor": this.commentAuthor.trim(),
      "suggestion": this.suggestion
    }
    console.log(object);
    this.publishService.review(this.taskId, object).subscribe(
      result => {
        this.toastrService.success(result);
      }
    );
  }

}
