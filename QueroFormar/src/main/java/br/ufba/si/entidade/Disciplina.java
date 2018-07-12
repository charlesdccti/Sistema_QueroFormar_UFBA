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
	private ArrayList<Disciplina> rreRequisitosList = new ArrayList<Disciplina>();
    private ArrayList<Disciplina> liberaList = new ArrayList<Disciplina>();
    // Nossa heuristica é o "peso" = tamanho da cadeia mais longa
    private Integer peso;
    
    
	
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
	
	
}
