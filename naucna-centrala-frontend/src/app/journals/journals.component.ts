import { Component, OnInit, NgZone } from '@angular/core';
import { JournalService } from '../services/journal/journal.service';
import { UtilService } from '../services/util/util.service';

@Component({
  selector: 'app-journals',
  templateUrl: './journals.component.html',
  styleUrls: ['./journals.component.scss']
})
export class JournalsComponent implements OnInit {

  journals: any;
  role: string;

  constructor(
    private journalService: JournalService,
    private utilService: UtilService,
    private zone: NgZone
  ) { }

  ngOnInit() {
    this.role = this.utilService.getRole();
    this.getJournals();
  }

  getJournals() {
    this.journalService.getJournals().subscribe(
      data => this.journals = data
    )
  }

  subscribe(id: number){
    this.journalService.subscribe(id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  unsubscribe(id: number) {
    this.journalService.unsubscribe(id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  payMembershipFee(id: number) {
    this.journalService.payMembershipFee(id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  listArticles(id: number) {
    
  }

  addArticle(id: number) {

  }
}
