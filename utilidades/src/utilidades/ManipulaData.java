package utilidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class ManipulaData {

	public static Date formataData(String data) throws Exception {
		if (data == null || data.equals(""))
			return null;

		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}
	
	public static String dataSqlParaDataString(Date data) {		
		return new SimpleDateFormat("dd/MM/yyyy").format(data);		
	}
}
