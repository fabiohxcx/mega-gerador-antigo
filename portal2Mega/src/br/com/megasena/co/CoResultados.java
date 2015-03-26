package br.com.megasena.co;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import types.TONrosSorteados;
import br.com.megasena.bean.BTOResultados;
import br.com.megasena.sb.SBMegaSena;

@ManagedBean
public class CoResultados {
	
	public String doExibir(){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);
		
		List<BTOResultados> btoResultadosArray = new ArrayList<BTOResultados>();	
		SBMegaSena sbMegaSena =  new SBMegaSena();		
		TONrosSorteados[] toNrosSorteados = sbMegaSena.listaNrosSorteados();
		
		for (int i = 0; i < toNrosSorteados.length; i++) {
			BTOResultados btoResultados =  new BTOResultados();		
			btoResultados.setConcurso(toNrosSorteados[i].getNro());
			btoResultados.setData(toNrosSorteados[i].getData());
			btoResultados.setSequencia(toNrosSorteados[i].getSequencia());
			btoResultadosArray.add(btoResultados);
		}
		
		session.setAttribute("varbTOResultados", btoResultadosArray);
		
		
		return "resultados";
	}

}
