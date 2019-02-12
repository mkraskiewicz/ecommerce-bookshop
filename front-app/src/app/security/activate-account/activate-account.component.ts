import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import {Params, ActivatedRoute, Router} from '@angular/router';


@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.css']
})
export class ActivateAccountComponent implements OnInit {

  form: any = {};
  isReset = false;
  isResetFailed = false;
  errorMessage = '';
  token: string;
  sendAgain: string ="";

  constructor(private userService:UserService,private route:ActivatedRoute, 
    private router:Router) { }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
  		this.token = params['token'];
    });
  }

  onSubmit() {
    console.log(this.form);

 
    this.userService.acivateAccount(this.token).subscribe(
      data => {

        if(data.hasOwnProperty("error")){
          this.errorMessage = data["error"];
          console.log(  this.errorMessage );
          this.isResetFailed = true;
        }else {
        console.log(data);
        this.isReset = true;
        this.isResetFailed = false;}
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isResetFailed = true;
      }
    );
  }

  resendEmail(){
    this.userService.acivateAccount(this.token).subscribe(
      data => {

        if(data.hasOwnProperty("error")){
          this.errorMessage = data["error"];
          console.log(  this.errorMessage );
          this.isResetFailed = true;
        }else {
        console.log(data);
        this.sendAgain = "Activation mail has been sent again";
        }
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isResetFailed = true;
      }
    );
  }
}
