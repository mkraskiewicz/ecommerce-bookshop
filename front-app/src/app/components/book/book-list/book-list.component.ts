import { Component, OnInit, ViewChild } from '@angular/core';
import { Book } from '../../../models/book';
import { BookService } from 'src/app/services/book.service';
import {MatSort, MatTableDataSource, MatPaginator } from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import {Router} from '@angular/router';
@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
	private selectedBook : Book;
	private checked: boolean;
	private bookList: Book[];
	private allChecked: boolean;
  private removeBookList: Book[] = new Array();
  dataSource = new MatTableDataSource();
  selection = new SelectionModel(true, []);
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator; 

  displayedColumns: string[] = ['select','id', 'title', 'author', 'category', 'price', 'format', 'active', 'view'];

  constructor(
  		private bookService: BookService, private router:Router) { }

  ngOnInit() {
  	this.bookService.getBookList().subscribe(
  		data => {
        //this.bookList = res;
        this.dataSource = new MatTableDataSource(data);
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

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  viewBook(book:Book) {
    this.selectedBook=book;
    this.router.navigate(['/viewBook', this.selectedBook.id]);
  }
}
