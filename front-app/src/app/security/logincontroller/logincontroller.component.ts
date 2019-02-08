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
  loading = false;
  
  constructor(private userService:UserService) { }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.form);
    console.log(this.loading);
    this.loading = true;
    console.log(this.loading);
    this.userService.remindEmail(this.form.email).subscribe(
      data => {
        this.loading = false;
        if(data.hasOwnProperty("error")){
          this.errorMessage = data["error"];
          console.log(  this.errorMessage );
          this.isSentFailed = true;
          this.loading = false;
        }else {
        console.log(data);
        console.log("passed");
        this.loading = false;
        this.isSentUp = true;
        this.isSentFailed = false;}
      },
      error => {
        console.log(error);
        console.log(error);
        this.loading = false;
        this.errorMessage = error.error.message;
        this.isSentFailed = true;
      }
    );
  }
}
