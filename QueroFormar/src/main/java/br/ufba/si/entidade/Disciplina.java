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
	private String cargaHoraria;
	private String natureza;
	private ResultadoEnum resultado;
	private String semestre;
	private ArrayList<String> codPreRequisitosList;
	private ArrayList<String> codAbertasList;
	
	
	public Disciplina() {
	
	}

	public Disciplina(String codigo, String nome, String cargaHoraria,
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

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
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
	
	
}
