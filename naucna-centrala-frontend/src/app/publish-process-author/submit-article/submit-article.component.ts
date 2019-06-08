import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { JournalService } from 'src/app/services/journal/journal.service';
import { PublishService } from 'src/app/services/publish/publish.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-submit-article',
  templateUrl: './submit-article.component.html',
  styleUrls: ['./submit-article.component.scss']
})
export class SubmitArticleComponent implements OnInit {

  private taskId;
  private journalId;

  form: FormGroup;
  areas: any;
  journal: any;
  submitter: any;
  file: File;

  numberOfAuthors: number;
  name:string;
  surname:string;
  email:string;
  city:string;
  country:string;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private journalService: JournalService,
    private publishService: PublishService,
    private toastrService: ToastrService
  ) { 
    this.form = fb.group({
      'title' : [null, Validators.required],
      'authors': [[]],
      'keywords' : [null, Validators.required],
      'abstract' : [null, Validators.required],
      'area': [null, Validators.required]
    });
  }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
        this.taskId = params['taskId'];
      }
    );
    this.getJournal();
    this.addSubmitter();
    this.numberOfAuthors = 1;
  }

  getJournal() {
    this.publishService.getJournal(this.taskId).subscribe(
      data => {
        this.journal = data;
        this.areas = data.areas;
      }
    )
  }
  
  addSubmitter() {
    this.publishService.getAuthor(this.taskId).subscribe(
      result => {
        this.submitter = result;
        this.form.value.authors.push(result);
      }
    );
  }

  addAuthor() {
    let o: Object = {
      'name': this.name,
      'surname': this.surname,
      'email': this.email,
      'city': this.city,
      'country': this.country
    }
    this.form.value.authors.push(o);
    this.name = "";
    this.surname = "";
    this.email = "";
    this.city = "";
    this.country = "";
    this.numberOfAuthors++;
  }

  removeAuthor(index: number) {
    this.form.value.authors.splice(index, 1);
    this.numberOfAuthors--;
  }

  loadFile(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.file = fileList[0];
      console.log(this.file.name);
      console.log(this.file.type);
    }
  }

  getAuthors() {
    let l = [];
    for (let i in this.form.value.authors) {
      l.push(this.form.value.authors[i]); // 1, "string", false
    }
    return l;
  }

  submitArticle() {
    let formData = new FormData();
    formData.append('file', this.file, this.file.name);
    formData.append('title', this.form.value.title);
    formData.append('journal', this.journal.name);
    formData.append('articleAbstract', this.form.value.abstract);
    formData.append('keywords', this.form.value.keywords);
    formData.append('area', this.form.value.area);
    formData.append('authors', JSON.stringify(this.form.value.authors));
    console.log(formData);
    this.publishService.submitArticle(this.taskId, formData).subscribe(
      result => {
        console.log(result);
        this.toastrService.success(result);
      }
    )
  }

}
