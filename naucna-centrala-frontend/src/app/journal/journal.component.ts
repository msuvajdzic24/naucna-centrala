import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JournalService } from '../services/journal/journal.service';
import { UtilService } from '../services/util/util.service';

@Component({
  selector: 'app-journal',
  templateUrl: './journal.component.html',
  styleUrls: ['./journal.component.scss']
})
export class JournalComponent implements OnInit {

  id: number;
  journal: any;
  role: string;

  constructor(
    private route: ActivatedRoute,
    private journalService: JournalService,
    private utilService: UtilService,
    private zone: NgZone
  ) { }

  ngOnInit() {
    this.role = this.utilService.getRole();
    this.route.params.subscribe(
      params => {
        this.id = Number(params['id']);
        this.getJournal();
      }
    );
  }

  getJournal() {
    this.journalService.getJournal(this.id).subscribe(
      data => this.journal = data
    )
  }

  subscribe(){
    this.journalService.subscribe(this.id).subscribe(
      data =>{
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )

  }

  unsubscribe() {
    this.journalService.unsubscribe(this.id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  payMembershipFee() {
    this.journalService.payMembershipFee(this.id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  listArticles() {
    
  }

  addArticle() {

  }
}
