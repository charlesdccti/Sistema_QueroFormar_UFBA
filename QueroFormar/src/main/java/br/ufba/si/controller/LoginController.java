package br.ufba.si.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufba.si.business.AutenticarSiac;
import br.ufba.si.entidade.Disciplina;
import br.ufba.si.entidade.Fluxograma;
import br.ufba.si.entidade.Usuario;
import br.ufba.si.utils.ResultadoEnum;

@Named
@ViewScoped
@ManagedBean(name = "loginController")
public class LoginController implements Serializable {

    private static final long serialVersionUID = -117457205700614867L;
	private Usuario usuarioLogado;
	private String cpf;
    private String senha;
    /*Remover Depois*/
    private String nome;
    private int value;
    private String matricula;
    
    private Fluxograma fluxogramaSi;
    private ArrayList<Disciplina> disciplinaSugeridaList = new ArrayList<Disciplina>();
    
    //@EJB
    //InicioController inicioController = new InicioController();

   

    public LoginController() {
    	
    }

    @PostConstruct
    public void init() {
        this.cpf = "";
        this.senha = "";
        usuarioLogado = new Usuario(cpf, senha);
        this.nome = usuarioLogado.getNome();
        Random rand = new Random();
        value = rand.nextInt(50) + 1;
        fluxogramaSi = new Fluxograma();
        
    }

    public String logIn() {
       	usuarioLogado.setLogin(cpf);
    	usuarioLogado.setSenha(senha);
    	
    	AutenticarSiac siac = new AutenticarSiac();
    	
    	try {
    				
    		boolean autenticou = siac.login("https://siac.ufba.br/SiacWWW/LogonSubmit.do",cpf, senha);
		
			if (autenticou) {
				 // Acessa página dos componetes curriculares
				siac.openPage("https://siac.ufba.br/SiacWWW/ConsultarComponentesCurricularesCursados.do", usuarioLogado);
				
				//Passar usuario para tela de inicial
				//passarUser();
				
				//Carregar lista de Aprovadas.
				obterMateriasAprovadas();
				
				// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
				// somente nas disciplinas que podem ser sugeridas
				
				
				
				//"/inicio?faces-redirect=false";
				return "/inicio.xhtml";
				
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

	private void obterMateriasAprovadas() {
		for (Disciplina disciplia : usuarioLogado.getMateriasCursadas()) {
			if(disciplia.getResultado().equals(ResultadoEnum.Aprovado) || disciplia.getResultado().equals(ResultadoEnum.DispensaUFBA) || disciplia.getResultado().equals(ResultadoEnum.Dispensado)){
				usuarioLogado.getMateriasAprovadas().add(disciplia);
			}
		}
	}
	
	
	private void removeAprovadasEmFluxograma() {
		//double notaAprovacao = 5;
		for (Disciplina materia : fluxogramaSi.getFluxogramaSI()) {
			for (Disciplina materiaAprovada  : usuarioLogado.getMateriasAprovadas()) {
				
				if( materia.getCodigo().equals(materiaAprovada.getCodigo())){
					
					fluxogramaSi.getFluxogramaSI().remove(materia);
					//usuarioLogado.getMateriasAprovadas().add(materia);
				}
			}
				
		}
		
		this.buscaGulosa(fluxogramaSi);
		
	}
	
	private void buscaGulosa(Fluxograma fluxograma) {
		
		Disciplina materiaMaiorPrioridade = fluxograma.getFluxogramaSI().get(1);
		
		if(fluxograma.getFluxogramaSI().isEmpty()){
			for (Disciplina materia : fluxograma.getFluxogramaSI()) {
				
				for (int i = 0; i < fluxograma.getFluxogramaSI().size(); i++) {	
					if(materia.getPeso() > materiaMaiorPrioridade.getPeso()){
						//atualiza a matria de maior prioridade
						materiaMaiorPrioridade = materia;
					}	
				}
				
			}
			
			// adiciona na lista de sugeridas e remove do fluxograma
			this.disciplinaSugeridaList.add(materiaMaiorPrioridade);
			fluxograma.getFluxogramaSI().remove(materiaMaiorPrioridade);
			
			buscaGulosa(fluxograma);
			
		}
	}
	

	/*public void CarregarDisciplinas(ArrayList<Disciplina> user, Fluxograma fluxo) {
		for (Disciplina fluxoGrama : fluxo.getFluxogramaSI()) {
			System.out.println(fluxoGrama.getCodigo());
			for (Disciplina disciplina : user) {
				if(fluxoGrama.getCodigo().equals(disciplina.getCodigo())){
					disciplina.setCargaHoraria(fluxoGrama.getCargaHoraria());
					disciplina.setNome(fluxoGrama.getNome());
					disciplina.setSemestre(fluxoGrama.getSemestre());
					disciplina.setNatureza(fluxoGrama.getNatureza());
				}
			}
		}
	}*/
	
	
	/*private void passarUser() {
		inicioController.getUsuarioLogado().setLogin(usuarioLogado.getLogin());
		inicioController.getUsuarioLogado().setMatricula(usuarioLogado.getMatricula());
		inicioController.getUsuarioLogado().setNome(usuarioLogado.getNome());
		inicioController.getUsuarioLogado().setSenha(usuarioLogado.getSenha());
	}*/

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Fluxograma getFluxogramaSi() {
		return fluxogramaSi;
	}

	public void setFluxogramaSi(Fluxograma fluxogramaSi) {
		this.fluxogramaSi = fluxogramaSi;
	}
}