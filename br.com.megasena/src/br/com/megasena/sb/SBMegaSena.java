package br.com.megasena.sb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import types.TONrosSorteados;
import utilidades.ManipulaNumeros;
import br.com.megasena.eb.EBMegaSena;

public class SBMegaSena {

	public void importaDados(String nomeArquivo, String caminhoArquivo) {
		try {

			ArrayList<TONrosSorteados> listaNrosSorteados = new ArrayList<TONrosSorteados>();

			FileReader arq = new FileReader(caminhoArquivo + "\\" + nomeArquivo);
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = null;

			do {
				linha = lerArq.readLine(); // lê a primeira linha
				if (linha != null) {
					String dadosLinha[] = linha.split("\\|");
					TONrosSorteados toNrosSorteados = preencheTONrosSorteados(dadosLinha);
					listaNrosSorteados.add(toNrosSorteados);
					System.out.println("Nro:" + toNrosSorteados.getNro() + " data:" + toNrosSorteados.getData() + " sequencia:" + toNrosSorteados
							.getSequencia());
				}
			} while (linha != null);

			TONrosSorteados[] toNrosSorteados = (TONrosSorteados[]) listaNrosSorteados.toArray(new TONrosSorteados[listaNrosSorteados.size()]);
			EBMegaSena ebMegaSena = new EBMegaSena();
			ebMegaSena.importaDados(toNrosSorteados);

			System.out.println("Dados importados com sucesso");
			lerArq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private TONrosSorteados preencheTONrosSorteados(String[] dadosLinha) {

		return new TONrosSorteados(Integer.parseInt(dadosLinha[0]), dadosLinha[1],
				dadosLinha[2] + "-" + dadosLinha[3] + "-" + dadosLinha[4] + "-" + dadosLinha[5] + "-" + dadosLinha[6] + "-" + dadosLinha[7]);
	}

	public String insereNrosSorteados(TONrosSorteados toNrosSorteados) {
		EBMegaSena ebMegaSena = new EBMegaSena();
		TONrosSorteados toNrosSorteadosTemp = ebMegaSena.getNrosSorteados(toNrosSorteados.getNro());

		if (toNrosSorteadosTemp == null) {
			ebMegaSena.insereNrosSorteados(toNrosSorteados);
			return "Sorteio Inserido no banco";
		} else {
			return "Esse sorteio já está no banco de dados";
		}

	};

	public void insereNrosApostados(TONrosSorteados toNrosSorteados) {

		System.out.println(toNrosSorteados.getSequencia());
		EBMegaSena ebMegaSena = new EBMegaSena();
		
		TONrosSorteados[] toNrosApostados = ebMegaSena.listaNrosApostados(toNrosSorteados.getNro());

		//pega meu TO pra inserir e seta o conjunto a partir da sequencia digitada
		toNrosSorteados.setNumerosConjunto(ManipulaNumeros.converteSeqEmConj(toNrosSorteados.getSequencia()));
				
		SortedSet<Integer> conjuntoAdicionar = new TreeSet<Integer>();
		SortedSet<Integer> conjuntoDoTO = new TreeSet<Integer>();

		for (int i = 0; i < toNrosSorteados.getNumerosConjunto().length; i++) {
			conjuntoAdicionar.add(toNrosSorteados.getNumerosConjunto()[i]);
		}

		SortedSet<Integer> conjuntoInterseccao = new TreeSet<Integer>(conjuntoAdicionar);
		boolean podeAdiciona = true;
		
		if (toNrosApostados != null) {			
			for (int i = 0; i < toNrosApostados.length; i++) {
				conjuntoInterseccao = new TreeSet<Integer>(conjuntoAdicionar);
				for (int j = 0; j < 6; j++) {
					conjuntoDoTO.add(toNrosApostados[i].getNumerosConjunto()[j]);
				}
				conjuntoInterseccao.retainAll(conjuntoDoTO); 
				
				if (conjuntoInterseccao.size() > 3) {
					System.out.println("Você ja utilizou os numeros em uma aposta" + conjuntoInterseccao.toString());
					System.out.println("Na aposta " + toNrosApostados[i].getNro() + " " + toNrosApostados[i].getSequencia());
					System.out.println("Sua aposta nao foi aceita por possuir mais de 3 numeros já utilizado em outra aposta\n");
					podeAdiciona = false;
				}
				
				conjuntoDoTO.clear();// apaga todos os valores
			}
		} 
		
		if (podeAdiciona) {
			System.out.println("Aposta inserida\n");
			ebMegaSena.insereNrosApostados(toNrosSorteados);					
		}
		
		// no máx 1 numero repetido
/*		if (contador < 99) {
			System.out.println("Aposta inserida\n");
			ebMegaSena.insereNrosApostados(toNrosSorteados);
		} else {
			
		}*/

	};

	public TONrosSorteados getNrosSorteados(int nro) {
		EBMegaSena ebMegaSena = new EBMegaSena();
		return ebMegaSena.getNrosSorteados(nro);
	};

	public TONrosSorteados[] listaNrosApostados(int sorteio) {
		EBMegaSena ebMegaSena = new EBMegaSena();
		return ebMegaSena.listaNrosApostados(sorteio);
	};

	public TONrosSorteados[] listaNrosSorteados() {
		EBMegaSena ebMegaSena = new EBMegaSena();
		return ebMegaSena.listaNrosSorteados();
	};

	public int[][] contaNumeros() {
		TONrosSorteados[] toNrosSorteados = listaNrosSorteados();

		int n[] = new int[60];
		int pos[] = new int[60];

		for (int i = 0; i < n.length; i++) {
			n[i] = 0;
			pos[i] = i;
		}

		int tamanho = toNrosSorteados[0].getNumerosConjunto().length;

		for (int i = 0; i < toNrosSorteados.length; i++) {
			for (int j = 0; j < tamanho; j++) {
				n[toNrosSorteados[i].getNumerosConjunto()[j] - 1]++;
			}
		}

		int indexMin;
		int aux = 0;
		int aux2 = 0;

		for (int i = 0; i < n.length - 1; i++) {
			indexMin = i;
			for (int j = i + 1; j < n.length; j++) {
				if (n[j] < n[indexMin]) {
					indexMin = j;
				}
			}
			if (indexMin != i) {
				aux = n[indexMin];
				n[indexMin] = n[i];
				n[i] = aux;

				aux2 = pos[indexMin];
				pos[indexMin] = pos[i];
				pos[i] = aux2;
			}
		}
		
		int[][] estatisticaDezenas = new int[n.length][2];
		
		for (int i = n.length - 1; i >= 0; i--) {
			estatisticaDezenas[i][0] = pos[i] + 1;
			estatisticaDezenas[i][1] = n[i];
			
			System.out.println((i + 1) + " " + (pos[i] + 1) + " = " + n[i]);
		}
		
		return estatisticaDezenas;

		// criação do arquivo
		/*String nomeDiretorio = "C:\\arquivos_megasena";
		String nomeArquivo = "contadornumeros.txt";
		File diretorio = new File(nomeDiretorio);

		if (diretorio.exists()) {
			System.out.println("Diretório " + nomeDiretorio + " já existe!");
		} else {
			if (diretorio.mkdir()) {
				System.out.println("Diretório " + nomeDiretorio + " criado com sucesso!");
			}
		}

		File arquivo = new File(diretorio, nomeArquivo);

		try {
			arquivo.createNewFile();

			if (arquivo.exists()) {
				System.out.println("Arquivo " + nomeArquivo + " criado com sucesso!");
			}
			FileWriter fileWriter = new FileWriter(arquivo, false);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			for (int i = n.length - 1; i >= 0; i--) {
				printWriter.println((i + 1) + " " + (pos[i] + 1) + " = " + n[i]);
				System.out.println((i + 1) + " " + (pos[i] + 1) + " = " + n[i]);
			}

			printWriter.flush();
			printWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}
	//TODO funçao para verificar os numeros mais atrasados
	
	public String geraMaster() {
		// TONrosSorteados[] toNrosApostados = listaNrosApostados(1563);
		TONrosSorteados[] toNrosSorteados = listaNrosSorteados();
		//for (int i = 0; i < 1000; i++) {
			//System.out.println(i + " contador");
			//System.out.println(geraSequenciaMaiorChance(toNrosSorteados));
			return geraSequenciaMaiorChance(toNrosSorteados, null);
		//}
		//return "fim";
	}

	public String geraSequenciaMaiorChance(TONrosSorteados[] toNrosSorteados, StringBuffer resultadoAnt) {
		
//		TONrosSorteados[] toNrosApostados = listaNrosApostados(1564);
//		ArrayList<Integer> numerosIgnorados = new ArrayList<Integer>();
//		for (int i = 0; i < toNrosSorteados[0].getNumerosConjunto().length; i++) {
//			numerosIgnorados.add(toNrosSorteados[toNrosSorteados.length - 1].getNumerosConjunto()[i]);
//			numerosIgnorados.add(toNrosSorteados[toNrosSorteados.length - 2].getNumerosConjunto()[i]);
//		}
//		for (int i = 0; i < toNrosApostados.length; i++) {
//			for (int j = 0; j < toNrosSorteados[0].getNumerosConjunto().length; j++) {
//				numerosIgnorados.add(toNrosApostados[i].getNumerosConjunto()[j]);
//			}
//		}
		
		ArrayList<Integer> numerosIgnorados = new ArrayList<Integer>();
		
		//numerosIgnorados.add(26);
		
		//String sequencia = "10-30-35-39-49-50";
		String sequencia = ManipulaNumeros.geraSequencia(numerosIgnorados);
		int[] conjunto = ManipulaNumeros.converteSeqEmConj(sequencia);
		SortedSet<Integer> arrayConjunto = new TreeSet<Integer>();
		SortedSet<Integer> arrayConjuntoLista = new TreeSet<Integer>();

		// adiciona conjunto array em SortedSet
		for (int i = 0; i < conjunto.length; i++) {
			arrayConjunto.add(conjunto[i]);
		}

		// ordena o array
		int[] conjuntoOrdenado = ManipulaNumeros.ordenaNumeros(conjunto);
		StringBuffer resultado = new StringBuffer();
		
		if(resultadoAnt != null){
			resultado.append(resultadoAnt);
		}
		
		System.out.println("Sequencia gerado: " + sequencia);
		resultado.append("Sequencia gerado: <font color=\"red\">" + sequencia + "</font><br />");

		boolean descarta = false;

		for (int i = 0; i < toNrosSorteados.length; i++) {
			SortedSet<Integer> arrayConjuntoInterseccao = new TreeSet<Integer>(arrayConjunto);
			// verifica se 5 ou 6 numeros gerados já foram sorteados alguma vez
			// na historia
			for (int j2 = 0; j2 < toNrosSorteados[i].getNumerosConjunto().length; j2++) {
				arrayConjuntoLista.add(toNrosSorteados[i].getNumerosConjunto()[j2]);
			}
			arrayConjuntoInterseccao.retainAll(arrayConjuntoLista);

			if (arrayConjuntoInterseccao.size() == 6) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>A sequencia gerada já foi sorteada. Dados:");
				System.out.println("Data:" + toNrosSorteados[i].getData() + "\nSequencia: " + toNrosSorteados[i].getSequencia() + "\nNro Sorteiro: " + toNrosSorteados[i].getNro());
				
				resultado.append(">>>>>>>>>>>>>>>>>>>>>>A sequencia gerada já foi sorteada. Dados: <br />");
				resultado.append("Data:" + toNrosSorteados[i].getData() + "\nSequencia: " + toNrosSorteados[i].getSequencia() + "\nNro Sorteiro: " + toNrosSorteados[i].getNro() + "<br />");
								
				descarta = true;
				//System.exit(0); // FIXME comentar
			} else if (arrayConjuntoInterseccao.size() == 5) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>5 dos numeros gerados já cairam numa sequencia. Dados");
				System.out.println("Os numeros que já cairam: " + arrayConjuntoInterseccao.toString());
				System.out.println("Fazem parte do sorteiro:");
				System.out.println("Data:" + toNrosSorteados[i].getData() + "\nSequencia: " + toNrosSorteados[i].getSequencia() + "\nNro Sorteiro: " + toNrosSorteados[i].getNro());
				
				resultado.append(">>>>>>>>>>>>>>>>>>>>>5 dos numeros gerados já cairam numa sequencia. Dados" + "<br />");
				resultado.append("Os numeros que já cairam: " + arrayConjuntoInterseccao.toString() + "<br />");
				resultado.append("Fazem parte do sorteiro:" + "<br />");
				resultado.append("Data:" + toNrosSorteados[i].getData() + "\nSequencia: " + toNrosSorteados[i].getSequencia() + "\nNro Sorteiro: " + toNrosSorteados[i].getNro() + "<br />");
								
				descarta = true;
			}
			arrayConjuntoInterseccao.clear();
			arrayConjuntoLista.clear();
		}

		// equilibrio pares e impares
		int pares = 0;
		int impares = 0;

		for (int j = 0; j < conjunto.length; j++) {
			if (ManipulaNumeros.ehPar(conjunto[j])) {
				pares++;
			} else {
				impares++;
			}
		}

		if (pares == 1) {
			descarta = true;
			System.out.println("Apenas 1 é par");			
			resultado.append("Apenas 1 é par" + "<br />");			
		} else if (pares == 6) {
			descarta = true;
			System.out.println("** todos os numeros são pares");
			resultado.append("** todos os numeros são pares" + "<br />");
		}

		if (impares == 1) {
			descarta = true;
			System.out.println("Apenas 1 é impar");
			resultado.append("Apenas 1 é impar"+ "<br />");
		} else if (impares == 6) {
			descarta = true;
			System.out.println("** todos os numeros são impares");
			resultado.append("** todos os numeros são impares"+ "<br />");
		}

		// se existe sequencia de 3 numeros
		for (int i = 0; i < 6; i++) {
			if (arrayConjunto.contains(conjunto[i] + 1) && arrayConjunto.contains(conjunto[i] + 2)) {
				descarta = true;
				System.out.println("_ há uma sequencia de 3 numeros: " + conjuntoOrdenado[0] + "-" + conjuntoOrdenado[1] + "-" + conjuntoOrdenado[2] + "-" + conjuntoOrdenado[3] + "-" + conjuntoOrdenado[4] + "-" + conjuntoOrdenado[5]);
				resultado.append("_ há uma sequencia de 3 numeros: " + conjuntoOrdenado[0] + "-" + conjuntoOrdenado[1] + "-" + conjuntoOrdenado[2] + "-" + conjuntoOrdenado[3] + "-" + conjuntoOrdenado[4] + "-" + conjuntoOrdenado[5] + "<br />");
			}
		}

		// se existe 3 numeros na vertical
		for (int i = 0; i < 6; i++) {
			if (arrayConjunto.contains(conjunto[i] + 10) && arrayConjunto.contains(conjunto[i] + 20)) {
				descarta = true;
				System.out.println("| há uma sequencia de 3 numeros na vertical: " + conjuntoOrdenado[0] + "-" + conjuntoOrdenado[1] + "-" + conjuntoOrdenado[2] + "-" + conjuntoOrdenado[3] + "-" + conjuntoOrdenado[4] + "-" + conjuntoOrdenado[5]);
				resultado.append("| há uma sequencia de 3 numeros na vertical: " + conjuntoOrdenado[0] + "-" + conjuntoOrdenado[1] + "-" + conjuntoOrdenado[2] + "-" + conjuntoOrdenado[3] + "-" + conjuntoOrdenado[4] + "-" + conjuntoOrdenado[5] + "<br />");
			}
		}
		// se existe 3 numeros na diagonal
		for (int i = 0; i < 6; i++) {
			if ((arrayConjunto.contains(conjunto[i] + 11) && arrayConjunto.contains(conjunto[i] + 22))  || arrayConjunto.contains(conjunto[i] + 9) && arrayConjunto.contains(conjunto[i] + 18)) {
				descarta = true;
				System.out.println("\\ há uma sequencia de 3 numeros na diagonal: " + conjuntoOrdenado[0] + "-" + conjuntoOrdenado[1] + "-" + conjuntoOrdenado[2] + "-" + conjuntoOrdenado[3] + "-" + conjuntoOrdenado[4] + "-" + conjuntoOrdenado[5]);
				resultado.append("\\ há uma sequencia de 3 numeros na diagonal: " + conjuntoOrdenado[0] + "-" + conjuntoOrdenado[1] + "-" + conjuntoOrdenado[2] + "-" + conjuntoOrdenado[3] + "-" + conjuntoOrdenado[4] + "-" + conjuntoOrdenado[5] + "<br />");
			
			}
		}
		
		// excluir numeros dos ultimos sorteios
		for (int i = 0; i < 6; i++) {
			if (arrayConjunto.contains(toNrosSorteados[toNrosSorteados.length - 1].getNumerosConjunto()[i])) {
				System.out.println("O numero " + toNrosSorteados[toNrosSorteados.length - 1].getNumerosConjunto()[i] + " ja foi sorteado no ultimo jogo.");
				resultado.append("O numero " + toNrosSorteados[toNrosSorteados.length - 1].getNumerosConjunto()[i] + " ja foi sorteado no ultimo jogo." + "<br />");
				descarta = true;
			}
		}
		
		for (int i = 0; i < 6; i++) {
			if (arrayConjunto.contains(toNrosSorteados[toNrosSorteados.length - 2].getNumerosConjunto()[i])) {
				System.out.println("O numero " + toNrosSorteados[toNrosSorteados.length - 2].getNumerosConjunto()[i] + " ja foi sorteado no penultimo jogo.");
				resultado.append("O numero " + toNrosSorteados[toNrosSorteados.length - 2].getNumerosConjunto()[i] + " ja foi sorteado no penultimo jogo." + "<br />");
				descarta = true;
			}
		}

		/*
		 * for (int i = 0; i < 6; i++) { if
		 * (arrayConjunto.contains(toNrosSorteados[toNrosSorteados.length -
		 * 3].getNumerosConjunto()[i])) { System.out.println("O numero " +
		 * toNrosSorteados[toNrosSorteados.length - 3] .getNumerosConjunto()[i]
		 * + " ja foi sorteado no antepenultimo jogo."); descarta = true; } }
		 */

		// descartar sequencias de numero que ficam em apenas 1 linha
		int cont1 = 0;
		int cont2 = 0;
		int cont3 = 0;
		int cont4 = 0;
		int cont5 = 0;
		int cont6 = 0;

		for (int i = 0; i < 6; i++) {
			if (conjunto[i] >= 1 && conjunto[i] <= 10) {
				cont1++;
			}
			if (conjunto[i] > 10 && conjunto[i] <= 20) {
				cont2++;
			}
			if (conjunto[i] > 20 && conjunto[i] <= 30) {
				cont3++;
			}
			if (conjunto[i] > 30 && conjunto[i] <= 40) {
				cont4++;
			}
			if (conjunto[i] > 40 && conjunto[i] <= 50) {
				cont5++;
			}
			if (conjunto[i] > 50 && conjunto[i] <= 60) {
				cont6++;
			}
		}

		if (cont1 > 3) {
			descarta = true;
			System.out.println("L1 *****************************há " + cont1 + " numeros na primeira linha");
			resultado.append("L1 *****************************há " + cont1 + " numeros na primeira linha"+ "<br />");
		}
		if (cont2 > 3) {
			descarta = true;
			System.out.println("L2 *****************************há " + cont2 + " numeros na segunda linha");
			resultado.append("L2 *****************************há " + cont2 + " numeros na segunda linha"+ "<br />");
		}
		if (cont3 > 3) {
			descarta = true;
			System.out.println("L3 *****************************há " + cont3 + " numeros na terceira linha");
			resultado.append("L3 *****************************há " + cont3 + " numeros na terceira linha"+ "<br />");
		}
		if (cont4 > 3) {
			descarta = true;
			System.out.println("L4 *****************************há " + cont4 + " numeros na quarta linha");
			resultado.append("L4 *****************************há " + cont4 + " numeros na quarta linha"+ "<br />");
		}
		if (cont5 > 3) {
			descarta = true;
			System.out.println("L5 *****************************há " + cont5 + " numeros na quinta linha");
			resultado.append("L5 *****************************há " + cont5 + " numeros na quinta linha"+ "<br />");
		}
		if (cont6 > 3) {
			descarta = true;
			System.out.println("L6 *****************************há " + cont6 + " numeros na sexta linha");
			resultado.append("L6 *****************************há " + cont6 + " numeros na sexta linha"+ "<br />");
		}
		
		int soma = 0;
		
		for (int i = 0; i < conjunto.length; i++) {
			soma += conjunto[i];
		}
		
		if (soma < 132 || soma > 234){
			descarta = true;
			System.out.println("¨¨¨¨¨¨A soma das dezenas ficou fora da faixa permitida: " + soma);
			resultado.append("¨¨¨¨¨¨A soma das dezenas ficou fora da faixa permitida: " + soma+ "<br />");
		}		
		

		if (!descarta) {
			System.out.println("Boa sorte \n");
			resultado.append("Boa sorte" + "<br />");
			
			return resultado.toString();

		} else {
			System.out.println("Gera de novo \n");
			resultado.append("Gera de novo" + "<br /><br />");
			
			return geraSequenciaMaiorChance(toNrosSorteados, resultado);
			//return null;
		}

	}

	public String verificaNumerosAcertados(int nroJogo) {
		EBMegaSena ebMegaSena = new EBMegaSena();
		TONrosSorteados[] toNrosApostados = ebMegaSena.listaNrosApostados(nroJogo);
		TONrosSorteados toNrosSorteados = ebMegaSena.getNrosSorteados(nroJogo);

		if (toNrosSorteados == null) {
			return "Concurso não cadastrado";
		} else if (toNrosApostados == null) { 
			return "Não há apostas cadasradas para este concurso";
		} else {

			ArrayList<Integer> numerosSorteadosArrayList = new ArrayList<Integer>();

			for (int i = 0; i < toNrosSorteados.getNumerosConjunto().length; i++) {
				numerosSorteadosArrayList.add(toNrosSorteados.getNumerosConjunto()[i]);
			}
			StringBuffer resultado = new StringBuffer();

			System.out.println("Sequencia sorteada no concurso: " + toNrosSorteados.getNro() + ": " + toNrosSorteados.getSequencia());
			resultado.append("Sequencia sorteada no concurso: " + toNrosSorteados.getNro() + ": " + toNrosSorteados.getSequencia() + "<br />");

			for (int i = 0; i < toNrosApostados.length; i++) {
				ArrayList<Object> temp = new ArrayList<Object>();
				for (int j = 0; j < 6; j++) {
					if (numerosSorteadosArrayList.contains(toNrosApostados[i].getNumerosConjunto()[j])) {
						temp.add(toNrosApostados[i].getNumerosConjunto()[j]);
					}
				}

				if (!temp.isEmpty()) {
					System.out.println(i + 1 + ":");
					resultado.append(i + 1 + ":<br />");
					if (temp.size() == 3) {
						System.out.println("Quase!!");
						resultado.append("Quase!!<br />");
					}
					if (temp.size() == 4) {
						System.out.println("Aeww ganhou uma graninha pra beber");
						resultado.append("Aeww ganhou uma graninha pra beber<br />");
					}
					if (temp.size() == 5) {
						System.out.println("Bora compra um carro");
						resultado.append("Bora compra um carro<br />");
					}
					if (temp.size() == 6) {
						System.out.println("Finalmente milhonario!!! Caralho!!!");
						resultado.append("Finalmente milhonario!!! Caralho!!!<br />");
					}

					System.out.println("Você acertou os números: " + temp);
					resultado.append("Você acertou os números: " + temp + "<br />");
					System.out.println("Da sequencia apostada: " + toNrosApostados[i].getSequencia());
					resultado.append("Da sequencia apostada: " + toNrosApostados[i].getSequencia() + "<br />");
				}

			}

			return resultado.toString();
			// System.out.println(toNrosSorteados.getSequencia());
		}

	}

	public String geraCombinacaoDezNumeros(ArrayList<Integer> conjunto) {

		int A = (int) conjunto.get(0);
		int B = (int) conjunto.get(1);
		int C = (int) conjunto.get(2);
		int D = (int) conjunto.get(3);
		int E = (int) conjunto.get(4);
		int F = (int) conjunto.get(5);
		int G = (int) conjunto.get(6);
		int H = (int) conjunto.get(7);
		int I = (int) conjunto.get(8);
		int J = (int) conjunto.get(9);

		System.out.println("1: " + A + "-" + B + "-" + D + "-" + F + "-" + G + "-" + I);
		System.out.println("2: " + A + "-" + C + "-" + D + "-" + F + "-" + H + "-" + I);
		System.out.println("3: " + A + "-" + C + "-" + E + "-" + F + "-" + H + "-" + J);
		System.out.println("4: " + B + "-" + C + "-" + E + "-" + G + "-" + H + "-" + J);
		System.out.println("5: " + B + "-" + D + "-" + E + "-" + G + "-" + I + "-" + J);
		
		StringBuffer resultado = new StringBuffer();
		
		resultado.append("1: " + A + "-" + B + "-" + D + "-" + F + "-" + G + "-" + I + "<br />");
		resultado.append("2: " + A + "-" + C + "-" + D + "-" + F + "-" + H + "-" + I + "<br />");
		resultado.append("3: " + A + "-" + C + "-" + E + "-" + F + "-" + H + "-" + J + "<br />");
		resultado.append("4: " + B + "-" + C + "-" + E + "-" + G + "-" + H + "-" + J + "<br />");
		resultado.append("5: " + B + "-" + D + "-" + E + "-" + G + "-" + I + "-" + J + "<br />");
		
		return resultado.toString();
	}

	public static void main(String[] args) {

		//new SBMegaSena().contaNumeros();
		//new SBMegaSena().verificaNumerosAcertados(1574);
		new SBMegaSena().geraMaster(); // TODO gera eh aki
		// new SBMegaSena().importaDados("", "");

		//new SBMegaSena().listaNrosApostados(0);
		
		//new SBMegaSena().importaDados("resultados.txt", "C:\\arquivos_megasena");
		
		
		//new SBMegaSena().insereNrosSorteados(toNrosSorteados);
		//new SBMegaSena().insereNrosApostados(toNrosSorteados);
		
/*		TONrosSorteados 
		toNrosSorteados = new TONrosSorteados(1575, "17/02/2014", "15-25-42-53-54-59");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);*/
		
		/*toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "02-34-35-39-43-49");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);
		toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "06-09-23-36-37-45");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);
		toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "08-16-17-19-39-55");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);
		toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "07-12-25-38-57-58");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);
		toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "16-21-22-29-38-43");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);
		toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "02-13-19-22-38-40");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);
		toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "06-12-17-35-36-38");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);
		toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "08-11-21-28-50-59");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);
		toNrosSorteados = new TONrosSorteados(1571, "05/02/2014", "02-14-19-38-47-49");
		new SBMegaSena().insereNrosApostados(toNrosSorteados);*/
		
//		TODO Gera Combinação aki
//		  Set<Integer> conjunto = new TreeSet<Integer>();
//		  
//		  conjunto.add(12); conjunto.add(30); conjunto.add(35);
//		  conjunto.add(36); conjunto.add(39); conjunto.add(43);
//		  conjunto.add(7); conjunto.add(10); conjunto.add(49);
//		  conjunto.add(50);
//		  
//		  ArrayList<Integer> novoConnjunto = new ArrayList<Integer>(conjunto);
//		  
//		 new SBMegaSena().geraCombinacaoDezNumeros(novoConnjunto);


		 

		/*
		 * System.out.println("Inicio"); for (int i = 0; i < 100001; i++){
		 * //System.out.print(i + " "); new
		 * SBMegaSena().geraSequenciaMaiorChance(); if(i==100000)
		 * System.out.println("Fim"); }
		 */

		/*
		 * TONrosSorteados[] toNrosSorteados = new
		 * SBMegaSena().listaNrosApostados(1562);
		 * 
		 * for (int i = 0; i < toNrosSorteados.length; i++) {
		 * System.out.println( "Data:" + toNrosSorteados[i].getData() +
		 * " Sequencia: " + toNrosSorteados[i].getSequencia() + " Sorteio: " +
		 * toNrosSorteados[i].getNro() + " n1:" +
		 * toNrosSorteados[i].getNumerosConjunto()[0]); }
		 * 
		 * System.out.println("");
		 */

		/*
		 * TONrosSorteados[] toNrosSorteados = new
		 * SBMegaSena().listaNrosSorteados();
		 * 
		 * for (int i = 0; i < toNrosSorteados.length; i++) {
		 * 
		 * int[] conjunto = toNrosSorteados[i].getNumerosConjunto();
		 * ManipulaNumeros.ordenaNumeros(conjunto);
		 * 
		 * if ((conjunto[0] == conjunto[1] - 1 && conjunto[1] == conjunto[2] -
		 * 1) || (conjunto[1] == conjunto[2] - 1 && conjunto[2] == conjunto[3] -
		 * 1) || (conjunto[2] == conjunto[3] - 1 && conjunto[3] == conjunto[4] -
		 * 1) || (conjunto[3] == conjunto[4] - 1 && conjunto[4] == conjunto[5] -
		 * 1)) { System.out.println("há uma sequencia de 3 numeros");
		 * System.out.println(conjunto[0] + "-" + conjunto[1] + "-" +
		 * conjunto[2] + "-" + conjunto[3] + "-" + conjunto[4] + "-" +
		 * conjunto[5]); System.out.println(toNrosSorteados[i].getData()); }
		 * 
		 * // se existe 3 numeros na vertical for (int k = 0; k < 5; k++) {
		 * boolean ok1 = false; boolean ok2 = false; for (int j = 0; j < 5; j++)
		 * { if (conjunto[k] == conjunto[j] - 10) { ok1 = true; } for (int l =
		 * 0; l < 5; l++) { if (conjunto[k] == conjunto[l] - 20) { ok2 = true; }
		 * } }
		 * 
		 * if (ok1 && ok2) {
		 * System.out.println("há uma sequencia de 3 numeros na vertical");
		 * System.out.println(conjunto[0] + "-" + conjunto[1] + "-" +
		 * conjunto[2] + "-" + conjunto[3] + "-" + conjunto[4] + "-" +
		 * conjunto[5]); }
		 * 
		 * }
		 */

		// }

	}

}
