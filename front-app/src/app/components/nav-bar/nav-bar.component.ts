import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../security/auth/token-storage.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  private roles: string[];
  private authority: string;
  private username: string;

  constructor(private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.username = this.tokenStorage.getUsername();
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if (role === 'ROLE_USER') 
        this.authority = 'user';
        return false;
      });
    }
  }

  logout() {
    console.info("Click");
    this.tokenStorage.signOut();
    window.location.reload();
  }

}
