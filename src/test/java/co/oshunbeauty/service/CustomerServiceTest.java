package co.oshunbeauty.service;

import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getCustomer;
import static co.oshunbeauty.resources.EntitiesMocks.getCustomers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CustomerServiceTest {
	
	@Mock
	private CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
	
	private CustomerService customerService = new CustomerService(customerRepository);
	
	private static final String IDENTIFICATION = "1017215615";
	private static final String USER_TEST = "testOshun";
	
	@BeforeEach
	public void setUp() {
	}
	
	@Test
	public void testGetCustomerByIdNotFound(){
		// Given
		// When
		Mockito.when(customerRepository.findById(any(String.class))).thenReturn(Optional.empty());
		Optional<Customer> customerFound = customerService.getCustomerById(IDENTIFICATION);
		// Then
		verify(customerRepository, times(1)).findById(any(String.class));
		assertTrue(customerFound.isEmpty());
	}
	
	@Test
	public void testGetCustomerById() {
		// Given
		Customer customerToFind = getCustomer();
		customerToFind.setIdentification(IDENTIFICATION);
		// When
		Mockito.when(customerRepository.findById(any(String.class))).thenReturn(Optional.of(customerToFind));
		Optional<Customer> customerFound = customerService.getCustomerById(IDENTIFICATION);
		// Then
		verify(customerRepository, times(1)).findById(any(String.class));
		assertAll(
				() -> assertTrue(customerFound.isPresent()),
				() -> assertEquals(IDENTIFICATION, customerFound.get().getIdentification())
		);
	}
	
	@Test
	public void testToGetAllCustomers() {
		// Given
		List<Customer> customers = getCustomers();
		// When
		Mockito.when(customerRepository.findAll()).thenReturn(customers);
		List<Customer> currentCustomer = customerService.getAllCustomers();
		// Then
		verify(customerRepository, times(1)).findAll();
		assertAll(
				() -> assertNotNull(currentCustomer),
				() -> assertEquals(4, currentCustomer.size()),
				() -> assertEquals(IDENTIFICATION, currentCustomer.get(0).getIdentification())
		);
	}
	
	@Test
	public void testToUpdateCustomer(){
		// Given
		Customer customerToUpdate = getCustomer();
		customerToUpdate.setIdentification(IDENTIFICATION);
		customerToUpdate.setIdentification("1017215615");
		
		Customer currentCustomer = getCustomer();
		currentCustomer.setIdentification(IDENTIFICATION);
		
		Customer customerUpdated = BeanUtils.instantiateClass(Customer.class);
		BeanUtils.copyProperties(customerToUpdate, customerUpdated);
		customerUpdated.setLastModifiedUser(USER_TEST);
		
		// When
		Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(customerUpdated);
		Customer currentCustomerUpdated = customerService.updateCustomer(currentCustomer, customerToUpdate, USER_TEST);
		// Then
		verify(customerRepository, times(1)).save(any(Customer.class));
		assertAll(
				() -> assertNotNull(currentCustomerUpdated),
				() -> assertEquals(USER_TEST, currentCustomerUpdated.getLastModifiedUser()),
				() -> assertEquals("1017215615", currentCustomerUpdated.getIdentification())
		);
	}
	
	@Test
	public void testToSaveCustomer(){
		// Given
		Customer customerToSave = getCustomer();
		// When
		Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(customerToSave);
		Customer currentCustomer = customerService.saveCustomer(customerToSave, USER_TEST);
		// Then
		verify(customerRepository, times(1)).save(any(Customer.class));
		assertAll(
				() -> assertNotNull(currentCustomer),
				() -> assertEquals(USER_TEST, currentCustomer.getCreationUser()),
				() -> assertEquals(IDENTIFICATION, currentCustomer.getIdentification())
		);
	}
}
