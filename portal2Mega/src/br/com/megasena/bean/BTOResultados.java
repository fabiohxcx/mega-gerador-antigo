package br.com.megasena.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BTOResultados {
	protected int concurso;
	protected String data;
	protected String sequencia;

	public int getConcurso() {
		return concurso;
	}

	public void setConcurso(int concurso) {
		this.concurso = concurso;
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
