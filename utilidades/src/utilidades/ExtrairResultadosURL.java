package utilidades;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import types.TONrosSorteados;

public class ExtrairResultadosURL {

	public static TONrosSorteados recuperaUltimoJogo(String nroJogo) {
		//opcao 1 = numerojogo
		//opcao 2 = data
		//outras = sequencia
		Document doc;
		try {
			// need http protocol
			if (nroJogo != null && !nroJogo.equals("")) {
				doc = Jsoup.connect("http://g1.globo.com/loterias/megasena.html#" + nroJogo).get();
				
			} else {
				doc = Jsoup.connect("http://g1.globo.com/loterias/megasena.html").get();
			}
			
			TONrosSorteados toNrosSorteados = new TONrosSorteados();
			
			
			Elements numeroConcurso = doc.select("span[class=numero-concurso]");
			Elements data = doc.select("span[class=data-concurso]");
			Elements numeros = doc.select("span[class=numero-sorteado]");
			
			toNrosSorteados.setNro(Integer.parseInt(numeroConcurso.text().substring(9, 13)));
			toNrosSorteados.setData(data.text());
			StringBuffer dezenas = new StringBuffer();
			
			for (int i = 0; i < numeros.size(); i++) {
				dezenas.append(String.format("%02d", Integer.parseInt(numeros.get(i).text())) + "-");
			}
			
			dezenas.deleteCharAt(dezenas.length()-1);
			
			toNrosSorteados.setSequencia(dezenas.toString());
			
			return toNrosSorteados;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static void main(String[] args) {
		
		TONrosSorteados toNrosSorteados = ExtrairResultadosURL.recuperaUltimoJogo("1598");
		
		System.out.println("jogo: "+toNrosSorteados.getNro());
		System.out.println("data: "+toNrosSorteados.getData());
		System.out.println("sequencia:"+toNrosSorteados.getSequencia());
	}

}
