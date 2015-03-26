package br.com.megasena.co;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import types.TONrosSorteados;
import utils.Escopos;
import br.com.megasena.bean.BTOCadAposta;
import br.com.megasena.sb.SBMegaSena;

@ManagedBean
public class CoCadAposta {	
	
	public CoCadAposta() {
		doExibirApostas();
	}
	
	public String doCadastrar() {

		BTOCadAposta btoCadAposta = (BTOCadAposta) Escopos.requestGet("bTOCadAposta");

		if (btoCadAposta.getConcurso() == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Preencher campo: Concurso"));
		} else if (btoCadAposta.getData().equals("") || btoCadAposta.getData() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Preencher campo: Data"));
		} else if (btoCadAposta.getSequencia().equals("") || btoCadAposta.getSequencia() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Preencher campo: Dezenas"));
		} else {
			SBMegaSena sbMegaSena = new SBMegaSena();
			TONrosSorteados toNrosSorteados = new TONrosSorteados(btoCadAposta.getConcurso(), btoCadAposta.getData(), btoCadAposta.getSequencia());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Aposta cadastrado com sucesso!"));
			sbMegaSena.insereNrosApostados(toNrosSorteados);
					
			btoCadAposta.setSequencia("");

		}
		return "";
	}

	public String doLimpar() {
		BTOCadAposta btoCadAposta = new BTOCadAposta();

		Escopos.requestSet(btoCadAposta, "bTOCadAposta");

		return "";
	}
	
	public String doConferir(){
		
		BTOCadAposta btoCadAposta = (BTOCadAposta) Escopos.requestGet("bTOCadAposta");
		
		SBMegaSena sbMegaSena = new SBMegaSena();
		
		btoCadAposta.setConferirResutado(sbMegaSena.verificaNumerosAcertados(btoCadAposta.getConcurso()));
		
		return "";
	}
	
	public String doExibirApostas() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);

		List<BTOCadAposta> btoCadApostaArray = new ArrayList<BTOCadAposta>();

		TONrosSorteados[] toNrosSorteados = new SBMegaSena().listaNrosApostados(0);

		for (int i = 0; i < toNrosSorteados.length; i++) {
			BTOCadAposta btoCadAposta = new BTOCadAposta();
			btoCadAposta.setConcurso(toNrosSorteados[i].getNro());
			btoCadAposta.setData(toNrosSorteados[i].getData());
			btoCadAposta.setSequencia(toNrosSorteados[i].getSequencia());
			btoCadApostaArray.add(btoCadAposta);

		}

		session.setAttribute("varbTOApostas", btoCadApostaArray);
		
		return "";

	}
	
	public String doExcluir(){
		
		BTOCadAposta btoCadAposta = (BTOCadAposta) Escopos.requestGet("aposta");
		
		Escopos.requestSet(btoCadAposta, "bTOCadAposta");
		
		return "excluir_aposta";
	}

}
