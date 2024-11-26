import CustomerFactory from "./customer-factory";
import Address from "../value-object/address";

describe("Customer factory unit test", () => {
  it("should create a customer", () => {
    let customer = CustomerFactory.create("John")

    expect(customer.id).toBeDefined()
    expect(customer.name).toBe("John")
    expect(customer.Address).toBeUndefined()

  })

  it("should create a customer with an address", () => {
    const address = new Address("Street", 211, "88130-250", "Santa Catarina")

    let customer = CustomerFactory.createWithAddress("John", address)

    expect(customer.id).toBeDefined()
    expect(customer.name).toBe("John")
    expect(customer.Address).toBe(address)
  })

})