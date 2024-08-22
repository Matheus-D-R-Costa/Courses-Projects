package cli

import (
	"fmt"
	"gobooks/internal/service"
	"os"
	"strconv"
	"time"
)

type Cli struct {
	service *service.BookService
}

func NewCli(service *service.BookService) *Cli {
	return &Cli{service: service}
}

func (cli Cli) Run() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: books <command> [args]")
		return
	}

	command := os.Args[1]

	switch command {
	case "search":
		if len(os.Args) < 3 {
			fmt.Println("Usage: books search <book title>")
			return
		}

		bookName := os.Args[2]
		cli.searchBooks(bookName)

	case "simulate":
		if len(os.Args) < 3 {
			fmt.Println("Usage: books simulate <book Id> <book Id> <book Id> ...")
			return
		}

		bookIds := os.Args[2:]
		cli.SimulateReading(bookIds)

	}

}

func (cli *Cli) searchBooks(name string) {
	books, err := cli.service.SearchByTitle(name)
	if err != nil {
		fmt.Println("Error searching books:", err)
		return
	}

	if len(books) == 0 {
		fmt.Println("No books found.")
		return
	}

	fmt.Printf("%d Books founds\n", len(books))

	for _, book := range books {
		fmt.Printf("Id: %d, Title: %s, Author: %s, Genre: %s\n", book.Id, book.Title, book.Author, book.Genre)
	}

}

func (cli *Cli) SimulateReading(bookIdsStr []string) {
	var bookIds []int

	for _, idStr := range bookIdsStr {
		id, err := strconv.Atoi(idStr)
		if err != nil {
			fmt.Println("Invalid book Id", idStr)
			continue
		}

		bookIds = append(bookIds, id)

	}

	responses := cli.service.SimulateMultipleReadings(bookIds, 2*time.Second)

	for _, response := range responses {
		fmt.Println(response)
	}

}
