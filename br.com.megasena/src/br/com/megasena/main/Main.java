package br.com.megasena.main;

import java.util.Scanner;

import types.TONrosSorteados;
import utilidades.ExtrairResultadosURL;
import utilidades.ManipulaNumeros;
import br.com.megasena.sb.SBMegaSena;

public class Main {

	public static void main(String[] args) {

		String opcao = "OK";
		
		System.out.println("Digite uma opção:");
		System.out.println("0 - sair do programa");
		System.out.println("1 - importar numeros sorteados do txt.");
		System.out.println("2 - gera numero aleatorio de 1 a 60");
		System.out.println("3 - gera sequencia");
		System.out.println("4 - insere no banco ultimo jogo");

		Scanner ler = new Scanner(System.in);
		opcao = ler.nextLine();

		while (!opcao.equals("0")) {

			if (opcao.equals("1")) {
				System.out.println("Digite o caminho do arquivo:");
				String caminhoArquivo = ler.nextLine();
				System.out.println("Digite o nome do arquivo:");
				String nomeArquivo = ler.nextLine();

				System.out.println("importando dados...");
				SBMegaSena sbMegaSena = new SBMegaSena();
				sbMegaSena.importaDados(nomeArquivo, caminhoArquivo);

			} else if (opcao.equals("2")) {
				System.out.println(ManipulaNumeros.geraUmNumero(null,null)+"\n");

			} else if (opcao.equals("3")){
				System.out.println(ManipulaNumeros.geraSequencia(null)+"\n");				
			} else if (opcao.equals("4")){
				
				SBMegaSena sbMegaSena = new SBMegaSena();

				TONrosSorteados toNrosSorteados = ExtrairResultadosURL.recuperaUltimoJogo(null);
				
				TONrosSorteados toNrosSorteados2 = sbMegaSena.getNrosSorteados(toNrosSorteados.getNro());
				
				if(toNrosSorteados2 == null){
					sbMegaSena.insereNrosSorteados(toNrosSorteados);						
				} else {
					System.out.println("jogo já cadastrado no banco");
				}
				
				System.out.println("jogo:" + toNrosSorteados.getNro());
				System.out.println("data:" + toNrosSorteados.getData());
				System.out.println("sequencia:" + toNrosSorteados.getSequencia());
				
			}
			
			System.out.println("Digite uma opção:");
			opcao = ler.nextLine();
		}
		
		
		System.out.println("Programa finalizado");
		ler.close();
	}

}
