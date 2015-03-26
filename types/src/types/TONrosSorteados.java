package types;


public class TONrosSorteados {
	private int nro;
	private String data;
	private String sequencia;
	private int[] numerosConjunto;

	public int[] getNumerosConjunto() {
		return numerosConjunto;
	}

	public void setNumerosConjunto(int[] numerosConjunto) {
		this.numerosConjunto = numerosConjunto;
	}
	public TONrosSorteados() {
		super();
	}

	public TONrosSorteados(int nro, String data, String sequencia, int[] numerosConjunto) {
		super();
		this.nro = nro;
		this.data = data;
		this.sequencia = sequencia;
		this.numerosConjunto = numerosConjunto;
	}

	public TONrosSorteados(int nro, String data, String sequencia) {
		super();
		this.nro = nro;
		this.data = data;
		this.sequencia = sequencia;
	}

	public int getNro() {
		return nro;
	}

	public void setNro(int nro) {
		this.nro = nro;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSequencia() {
		return sequencia;
	}

	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}

}
