import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-logincontroller',
  templateUrl: './logincontroller.component.html',
  styleUrls: ['./logincontroller.component.css']
})
export class LogincontrollerComponent implements OnInit {

  form: any = {};
  isSentUp = false;
  isSentFailed = false;
  errorMessage = '';
  
  constructor(private userService:UserService) { }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.form);

 
    this.userService.remindEmail(this.form.email).subscribe(
      data => {

        if(data.hasOwnProperty("error")){
          this.errorMessage = data["error"];
          console.log(  this.errorMessage );
          this.isSentFailed = true;
        }else {
        console.log(data);
        this.isSentUp = true;
        this.isSentFailed = false;}
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isSentFailed = true;
      }
    );
  }
}
