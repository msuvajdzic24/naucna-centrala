import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { JournalService } from 'src/app/services/journal/journal.service';
import { PublishService } from 'src/app/services/publish/publish.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-resubmit-article',
  templateUrl: './resubmit-article.component.html',
  styleUrls: ['./resubmit-article.component.scss']
})
export class ResubmitArticleComponent implements OnInit {

  private taskId;

  comment: any; 
  form: FormGroup;
  article: any;
  file: File;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private publishService: PublishService,
    private toastrService: ToastrService
  ) { 
    this.form = fb.group({
      'title' : [null, Validators.required],
      'keywords' : [null, Validators.required],
      'abstract' : [null, Validators.required]
    });
  }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
        this.taskId = params['taskId'];
      }
    );
    this.getArticle();
    this.getComment();
  }

  getArticle() {
    this.publishService.getArticle(this.taskId).subscribe(
      result => {
        this.article = result;
        this.form.setValue({
          title: result.title, 
          keywords: result.keywords,
          abstract: result.articleAbstract
        });
      }
    );
  }

  getComment() {
    this.publishService.getComment(this.taskId).subscribe(
      result => this.comment = result
    );
  }

  loadFile(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.file = fileList[0];
      console.log(this.file.name);
      console.log(this.file.type);
    }
  }

  resubmitArticle() {
    let formData = new FormData();
    formData.append('file', this.file, this.file.name);
    formData.append("articleId", this.article.id);
    formData.append('title', this.form.value.title);
    formData.append('articleAbstract', this.form.value.abstract);
    formData.append('keywords', this.form.value.keywords);
    console.log(formData);
    this.publishService.resubmitArticle(this.taskId, formData).subscribe(
      result => {
        console.log(result);
        this.toastrService.success(result);
      }
    )
  }

}
