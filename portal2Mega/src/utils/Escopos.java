package utils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Escopos {
	
	public static Object requestGet(String objeto){
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		
		return request.getAttribute(objeto);
	}
	
	public static void requestSet(Object objeto, String nomeRequest){
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		request.setAttribute(nomeRequest, objeto);
	}
	
	public static Object sessionGet(String nomeSession){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);
		
		return session.getAttribute(nomeSession);
	}
	
	public static void sessionPut(Object objeto, String nomeSession){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);
		
		session.setAttribute(nomeSession, objeto);	
	}

}
