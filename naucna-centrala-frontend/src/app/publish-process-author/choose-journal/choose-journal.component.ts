import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PublishService } from 'src/app/services/publish/publish.service';
import { JournalService } from 'src/app/services/journal/journal.service';

@Component({
  selector: 'app-choose-journal',
  templateUrl: './choose-journal.component.html',
  styleUrls: ['./choose-journal.component.scss']
})
export class ChooseJournalComponent implements OnInit {

  private taskId;
  private task;
  private journals: any;

  constructor(
    private publishService: PublishService,
    private journalService: JournalService,
    private router: Router
  ) {
    this.publishService.startProcess().subscribe(
      result => {
        this.taskId = result;
        console.log(this.taskId);
      }
    );
  }

  ngOnInit() {
    this.getJournals();
  }

  getJournals() {
    this.journalService.getJournals().subscribe(
      data => {
        this.journals = data;
        console.log(data);
      }
    )
  }

  addArticle(i: number) {
    let object = {
      "journalId": this.journals[i]['id']
    }
    this.publishService.chooseJournal(this.taskId, object).subscribe(
      result => {
        this.task = result;
        if (this.task.name === "Izvrsenje uplate") {
          this.router.navigate(['/payMembershipFee/' + this.task.id]);
        } else {
          this.router.navigate(['/submitArticle/' + this.task.id]);
        }
      }
    )
  }

}
