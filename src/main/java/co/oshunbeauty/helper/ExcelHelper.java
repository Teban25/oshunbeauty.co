package co.oshunbeauty.helper;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExcelHelper {
	
	public Workbook getWorkBookFromExcel(InputStream inputStream) throws IOException {
		try {
			return WorkbookFactory.create(inputStream);
		} catch(IOException ex) {
			log.error("There were an error trying to parse the excel file: ", ex.getMessage(), ex);
			throw new IOException("Hubo un error haciendo la transformación del archivo excel", ex);
		}
	}
}
