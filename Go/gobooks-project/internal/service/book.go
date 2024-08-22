package service

import (
	"database/sql"
	"fmt"
	"time"
)

type Book struct {
	Id     int
	Title  string
	Author string
	Genre  string
}

type BookService struct {
	db *sql.DB
}

func NewBookService(database *sql.DB) *BookService {
	return &BookService{db: database}
}

func (service *BookService) SimulateMultipleReadings(bookIds []int, duration time.Duration) []string {
	results := make(chan string, len(bookIds))

	for _, id := range bookIds {
		go func(bookId int) {
			service.SimulateReading(bookId, duration, results)
		}(id)
	}

	var responses []string
	for range bookIds {
		responses = append(responses, <-results)
	}

	close(results)

	return responses

}

func (service *BookService) SimulateReading(bookId int, duration time.Duration, results chan<- string) {
	book, err := service.GetById(bookId)
	if err != nil || book == nil {
		results <- fmt.Sprintf("Book %d not found")
	}

	time.Sleep(duration)

	results <- fmt.Sprintf("Book %d read", book.Title)

}

func (service *BookService) SearchByTitle(title string) ([]Book, error) {
	query := "SELECT * FROM Books WHERE title = ?"

	rows, err := service.db.Query(query, "%"+title+"%")
	if err != nil {
		return nil, err
	}

	defer rows.Close()

	var books []Book
	for rows.Next() {
		var book Book

		err := rows.Scan(&book.Id, &book.Title, &book.Author, &book.Genre)
		if err != nil {
			return nil, err
		}

		books = append(books, book)

	}

	return books, nil

}

func (service *BookService) Create(book *Book) error {
	query := "INSERT INTO Books (title, author, genre) VALUES (?, ?, ?)"

	result, err := service.db.Exec(query, book.Title, book.Author, book.Genre)
	if err != nil {
		return err
	}

	lastInsertId, err := result.LastInsertId()
	if err != nil {
		return err
	}

	book.Id = int(lastInsertId)

	return nil

}

func (service *BookService) GetAll() ([]Book, error) {
	query := "SELECT * FROM Books"

	rows, err := service.db.Query(query)
	if err != nil {
		return nil, err
	}

	defer rows.Close()

	var books []Book
	for rows.Next() {
		var book Book

		err := rows.Scan(&book.Id, &book.Title, &book.Author, &book.Genre)
		if err != nil {
			return nil, err
		}

		books = append(books, book)

	}

	return books, nil

}

func (service *BookService) GetById(id int) (*Book, error) {
	query := "SELECT * FROM Books WHERE id = ?"

	row := service.db.QueryRow(query, id)

	var book Book

	err := row.Scan(&book.Id, &book.Title, &book.Author, &book.Genre)
	if err != nil {
		return nil, err
	}

	return &book, nil

}

func (service *BookService) Update(book *Book) error {
	query := "UPDATE Books SET title = ?, author = ?, genre = ? WHERE id = ?"

	_, err := service.db.Exec(query, book.Title, book.Author, book.Genre, book.Id)

	return err

}

func (service *BookService) Delete(id int) error {
	query := "DELETE FROM Books WHERE id = ?"

	_, err := service.db.Exec(query, id)

	return err

}
