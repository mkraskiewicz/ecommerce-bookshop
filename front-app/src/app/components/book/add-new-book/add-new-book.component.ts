import { Component, OnInit } from '@angular/core';
import { Book } from '../../../models/book';
import { BookService } from '../../../services/book.service';


@Component({
  selector: 'app-add-new-book',
  templateUrl: './add-new-book.component.html',
  styleUrls: ['./add-new-book.component.css']
})
export class AddNewBookComponent implements OnInit {

  private newBook: Book = new Book();
  private bookAdded: boolean;
  private id: Number;
  public files: any[] = [];
  public imgURL: any;

  selectedFiles: FileList
  currentFileUpload: File


  constructor(private addBookService:BookService) { }

  onFileChanged(event: any) {
    this.files = event.target.files;
  }

  onSubmit() {
    const formData = new FormData();

    this.addBookService.sendBook(this.newBook)
       .subscribe(
          data =>{
            if(this.currentFileUpload != null){
              this.addBookService.sendImage(this.currentFileUpload,data.id).subscribe()
            }
          });

        this.bookAdded = true;
  }

  createNewBook() {
    
    this.bookAdded = false;
    this.reloadPage();
  }

  reloadPage() {
    window.location.reload();
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

  

  ngOnInit() {
    this.bookAdded=false;
    this.newBook.active=true;

  }
  
  
}
