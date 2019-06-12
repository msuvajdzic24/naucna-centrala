import { UtilService } from './../../services/util/util.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublishService } from 'src/app/services/publish/publish.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-choose-reviewers',
  templateUrl: './choose-reviewers.component.html',
  styleUrls: ['./choose-reviewers.component.scss']
})
export class ChooseReviewersComponent implements OnInit {

  private taskId;

  tab: string;
  article: any;
  all: any;
  area: any;
  similar: any;
  differentRegion: any;
  requiredReviewers: number;
  selectedNumber: number;
  selectedReviewers: any;
  username: any;

  constructor(
    private route: ActivatedRoute,
    private publishService: PublishService,
    private toastrService: ToastrService,
    private utilService: UtilService
  ) { }

  async ngOnInit() {
    this.route.params.subscribe(
      params => {
        this.taskId = params['taskId'];
      }
    );
    this.tab = "all";
    this.selectedNumber = 0;
    this.selectedReviewers = [];
    this.username = await this.utilService.getUsername();
    this.getReviewers();
  }

  getReviewers() {
    this.publishService.getReviewers(this.taskId).subscribe(
      result => {
        console.log(result)
        this.requiredReviewers = result.numberOfReviewers;
        this.all = result.all;
        this.all = this.all.filter(element => element.username != this.username);
        this.area = result.area;
        this.area = this.area.filter(element => element.username != this.username);
        for (let reviewerUsername of result.choosenReviewers) { 
          let obj = {
            "username": reviewerUsername,
            "delete": false
          }
          this.selectedReviewers.push(obj);
          this.selectedNumber++;
        }
      }
    );
  }

  changeTab(tab: string) {
    this.tab = tab;
  }

  addReviewer(username: string) {
    for (let reviewer of this.selectedReviewers) { 
      if (reviewer.username === username) {
        return;
      }
    }
    let obj = {
      "username": username,
      "delete": true
    }
    this.selectedReviewers.push(obj);
    this.selectedNumber++;
  }

  removeReviewer(index: number) {
    this.selectedReviewers.splice(index, 1);
    this.selectedNumber--;
  }

  finish() {
    let reviewers = [];
    for (let reviewer of this.selectedReviewers) { 
      reviewers.push(reviewer.username);
    }
    let object = {
      'reviewers': reviewers
    }
    this.publishService.chooseReviewers(this.taskId, object).subscribe(
      result => {
        this.toastrService.success(result);
      }
    )
  }

}
