package co.oshunbeauty.exception;

public class BusinessSaleOrderException extends Exception {
	
	public BusinessSaleOrderException() {
	}
	
	public BusinessSaleOrderException(String message) {
		super(message);
	}
	
	public BusinessSaleOrderException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BusinessSaleOrderException(Throwable cause) {
		super(cause);
	}
}
