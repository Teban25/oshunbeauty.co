package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.CustomerService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getCustomer;
import static co.oshunbeauty.resources.EntitiesMocks.getCustomers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
	
	
	@InjectMocks
	CustomerController customerController;
	
	@Mock
	CustomerService customerService;
	
	@Mock
	ValidationsService validationsService;
	
	private static final String CUSTOMER_ID = "1017215615";
	private static final String USER_TEST = "oshunTest";
	
	@Test
	public void testThatGetCustomerById() {
		// GIVEN
		Customer customerToFind = getCustomer();
		customerToFind.setIdentification(CUSTOMER_ID);
		// WHEN
		when(customerService.getCustomerById(any(String.class))).thenReturn(Optional.of(customerToFind));
		Customer actualCustomer = customerController.getCustomerById(CUSTOMER_ID);
		// THEN
		verify(customerService, times(1)).getCustomerById(any(String.class));
		assertAll(
				() -> assertNotNull(actualCustomer),
				() -> assertEquals(CUSTOMER_ID, actualCustomer.getIdentification())
		);
	}
	
	@Test
	public void testThatNotFoundGetCustomerById() {
		// GIVEN
		
		// WHEN
		when(customerService.getCustomerById(any(String.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> customerController.getCustomerById(CUSTOMER_ID));
		verify(customerService, times(1)).getCustomerById(any(String.class));
	}
	
	@Test
	public void testThatGetAllCustomer() {
		// GIVEN
		List<Customer> customersToFind = getCustomers();
		// WHEN
		when(customerService.getAllCustomers()).thenReturn(customersToFind);
		List<Customer> actualCustomers = customerController.getAllCustomers();
		// THEN
		verify(customerService, times(1)).getAllCustomers();
		assertAll(
				() -> assertNotNull(actualCustomers),
				() -> assertEquals(4, actualCustomers.size()),
				() -> assertEquals(CUSTOMER_ID, actualCustomers.get(0).getIdentification())
		);
	}
	
	@Test
	public void testThatSaveCustomer() {
		// GIVEN
		Customer customerToSave = getCustomer();
		Customer customerSaved = BeanUtils.instantiateClass(Customer.class);
		BeanUtils.copyProperties(customerToSave, customerSaved);
		customerSaved.setIdentification(CUSTOMER_ID);
		// WHEN
		when(customerService.saveCustomer(any(Customer.class), any(String.class)))
				.thenReturn(customerSaved);
		Customer actualCustomer = customerController.saveCustomer(customerToSave);
		// THEN
		verify(validationsService, times(1)).isCustomerValidToSave(any(Customer.class));
		verify(customerService, times(1)).saveCustomer(any(Customer.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualCustomer),
				() -> assertNotNull(actualCustomer.getCreationDate()),
				() -> assertNotNull(actualCustomer.getLastModifiedDate()),
				() -> assertEquals(CUSTOMER_ID, actualCustomer.getIdentification()),
				() -> assertEquals("test", actualCustomer.getCreationUser())
		);
	}
	
	@Test
	public void testThatFailsSavingCustomer() {
		// GIVEN
		Customer customerToSave = getCustomer();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isCustomerValidToSave(any(Customer.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> customerController.saveCustomer(customerToSave));
		verify(validationsService,
				times(1)).isCustomerValidToSave(any(Customer.class));
		verifyNoInteractions(customerService);
	}
	
	@Test
	public void testThatFailsUpdatingCustomerDueToValidations() {
		// GIVEN
		Customer customerToSave = getCustomer();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isCustomerValidToUpdate(any(Customer.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> customerController.updateCustomer(CUSTOMER_ID, customerToSave));
		verify(validationsService,
				times(1)).isCustomerValidToUpdate(any(Customer.class));
		verifyNoInteractions(customerService);
	}
	
	@Test
	public void testThatFailsUpdatingCustomerDueToWrongIds() {
		// GIVEN
		Customer customerToUpdate = getCustomer();
		customerToUpdate.setIdentification("1017217661");
		
		Customer customerFound = getCustomer();
		customerFound.setIdentification(CUSTOMER_ID);
		// WHEN
		when(customerService.getCustomerById(any(String.class))).thenReturn(Optional.of(customerFound));
		// THEN
		assertThrows(BadRequestException.class, () -> customerController.updateCustomer(CUSTOMER_ID, customerToUpdate));
		verify(validationsService, times(1)).isCustomerValidToUpdate(any(Customer.class));
		verify(customerService, times(1)).getCustomerById(any(String.class));
		verifyNoMoreInteractions(customerService);
	}
	
	@Test
	public void testThatUpdateCustomer() {
		// GIVEN
		Customer customerToUpdate = getCustomer();
		customerToUpdate.setIdentification(CUSTOMER_ID);
		
		Customer customerFound = getCustomer();
		customerFound.setIdentification(CUSTOMER_ID);
		
		Customer customerUpdated = BeanUtils.instantiateClass(Customer.class);
		BeanUtils.copyProperties(customerToUpdate, customerUpdated);
		customerUpdated.setLastModifiedUser(USER_TEST);
		// WHEN
		when(customerService.getCustomerById(any(String.class))).thenReturn(Optional.of(customerFound));
		when(customerService.updateCustomer(any(Customer.class), any(Customer.class), any(String.class)))
				.thenReturn(customerUpdated);
		Customer actualCustomer = customerController.updateCustomer(CUSTOMER_ID, customerToUpdate);
		// THEN
		verify(validationsService, times(1)).isCustomerValidToUpdate(any(Customer.class));
		verify(customerService, times(1)).getCustomerById(any(String.class));
		verify(customerService, times(1))
				.updateCustomer(any(Customer.class), any(Customer.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualCustomer),
				() -> assertNotNull(actualCustomer.getCreationDate()),
				() -> assertNotNull(actualCustomer.getLastModifiedDate()),
				() -> assertEquals(CUSTOMER_ID, actualCustomer.getIdentification()),
				() -> assertEquals(USER_TEST, actualCustomer.getLastModifiedUser())
		);
	}
	
	@Test
	public void testThatDeleteCustomer() {
		// GIVEN
		Customer customerFound = getCustomer();
		customerFound.setIdentification(CUSTOMER_ID);
		
		// WHEN
		when(customerService.getCustomerById(any(String.class))).thenReturn(Optional.of(customerFound));
		customerController.deleteCustomer(CUSTOMER_ID);
		// THEN
		verify(customerService, times(1)).getCustomerById(any(String.class));
		verify(customerService, times(1)).deleteCustomer(any(Customer.class));
	}
	
	@Test
	public void testThatFailsDeletingCustomer() {
		// GIVEN
		
		// WHEN
		when(customerService.getCustomerById(any(String.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(BadRequestException.class, () -> customerController.deleteCustomer(CUSTOMER_ID));
		verify(customerService, times(1)).getCustomerById(any(String.class));
		verifyNoMoreInteractions(customerService);
	}
}
