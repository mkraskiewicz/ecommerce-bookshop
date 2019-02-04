import { Component, OnInit } from '@angular/core';
import {Params, ActivatedRoute, Router} from '@angular/router';

import { Book } from '../../../models/book';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {

  private book:Book = new Book();
  private bookId: number;
  private bookUpdated: boolean;
  public files: any[] = [];
  public imgURL: any;
  
  selectedFiles: FileList
  currentFileUpload: File
  
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
  		},
  		error => {
  			console.log(error);
  		}
    );
  }

  onSubmit() {
    const formData = new FormData();

    this.bookService.sendBook(this.book)
       .subscribe(
          data =>{
            if(this.currentFileUpload != null){
              this.bookService.sendImage(this.currentFileUpload,data.id).subscribe()
            }
          });

        this.bookUpdated = true;
  }

  selectFile(event) {
    const file = event.target.files.item(0)
 
    if (file.type.match('image.*')) {
      this.selectedFiles = event.target.files;
    } else {
      alert('invalid format!');
    }
  }
 

  upload() {
    this.currentFileUpload = this.selectedFiles.item(0);
    this.selectedFiles = undefined
    var reader = new FileReader();
    
    reader.readAsDataURL(this.currentFileUpload); 
    reader.onload = (_event) => { 
      this.imgURL = reader.result; 
    } 
  }
  updateNewBook() {
    
    this.bookUpdated = false;
    this.reloadPage();
  }

  reloadPage() {
    window.location.reload();
  }
  
  // getImageFromService() {
  //   this.bookService.getBookImageJson(this.bookId).subscribe(data => {
  //     this.currentFileUpload = data});
  //   }


}
