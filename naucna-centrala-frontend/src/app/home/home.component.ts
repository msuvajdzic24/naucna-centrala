import { Component, OnInit } from '@angular/core';
import { UtilService } from '../services/util/util.service';
import { Router } from '@angular/router';
import { PublishService } from '../services/publish/publish.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  role: string;
  tasks: any;

  constructor(
    private router: Router,
    private utilService: UtilService,
    private publishService: PublishService
  ) { }

  ngOnInit() {
    this.publishService.getTasks().subscribe(
      result => {
        this.tasks = result
        console.log(result);
      }
    );
    this.role = this.utilService.getRole();
  }

  publishArticle() {
    this.router.navigate(['/chooseJournal'])
  }

}
