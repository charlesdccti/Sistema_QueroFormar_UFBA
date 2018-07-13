package br.ufba.si.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufba.si.business.AutenticarSiac;
import br.ufba.si.entidade.Disciplina;
import br.ufba.si.entidade.Fluxograma;
import br.ufba.si.entidade.Usuario;
import br.ufba.si.utils.ResultadoEnum;

@Named
@SessionScoped
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
    
    private Fluxograma fluxogramaOriginal = new Fluxograma();
   
    private ArrayList<Disciplina> disciplinaSugeridaList = new ArrayList<Disciplina>();
	
    //component do prami
    private int currentLevel = 1;  
    
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
        if(fluxogramaSi == null || fluxogramaSi.getFluxogramaSI().size() == 0)
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
				
				this.removeAprovadasEmFluxograma(fluxogramaSi);
				
				// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
				for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
					fluxogramaSi = this.buscaGulosa(fluxogramaSi);

				
				
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
				//.contains("Optativa")
				
				if((disciplia.getResultado().equals(ResultadoEnum.DispensaUFBA) || disciplia.getResultado().equals(ResultadoEnum.Dispensado)) && disciplia.getNatureza() == null && disciplia.getNome().contains("OPTATIVA")){
					disciplia.setNatureza("Optativa");
				}
				
				System.out.println(disciplia.getNome() + " " + disciplia.getNatureza());
				usuarioLogado.getMateriasAprovadas().add(disciplia);
			}
		}
	}
    
    
    
    private void removeAprovadasEmFluxograma(Fluxograma fluxogramaFaltante) {
		ArrayList<Disciplina> fluxoGramaFaltantes = new ArrayList<Disciplina>();
		fluxoGramaFaltantes.addAll(fluxogramaFaltante.getFluxogramaSI());
		for (Disciplina materia : usuarioLogado.getMateriasAprovadas()){
			System.out.println(materia.getCodigo() + " " + materia.getNatureza());
			for (Disciplina disciplina : fluxogramaFaltante.getFluxogramaSI()) {
				if(disciplina.getCodigo() != null && disciplina.getCodigo().equals(materia.getCodigo())){
					fluxoGramaFaltantes.remove(disciplina);				
					
				}else if (disciplina.getCodigo() == null && disciplina.getNatureza().trim().equalsIgnoreCase(materia.getNatureza().trim())){
					fluxoGramaFaltantes.remove(disciplina);
				}
			}
		}
		fluxogramaFaltante.getFluxogramaSI().clear();
		fluxogramaFaltante.getFluxogramaSI().addAll(fluxoGramaFaltantes);
		//this.buscaGulosa(fluxogramaSi);
	}
    
    
    
    
    
    /*private void obterMateriasAprovadas() {
    	int qtd = 0;
    	
    	for (int i = 0; qtd < 4 && i < fluxogramaSi.getFluxogramaSI().size(); i++) {
    		Disciplina disciplia = fluxogramaSi.getFluxogramaSI().get(i);

    		usuarioLogado.getMateriasAprovadas().add(disciplia);
    		qtd++;
    		
    	}
    }*/
	
	/*private void removeAprovadasEmFluxograma(Fluxograma fluxograma) {
		
		for (Disciplina materia : usuarioLogado.getMateriasAprovadas()){
			
			if( fluxogramaSi.getFluxogramaSI().contains(materia)){
	
				//materia.setAtivo(0);
				fluxogramaSi.getFluxogramaSI().remove(materia);
			}
		}
		
		//this.buscaGulosa(fluxogramaSi);
		
	}*/
    
    
