package br.ufba.si.entidade;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;

@Entity
public class Usuario implements Serializable {

   	private static final long serialVersionUID = -7976817809144840921L;
	private String matricula;
    private String nome;
    private String login;
    private String senha;
    
    
    public Usuario() {
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
}
