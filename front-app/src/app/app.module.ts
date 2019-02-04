import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule } from '@angular/material';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { LoginComponent } from './security/login/login.component';
import { RegisterComponent } from './security/register/register.component';
import { httpInterceptorProviders } from './security/auth/auth-interceptor';
import {DemoMaterialModule} from '../material-module';
import { HomeComponent } from './components/home/home.component';
import { AddNewBookComponent } from './components/book/add-new-book/add-new-book.component';
import { BookService } from './services/book.service';
import { BookListComponent } from './components/book/book-list/book-list.component';
import { BookViewComponent } from './components/book/book-view/book-view.component';
import { BookEditComponent } from './components/book/book-edit/book-edit.component';


@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    AddNewBookComponent,
    BookListComponent,
    BookViewComponent,
    BookEditComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    DemoMaterialModule
  ],
  providers: [httpInterceptorProviders, 
    BookService],
  bootstrap: [AppComponent]
})
export class AppModule { }
