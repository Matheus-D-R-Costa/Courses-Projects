package web

import (
	"encoding/json"
	"gobooks/internal/service"
	"net/http"
	"strconv"
)

type BookHandlers struct {
	service *service.BookService
}

func NewBookHandlers(service *service.BookService) *BookHandlers {
	return &BookHandlers{service: service}
}

func (handler *BookHandlers) Create(res http.ResponseWriter, req *http.Request) {
	var book service.Book

	err := json.NewDecoder(req.Body).Decode(&book)
	if err != nil {
		http.Error(res, "Invalid request body", http.StatusBadRequest)
		return
	}

	err = handler.service.Create(&book)
	if err != nil {
		http.Error(res, "Failed to create book", http.StatusInternalServerError)
	}

	res.WriteHeader(http.StatusCreated)
	json.NewEncoder(res).Encode(book)

}

func (handler *BookHandlers) GetAll(res http.ResponseWriter, req *http.Request) {
	books, err := handler.service.GetAll()
	if err != nil {
		http.Error(res, "Failed to get Books", http.StatusInternalServerError)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	json.NewEncoder(res).Encode(books)

}

func (handler *BookHandlers) GetById(res http.ResponseWriter, req *http.Request) {
	stringId := req.PathValue("id")

	id, err := strconv.Atoi(stringId)
	if err != nil {
		http.Error(res, "Invalid book id", http.StatusBadRequest)
		return
	}

	book, err := handler.service.GetById(id)
	if err != nil {
		http.Error(res, "Failed to get book", http.StatusInternalServerError)
		return
	}

	if book == nil {
		http.Error(res, "Book not found", http.StatusNotFound)
	}

	res.Header().Set("Content-Type", "application/json")
	json.NewEncoder(res).Encode(book)

}

func (handler *BookHandlers) Update(res http.ResponseWriter, req *http.Request) {
	stringId := req.PathValue("id")

	id, err := strconv.Atoi(stringId)
	if err != nil {
		http.Error(res, "Invalid book id", http.StatusBadRequest)
		return
	}

	var book service.Book
	if err := json.NewDecoder(req.Body).Decode(&book); err != nil {
		http.Error(res, "Invalid request body", http.StatusBadRequest)
		return
	}

	book.Id = id

	if err := handler.service.Update(&book); err != nil {
		http.Error(res, "Failed to update book", http.StatusInternalServerError)
		return
	}

	res.WriteHeader(http.StatusOK)
	json.NewEncoder(res).Encode(book)

}

func (handler *BookHandlers) DeleteBook(res http.ResponseWriter, req *http.Request) {
	stringId := req.PathValue("id")

	id, err := strconv.Atoi(stringId)
	if err != nil {
		http.Error(res, "Invalid book id", http.StatusBadRequest)
		return
	}

	if err := handler.service.Delete(id); err != nil {
		http.Error(res, "Failed to delete book", http.StatusInternalServerError)
		return
	}

	res.WriteHeader(http.StatusNoContent)

}