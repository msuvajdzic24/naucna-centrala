import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EditionService } from '../services/edition/edition.service';

@Component({
  selector: 'app-edition',
  templateUrl: './edition.component.html',
  styleUrls: ['./edition.component.scss']
})
export class EditionComponent implements OnInit {

  id: number;
  edition: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private editionService: EditionService,
    private zone: NgZone
  ) { }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
        this.id = Number(params['id']);
        this.getEdition();
      }
    )
  }

  getEdition() {
    this.editionService.getEdition(this.id).subscribe(
      data => this.edition = data
    )
  }

  buy() {
    this.editionService.buyEdition(this.id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  listArticles() {
    
  }

}
