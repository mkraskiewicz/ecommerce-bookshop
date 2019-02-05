import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Book } from '../../../models/book';
import { BookService } from 'src/app/services/book.service';
import {MatSort, MatTableDataSource, MatPaginator, MatDialog,
   MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import {Router} from '@angular/router';

export interface DialogData {
  ifDelete:boolean;
}

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
	private selectedBook : Book;

  dataSource = new MatTableDataSource();
  selection = new SelectionModel(true, []);
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator; 

  displayedColumns: string[] = ['select','id', 'title', 'author', 'category', 'price', 'format', 'active', 'view', 'delete'];

  constructor(
      private bookService: BookService, private router:Router,
      public dialog: MatDialog) { }

  ngOnInit() {
  	this.bookService.getBookList().subscribe(
  		data => {
        //this.bookList = res;
        this.dataSource = new MatTableDataSource<Book>(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
  		}, 
  		error => {
  			console.log(error);
  		}
  	);
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
        this.selection.clear() :
        this.dataSource.data.forEach(row => this.selection.select(row));
  }

  removeSelectedBooks() {
        this.dataSource.data.forEach(row =>  {
          if(this.selection.isSelected(row)){
            this.bookService.deleteBook(row['id']).subscribe();
          }      
        });
        this.getBooks();
  }
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  viewBook(book:Book) {
    this.selectedBook=book;
    this.router.navigate(['/viewBook', this.selectedBook.id]);
  }

  openDeleteDialog(id: number): void {
    const dialogRef = this.dialog.open(DialogDeleteBookDialog, {
      width: '250px',
      data: {ifDelete: false}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.bookService.deleteBook(id).subscribe();
        this.getBooks();

      }
    });
  }

  openDeleteAllDialog(): void {
    const dialogRef = this.dialog.open(DialogDeleteBookDialog, {
      width: '250px',
      data: {ifDelete: false}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
      this.removeSelectedBooks();
      }
    });
  }

  private getBooks(){
    this.bookService.getBookList().subscribe(
      data => {
        //this.bookList = res;
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        window.location.reload();
      }, 
      error => {
        console.log(error);
      }
    );
  }

}

@Component({
  selector: 'dialog-delete-book-dialog',
  templateUrl: 'dialog-delete-book.html',
})
export class DialogDeleteBookDialog {

  constructor(
    public dialogRef: MatDialogRef<DialogDeleteBookDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}