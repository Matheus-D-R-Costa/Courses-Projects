package service

import (
	"context"
	"grpc/internal/database"
	"grpc/internal/pb"
	"io"

	"google.golang.org/grpc"
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

func (service *CategoryService) CreateCategory(ctx context.Context, in *pb.CreateCategoryRequest) (*pb.Category, error) {
	category, err  := service.CategoryDb.Create(in.Name, in.Description)
	if err != nil {
		return nil, err
	}

	return &pb.Category{
		Id: category.Id,
		Name: category.Name,
		Description: category.Description,
	}, nil

}

func (service *CategoryService) CreateCategoyStream(stream grpc.ClientStreamingServer[pb.CreateCategoryRequest, pb.CategoryList]) error {
	categories := &pb.CategoryList{}

	for {
		category, err := stream.Recv()
		if err == io.EOF {
			return stream.SendAndClose(categories)
		}

		if err != nil {
			return err
	  }

		categoryResult, err := service.CategoryDb.Create(category.Name, category.Description)
		if err != nil {
			return err
	  }

		categories.Categories = append(categories.Categories, &pb.Category{
			Id: categoryResult.Id,
			Name: categoryResult.Name,
			Description: category.Description,
		})

	}

}

func (service *CategoryService) CreateCategoryStreamBidirectional(stream grpc.BidiStreamingServer[pb.CreateCategoryRequest, pb.Category]) error {
	for {
		category, err := stream.Recv()
		if err == io.EOF {
			return nil
		}

		if err != nil {
			return err
	  }

		categoryResult, err := service.CategoryDb.Create(category.Name, category.Description)
		if err != nil {
			return err
	  }

		err = stream.Send(&pb.Category{
			Id: categoryResult.Id,
			Name: categoryResult.Name,
			Description: category.Description,
		})

		if err != nil {
			return err
	  }

	}
}


func (service *CategoryService) ListCategories(ctx context.Context, in *pb.Blank) (*pb.CategoryList, error) {
	categories, err := service.CategoryDb.FindAll()
	if err != nil {
		return nil, err
	}

	var categoriesResponse []*pb.Category

	for _, category := range categories {
		categoryResponse := &pb.Category{
			Id: category.Id,
			Name: category.Name,
			Description: category.Description,
		}

		categoriesResponse = append(categoriesResponse, categoryResponse)

	}

	return &pb.CategoryList{Categories: categoriesResponse}, nil

}

func (service *CategoryService) FindCategoryById(ctx context.Context, in *pb.CategoryIdRequest) (*pb.Category, error) {
	category, err := service.CategoryDb.FindById(in.Id)
	if err != nil {
		return nil, err
	}

	return &pb.Category{
		Id: category.Id,
		Name: category.Name,
		Description: category.Description,
	}, nil

}