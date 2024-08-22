package main

import (
	"database/sql"
	"gobooks/internal/cli"
	"gobooks/internal/service"
	"gobooks/internal/web"
	"log"
	"net/http"
	"os"

	_ "github.com/mattn/go-sqlite3"
)

func main() {
	db, err := sql.Open("sqlite3", "./books.db")
	if err != nil {
		log.Fatal(err)
	}

	defer db.Close()

	bookService := service.NewBookService(db)
	bookHandlers := web.NewBookHandlers(bookService)

	if len(os.Args) > 1 && (os.Args[1] == "search" || os.Args[1] == "simulate") {
		cli := cli.NewCli(bookService)
		cli.Run()
		return
	}

	router := http.NewServeMux()
	router.HandleFunc("POST /books", bookHandlers.Create)
	router.HandleFunc("GET /books", bookHandlers.GetAll)
	router.HandleFunc("GET /books/{id}", bookHandlers.GetById)
	router.HandleFunc("PUT /books/{id}", bookHandlers.Update)
	router.HandleFunc("DELETE /books/{id}", bookHandlers.DeleteBook)

	http.ListenAndServe(":8080", router)

}
