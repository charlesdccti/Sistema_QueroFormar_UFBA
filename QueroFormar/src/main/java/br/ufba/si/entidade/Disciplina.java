package br.ufba.si.entidade;

import java.util.ArrayList;

import br.ufba.si.utils.ResultadoEnum;

/**
 * @author charles
 *
 */
public class Disciplina {

	private Integer id; // caso for pesista em um banco, essa coluna será o auto_increment 
	private String codigo;
	private String nome;
	private int cargaHoraria;
	private String natureza;
	private double nota; 
	private ResultadoEnum resultado;
	private String semestre;
	private ArrayList<String> codPreRequisitosList;
	private ArrayList<String> codAbertasList;
	private ArrayList<Disciplina> preRequisitosList = new ArrayList<Disciplina>();
    private ArrayList<Disciplina> liberaList = new ArrayList<Disciplina>();
    // Nossa heuristica é o "peso" = tamanho da cadeia mais longa
    private Integer peso;
    private Integer ativo = 1; // se 1 entao disciplina nao foi removida
    
	
	public Disciplina() {
	
	}

	public Disciplina(String codigo, String nome, int cargaHoraria,
			String natureza, ResultadoEnum resultado, String semestre,
			ArrayList<String> codPreRequisitosList,
			ArrayList<String> codAbertasList) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.cargaHoraria = cargaHoraria;
		this.natureza = natureza;
		this.resultado = resultado;
		this.semestre = semestre;
		this.codPreRequisitosList = codPreRequisitosList;
		this.codAbertasList = codAbertasList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public ResultadoEnum getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoEnum resultado) {
		this.resultado = resultado;
	}

	public ArrayList<String> getCodPreRequisitosList() {
		return codPreRequisitosList;
	}

	public void setCodPreRequisitosList(ArrayList<String> codPreRequisitosList) {
		this.codPreRequisitosList = codPreRequisitosList;
	}

	public ArrayList<String> getCodAbertasList() {
		return codAbertasList;
	}

	public void setCodAbertasList(ArrayList<String> codAbertasList) {
		this.codAbertasList = codAbertasList;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}
	

	public ArrayList<Disciplina> getPreRequisitosList() {
		return preRequisitosList;
	}

	public void setPreRequisitosList(ArrayList<Disciplina> preRequisitosList) {
		this.preRequisitosList = preRequisitosList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + cargaHoraria;
		result = prime * result + ((codAbertasList == null) ? 0 : codAbertasList.hashCode());
		result = prime * result + ((codPreRequisitosList == null) ? 0 : codPreRequisitosList.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((liberaList == null) ? 0 : liberaList.hashCode());
		result = prime * result + ((natureza == null) ? 0 : natureza.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		long temp;
		temp = Double.doubleToLongBits(nota);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
		result = prime * result + ((resultado == null) ? 0 : resultado.hashCode());
		result = prime * result + ((preRequisitosList == null) ? 0 : preRequisitosList.hashCode());
		result = prime * result + ((semestre == null) ? 0 : semestre.hashCode());
		return result;
	}

	
	
	@Override
	public String toString() {
		return "Disciplina [codigo=" + codigo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disciplina other = (Disciplina) obj;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (cargaHoraria != other.cargaHoraria)
			return false;
		if (codAbertasList == null) {
			if (other.codAbertasList != null)
				return false;
		} else if (!codAbertasList.equals(other.codAbertasList))
			return false;
		if (codPreRequisitosList == null) {
			if (other.codPreRequisitosList != null)
				return false;
		} else if (!codPreRequisitosList.equals(other.codPreRequisitosList))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (liberaList == null) {
			if (other.liberaList != null)
				return false;
		} else if (!liberaList.equals(other.liberaList))
			return false;
		if (natureza == null) {
			if (other.natureza != null)
				return false;
		} else if (!natureza.equals(other.natureza))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (Double.doubleToLongBits(nota) != Double.doubleToLongBits(other.nota))
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		if (resultado != other.resultado)
			return false;
		if (preRequisitosList == null) {
			if (other.preRequisitosList != null)
				return false;
		} else if (!preRequisitosList.equals(other.preRequisitosList))
			return false;
		if (semestre == null) {
			if (other.semestre != null)
				return false;
		} else if (!semestre.equals(other.semestre))
			return false;
		return true;
	}
	
	
	
	
}
