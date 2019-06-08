import { Component, OnInit, NgZone } from '@angular/core';
import { EditionService } from '../services/edition/edition.service';

@Component({
  selector: 'app-editions',
  templateUrl: './editions.component.html',
  styleUrls: ['./editions.component.scss']
})
export class EditionsComponent implements OnInit {

  editions: any

  constructor(
    private editionService: EditionService,
    private zone: NgZone
  ) { }

  ngOnInit() {
    this.getEditions();
  }

  getEditions() {
    this.editionService.getEditions().subscribe(
      data => this.editions = data
    )
  }

  buy(id: number) {
    this.editionService.buyEdition(id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  listArticles(id: number) {
    
  }

}
