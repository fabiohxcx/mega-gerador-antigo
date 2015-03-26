package br.com.megasena.co;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import types.TONrosSorteados;
import utilidades.ExtrairResultadosURL;
import utils.Escopos;
import br.com.megasena.bean.BTOCadSorteio;
import br.com.megasena.sb.SBMegaSena;

@ManagedBean
public class CoCadSorteio {
	
	public String doExibirResultadoG1(){
		
		BTOCadSorteio btoCadSorteio = new BTOCadSorteio();
		
		TONrosSorteados toNrosSorteados = ExtrairResultadosURL.recuperaUltimoJogo(null);
		
		btoCadSorteio.setConcurso(toNrosSorteados.getNro());
		btoCadSorteio.setData(toNrosSorteados.getData());
		btoCadSorteio.setSequencia(toNrosSorteados.getSequencia());

		Escopos.requestSet(btoCadSorteio, "bTOCadSorteio");
		
		return "";
	}
	
	public String doCadastrar() {

		BTOCadSorteio btoCadSorteio = (BTOCadSorteio) Escopos.requestGet("bTOCadSorteio");

		if (btoCadSorteio.getConcurso() == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Preencher campo: Concurso"));
		} else if (btoCadSorteio.getData().equals("") || btoCadSorteio.getData() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Preencher campo: Data"));
		} else if (btoCadSorteio.getSequencia().equals("") || btoCadSorteio.getSequencia() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Preencher campo: Dezenas"));
		} else {
			SBMegaSena sbMegaSena = new SBMegaSena();
			TONrosSorteados toNrosSorteados2 = sbMegaSena.getNrosSorteados(btoCadSorteio.getConcurso());

			if (toNrosSorteados2 == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Concurso cadastrado com sucesso!"));

				TONrosSorteados toNrosSorteados = new TONrosSorteados(btoCadSorteio.getConcurso(), btoCadSorteio.getData(),
						btoCadSorteio.getSequencia());

				sbMegaSena.insereNrosSorteados(toNrosSorteados);
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Concurso já está cadastrado no banco"));
			}

		}
				
		BTOCadSorteio btoCadSorteio2 = new BTOCadSorteio();
		Escopos.requestSet(btoCadSorteio2, "bTOCadSorteio");		
		
		return "";
	}

	public String doLimpar(){
		
		BTOCadSorteio btoCadSorteio = new BTOCadSorteio();
	
		Escopos.requestSet(btoCadSorteio, "bTOCadSorteio");
		
		return "";
	}

}
