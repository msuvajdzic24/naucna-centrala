import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublishService } from 'src/app/services/publish/publish.service';
import { saveAs } from 'file-saver';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-review-request',
  templateUrl: './review-request.component.html',
  styleUrls: ['./review-request.component.scss']
})
export class ReviewRequestComponent implements OnInit {

  private taskId;
  private article;
  private comment;

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
    this.comment = "";
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
  
  accept() {
    let object = Object();
    object["relevant"] = true;
    object['properlyFormatted'] = true;
    object['comment'] = "";
    this.publishService.decision(this.taskId, object).subscribe(
      result => {
        console.log(result);
        this.toastrService.success(result);
      }
    );
  }

  decline() {
    let object = Object();
    object["relevant"] = false;
    object['properlyFormatted'] = true;
    object['comment'] = "";
    this.publishService.decision(this.taskId, object).subscribe(
      result => {
        console.log(result);
        this.toastrService.success(result);
      }
    );
  }

  correction() {
    let object = Object();
    object["relevant"] = true;
    object['properlyFormatted'] = false;
    object['comment'] = this.comment.trim();
    this.publishService.decision(this.taskId, object).subscribe(
      result => {
        console.log(result);
        this.toastrService.success(result);
      }
    );
  }

}
