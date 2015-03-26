package br.com.megasena.co;

import javax.faces.bean.ManagedBean;

import utils.Escopos;
import br.com.megasena.bean.BTOGerador;
import br.com.megasena.sb.SBMegaSena;

@ManagedBean
public class CoGerador {
	
	public String doGerarAction(){
		
		BTOGerador btoGerador = new BTOGerador();		
		SBMegaSena sbMegaSena = new SBMegaSena();
		
		btoGerador.setResultados(sbMegaSena.geraMaster());
		
		Escopos.requestSet(btoGerador, "bTOGerador");
		
		return "";
	}
	
	public String doLimparAction(){
		BTOGerador btoGerador = new BTOGerador();	
		Escopos.requestSet(btoGerador, "bTOGerador");
		
		return "";
	}

}
