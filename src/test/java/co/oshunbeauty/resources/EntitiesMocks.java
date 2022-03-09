package co.oshunbeauty.resources;

import co.oshunbeauty.entity.Category;
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
}
