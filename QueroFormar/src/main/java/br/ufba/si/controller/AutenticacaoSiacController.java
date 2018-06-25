package br.ufba.si.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufba.si.entidade.Usuario;

@Named
@SessionScoped
@ManagedBean(name = "autenticacaoSiacController")
public class AutenticacaoSiacController implements Serializable {

    private static final long serialVersionUID = -117457205700614867L;
	private Usuario usuarioLogado;
	private String login;
    private String senha;
   

    public AutenticacaoSiacController() {
    	
    }

    @PostConstruct
    public void init() {
        usuarioLogado = new Usuario();
    }

    public String logIn() {
       	usuarioLogado.setLogin(login);
    	usuarioLogado.setSenha(senha);
    	
    	 /*usuarioLogado = usuarioDAO.buscar(login, senha);*/
    	
        if (usuarioLogado.getNome() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou Senha Inválidos", "Login Inválido"));
            return null;
        } else {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if (session != null) {
                session.setAttribute("usuario", usuarioLogado);
            }
            return "/index?faces-redirect=true";
        }
    }

    public String logOff() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        return "/login?faces-redirect=true";
    }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}