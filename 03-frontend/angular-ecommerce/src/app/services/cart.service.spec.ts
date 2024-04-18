import { TestBed } from '@angular/core/testing';

import { CartService } from './cart.service';
import { CartItem } from '../common/cart-item';

describe('CartService', () => {
  let service: CartService;
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


  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should add a product to the cart', () => {
    const cartItem = new CartItem(testProduct);
      service.addToCart(cartItem);

      expect(service.cartItems.length).toBe(1);
      expect(service.cartItems[0]).toBe(cartItem);

      service.addToCart(cartItem);
      expect(service.cartItems.length).toBe(1);
      expect(service.cartItems[0].quantity).toBe(2);
  });

  it('should decrement quantity', () => {
    const cartItem = new CartItem(testProduct);
    service.cartItems = [];
    service.addToCart(cartItem);
    service.addToCart(cartItem);
    expect(service.cartItems[0].quantity).toBe(2);
    service.decrementQuantity(cartItem);
    expect(service.cartItems[0].quantity).toBe(1);
    service.decrementQuantity(cartItem);
    expect(service.cartItems.length).toBe(0);
  });

  it('should remove item', () => {
    const cartItem = new CartItem(testProduct);
    service.cartItems = [];
    service.addToCart(cartItem);
    service.addToCart(cartItem);
    expect(service.cartItems[0].quantity).toBe(2);

    service.remove(cartItem);
    expect(service.cartItems.length).toBe(0);
  });

});