//	
//	private void removeAprovadasEmFluxograma(Fluxograma fluxograma) {
//		//double notaAprovacao = 5;
//		//ArrayList<Disciplina> posiçoesre;
//		Integer qtd = 0;
//		
////		for (int i = 0; i < fluxograma.getFluxogramaSI().size(); i++) {			
////			Disciplina materia = fluxograma.getFluxogramaSI().get(i);
//		
//		for (Disciplina materia : fluxograma.getFluxogramaSI()){
//
////			if( materia != null ) {
////				for (Disciplina materiaAprovada :  usuarioLogado.getMateriasAprovadas()){
////					
////					if( materia.getAtivo() != 0 || materia.getCodigo().isEmpty() != true && materia.getCodigo() != null )
//						
//						
////
////						if(materiaAprovada.getCodigo() != null && materiaAprovada.getCodigo().isEmpty() != true)
//							
//							//System.out.println(materiaAprovada.getCodigo()+" == "+ materiaAprovada.getCodigo());
////							System.out.println(qtd);
////							qtd++;
////							if( materia.getCodigo().contains(materiaAprovada.getCodigo()) || materiaAprovada.getCodigo().contains(materia.getCodigo())){
////	
////								materia.setAtivo(0);
////								break;
////							}
////				}
////			}
////
//		}
//
//		this.buscaGulosa(fluxogramaSi);
//
//	}
	
	private Fluxograma buscaGulosa(Fluxograma fluxograma) {

		if(fluxograma.getFluxogramaSI().isEmpty() != true){
			
			Disciplina materiaMaiorPrioridade = new Disciplina();
			materiaMaiorPrioridade.setPeso(0); 

			for (Disciplina materia : fluxograma.getFluxogramaSI()) {
				System.out.println(materia.getNome());
				System.out.println(materia.getPeso());
				if(materia.getPeso()!= null && materia.getPeso() > materiaMaiorPrioridade.getPeso()){
					//atualiza a matria de maior prioridade
					materiaMaiorPrioridade = materia;
				}	

			}

			if(materiaMaiorPrioridade.getPeso() != 0){
				
				//  remove do fluxograma
				fluxograma.getFluxogramaSI().remove(materiaMaiorPrioridade);
				// adiciona na lista de sugeridas e
				this.disciplinaSugeridaList.add(materiaMaiorPrioridade);
			}else 
				fluxograma = null;
		}
		
		return fluxograma;
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
    
	public String listaDiscipliasFluxograma() {
		
		if(fluxogramaSi.getFluxogramaSI().size() < 45)
			fluxogramaSi = new Fluxograma();
		
		return "fluxogramaSI.xhtml";
	}
	
	
	public String listaDiscipliasSugeridas() {
			
		// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
		// somente nas disciplinas que podem ser sugeridas
		this.removeAprovadasEmFluxograma(fluxogramaSi);
		
		// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
		for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
			fluxogramaSi = this.buscaGulosa(fluxogramaSi);
		
		return "disciplinaSugeridaList.xhtml";
	}

	
	
	
	/*public String listaDiscipliasSugeridas() {
		
		if(fluxogramaSi.getFluxogramaSI().size() < 45)
			fluxogramaSi = new Fluxograma();
		
		// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
		// somente nas disciplinas que podem ser sugeridas
		this.removeAprovadasEmFluxograma(fluxogramaSi);
		
		// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
		for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
			fluxogramaSi = this.buscaGulosa(fluxogramaSi);
		
		return "disciplinaSugeridaList.xhtml";
	}*/

	/*public String listaDiscipliasSugeridas() {
		
		if(fluxogramaSi.getFluxogramaSI().size() < 45)
			fluxogramaSi = new Fluxograma();
		
		// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
		// somente nas disciplinas que podem ser sugeridas
		this.removeAprovadasEmFluxograma(fluxogramaSi);
		
		// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
		for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
			fluxogramaSi = this.buscaGulosa(fluxogramaSi);
		
		return "disciplinaSugeridaList.xhtml";
	}*/
	
	
	
	
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
	
	public ArrayList<Disciplina> getDisciplinaSugeridaList() {
		return disciplinaSugeridaList;
	}

	public void setDisciplinaSugeridaList(ArrayList<Disciplina> disciplinaSugeridaList) {
		this.disciplinaSugeridaList = disciplinaSugeridaList;
	}

	/*
	 * Component do prime
	 */
    public int getCurrentLevel() {  
        return currentLevel;  
    }  
  
    public void setCurrentLevel(int currentLevel) {  
        this.currentLevel = currentLevel;  
    }

	public Fluxograma getFluxogramaOriginal() {
		return fluxogramaOriginal;
	}

	public void setFluxogramaOriginal(Fluxograma fluxogramaOriginal) {
		this.fluxogramaOriginal = fluxogramaOriginal;
	}
}