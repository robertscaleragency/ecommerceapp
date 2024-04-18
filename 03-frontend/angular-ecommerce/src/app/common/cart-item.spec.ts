import { CartItem } from './cart-item';

describe('CartItem', () => {
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

    expect(cartItem).toBeTruthy();
    expect(cartItem.id).toBe(testProduct.id);
    expect(cartItem.name).toBe(testProduct.name);
    expect(cartItem.quantity).toBe(1);

  });
});
