package main

import (
	"database/sql"
	"grpc/internal/database"
	"grpc/internal/pb"
	"grpc/internal/service"
	"net"

	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"

	_ "github.com/mattn/go-sqlite3"
)

func main() {
	db, err := sql.Open("sqlite3", "./db.sqlite")
	if err != nil {
		panic(err)
	}

	defer db.Close()

	categoryDb := database.NewCategory(db)
	CategoryService := service.NewCategoryServer(*categoryDb)

	grpcServer := grpc.NewServer()

	pb.RegisterCategoryServiceServer(grpcServer, CategoryService)

	reflection.Register(grpcServer)

	lis, err := net.Listen("tcp", ":50051")
	if err != nil {
		panic(err)
	}

	if err := grpcServer.Serve(lis); err != nil {
		panic(err)
	}

}