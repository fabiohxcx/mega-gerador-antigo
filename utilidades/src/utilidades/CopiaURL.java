package utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CopiaURL {

	public void getPage(URL url, File file) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		BufferedWriter out = new BufferedWriter(new FileWriter(file));

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			// Imprime página no console
			System.out.println(inputLine);
			// Grava pagina no arquivo
			out.write(inputLine);
			out.newLine();
		}

		in.close();
		out.flush();
		out.close();
	}

	/*public static void main(String[] args) {
		URL url = null;
		File file = new File("C:\\Users\\Fabio\\Documents\\page.html");
		try {
			url = new URL("http://www.mballem.com/post/capturando-html-de-pgina-web-com-java");
			new CopiaURL().getPage(url, file);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

}
