package br.ufba.si.controller;
//inicioController

import java.io.Serializable;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import br.ufba.si.entidade.Fluxograma;
import br.ufba.si.entidade.Usuario;

@Named
@ManagedBean(name = "inicioController")
public class InicioController implements Serializable {

	
	private static final long serialVersionUID = 2661206256546477703L;
	
	private Usuario usuarioLogado;
	private Fluxograma fluxogramaSi;
	
	@PostConstruct
    public void init() {
        fluxogramaSi = new Fluxograma();
    }
	
	

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
