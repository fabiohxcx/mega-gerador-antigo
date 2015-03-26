package br.com.megasena.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class BTOCadAposta {
	protected int concurso;
	protected String data;
	protected String sequencia;
	protected String conferirResutado;
	
	/**
	 * @return the conferirResutado
	 */
	public String getConferirResutado() {
		return conferirResutado;
	}
	/**
	 * @param conferirResutado the conferirResutado to set
	 */
	public void setConferirResutado(String conferirResutado) {
		this.conferirResutado = conferirResutado;
	}
	/**
	 * @return the concurso
	 */
	public int getConcurso() {
		return concurso;
	}
	/**
	 * @param concurso the concurso to set
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
	 * @param data the data to set
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
	 * @param sequencia the sequencia to set
	 */
	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}
	
	
}
