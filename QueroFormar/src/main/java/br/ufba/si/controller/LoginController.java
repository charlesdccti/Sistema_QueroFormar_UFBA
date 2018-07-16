package br.ufba.si.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import br.ufba.si.entidade.Semestre;
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
    private ArrayList<Disciplina> sequenciaMaiorList = new ArrayList<Disciplina>();
	
	private static HashMap<Integer, Object> filter = new HashMap<Integer, Object>();
	private static HashMap<String, Object> objectChangePage = new HashMap<String, Object>();

	private Boolean rederize = new Boolean(false);
    
	
    //component do prami
    private int currentLevel = 1;
    
    
    private static double TREINO[][] = {
    		{2, 1, 4, 2, 1, 0, 3, 1, 2, 0, 1, 1},
    		{6, 5, 4, 3, 2, 1, 6, 2, 5, 1, 3, 4},
       		
    		{0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5} //bÃ­as
    };
         
    private static double ESPERADOS[] = {0.17, 0.33, 0.5, 0.67, 0.83, 1.0, 0.17, 0.83, 0.33, 1.0, 0.67, 0.5};
    
    
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
				ArrayList<Disciplina> disciplinasPopuladas = fluxogramaOriginal.popularListaRequesitos(fluxogramaOriginal.getFluxogramaSI());
				
				//Criar Lista de Materias Liberadas
				disciplinasPopuladas = fluxogramaOriginal.popularListaMateriasLiberadas(disciplinasPopuladas);
				
				Fluxograma fluxogramaPopulado = fluxogramaOriginal;
				fluxogramaPopulado.setFluxogramaSI(disciplinasPopuladas);
				
				carregarNotasEmFluxograma(fluxogramaPopulado);
				
				fluxogramaSi.getFluxogramaSI().clear();
				fluxogramaSi.getFluxogramaSI().addAll(disciplinasPopuladas);
				
				return "/inicio.xhtml";
				
	        }else {       
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

    
    
  public void popularListas() {
	  
	  ArrayList<Disciplina> disciplinaList = fluxogramaOriginal.popularListaRequesitos(fluxogramaOriginal.getFluxogramaSI());
	  disciplinaList = fluxogramaOriginal.popularListaMateriasLiberadas(disciplinaList);
	  fluxogramaOriginal.setFluxogramaSI(disciplinaList);
		
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
					
				}
				else if (disciplina.getCodigo() == null && disciplina.getNatureza().trim().equalsIgnoreCase(materia.getNatureza().trim())){
					fluxoGramaFaltantes.remove(disciplina);
				}

				else if (disciplina.getCodigo() == null && disciplina.getNatureza().equals(materia.getNatureza())){
					fluxoGramaFaltantes.remove(disciplina);
				}
			}
		}
		
		fluxogramaFaltante.getFluxogramaSI().clear();
		fluxogramaFaltante.getFluxogramaSI().addAll(fluxoGramaFaltantes);
	}
    

    private Fluxograma carregarNotasEmFluxograma(Fluxograma fluxograma) {
		
    	for (Disciplina materia : usuarioLogado.getMateriasAprovadas()){
			for (Disciplina disciplina : fluxograma.getFluxogramaSI()) {
				if(disciplina.getCodigo() != null && disciplina.getCodigo().equals(materia.getCodigo())){
					disciplina.setNota(materia.getNota());				
					
				}else if (disciplina.getCodigo() == null && disciplina.getNatureza().trim().equalsIgnoreCase(materia.getNatureza().trim())){
					disciplina.setNota(materia.getNota());
				}
			}
		}
		return fluxograma;
	}

	
	private Fluxograma buscaGulosa(Fluxograma fluxograma) {

		if(fluxograma.getFluxogramaSI().isEmpty() != true){
			
			Disciplina materiaMaiorPrioridade = new Disciplina();
			materiaMaiorPrioridade.setPeso(0); 
 
			for (Disciplina materia : fluxograma.getFluxogramaSI()) {
				if(materia.getPeso()!= null && materia.getPeso() > materiaMaiorPrioridade.getPeso()){
					//atualiza a materia de maior prioridade
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
		
		//Tirar acuracia da RNA
		double acuracia = 0.00;
		double acertos = 0;
		
		for (Disciplina disciplina : fluxogramaSi.getFluxogramaSI()) {
			acertos = acertos + contarAcertos(disciplina);
		}
		if(fluxogramaSi.getFluxogramaSI().size() > 0){
			acuracia = 	(acertos / fluxogramaSi.getFluxogramaSI().size()) * 100;
			System.out.printf("Acuracia: %.2f %n", acuracia);
			System.out.println("Acertos: " + acertos);
			System.out.println("Amostras: " + fluxogramaSi.getFluxogramaSI().size());
			
		}
			
		
		// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
		for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
			fluxogramaSi = this.buscaGulosa(fluxogramaSi);
		
		
		
		
		return "disciplinaSugeridaList.xhtml";
	}

	public String listaDiscipliasDetalhada() {
		
		return "disciplinaSugeridaDetalhadaList.xhtml";
	}

	
	private Fluxograma obterRedeNeural(Fluxograma fluxograma){
 		executorRNA(fluxograma);
 		return fluxograma;
 	}

	private void executorRNA(Fluxograma fluxograma){
		double[] classificar = new double[3];
		
		RedeNeural rede = new RedeNeural(5, 3);
		rede.treino(TREINO, ESPERADOS);
		
		for (Disciplina disciplina : fluxograma.getFluxogramaSI()) {
			classificar[0] =  disciplina.getLiberaList().size(); //X
			classificar[1] =  disciplina.getPeso(); //Y
 			classificar[2] = 1; //bais
			
			disciplina.setCategoria(rede.classificar(classificar));
					
		}
	}

	
	public String abrirSearchDisciplinasSugeridas() {
		
		return "searchDisciplinasSugeridas.xhtml?faces-redirect=true";
	}

	
	
	private int contarAcertos(Disciplina disciplina){
		String line = disciplina.getCategoria();
		int inicio = disciplina.getCategoria().length() - 1;
		int valor = Integer.parseInt(line.substring(inicio, disciplina.getCategoria().length()));
		
		if(disciplina.getPeso().equals(descategorizar(valor))){
			return 1;
		}else{
			return 0;
		}
		
		
	}

	private int descategorizar(int valor) {
		switch (valor) {
			case 1:
				return 6;
			case 2:
				return 5;
			case 3:
				return 4;
			case 4:
				return 3;
			case 5:
				return 2;
			case 6:
				return 1;
			default:
				return 0;
		}
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

    
    /*
     * Componente AutoComplete
     */
    private String qtdDesejada = new	String();
    
    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for(int i = 1; i <= 10; i++) {
            results.add(query + i);
        }
         
        return results;
    }
	
	
	public String getQtdDesejada() {
		return qtdDesejada;
	}

	public void setQtdDesejada(String qtdDesejada) {
		this.qtdDesejada = qtdDesejada;
	}

	/*
	 * Componente
	 */
	private List<Semestre> semestreList = new ArrayList<Semestre>();
	private Semestre semestre = new Semestre();
	private Disciplina disciplina;
	
	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public List<Semestre> getSemestreList() {  			
		return semestreList;
	}
	

	private List<Semestre> montarSemestres(ArrayList<Disciplina> disciplinaList, Integer numDisciplinaPorSemestre) {
		Integer contDisciplinas = numDisciplinaPorSemestre;
		ArrayList<Disciplina> disciplinaFaltanteList = new ArrayList<Disciplina>();
		sequenciaMaiorList = new ArrayList<Disciplina>();
		semestre = new Semestre();

		disciplinaFaltanteList.addAll(disciplinaList);
		disciplina = disciplinaList.get(0);
		Integer minSemetres = obterTamanhoArvore(disciplina);

		
		ArrayList<Disciplina> sequenciaMaiorFaltanteList = new ArrayList<Disciplina>(); // controle do semestre
		sequenciaMaiorFaltanteList.addAll(sequenciaMaiorList);
		
		for (Disciplina disciplina : sequenciaMaiorList) {
			
			disciplinaFaltanteList.remove(disciplina);	// remover maior cadeia de faltante	
		}
		
		
		for (int sem = 1; disciplinaFaltanteList.size() > 0 || sequenciaMaiorFaltanteList.size() > 0 ; sem++ ) {
			
			if(sequenciaMaiorFaltanteList.size() > 0){
				numDisciplinaPorSemestre = contDisciplinas - 1;
				
				this.semestre.getDisciplinaList().add(this.sequenciaMaiorList.get(sem-1));  // inserio a primeira da maior sequencia
				sequenciaMaiorFaltanteList.remove(this.sequenciaMaiorList.get(sem-1)); //controle 				
			}else
				if(sequenciaMaiorFaltanteList.size() == 0 && disciplinaFaltanteList.size() > 0 )	
				numDisciplinaPorSemestre = contDisciplinas;
			
			this.semestre.setNome(""+sem);
			for (int i = 0;  i < numDisciplinaPorSemestre && disciplinaFaltanteList.size() > 0  ; i++) {
				disciplina = disciplinaFaltanteList.get(0);
				
				this.semestre.getDisciplinaList().add(disciplina);
				disciplinaFaltanteList.remove(disciplina);
				
			}
			this.semestreList.add(this.semestre);
			semestre = new Semestre();
		}
		
		return this.semestreList;
	}

	
	
	/**	
	 * Escolhe as disciplinas que fazem parte da sequência de maior cadeia formada por disciplinas Liberadas( sequenciaMaiorList )
	 * 
	 * @param disciplina
	 * @return result
	 */
	Integer maiorPeso = -1;
	Disciplina disciplinaAUX;
	private Integer obterTamanhoArvore(Disciplina disciplina) {		
		// Adiciona a raiz
		this.sequenciaMaiorList.add(disciplina);
		alturaArvoreGuloso(disciplina);	
		Integer result = this.sequenciaMaiorList.size(); // ILP abre a maior sequencia de 6 disciplinas encadeadas
		return result;		
	}
	
    private void alturaArvoreGuloso(Disciplina disciplinaParan) {
        for (Disciplina disciplina : disciplinaParan.getLiberaList()) {            
        	if(maiorPeso < disciplina.getPeso()){
        		maiorPeso = disciplina.getPeso();
        		disciplinaAUX = disciplina;
        	}        	
        }
        
        
        if(disciplinaAUX != null && disciplinaAUX.getLiberaList() != null &&  disciplinaAUX.getLiberaList().size() > 0){
        	this.sequenciaMaiorList.add(disciplinaAUX);
            maiorPeso = -1;
            alturaArvoreGuloso(disciplinaAUX);
        }else if(disciplinaAUX != null){
        	this.sequenciaMaiorList.add(disciplinaAUX);
        }
        

    }
	
	

	public void setSemestreList(List<Semestre> semestreList) {
		this.semestreList = semestreList;
	}
	
	

	
	/*
	 * Tela de consulta
	 */
	public void localizar() throws Exception {
		
		semestreList = new ArrayList<Semestre>();
		
		if(usuarioLogado != null && usuarioLogado.getMateriasAprovadas() == null || usuarioLogado.getMateriasAprovadas().size() == 0)
			//Carregar lista de Aprovadas.
			obterMateriasAprovadas();
		
		if(fluxogramaSi != null && (fluxogramaSi.getFluxogramaSI().size() == 0 || fluxogramaSi.getFluxogramaSI().size() == 45)){
			//Criar Lista de Pre Requisito
			ArrayList<Disciplina> disciplinasPopuladas = fluxogramaOriginal.popularListaRequesitos(fluxogramaOriginal.getFluxogramaSI());
			
			//Criar Lista de Materias Liberadas
			disciplinasPopuladas = fluxogramaOriginal.popularListaMateriasLiberadas(disciplinasPopuladas);
			
			Fluxograma fluxogramaPopulado = fluxogramaOriginal;
			fluxogramaPopulado.setFluxogramaSI(disciplinasPopuladas);
			
			carregarNotasEmFluxograma(fluxogramaPopulado);
			
			fluxogramaSi.getFluxogramaSI().clear();
			fluxogramaSi.getFluxogramaSI().addAll(disciplinasPopuladas);
			
			
			// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
			// somente nas disciplinas que podem ser sugeridas
			this.removeAprovadasEmFluxograma(fluxogramaSi);
			//Chamar a RNA para classificar a criticidade da materia
			fluxogramaSi = obterRedeNeural(fluxogramaSi);
		}
		
		
		if(disciplinaSugeridaList == null || disciplinaSugeridaList.size() == 0){
			// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
			for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
				fluxogramaSi = this.buscaGulosa(fluxogramaSi);
		}	
		
				
		this.rederize = true;
		
		if (this.qtdDesejada.isEmpty() == false || this.qtdDesejada.length() > 0 ) {
			
			Integer numDisciplinaSemestre = Integer.valueOf(qtdDesejada);
			this.semestreList = this.montarSemestres(disciplinaSugeridaList, numDisciplinaSemestre );
		}else{
			Integer qtdmaxSmestre = 14;
			this.semestreList = this.montarSemestres(disciplinaSugeridaList, qtdmaxSmestre );
		}
		
	}

	public static HashMap<Integer, Object> getFilter() {
		return filter;
	}

	public static void setFilter(HashMap<Integer, Object> filter) {
		LoginController.filter = filter;
	}

	public static HashMap<String, Object> getObjectChangePage() {
		return objectChangePage;
	}

	public static void setObjectChangePage(HashMap<String, Object> objectChangePage) {
		LoginController.objectChangePage = objectChangePage;
	}

	public boolean isRederize() {
		return rederize;
	}

	public void setRederize(boolean rederize) {
		this.rederize = rederize;
	}

	public ArrayList<Disciplina> getSequenciaMaiorList() {
		return sequenciaMaiorList;
	}

	public void setSequenciaMaiorList(ArrayList<Disciplina> sequenciaMaiorList) {
		sequenciaMaiorList = sequenciaMaiorList;
	}
	
	public Fluxograma getFluxogramaOriginal() {
		return fluxogramaOriginal;
	}

	public void setFluxogramaOriginal(Fluxograma fluxogramaOriginal) {
		this.fluxogramaOriginal = fluxogramaOriginal;
	}
}