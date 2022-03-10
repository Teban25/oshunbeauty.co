package co.oshunbeauty.validation;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.entity.Category;
import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.entity.Keyword;
import co.oshunbeauty.exception.BadRequestException;
import java.util.Set;
import java.util.StringJoiner;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationsService {
	
	private Validator validator;
	
	@Autowired
	public ValidationsService(Validator validator) {
		this.validator = validator;
	}
	
	public void isCategoryValidToSave(Category category) {
		if(category.getCategoryId() != null) {
			throw new BadRequestException("Para crear una nueva categoria no debe existir el id");
		}
		
		Set<ConstraintViolation<Category>> violations = validateMandatoryFields(category);
		if(isNotAnEmptyViolations(violations)) {
			String messagesViolations = getMessagesViolations(violations);
			throw new BadRequestException("No se aceptaron las validaciones minimas para crear una categoria: "
					+ messagesViolations);
		}
	}
	
	public void isCategoryValidToUpdate(Category category) {
		if(category.getCategoryId() == null) {
			throw new BadRequestException("Para actualizar una nueva categoria debe existir el id");
		}
		
		Set<ConstraintViolation<Category>> violations = validateMandatoryFields(category);
		if(isNotAnEmptyViolations(violations)) {
			String messagesViolations = getMessagesViolations(violations);
			throw new BadRequestException("No se aceptaron las validaciones minimas para actualizar la categoria: " +
					messagesViolations);
		}
	}
	
	public void isBrandValidToSave(Brand brand) {
		if(brand.getBrandId() != null) {
			throw new BadRequestException("Para crear una nueva marca no debe existir el id");
		}
		
		Set<ConstraintViolation<Brand>> violations = validateMandatoryFields(brand);
		if(isNotAnEmptyViolations(violations)) {
			String messagesViolations = getMessagesViolations(violations);
			throw new BadRequestException("No se aceptaron las validaciones minimas para crear una marca: "
					+ messagesViolations);
		}
	}
	
	
	public void isBrandValidToUpdate(Brand brand) {
		if(brand.getBrandId() == null) {
			throw new BadRequestException("Para actualizar una marca debe existir el id");
		}
		
		Set<ConstraintViolation<Brand>> violations = validateMandatoryFields(brand);
		if(isNotAnEmptyViolations(violations)) {
			String messagesViolations = getMessagesViolations(violations);
			throw new BadRequestException("No se aceptaron las validaciones minimas para actualizar la marca: " +
					messagesViolations);
		}
	}
	
	public void isCustomerValidToSave(Customer customer) {
		if(customer.getIdentification() == null) {
			throw new BadRequestException("Para crear un nuevo cliente debe existir la identificacion");
		}
		
		Set<ConstraintViolation<Customer>> violations = validateMandatoryFields(customer);
		if(isNotAnEmptyViolations(violations)) {
			String messagesViolations = getMessagesViolations(violations);
			throw new BadRequestException("No se aceptaron las validaciones minimas para crear un cliente: " +
					messagesViolations);
		}
	}
	
	public void isCustomerValidToUpdate(Customer customer) {
		if(customer.getIdentification() == null) {
			throw new BadRequestException("Para actualizar una nueva marca debe existir el id");
		}
		
		Set<ConstraintViolation<Customer>> violations = validateMandatoryFields(customer);
		if(isNotAnEmptyViolations(violations)) {
			String messagesViolations = getMessagesViolations(violations);
			throw new BadRequestException("No se aceptaron las validaciones minimas para actualizar el cliente: " +
					messagesViolations);
		}
	}
	
	public void isKeywordValidToSave(Keyword keyword) {
		if(keyword.getKeywordId() != null) {
			throw new BadRequestException("Para crear una palabra clave no debe existir el id");
		}
		
		Set<ConstraintViolation<Keyword>> violations = validateMandatoryFields(keyword);
		if(isNotAnEmptyViolations(violations)) {
			String messagesViolations = getMessagesViolations(violations);
			throw new BadRequestException("No se aceptaron las validaciones minimas para crear una palabra clave: "
					+ messagesViolations);
		}
	}
	
	public void isKeywordValidToUpdate(Keyword keyword) {
		if(keyword.getKey() == null) {
			throw new BadRequestException("Para actualizar una palabra clave debe existir el id");
		}
		
		Set<ConstraintViolation<Keyword>> violations = validateMandatoryFields(keyword);
		if(isNotAnEmptyViolations(violations)) {
			String messagesViolations = getMessagesViolations(violations);
			throw new BadRequestException("No se aceptaron las validaciones minimas para actualizar la palabra clave: " +
					messagesViolations);
		}
	}
	
	private <T> boolean isNotAnEmptyViolations(Set<ConstraintViolation<T>> violations) {
		return !violations.isEmpty();
	}
	
	private <T> Set<ConstraintViolation<T>> validateMandatoryFields(T object) {
		return validator.validate(object);
	}
	
	private <T> String getMessagesViolations(Set<ConstraintViolation<T>> violations) {
		StringJoiner messagesViolations = new StringJoiner(",");
		for(ConstraintViolation<T> violation : violations){
			addMessageViolation(messagesViolations, violation);
		}
		
		return messagesViolations.toString();
	}
	
	private <T> void addMessageViolation(StringJoiner messagesViolations, ConstraintViolation<T> violation) {
		messagesViolations.add("Campo: " + violation.getPropertyPath().toString() + ", mensaje: " + violation.getMessage());
	}
}
