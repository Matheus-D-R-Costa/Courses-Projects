import Product from "../entity/product";
import productService from "./product-service";

describe("Product service unit tests", () => {

  it("should change the prices of all products", () => {

    const product01 = new Product("prd1", "p1", 30);
    const product02 = new Product("prd2", "p2", 10);
    const product03 = new Product("prd3", "p3", 5);

    const products = [product01, product02, product03];

    productService.increasePrice(products, 100);

    expect(product01.price).toBe(60);
    expect(product02.price).toBe(20);
    expect(product03.price).toBe(10);

  });

});