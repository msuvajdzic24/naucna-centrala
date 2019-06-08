import { Component, OnInit } from '@angular/core';
import { RegistrationService } from 'src/app/services/registration/registration.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  private formFieldsDto;
  private formFields;
  private taskId;
  private processInstance;

  constructor(
    private registrationService: RegistrationService,
    private router: Router,
    private toastrService: ToastrService
  ) {
    this.registrationService.startProcess().subscribe(
      result => {
        this.formFieldsDto = result;
        this.formFields = result.formFields;
        console.log(this.formFields);
        this.taskId = result.taskId;
        this.processInstance = result.processInstanceId;
      }
    );
  }

  ngOnInit() {
  }

  register(value) {
    let form = new Array();
    for (var property in value) {
      form.push({fieldId : property, fieldValue : value[property]});
    }
    console.log(form);
    this.registrationService.register(this.taskId, form).subscribe(
      result => {
        console.log(result);
        this.toastrService.success(result.toString() + " Please confirm email.");
        this.router.navigate(['/login']);
      },
      error => {
        console.log(error);
        this.toastrService.error(error.error);
      }
    );
  }

}
