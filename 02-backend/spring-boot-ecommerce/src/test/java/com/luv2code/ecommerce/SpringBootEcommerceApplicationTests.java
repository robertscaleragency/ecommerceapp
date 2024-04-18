package com.luv2code.ecommerce;

import com.luv2code.ecommerce.dao.CustomerRepository;
import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.dto.PurchaseResponse;
import com.luv2code.ecommerce.entity.Address;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.OrderItem;
import com.luv2code.ecommerce.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootEcommerceApplicationTests {

	@Autowired
	CheckoutService checkoutService;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private MockMvc mockMvc;

	//Test calling an endpoint that does not exist
	@Test
	public void callGetUnusedEndpoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/test-endpoint")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//Test calling the products endpoint
	@Test
	public void callGetProductsEndpoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	//Test calling the categories endpoint
	@Test
	public void callGetCategoryEndpoint() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product-category")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}


	//Create a test purchase
	private Purchase getTestPurchase() {
		Purchase purchase = new Purchase();

		Customer customer = new Customer();
		customer.setEmail("test.elek@gmail.com");
		customer.setFirstName("Teszt");
		customer.setLastName("Elek");

		Address shippingAddress = new Address();
		shippingAddress.setCity("Budapest");
		shippingAddress.setCountry("Hungary");
		shippingAddress.setZipCode("1135");
		shippingAddress.setState("Pest");
		shippingAddress.setStreet("Frangepán utca 3.");


		Order order = new Order();

		purchase.setCustomer(customer);
		purchase.setBillingAddress(shippingAddress);
		purchase.setShippingAddress(shippingAddress);
		purchase.setOrder(order);

		Set<OrderItem> orderItemSet = new HashSet<>();

		orderItemSet.add(OrderItem.builder()
				.imageUrl("TEST_IMAGE_URL")
				.productId(1L)
				.quantity(10)
				.unitPrice(BigDecimal.valueOf(1000L))
				.build());

		orderItemSet.add(OrderItem.builder()
				.imageUrl("TEST_IMAGE_URL")
				.productId(2L)
				.quantity(5)
				.unitPrice(BigDecimal.valueOf(10L))
				.build());
		purchase.setOrderItems(orderItemSet);
		return purchase;
	}


	//Test sending an order and getting an order number
	@Test
	public void testPlaceOrderGetOrderNumber() {
		//Create new purchase
		PurchaseResponse purchaseResponse =  checkoutService.placeOrder(getTestPurchase());
		// Check if order has an id
		assertThat(purchaseResponse.getOrderTrackingNumber()).isNotNull();
	}

	//test is the customer is created when sending an order
	@Test
	public void testPlaceOrderIsCustomerSaved() {
		//Checkout service - create a new order
		checkoutService.placeOrder(getTestPurchase());

		//List all customers in database
		List<Customer> customer = customerRepository.findAll();

		//Database has at least one customer
		assertThat(customer.size()).isNotZero();
	}

	//Test placing an order
	@Test
	public void testPlaceOrderIsCustomerSavedAndOrderIsPresent() {
		//Checkout service - create a new order
		checkoutService.placeOrder(getTestPurchase());

		//List all customers in database
		List<Customer> customer = customerRepository.findAll();

		//Get the order of customers
		Set<Order> orders = customer.stream().findFirst().orElseThrow().getOrders();

		//Customer has at least one order
		assertThat(orders.size()).isNotZero();

		// It has at least one product
		assertThat(orders.stream().findFirst().orElseThrow().getOrderItems().size()).isNotZero();
	}
}
