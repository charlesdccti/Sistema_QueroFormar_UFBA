package br.ufba.si.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufba.si.business.AutenticarSiac;
import br.ufba.si.entidade.Usuario;

@Named
@SessionScoped
@ManagedBean(name = "loginController")
public class LoginController implements Serializable {

    private static final long serialVersionUID = -117457205700614867L;
	private Usuario usuarioLogado;
	private String cpf;
    private String senha;
   

    public LoginController() {
    	
    }

    @PostConstruct
    public void init() {
        usuarioLogado = new Usuario();
        this.cpf = "";
        this.senha = "";
    }

    public String logIn() {
       	usuarioLogado.setLogin(cpf);
    	usuarioLogado.setSenha(senha);
    	
    	AutenticarSiac navegador = new AutenticarSiac();
    	
    	navegador.main("https://siac.ufba.br/SiacWWW/LogonSubmit.do",cpf, senha);
    	
    	try {
    				
    		boolean autenticou = navegador.login("https://siac.ufba.br/SiacWWW/LogonSubmit.do",cpf, senha);
		
			if (autenticou) {
			    
				return "/index?faces-redirect=true";
				
	        } else {       
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF ou Senha Inválidos", "Login Inválido"));
	            return null;
	            
	        }   	
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }

    public String logOff() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        return "/login?faces-redirect=true";
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}