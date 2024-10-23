package service

import (
	"context"
	"grpc/internal/database"
	"grpc/internal/pb"
)

type CategoryService struct {
	pb.UnimplementedCategoryServiceServer
	CategoryDb database.Category
}

func NewCategoryServer(categoryDb database.Category) *CategoryService {
	return &CategoryService{
		CategoryDb: categoryDb,
	}
}

func (service *CategoryService) CreateCategory(ctx context.Context, in *pb.CreateCategoryRequest) (*pb.CategoryResponse, error) {
	category, err  := service.CategoryDb.Create(in.Name, in.Description)
	if err != nil {
		return nil, err
	}

	categoryResponse := &pb.CategoryResponse{
		Category: &pb.Category{
			Id: category.Id,
			Name: category.Name,
			Description: category.Description,
		},
	}

	return categoryResponse, nil

}