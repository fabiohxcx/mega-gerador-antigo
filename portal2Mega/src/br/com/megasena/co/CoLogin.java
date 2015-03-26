package br.com.megasena.co;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import types.TEEmailNaoCadastrado;
import types.TEEmailNaoInformado;
import types.TEFormatoEmailInvalido;
import types.TESenhaNaoInformado;
import types.TOLogin;
import utilidades.Criptografia;
import br.com.megasena.bean.BTOLogin;
import br.com.megasena.sb.SBLogin;

@ManagedBean
public class CoLogin {

	public String doLogar() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		BTOLogin btoLogin = (BTOLogin) request.getAttribute("bTOLogin");

		TOLogin toLoginTela = new TOLogin(btoLogin.getEmail(), btoLogin.getSenha());
		SBLogin sbLogin = new SBLogin();
		TOLogin toLogin = null;

		try {
			toLogin = sbLogin.getLogin(toLoginTela);
		} catch (SQLException e1) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e1.getMessage()));
			e1.printStackTrace();
		} catch (TEEmailNaoInformado e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "E-mail não informado!"));
			e.printStackTrace();
		} catch (TEFormatoEmailInvalido e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Formato e-mail inválido"));
			e.printStackTrace();
		} catch (TESenhaNaoInformado e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha não informado!"));
			e.printStackTrace();
		} catch (TEEmailNaoCadastrado e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "E-mail e/ou Senha incorreta(s)!"));
			e.printStackTrace();
		} 
		
		if (toLogin != null) {
			String senhaCrypto = Criptografia.sha256Hex(toLoginTela.getSenha());

			if (toLogin.getEmail().equals(btoLogin.getEmail()) && toLogin.getSenha().equals(senhaCrypto)) {
				autentica(toLoginTela);
				//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "E-mail e senha Corretas!!!"));
				return "LOGAR";
			} else {
				FacesContext.getCurrentInstance()
						.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "E-mail e/ou Senha incorreta(s)!"));
			}
		} 

		return "";
		
	}
	
	private void autentica(TOLogin toLoginTela){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);
		session.setAttribute("usuario", toLoginTela.getEmail());
	}
	
	public String doLogoff(){
		//Contexto da Aplicação
		FacesContext fc = FacesContext.getCurrentInstance();
		//Verifica a sessao e a grava na variavel
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		//Fecha/Destroi sessao 
		session.invalidate();
		
		return "login";
	}

}
