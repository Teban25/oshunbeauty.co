package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.CustomerService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs/customers")
@Slf4j
public class CustomerController {
	
	private ValidationsService validationsService;
	private CustomerService customerService;
	
	@Autowired
	public CustomerController(ValidationsService validationsService, CustomerService customerService) {
		this.validationsService = validationsService;
		this.customerService = customerService;
	}
	
	@GetMapping("/")
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable final String id) {
		Optional<Customer> customerFound = customerService.getCustomerById(id);
		
		if(customerFound.isEmpty()) {
			throw new ResourceNotFoundException(getMessageForCustomerNotFoundException(id));
		}
		
		return customerFound.get();
	}
	
	@PostMapping
	public Customer saveCustomer(@RequestBody final Customer customer) {
		validationsService.isCustomerValidToSave(customer);
		
		log.info("Saving new Customer with name {} by the user {}", customer.getIdentification(), "oshun");
		return customerService.saveCustomer(customer, "oshun");
	}
	
	@PutMapping("/{id}")
	public Customer updateCustomer(@PathVariable final String id, @RequestBody final Customer customer) {
		validationsService.isCustomerValidToUpdate(customer);
		
		Optional<Customer> currentCustomerFound = customerService.getCustomerById(id);
		validateCustomersAreEqualsById(customer, currentCustomerFound);
		
		log.info("Updating the Customer with name {} by the user {}", customer.getIdentification(), "oshun");
		return customerService.updateCustomer(currentCustomerFound.get(), customer, "oshun");
	}
	
	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable final String id) {
		Optional<Customer> currentCustomer = customerService.getCustomerById(id);
		
		if(currentCustomer.isEmpty()) {
			log.error("The customer with id {} was not found.", id);
			throw new BadRequestException(getMessageForCustomerNotFoundException(id));
		}
		
		log.info("Deleting the customer with name {} by the user {}", currentCustomer.get().getFirstName(), "oshun");
		customerService.deleteCustomer(currentCustomer.get());
	}
	
	private void validateCustomersAreEqualsById(Customer customer, Optional<Customer> currentCustomerFound) {
		if(currentCustomerFound.isEmpty() || currentCustomerFound.get().getIdentification() != customer.getIdentification() ) {
			log.error("When trying to update customer with id {}, the customer sent had another id",
					customer.getIdentification());
			throw new BadRequestException(getErrorMessageCustomersAreNotSame(customer));
		}
	}
	
	private String getErrorMessageCustomersAreNotSame(Customer customer) {
		return String.format("El cliente con id %s no fue encontrada o no corresponde a la " +
				"ingresada", customer.getIdentification());
	}
	
	private String getMessageForCustomerNotFoundException(String id) {
		return String.format("El cliente con identificacion %s no fue encontrada", id);
	}
}
