import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './security/register/register.component';
import { LoginComponent } from './security/login/login.component';
import { AddNewBookComponent } from './components/book/add-new-book/add-new-book.component'
import { BookListComponent } from './components/book/book-list/book-list.component';
import { BookViewComponent } from './components/book/book-view/book-view.component';
import { BookEditComponent } from './components/book/book-edit/book-edit.component';

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