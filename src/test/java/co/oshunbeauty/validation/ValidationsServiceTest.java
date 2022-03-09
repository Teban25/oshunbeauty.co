package co.oshunbeauty.validation;

import co.oshunbeauty.entity.Category;
import co.oshunbeauty.exception.BadRequestException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static co.oshunbeauty.resources.EntitiesMocks.getCategory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class ValidationsServiceTest {
	
	@Mock
	private Validator validator = Mockito.mock(Validator.class);
	
	private ValidationsService validationsService = new ValidationsService(validator);
	
	@Test
	public void testThatValidateIsCategoryValidToSave() {
		// GIVEN
		Category category = getCategory();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCategoryValidToSave(category);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatFailsValidationToSaveDueToConstrains() {
		// GIVEN
		Category category = getCategory();
		ConstraintViolation<Object> categoryConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> categoryConstrains = Set.of(categoryConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(categoryConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(categoryConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(categoryConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCategoryValidToSave(category));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatValidateIsCategoryValidToUpdate() {
		// GIVEN
		Category category = getCategory();
		category.setCategoryId(1L);
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCategoryValidToUpdate(category);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatFailsValidationToUpdateDueToConstrains() {
		// GIVEN
		Category category = getCategory();
		category.setCategoryId(1L);
		ConstraintViolation<Object> categoryConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> categoryConstrains = Set.of(categoryConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(categoryConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(categoryConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(categoryConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCategoryValidToUpdate(category));
		// THEN
		verify(validator, times(1)).validate(any());
	}
}
