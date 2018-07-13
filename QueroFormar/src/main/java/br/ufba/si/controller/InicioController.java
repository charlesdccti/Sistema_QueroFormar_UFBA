package br.ufba.si.controller;
//inicioController

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.ufba.si.business.AutenticarSiac;
import br.ufba.si.entidade.Fluxograma;
import br.ufba.si.entidade.Usuario;

@Named
@SessionScoped
@ManagedBean(name = "inicioController")
public class InicioController implements Serializable {

	
	private static final long serialVersionUID = 2661206256546477703L;
	
	private Usuario usuarioLogado;
	private Fluxograma fluxogramaSi;
	
	
	
	@EJB
	LoginController loginController;
	
	@PostConstruct
    public void init() {
        fluxogramaSi = new Fluxograma();
    }
	
	public InicioController(){
	   fluxogramaSi = new Fluxograma();
	   if(usuarioLogado == null){
		   usuarioLogado = new Usuario();
	   }
	   
	}
	
	public void obterDadosSiac(){
		
		usuarioLogado.setLogin(loginController.getCpf());
    	usuarioLogado.setSenha(loginController.getSenha());
    	
    	AutenticarSiac siac = new AutenticarSiac();
    	
    	try {
    				
    		boolean autenticou = siac.login("https://siac.ufba.br/SiacWWW/LogonSubmit.do",usuarioLogado.getLogin(), usuarioLogado.getSenha());
		
			if (autenticou) {
				 // Acessa página dos componetes curriculares
				siac.openPage("https://siac.ufba.br/SiacWWW/ConsultarComponentesCurricularesCursados.do", usuarioLogado);
				
				
				
				
	        } else {       
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao obter Histórico do SIAC", " "));	            
	        }   	
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
