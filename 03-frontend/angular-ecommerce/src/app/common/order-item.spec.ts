import { CartItem } from './cart-item';
import { OrderItem } from './order-item';

describe('OrderItem', () => {
  it('should create an instance', () => {
    const testProduct = {
      id: "1",
      sku: "TEST1",
      name: "TEST PRODUCT NAME",
      description: "TEST DESCRIPTIONN",
      unitPrice: 10,
      imageUrl: "imgUrl",
      active: true,
      unitsInStock: 5,
      dateCreated: new Date(),
      lastUpdate: new Date(),
    };

    const cartItem = new CartItem(testProduct);

    expect(new OrderItem(cartItem)).toBeTruthy();
  });
});
