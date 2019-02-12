import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/security/auth/token-storage.service';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {


	private dataFetched = false;
	private loginError:boolean;
	private loggedIn:boolean;
	private credential = {'username':'', 'password':''};

	private user: User = new User();
	private updateSuccess: boolean;
	private newPassword: string;
	private incorrectPassword: boolean;

  private roles: string[];
  private authority: string;
  private username: string;
  private isActivated: boolean;

  constructor(private tokenStorage: TokenStorageService, private userService: UserService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()){
      this.isActivated = true;
    } else {
      this.isActivated = false;
    }
  }

  onUpdateUserInfo () {
  	// this.userService.updateUserInfo(this.user, this.newPassword).subscribe(
  	// 	res => {
  	// 		console.log(res.text());
  	// 		this.updateSuccess=true;
  	// 	},
  	// 	error => {
  	// 		console.log(error.text());
  	// 		let errorMessage = error.text();
  	// 		if(errorMessage==="Incorrect current password!") this.incorrectPassword=true;
  	// 	}
  	// );
  }

}
