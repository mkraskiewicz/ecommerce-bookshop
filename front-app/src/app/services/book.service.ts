import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Book } from '../models/book';
import { Observable } from "rxjs";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private baseUrl = 'http://localhost:8080/api/v1/book';

  constructor(private http:HttpClient) { }

  sendBook(book:Book) {
    return this.http.post<Book>(this.baseUrl + '/add', book, httpOptions);
  }

  updateBook(book:Book, id: Number) {
    return this.http.put<Book>(this.baseUrl + '/' + id, book, httpOptions);
  }

  sendImage(file: File, id: Number) {
    let formdata: FormData = new FormData();
 
    formdata.append('file', file);
    return this.http.post(this.baseUrl + '/' + id +'/image', formdata, {
      responseType: 'text'
    });
  }



  getBook(id:number) {
    return this.http.get<Book>(this.baseUrl + '/' + id, httpOptions);
  }

  getBookList(){
    return this.http.get<Array<Book>>(this.baseUrl + '/all', httpOptions);
  }

  getBookImage(id:number): Observable<Blob> {
    return this.http.get(this.baseUrl + '/'+ id +'/image', { responseType: 'blob' });
  }

  getBookImageJson(id:number){
    return this.http.get(this.baseUrl + '/'+ id +'/image', { responseType: 'text' });
  }

}
