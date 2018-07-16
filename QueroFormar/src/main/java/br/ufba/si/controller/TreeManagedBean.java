
package br.ufba.si.controller;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.ufba.si.entidade.Disciplina;
import br.ufba.si.entidade.Fluxograma;
import br.ufba.si.entidade.Semestre;

@ManagedBean
@SessionScoped
public class TreeManagedBean {
	// TreeNode instance
	private TreeNode root;
	private TreeNode raiz;
	private TreeNode singleSelectedTreeNode;
	private TreeNode [] multipleSelectedTreeNodes;
	private TreeNode [] checkboxSelectedTreeNodes;
	private Disciplina disciplina = new Disciplina();
	
	private Disciplina disciplinaSelecionada = new Disciplina();
	
	private ArrayList<Disciplina> disciplinaList = new ArrayList<Disciplina>();
	private Fluxograma fluxograma = new Fluxograma();

	public TreeManagedBean(){
		/*
		 * Teste da arvore
		 */
//		Disciplina MATC89 = new Disciplina();
//		MATC89.setCodigo("MATC89");
//		
//		Fluxograma fluxogramaTeste = new Fluxograma();
//		
//		for (Disciplina disciplina : fluxogramaTeste.getFluxogramaSI()) {
//			if(disciplina.getCodigo() != null && disciplina.getCodigo().equals(MATC89.getCodigo())){
//				
//				this.disciplina = disciplina;
//				
//			}
//	
//		}
		
		
//		// This is the root node, so it's data is root and its parent is null
//		this.root = new DefaultTreeNode(this.disciplina.getNome(), null);
//		// Create documents node
//		TreeNode documents = new DefaultTreeNode("Documents", this.root);
//		// Create document node
//		TreeNode document01 = new DefaultTreeNode("document","Expenses.doc", documents);
//		// Create images node
//		TreeNode images = new DefaultTreeNode("Images", this.root);
//		// Create image node
//		TreeNode image01 = new DefaultTreeNode("image","Travel.gif", images);
//		// Create videos node
//		TreeNode videos = new DefaultTreeNode("Videos", this.root);
//		// Create video node
//		TreeNode video01 = new DefaultTreeNode("video","Play.avi", videos);
		//disciplinaList = fluxograma.getFluxogramaSI();
	}
	
//  public void popularListas() {
//		
//		disciplinaList = fluxograma.popularListaRequesitos(disciplinaList);
//		disciplinaList = fluxograma.popularListaMateriasLiberadas(disciplinaList);
//  }
	
	public String abrirArvore() {
		//this.nomeOriginal = this.usuarioSelecionado.getNome();
		//RequestContext.getCurrentInstance().execute("PF('editPanel').show()");
		
		return "exemplo.xhtml?faces-redirect=true";
	}
	
    public void consultar() {
    	
        this.root = new DefaultTreeNode(this.disciplinaSelecionada.getNome(), null);
        this.root.setExpanded(true);
        
        adicionarNos(this.disciplinaSelecionada.getPreRequisitosList(), this.root);
    }
     
    private void adicionarNos(ArrayList<Disciplina> disciplinaList, TreeNode pai) {
        for (Disciplina disciplina : disciplinaList) {
            TreeNode no = new DefaultTreeNode(disciplina.getNome(), pai);
            no.setExpanded(true);
            
            adicionarNos(disciplina.getPreRequisitosList(), no);
        }
    }
    
    public TreeNode getRaiz() {
        return raiz;
    }
	

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSingleSelectedTreeNode() {
		return singleSelectedTreeNode;
	}

	public void setSingleSelectedTreeNode(TreeNode singleSelectedTreeNode) {
		this.singleSelectedTreeNode = singleSelectedTreeNode;
	}

	public TreeNode[] getMultipleSelectedTreeNodes() {
		return multipleSelectedTreeNodes;
	}

	public void setMultipleSelectedTreeNodes(TreeNode[] multipleSelectedTreeNodes) {
		this.multipleSelectedTreeNodes = multipleSelectedTreeNodes;
	}

	public TreeNode[] getCheckboxSelectedTreeNodes() {
		return checkboxSelectedTreeNodes;
	}

	public void setCheckboxSelectedTreeNodes(TreeNode[] checkboxSelectedTreeNodes) {
		this.checkboxSelectedTreeNodes = checkboxSelectedTreeNodes;
	}

	public void onNodeSelect(NodeSelectEvent event){
		System.out.println("Node Data ::"+event.getTreeNode().getData()+" :: Selected");
	}

	public void onNodeUnSelect(NodeUnselectEvent event){
		System.out.println("Node Data ::"+event.getTreeNode().getData()+" :: UnSelected");
	}

	public void onNodeExpand(NodeExpandEvent event){
		System.out.println("Node Data ::"+event.getTreeNode().getData()+" :: Expanded");
	}

	public void onNodeCollapse(NodeCollapseEvent event){
		System.out.println("Node Data ::"+event.getTreeNode().getData()+" :: Collapsed");
	}

	public String printSelectedNodes(){
		System.out.println("Single Selection Is :: "+this.singleSelectedTreeNode.getData());
		for(TreeNode n : this.multipleSelectedTreeNodes){
			System.out.println("Multiple Selection Are :: "+n.getData());
		}
		for(TreeNode n : this.checkboxSelectedTreeNodes){
			System.out.println("CheckBox Selection Are :: "+n.getData());
		}
		return "";
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}


	public Disciplina getDisciplinaSelecionada() {
		return disciplinaSelecionada;
	}


	public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
		this.disciplinaSelecionada = disciplinaSelecionada;
	}
}