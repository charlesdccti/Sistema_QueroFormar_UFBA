package br.ufba.si.entidade;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author charles
 *
 */
public class Semestre {
	private String nome;
	private List<Disciplina> disciplinaList = new ArrayList<Disciplina>();

	
	
	public Semestre(){
		disciplinaList = new ArrayList<Disciplina>();
	}
	
	public Semestre(String nome) {
		super();
		this.nome = nome;
	}

	public List<Disciplina> getDisciplinaList() {
		return disciplinaList;
	}

	public void setDisciplinaList(List<Disciplina> disciplinaList) {
		this.disciplinaList = disciplinaList;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
