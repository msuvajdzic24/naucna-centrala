import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublishService } from 'src/app/services/publish/publish.service';
import { ToastrService } from 'ngx-toastr';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-correction',
  templateUrl: './correction.component.html',
  styleUrls: ['./correction.component.scss']
})
export class CorrectionComponent implements OnInit {

  private taskId;

  article: any;
  reviews: any;
  file: File;
  form: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private publishService: PublishService,
    private toastrService: ToastrService,
    private fb: FormBuilder,
  ) { 
    this.form = fb.group({
      'title' : [null, Validators.required],
      'keywords' : [null, Validators.required],
      'abstract' : [null, Validators.required],
      'authorMessage': [null, Validators.required]
    });
  }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
        this.taskId = params['taskId'];
      }
    );
    this.getArticle();
    this.getReviews();
  }

  getArticle() {
    this.publishService.getArticle(this.taskId).subscribe(
      result => {
        this.article = result;
        this.form.setValue({
          title: result.title, 
          keywords: result.keywords,
          abstract: result.articleAbstract,
          authorMessage: ''
        });
      }
    );
  }

  getReviews() {
    this.publishService.getReviewsForAuthor(this.taskId).subscribe(
      result => this.reviews = result
    )
  }

  loadFile(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.file = fileList[0];
      console.log(this.file.name);
      console.log(this.file.type);
    }
  }

  submitCorrection() {
    let formData = new FormData();
    formData.append('file', this.file, this.file.name);
    formData.append("articleId", this.article.id);
    formData.append('title', this.form.value.title);
    formData.append('articleAbstract', this.form.value.abstract);
    formData.append('keywords', this.form.value.keywords);
    formData.append('authorMessage', this.form.value.authorMessage);
    console.log(formData);
    this.publishService.submitCorrection(this.taskId, formData).subscribe(
      result => {
        console.log(result);
        this.toastrService.success(result);
      }
    )
  }

}
