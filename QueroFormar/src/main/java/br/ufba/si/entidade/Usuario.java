package br.ufba.si.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.Entity;

@Entity
public class Usuario implements Serializable {

   	private static final long serialVersionUID = -7976817809144840921L;
   	
	private String matricula;
    private String nome;
    private String login;
    private String senha;
    private String ultimoSemestreCursado;
    private ArrayList<Disciplina> MateriasCursadas = new ArrayList<Disciplina>();
    private ArrayList<Disciplina> MateriasAprovadas = new ArrayList<Disciplina>();

    
    
    public Usuario() {
    	this.matricula = "";
    	this.nome = "";
    	this.login = "";
    	this.senha = "";
    	this.MateriasCursadas = new ArrayList<Disciplina>();
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }    

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

	public ArrayList<Disciplina> getMateriasCursadas() {
		return MateriasCursadas;
	}

	public void setMateriasCursadas(ArrayList<Disciplina> materiasCursadas) {
		MateriasCursadas = materiasCursadas;
	}

	public ArrayList<Disciplina> getMateriasAprovadas() {
		return MateriasAprovadas;
	}

	public void setMateriasAprovadas(ArrayList<Disciplina> materiasAprovadas) {
		MateriasAprovadas = materiasAprovadas;
	}

	public String getUltimoSemestreCursado() {
		return ultimoSemestreCursado;
	}

	public void setUltimoSemestreCursado(String ultimoSemestreCursado) {
		this.ultimoSemestreCursado = ultimoSemestreCursado;
	}
}
