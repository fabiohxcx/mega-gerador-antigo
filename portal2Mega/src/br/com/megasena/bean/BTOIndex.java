package br.com.megasena.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.megasena.co.CoCadAposta;
import types.TONrosSorteados;
import utilidades.ExtrairResultadosURL;

@ManagedBean
@SessionScoped
public class BTOIndex {
	// dados para exibir ultimo sorteio no index.xhtml
	protected int concurso;
	protected String data;
	protected String sequencia;

	public BTOIndex() {
		super();
		TONrosSorteados toNrosSorteados = ExtrairResultadosURL.recuperaUltimoJogo(null);
		this.concurso = toNrosSorteados.getNro();
		this.data = toNrosSorteados.getData();
		this.sequencia = toNrosSorteados.getSequencia();
		
		new CoCadAposta();
	}

	/**
	 * @return the concurso
	 */
	public int getConcurso() {
		return concurso;
	}

	/**
	 * @param concurso
	 *            the concurso to set
	 */
	public void setConcurso(int concurso) {
		this.concurso = concurso;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the sequencia
	 */
	public String getSequencia() {
		return sequencia;
	}

	/**
	 * @param sequencia
	 *            the sequencia to set
	 */
	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}

}
