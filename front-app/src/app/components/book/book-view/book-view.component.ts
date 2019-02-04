import { Component, OnInit } from '@angular/core';
import {Params, ActivatedRoute, Router} from '@angular/router';

import { Book } from '../../../models/book';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-book-view',
  templateUrl: './book-view.component.html',
  styleUrls: ['./book-view.component.css']
})
export class BookViewComponent implements OnInit {

  private book:Book = new Book();
  private bookId: number;
  private imageToShow: any;

  constructor(
    private bookService: BookService, private route:ActivatedRoute, 
    private router:Router) { }

  ngOnInit() {

    this.route.params.forEach((params: Params) => {
  		this.bookId = Number.parseInt(params['id']);
    });
    
    this.bookService.getBook(this.bookId).subscribe(
  		data => {
        this.book = data;
        this.getImageFromService();
  		},
  		error => {
  			console.log(error);
  		}
    );
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
       this.imageToShow = reader.result;
    }, false);
 
    if (image) {
       reader.readAsDataURL(image);
    } 
 }

 getImageFromService() {
  this.bookService.getBookImage(this.bookId).subscribe(data => {
    this.createImageFromBlob(data);});
  }

  

}
