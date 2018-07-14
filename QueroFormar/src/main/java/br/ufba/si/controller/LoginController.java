package br.ufba.si.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.ufba.si.business.AutenticarSiac;
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
    /*Remover Depois*/
    private String nome;
    private int value;
    private String matricula;
    
    private Fluxograma fluxogramaSi;
   
    private ArrayList<Disciplina> disciplinaSugeridaList = new ArrayList<Disciplina>();
    private ArrayList<Disciplina> sequenciaMaiorList = new ArrayList<Disciplina>();
	
	private static HashMap<Integer, Object> filter = new HashMap<Integer, Object>();
	private static HashMap<String, Object> objectChangePage = new HashMap<String, Object>();

	private Boolean rederize = new Boolean(false);
    
	
    //component do prami
    private int currentLevel = 1;  
    
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
        if(fluxogramaSi == null || fluxogramaSi.getFluxogramaSI().size() == 0)
        	fluxogramaSi = new Fluxograma();
        
    }

    public String logIn() {
       	usuarioLogado.setLogin(cpf);
    	usuarioLogado.setSenha(senha); 	
    	
    	
    	AutenticarSiac siac = new AutenticarSiac();
    	
//    	try {
//    				
//    		boolean autenticou = siac.login("https://siac.ufba.br/SiacWWW/LogonSubmit.do",cpf, senha);
//		
//			if (autenticou) {
//				 // Acessa página dos componetes curriculares
//				siac.openPage("https://siac.ufba.br/SiacWWW/ConsultarComponentesCurricularesCursados.do", usuarioLogado);
				
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
				
//	        } else {       
//	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF ou Senha Inválidos", "Login Inválido"));
//	            return null;
//	            
//	        }   	
//    	} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	return null;
    }


	private void obterMateriasAprovadas() {
		for (Disciplina disciplia : usuarioLogado.getMateriasCursadas()) {
			if(disciplia.getResultado().equals(ResultadoEnum.Aprovado) || disciplia.getResultado().equals(ResultadoEnum.DispensaUFBA) || disciplia.getResultado().equals(ResultadoEnum.Dispensado)){
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
				// Não é porque disciplina faltante tem mesma natureza de de uma matéria aprovada que devemos que devemos retirar ela de disciplina faltantes
				//				else if (disciplina.getCodigo() == null && disciplina.getNatureza().equals(materia.getNatureza())){
				//					fluxoGramaFaltantes.remove(disciplina);
				//				}
			}
		}
		fluxogramaFaltante.getFluxogramaSI().clear();
		fluxogramaFaltante.getFluxogramaSI().addAll(fluxoGramaFaltantes);
		//this.buscaGulosa(fluxogramaSi);
	}
    
    

	
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
			
	//		// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
	//		// somente nas disciplinas que podem ser sugeridas
	//		this.removeAprovadasEmFluxograma(fluxogramaSi);
	//		
	//		// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
	//		for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
	//			fluxogramaSi = this.buscaGulosa(fluxogramaSi);
		
		return "disciplinaSugeridaList.xhtml";
	}
	
	public String abrirSearchDisciplinasSugeridas() {
		
		return "searchDisciplinasSugeridas.xhtml?faces-redirect=true";
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


	


	/*
	 * Componente
	 */
//	private List<String> semestreList = new ArrayList<String>();
//	
//	public List<String> getSemestreList() {
//		// Inica componente
//		this.semestreList.add("1");
//		this.semestreList.add("2");
//		this.semestreList.add("3");
//		this.semestreList.add("4");
//		this.semestreList.add("5");
//		
//		return semestreList;
//	}
//
//	public void setSemestreList(List<String> semestreList) {
//		this.semestreList = semestreList;
//	}
	
	
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
		//		/*
		//		 * Teste da arvore
		//		 */
		//		Disciplina MATC89 = new Disciplina();
		//		MATC89.setCodigo("MATC89");
		//		
		//		Fluxograma fluxogramaTeste = new Fluxograma();
		//		
		//		for (Disciplina disciplina : fluxogramaTeste.getFluxogramaSI()) {
		//			if(disciplina.getCodigo() != null && disciplina.getCodigo().equals(MATC89.getCodigo())){
		//				
		//				semestre.setNome("1");
		//				semestre.getDisciplinaList().add(disciplina);
		//				this.semestreList.add(semestre);
		//				
		//				semestre = new Semestre();
		//				semestre.setNome("2");
		//				semestre.getDisciplinaList().add(disciplina);
		//				this.semestreList.add(semestre);
		//				
		//			}
		//	
		//		}
		
		// rescupera da sessao 
		//this.qtdDesejada = (String)LoginController.getFilter().get(usuarioLogado.getMatricula());


		
		return semestreList;
	}
	

	private List<Semestre> montarSemestres(ArrayList<Disciplina> disciplinaList, Integer numDisciplinaPorSemestre) {
		
		ArrayList<Disciplina> disciplinaFaltanteList = new ArrayList<Disciplina>();
		disciplinaFaltanteList.addAll(disciplinaList);
		disciplina = disciplinaList.get(0);
		Integer minSemetres = obterTamanhoArvore(disciplina);		
		ArrayList<Disciplina> sequenciaMaiorFaltanteList = new ArrayList<Disciplina>(); // controle do semestre
		sequenciaMaiorFaltanteList.addAll(sequenciaMaiorList);
		
		for (Disciplina disciplina : sequenciaMaiorList) {
			
			disciplinaFaltanteList.remove(disciplina);	// remover maior cadeia de faltante	
			System.out.println(disciplina.getNome());
		}
		
		
		for (int sem = 1; disciplinaFaltanteList.size() > 0 && sequenciaMaiorFaltanteList.size() > 0 ; sem++ ) {
						
			this.semestre.getDisciplinaList().add(this.sequenciaMaiorList.get(0));  // inserio a primeira da maior sequencia
			sequenciaMaiorFaltanteList.remove(this.sequenciaMaiorList.get(0)); //controle 
			
			this.semestre.setNome(""+sem);
			for (int i = 0;  i < numDisciplinaPorSemestre-1 && disciplinaFaltanteList.size() > 0  ; i++) {
				disciplina = disciplinaList.get(i);
				
				this.semestre.getDisciplinaList().add(disciplina);
				disciplinaFaltanteList.remove(disciplina);
				
			}
			this.semestreList.add(this.semestre);
			disciplinaList.clear();
			semestre = new Semestre();
			disciplinaList.addAll(disciplinaFaltanteList); //atualiza as discplinas faltantes do laço
		}
		
		return this.semestreList;
	}

	private Integer obterTamanhoArvore(Disciplina disciplina) {
		// Adiciona a raiz
		this.sequenciaMaiorList.add(disciplina);
		alturaArvore(disciplina);
		
		Integer result = this.sequenciaMaiorList.size(); // ILP abre a maior sequencia de 6 disciplinas encadeadas
		return result;		
	}
	
    private void alturaArvore(Disciplina disciplinaParan) {
        for (Disciplina disciplina : disciplinaParan.getPreRequisitosList()) {
            
            this.sequenciaMaiorList.add(disciplina);
        	alturaArvore(disciplina);
        }
    }
	
	

	public void setSemestreList(List<Semestre> semestreList) {
		this.semestreList = semestreList;
	}
	
	

	
	/*
	 * Tela de consulta
	 */
	public void localizar() throws Exception {
		
		// Se o alunos tem diciplinas aprovadas, entao será removido do fluxogramaSI para aplicar a busca gulosa 
		// somente nas disciplinas que podem ser sugeridas
		this.removeAprovadasEmFluxograma(fluxogramaSi);
		
		// Adiciona as disciplinas ordenado por prioridade na lista de disciplinas sugeridas.
		for(; fluxogramaSi != null && fluxogramaSi.getFluxogramaSI().size() > 0 ;)
			fluxogramaSi = this.buscaGulosa(fluxogramaSi);
		
		this.rederize = true;
		
		if (this.qtdDesejada.isEmpty() == false || this.qtdDesejada.length() > 0 ) {
			
			Integer numDisciplinaSemestre = Integer.valueOf(qtdDesejada);
			this.semestreList = this.montarSemestres(disciplinaSugeridaList, numDisciplinaSemestre );
		}else{
			Integer qtdmaxSmestre = 14;
			this.semestreList = this.montarSemestres(disciplinaSugeridaList, qtdmaxSmestre );
		}
		
	}
	
	
	//	public String localizar() throws Exception {
	//		
	//		//adiciona qtdDesejada na sessão
	//		//LoginController.getObjectChangePage().put(usuarioLogado.getMatricula(), this.qtdDesejada);
	//		
	//		
	//		return "disciplinaSugeridaList.xhtml?faces-redirect=true";
	//	}

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
	

}