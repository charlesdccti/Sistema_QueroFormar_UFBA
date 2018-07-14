package br.ufba.si.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufba.si.business.AutenticarSiac;
import br.ufba.si.business.RedeNeural;
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
    
    private Fluxograma fluxogramaSi;
    
    private Fluxograma fluxogramaOriginal = new Fluxograma();
   
    private ArrayList<Disciplina> disciplinaSugeridaList = new ArrayList<Disciplina>();
	
    //component do prami
    private int currentLevel = 1;
    
    
    private static double TREINO[][] = {
			{1, 2, 3, 3, 4, 4, 5, 5, 6, 6},
			{0, 1, 2, 1, 4, 1, 1, 2, 2, 3},
			{0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5} //bías
	};
    
    private static double ESPERADOS[] = {1.0, 0.833333333, 0.666666667,  0.666666667, 0.5, 0.5, 0.333333333, 0.333333333, 0.166666667, 0.166666667};
    
    
    
    public LoginController() {
    	
    }

    @PostConstruct
    public void init() {
        this.cpf = "";
        this.senha = "";
        usuarioLogado = new Usuario(cpf, senha);
        if(fluxogramaSi == null || fluxogramaSi.getFluxogramaSI().size() == 0)
        	fluxogramaSi = new Fluxograma();
        
    }

    public String logIn() {
       	usuarioLogado.setLogin(cpf);
    	usuarioLogado.setSenha(senha); 	
    	boolean autenticou = true;
    	
    	AutenticarSiac siac = new AutenticarSiac();
    	
    	try {
    		if(!"Visao".equalsIgnoreCase(cpf)){
    			autenticou = siac.login("https://siac.ufba.br/SiacWWW/LogonSubmit.do",cpf, senha);
    		}
    		
		
			if (autenticou) {
				if(!"Visao".equalsIgnoreCase(cpf)){
					// Acessa página dos componetes curriculares
					siac.openPage("https://siac.ufba.br/SiacWWW/ConsultarComponentesCurricularesCursados.do", usuarioLogado);
				}
				//Carregar lista de Aprovadas.
				obterMateriasAprovadas();
				
				//Criar Lista de Pre Requisito
				fluxogramaOriginal.popularListaRequesitos(fluxogramaOriginal.getFluxogramaSI());
				
				//Criar Lista de Materias Liberadas
				fluxogramaOriginal.popularListaMateriasLiberadas(fluxogramaOriginal.getFluxogramaSI());
				
				carregarNotasEmFluxograma(fluxogramaOriginal);
				
				fluxogramaSi.getFluxogramaSI().clear();
				fluxogramaSi.getFluxogramaSI().addAll(fluxogramaOriginal.getFluxogramaSI());
				
				// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
				// somente nas disciplinas que podem ser sugeridas
				
				/*this.removeAprovadasEmFluxograma(fluxogramaSi);
				
				// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
				for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
					fluxogramaSi = this.buscaGulosa(fluxogramaSi);

				*/
				
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
				
				if((disciplia.getResultado().equals(ResultadoEnum.DispensaUFBA) || disciplia.getResultado().equals(ResultadoEnum.Dispensado)) && disciplia.getNatureza() == null && disciplia.getNome().contains("OPTATIVA")){
					disciplia.setNatureza("Optativa");
				}
				
				usuarioLogado.getMateriasAprovadas().add(disciplia);
			}
		}
	}
    
    private void removeAprovadasEmFluxograma(Fluxograma fluxogramaFaltante) {
		ArrayList<Disciplina> fluxoGramaFaltantes = new ArrayList<Disciplina>();
		fluxoGramaFaltantes.addAll(fluxogramaFaltante.getFluxogramaSI());
		for (Disciplina materia : usuarioLogado.getMateriasAprovadas()){
			
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
	}
    
    private void carregarNotasEmFluxograma(Fluxograma fluxograma) {
		for (Disciplina materia : usuarioLogado.getMateriasAprovadas()){
			for (Disciplina disciplina : fluxograma.getFluxogramaSI()) {
				if(disciplina.getCodigo() != null && disciplina.getCodigo().equals(materia.getCodigo())){
					disciplina.setNota(materia.getNota());				
					
				}else if (disciplina.getCodigo() == null && disciplina.getNatureza().trim().equalsIgnoreCase(materia.getNatureza().trim())){
					disciplina.setNota(materia.getNota());
				}
			}
		}
	}
	
	private Fluxograma buscaGulosa(Fluxograma fluxograma) {

		if(fluxograma.getFluxogramaSI().isEmpty() != true){
			
			Disciplina materiaMaiorPrioridade = new Disciplina();
			materiaMaiorPrioridade.setPeso(0); 

			for (Disciplina materia : fluxograma.getFluxogramaSI()) {
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

    public String logOff() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        return "/login?faces-redirect=true";
    }
    
	public String listaDiscipliasFluxograma() {
		
		if(fluxogramaOriginal.getFluxogramaSI().size() < 45)
			fluxogramaOriginal = new Fluxograma();
		
		return "fluxogramaSI.xhtml";
	}
	
	
	public String listaDiscipliasSugeridas() {
			
		// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
		// somente nas disciplinas que podem ser sugeridas
		this.removeAprovadasEmFluxograma(fluxogramaSi);
		
		
		//Chamar a RNA para classificar a criticidade da materia
		fluxogramaSi = obterRedeNeural(fluxogramaSi);
		
		// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
		for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
			fluxogramaSi = this.buscaGulosa(fluxogramaSi);
		
		
		
		return "disciplinaSugeridaList.xhtml";
	}


	
	private Fluxograma obterRedeNeural(Fluxograma fluxograma){
		obterVariaveisRNA(fluxograma);
		executorRNA(fluxograma);
		return fluxograma;
	}

	private void executorRNA(Fluxograma fluxograma){
		double[] classificar = new double[3];
		
		RedeNeural rede = new RedeNeural(10, 3);
		rede.treinar(TREINO, ESPERADOS);
		
		for (Disciplina disciplina : fluxograma.getFluxogramaSI()) {
			classificar[0] = disciplina.getMediaPreRequisitos();
			classificar[1] = disciplina.getLiberaList().size();
			classificar[2] = 1; //bais
			
			disciplina.setCategoria(rede.classificar(classificar));
					
		}
	}
	
	private void obterVariaveisRNA(Fluxograma fluxograma) {
		ArrayList<Disciplina> lista = new ArrayList<Disciplina>();
		ArrayList<Disciplina> listaAux = new ArrayList<Disciplina>();
		for (Disciplina disciplina : fluxograma.getFluxogramaSI()) {
			lista = new ArrayList<Disciplina>();
			listaAux = new ArrayList<Disciplina>();
			if(disciplina.getPreRequisitosList() != null && disciplina.getPreRequisitosList().size() > 0){
				obterMaterias(disciplina, listaAux);	
			}
			listaAux.remove(disciplina);
			for (Disciplina mat : listaAux) {
				if(!lista.contains(mat))
					lista.add(mat);
			}
			double mediaPreRequisitos = (obterNotas(lista)/lista.size());
			disciplina.setMediaPreRequisitos(Math.round(mediaPreRequisitos));

		}
	}
	
	private double obterNotas(ArrayList<Disciplina> lista){
		double nota = 0.0;
		for (Disciplina disciplina : lista) {
			nota =  disciplina.getNota() + nota;
		}
		return nota;
	}
	
	//Ontem quantidade de materias cursada ate a atua
	private void obterMaterias(Disciplina disciplina, ArrayList<Disciplina> lista){
		if(disciplina.getPreRequisitosList() != null && disciplina.getPreRequisitosList().size() > 0){
			for (Disciplina materia : disciplina.getPreRequisitosList()) {
				obterMaterias(materia, lista);
			}
		}
		lista.add(disciplina);
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

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
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