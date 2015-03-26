package br.com.megasena.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter (servletNames = {"Faces Servlet"})
public class ControleDeAcesso implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		if(session.getAttribute("usuario") != null || req.getRequestURI().endsWith("login.xhtml") || req.getRequestURI().contains("javax.faces.resource/")){
			chain.doFilter(request, response);
		} else {
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect("login.xhtml");
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}




}
