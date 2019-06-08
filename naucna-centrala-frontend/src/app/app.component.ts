import { Component } from '@angular/core';
import { UtilService } from './services/util/util.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'scientific-center-front';
  role: string;

  constructor(
    private utilSerivce: UtilService, 
  ) { }

  ngOnInit() {
  }

  checkRole() {
    this.role = this.utilSerivce.getRole();
  }
}
