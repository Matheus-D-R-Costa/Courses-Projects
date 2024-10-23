package database

import (
	"database/sql"

	"github.com/google/uuid"
)

type Course struct {
	db          *sql.DB
	Id          string
	Name        string
	Description string
	CategoryId  string
}

func NewCourse(db *sql.DB) *Course {
	return &Course{db: db}
}

func (course *Course) Create(name, description, categoryId string) (*Course, error) {
	id := uuid.New().String()

	_, err := course.db.Exec("INSERT INTO courses (id, name, description, category_Id) VALUES ($1, $2, $3, $)", id, name, description, categoryId)
	if err != nil {
		return nil, err
	}

	return &Course{
		Id: id,
		Name: name,
		Description: description,
		CategoryId: categoryId,
	}, nil

}

func (course *Course) FindAll() ([]Course, error) {
	rows, err := course.db.Query("SELECT id, name, description, category_id FROM courses")
	if err != nil {
		return nil, err
	}

	defer rows.Close()

	courses := []Course{}
	for rows.Next() {
		var id, name, description, categoryId string
		if err := rows.Scan(&id, &name, &description, &categoryId); err != nil {
			return nil, err
		}

		courses = append(courses, Course{Id: id, Name: name, Description: description, CategoryId: categoryId})
	}

	return courses, nil
}

func (course *Course) FindById(id string) (Course, error) {
	var name, description, categoryId string

	err := course.db.QueryRow("SELECT name, description, category_id FROM courses WHERE id = $1", id).Scan(&name, &description, &categoryId)
	if err != nil {
		return Course{}, err
	}

	return Course{Id: id, Name: name, Description: description, CategoryId: categoryId}, nil

}

func (course *Course) FindByCategoryId(categoryId string) ([]Course, error) {
	rows, err := course.db.Query("SELECT id, name, description, category_id FROM courses WHERE category_id = $1", categoryId)
	if err != nil {
		return nil, err
	}

	defer rows.Close()

	courses := []Course{}
	for rows.Next() {
		var id, name, description, categoryId string
		if err := rows.Scan(&id, &name, &description, &categoryId); err != nil {
			return nil, err
		}

		courses = append(courses, Course{Id: id, Name: name, Description: description, CategoryId: categoryId})

	}

	return courses, nil

}