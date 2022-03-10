package co.oshunbeauty.service;

import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.repository.CustomerRepository;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static co.oshunbeauty.constants.Constants.DateConstants.ZONE_ID;
import static co.oshunbeauty.constants.Constants.ServicesConstants.IGNORED_STANDARD_FIELDS;

@Service
public class CustomerService {
	
	private Set<String> ignoredCustomerFields = new HashSet<>(Arrays.asList("customer_id", "identification"));
	private CustomerRepository customerRepository;
	
	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
		ignoredCustomerFields.addAll(IGNORED_STANDARD_FIELDS);
	}
	
	public Customer saveCustomer(Customer customer, String user) {
		customer.setCreationDate(ZonedDateTime.now(ZONE_ID));
		customer.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		customer.setCreationUser(user);
		customer.setLastModifiedUser(user);
		
		return customerRepository.save(customer);
	}
	
	public Customer updateCustomer(Customer currentCustomer, Customer customerSent, String user) {
		BeanUtils.copyProperties(customerSent, currentCustomer, ignoredCustomerFields.stream().toArray(String[]::new));
		
		currentCustomer.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		currentCustomer.setLastModifiedUser(user);
		
		return customerRepository.save(currentCustomer);
	}
	
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}
	
	public Optional<Customer> getCustomerById(String customerId) {
		return customerRepository.findById(customerId);
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
}
