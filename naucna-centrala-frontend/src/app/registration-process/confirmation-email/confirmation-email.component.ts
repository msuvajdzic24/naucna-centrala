import { Component, OnInit } from '@angular/core';
import { RegistrationService } from 'src/app/services/registration/registration.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-confirmation-email',
  templateUrl: './confirmation-email.component.html',
  styleUrls: ['./confirmation-email.component.scss']
})
export class ConfirmationEmailComponent implements OnInit {

  success: boolean;
  taskId: string;

  constructor(
    private route: ActivatedRoute,
    private registrationService: RegistrationService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(
      params => {
        this.taskId = params['processId'];
      }
    );
    this.registrationService.confirmEmail(this.taskId).subscribe(
      result => {
        console.log(result);
        this.success = true;
      },
      error => {
        console.log(error);
        this.success = false;
      }
    );
  }

}
