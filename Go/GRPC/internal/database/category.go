package database

import (
	"database/sql"

	"github.com/google/uuid"
)


type Category struct {
	db          *sql.DB
	Id          string
	Name        string
	Description string
}

func NewCategory(db *sql.DB) *Category {
	return &Category{db: db}
}

func (category *Category) Create(name string, description string) (Category, error) {
	id := uuid.New().String()

	_, err := category.db.Exec("INSERT INTO categories (id, name, description) VALUES ($1, $2, $3)", id, name, description)
	if err != nil {
		return Category{}, err
	}

	return Category{Id: id, Name: name, Description: description}, nil

}

func (category *Category) FindAll() ([]Category, error) {
	rows, err := category.db.Query("SELECT id, name, description FROM categories")
	if err != nil {
		return nil, err
	}

	defer rows.Close()

	categories := []Category{}
	for rows.Next() {
		var id, name, description string
		if err := rows.Scan(&id, &name, &description); err != nil {
			return nil, err
		}

		categories = append(categories, Category{Id: id, Name: name, Description: description})

	}

	return categories, nil

}

func (category *Category) FindById(id string) (Category, error) {
	var name, description string

	err := category.db.QueryRow("SELECT name, description FROM categories WHERE id = $1", id).Scan(&name, &description)
	if err != nil {
		return Category{}, err
	}

	return Category{Id: id, Name: name, Description: description}, nil

}

func (category *Category) FindByCourseId(courseID string) (Category, error) {
	var id, name, description string

	err := category.db.QueryRow("SELECT c.id, c.name, c.description FROM categories c JOIN courses co ON c.id = co.category_id WHERE co.id = $1", courseID).Scan(&id, &name, &description)
	if err != nil {
		return Category{}, err
	}

	return Category{Id: id, Name: name, Description: description}, nil

}