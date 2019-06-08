import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublishService } from 'src/app/services/publish/publish.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-choose-new-reviewer',
  templateUrl: './choose-new-reviewer.component.html',
  styleUrls: ['./choose-new-reviewer.component.scss']
})
export class ChooseNewReviewerComponent implements OnInit {

  private taskId;

  tab: string;
  article: any;
  all: any;
  area: any;
  similar: any;
  differentRegion: any;
  requiredReviewers: number;
  selectedReviewers: any;

  addedNew: boolean;
  newReviewer: any;

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
    this.tab = "all";
    this.newReviewer = null;
    this.addedNew = false;
    this.selectedReviewers = [];
    this.getReviewers();
  }

  getReviewers() {
    this.publishService.getReviewers(this.taskId).subscribe(
      result => {
        console.log(result)
        this.requiredReviewers = result.numberOfReviewers;
        this.all = result.all;
        this.area = result.area;
        this.similar = result.similar;
        this.differentRegion = result.differentRegion;
        for (let reviewerUsername of result.choosenReviewers) { 
          let obj = {
            "username": reviewerUsername,
            "delete": false
          }
          this.selectedReviewers.push(obj);
        }
      }
    );
  }

  changeTab(tab: string) {
    this.tab = tab;
  }

  addNewReviewer(username: string) {
    for (let reviewer of this.selectedReviewers) { 
      if (reviewer.username === username) {
        return;
      }
    }
    this.newReviewer = username;
    this.addedNew = true;
  }

  removeNewReviewer() {
    this.newReviewer = null;
    this.addedNew = false;
  }

  finish() {
    let object = {
      'reviewer': this.newReviewer
    }
    this.publishService.chooseNewReviewer(this.taskId, object).subscribe(
      result => {
        this.toastrService.success(result);
      }
    )
  }

}
