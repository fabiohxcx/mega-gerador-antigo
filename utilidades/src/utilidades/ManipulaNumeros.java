package utilidades;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class ManipulaNumeros {
	
	public static int geraUmNumeroMath(ArrayList<Integer> numeros, Random gerador) {
		if (numeros == null) {
			numeros = new ArrayList<>();
		}

		if(gerador == null){
			gerador = new Random();
		}
				
		int numero = (int)Math.round(Math.random() * 59) + 1;
		
		if (numero > 0 && numero < 61 && !numeros.contains(numero)) {
			return numero;
		} else {
			return geraUmNumeroMath(numeros, gerador);			
		}

	}

	public static int geraUmNumero(ArrayList<Integer> numeros, Random gerador) {
		
		if(numeros == null){
			numeros = new ArrayList<>();
/*			numeros.add(47);
			numeros.add(34);
			numeros.add(7);
			numeros.add(56);
			numeros.add(31);
			numeros.add(6);
			numeros.add(18);
			numeros.add(58);
			numeros.add(38);
			numeros.add(57);
			numeros.add(40);
			numeros.add(3);
			numeros.add(60);
			numeros.add(20);
			numeros.add(11);
			numeros.add(1);
			numeros.add(35);
			numeros.add(46);
			numeros.add(19);
			numeros.add(48);
			numeros.add(25);
			numeros.add(14);
			numeros.add(15);
			numeros.add(39);
			numeros.add(45);
			numeros.add(55);
			numeros.add(9);
			numeros.add(21);
			numeros.add(22);
			numeros.add(26);*/
		}
		
		if(gerador == null){
			gerador = new Random();
		}
				
		int numero = gerador.nextInt(60) + 1;
		
		if (numero > 0 && numero < 61 && !numeros.contains(numero)) {
			return numero;
		} else {
			return geraUmNumero(numeros, gerador);			
		}
	}

	public static String geraSequencia(ArrayList<Integer> numerosIgnorados) {
		Random gerador = new Random();		
		SortedSet<Integer> jogo = new TreeSet<Integer>();
		int i, j, n;
		
		for (j = 1; j <= 3; j++) {
			// aceita somente números que ainda não foram usados
			do {
				// gera um número aleatório entre 1 e 60 (inclusive)
				// n = (int)Math.round(Math.random() * 59) + 1;
				n = geraUmNumero(numerosIgnorados, gerador);
			} while (jogo.contains(n));
			jogo.add(n);
		}

		for (j = 1; j <= 3; j++) {
			// aceita somente números que ainda não foram usados
			do {
				// gera um número aleatório entre 1 e 60 (inclusive)
				// n = (int)Math.round(Math.random() * 59) + 1;
				n = geraUmNumeroMath(numerosIgnorados, gerador);
			} while (jogo.contains(n));
			jogo.add(n);
		}
		
		Iterator<Integer> it = jogo.iterator();
		StringBuffer sequencia = new StringBuffer();
		sequencia.append(it.next()); //adiciona o primeiro elemento sem o traço

		for (i = 0; i < jogo.size() - 1; i++) {
			sequencia.append("-" + it.next());
		}

		return sequencia.toString();
	}

	public static int[] converteSeqEmConj(String sequencia) {
		String[] strNumeroConjunto = sequencia.split("-");
		int[] numerosConjunto = new int[strNumeroConjunto.length];

		for (int i = 0; i < strNumeroConjunto.length; i++) {
			numerosConjunto[i] = Integer.parseInt(strNumeroConjunto[i]);
		}

		return numerosConjunto;
	}

	public static boolean ehPar(int numero) {
		if (numero % 2 == 0)
			return true;
		else
			return false;
	}
	
	public static int[] ordenaNumeros(int[] v) {
		int indexMin;
		int aux;

		for (int i = 0; i < v.length - 1; i++) {
			indexMin = i;
			for (int j = i + 1; j < v.length; j++) {
				if (v[j] < v[indexMin]) {
					indexMin = j;
				}
			}
			if (indexMin != i) {
				aux = v[indexMin];
				v[indexMin] = v[i];
				v[i] = aux;
			}
		}
		return v;
	}

	public static void main(String[] args) {
		System.out.println(geraSequencia(null));
	}
}

