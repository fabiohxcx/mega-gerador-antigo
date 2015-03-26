package br.com.megasena.sb;

import java.sql.SQLException;

import types.TEEmailNaoCadastrado;
import types.TEEmailNaoInformado;
import types.TEFormatoEmailInvalido;
import types.TESenhaNaoInformado;
import types.TOLogin;
import utilidades.EmailValidator;
import br.com.megasena.eb.EBLogin;

public class SBLogin {
	public boolean existeLogin(String email) {
		EBLogin ebLogin = new EBLogin();
		return ebLogin.existeLogin(email);
	}

	public void incluirLogin(TOLogin toLogin) {
		EBLogin ebLogin = new EBLogin();
		ebLogin.incluirLogin(toLogin);
	}

	public void excluirLogin(String email) {
		EBLogin ebLogin = new EBLogin();
		ebLogin.excluirLogin(email);
	}

	public TOLogin getLogin(TOLogin toLogin) throws TESenhaNaoInformado, TEFormatoEmailInvalido, TEEmailNaoInformado, TEEmailNaoCadastrado, SQLException {
		
		if(toLogin.getEmail().equals("") || toLogin.getEmail() == null ){
			throw new TEEmailNaoInformado("E-mail Não Informado");
		}			
		
		if(!new EmailValidator().validate(toLogin.getEmail())){
			throw new TEFormatoEmailInvalido("Formado e-mail inválido!");
		}
		
		if(toLogin.getSenha().equals("") || toLogin.getSenha() == null ){
			throw new TESenhaNaoInformado("Senha Não Informado");
		}	
		
		EBLogin ebLogin = new EBLogin();
		TOLogin toLogin2  = ebLogin.getLogin(toLogin.getEmail());
		
		if(toLogin2 == null){
			throw new TEEmailNaoCadastrado("Email não cadastrado");
		}
		
		return toLogin2;
	}

}
