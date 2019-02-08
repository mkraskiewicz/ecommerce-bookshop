import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './security/register/register.component';
import { LoginComponent } from './security/login/login.component';
import { AddNewBookComponent } from './components/book/add-new-book/add-new-book.component'
import { BookListComponent } from './components/book/book-list/book-list.component';
import { BookViewComponent } from './components/book/book-view/book-view.component';
import { BookEditComponent } from './components/book/book-edit/book-edit.component';
import { HomeUserComponent } from './components/home/home-user/home-user.component';
import { LogincontrollerComponent } from './security/logincontroller/logincontroller.component';
import { ResetPasswordComponent } from './security/reset-password/reset-password.component';
import { MyProfileComponent } from './components/user/my-profile/my-profile.component';

const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'auth/login',
        component: LoginComponent
    },
    {
        path: 'signup',
        component: RegisterComponent
    },
    {
        path: 'addbook',
        component: AddNewBookComponent
    },
    {
        path: 'booklist',
        component: BookListComponent
    },
    {
		path: 'viewBook/:id',
		component: BookViewComponent
    },
    {
		path: 'editBook/:id',
		component: BookEditComponent
    },
    {
		path: 'homepage',
		component: HomeUserComponent
    },
    {
        path: 'auth/logincontroller',
        component: LogincontrollerComponent
    },
    {
        path: 'auth/resetpassword/:token',
        component: ResetPasswordComponent
    },
    {
        path: 'user/myprofile',
        component: MyProfileComponent
    },
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
