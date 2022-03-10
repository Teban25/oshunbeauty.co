package co.oshunbeauty.resources;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.entity.Category;
import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.entity.Keyword;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static co.oshunbeauty.constants.Constants.DateConstants.ZONE_ID;

public class EntitiesMocks {
	
	public static List<Category> getCategories() {
		Category category1 = new Category("tinturas", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		category1.setCategoryId(1L);
		Category category2 = new Category("sombras", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		category2.setCategoryId(2L);
		Category category3 = new Category("labiales", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		category3.setCategoryId(3L);
		Category category4 = new Category("shampoos", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		category4.setCategoryId(4L);
		
		return Arrays.asList(category1, category2, category3, category4);
	}
	
	public static Category getCategory() {
		Category category = new Category("tinturas", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		return category;
	}
	
	public static Brand getBrand() {
		Brand brand = new Brand("athos",ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		return brand;
	}
	
	public static List<Brand> getBrands() {
		Brand brand1 = new Brand("athos",ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Brand brand2 = new Brand("j&c",ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Brand brand3 = new Brand("serenity",ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Brand brand4 = new Brand("cepillos trad",ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");

		return Arrays.asList(brand1, brand2, brand3, brand4);
	}
	
	public static Customer getCustomer() {
		Customer customer = new Customer("1017215615", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		return customer;
	}
	
	public static List<Customer> getCustomers() {
		Customer customer1 = new Customer("1017215615", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Customer customer2 = new Customer("1017216663", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Customer customer3 = new Customer("71699652", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Customer customer4 = new Customer("43206356", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		return Arrays.asList(customer1, customer2, customer3, customer4);
	}
	
	public static Keyword getKeyword() {
		Keyword keyword = new Keyword("color","naranja", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		return keyword;
	}
	
	public static List<Keyword> getKeywords() {
		Keyword keyword1 = new Keyword("color","naranja", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Keyword keyword2 = new Keyword("tamaño","200ml", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Keyword keyword3 = new Keyword("color","verde", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		Keyword keyword4 = new Keyword("tamaño","500ml", ZonedDateTime.now(ZONE_ID), ZonedDateTime.now(ZONE_ID),
				"test", "test");
		
		return Arrays.asList(keyword1, keyword2, keyword3, keyword4);
	}
}
