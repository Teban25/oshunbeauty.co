package co.oshunbeauty.constants;

import java.time.ZoneId;
import java.util.Set;

public class Constants {
	
	public static class DateConstants {
		public static final ZoneId ZONE_ID = ZoneId.of("America/Bogota");
	}
	
	public static class ServicesConstants {
		public static final Set<String> IGNORED_STANDARD_FIELDS = Set.of("creationDate", "lastModifiedDate",
				"creationUser", "lastModifiedUser");
		
		public static final String PRODUCTS_SHEET = "productos";
		public static final String ORDER_SHEET = "ordenes";
	}
}
