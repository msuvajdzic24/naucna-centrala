import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PublishService } from 'src/app/services/publish/publish.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-pay-membership-fee',
  templateUrl: './pay-membership-fee.component.html',
  styleUrls: ['./pay-membership-fee.component.scss']
})
export class PayMembershipFeeComponent implements OnInit {

  private taskId;
  private task;

  journal: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private publishService: PublishService,
    private toastrService: ToastrService
  ) { }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
        this.taskId = params['taskId'];
      }
    );
    this.getJournal();
  }

  getJournal() {
    this.publishService.getJournal(this.taskId).subscribe(
      data => {
        this.journal = data;
      }
    )
  }

  successfulPayment() {
    let object = {
      payment: true
    }
    this.publishService.payMembershipFee(this.taskId, object).subscribe(
      result => {
        this.task = result;
        this.toastrService.success("Successfully paid membership fee");
        this.router.navigate(['/submitArticle/' + this.task.id]);
      }
    );
  }

  unsuccessfulPayment() {
    let object = {
      payment: false
    }
    this.publishService.payMembershipFee(this.taskId, object).subscribe(
      result => {
        this.toastrService.error("Unsuccessfully paid membership fee");
        this.router.navigate(['/home']);
      }
    );
  }

}
