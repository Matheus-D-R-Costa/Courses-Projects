import Order from './checkout/entity/order'
import OrderItem from './checkout/entity/order_item'
import Customer from './customer/entity/customer'
import Address from './customer/value-object/address'

let customer = new Customer("1", "kylian")
const addr = new Address("Rua dos Polvos", 41,"88130333", "Palhonza")
customer.Address = addr
customer.activate()

const item1 = new OrderItem("1", "Lubrificante", 50, 1)
const item2 = new OrderItem("2", "Camisinha PP para Cavalos doutrinados", 15, 5)

const order = new Order("1", "1", [item1, item2])

console.log(order)
console.log(order)