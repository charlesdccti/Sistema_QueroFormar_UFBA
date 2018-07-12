package br.ufba.si.entidade;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;

import br.ufba.si.utils.ResultadoEnum;

@Entity
public class Fluxograma implements Serializable {

	private static final long serialVersionUID = -15808455122807168L;
	
	private ArrayList<Disciplina> fluxogramaSI;
	
	public Fluxograma() {
		fluxogramaSI = new ArrayList<Disciplina>();

		/*1º SEMESTRE*/
		//(codigo, nome, cargaHoraria, natureza, resultado, semestre, codPreRequisitosList, codAbertasList)		
		Disciplina MATA68 = new Disciplina( "MATA68", "COMPUTADOR, ÉTICA E SOCIEDADE", 51, "Obrigatória", null, "1", null, null);
		
		fluxogramaSI.add(MATA68);
		
		Disciplina MATA37 = new Disciplina( "MATA37", "INTRODUÇÃO À LÓGICA DE PROGRAMAÇÃO", 68, "Obrigatória", null, "1", null, new ArrayList<String>(){{add("MATA76");
					add("MATD04");}});
		
		fluxogramaSI.add(MATA37);
		
		Disciplina MATA42 = new Disciplina( "MATA42", "MATEMÁTICA DISCRETA I", 68, "Obrigatória", null, "1", null, new ArrayList<String>(){{add("MATC73");
					add("MATD04");
					add("MATC94");}});
	
		fluxogramaSI.add(MATA42);
		
		Disciplina MATA02 = new Disciplina( "MATA02", "CÁLCULO A", 102, "Obrigatória", null, "1", null, new ArrayList<String>(){{add("MAT236");}});
		
		fluxogramaSI.add(MATA02);
		
		Disciplina MATA39 = new Disciplina( "MATA39", "SEMINÁRIOS DE INTRODUÇÃO AO CURSO", 51, "Obrigatória", null, "1", null, null);
		
		fluxogramaSI.add(MATA39);
		
		// ===== 2 semestre ==== //
		
		Disciplina MATC90 = new Disciplina( "MATC90", "CIRCUITOS DIGITAIS E ARQUITETURA DE COMPUTADORES", 68, "Obrigatória", null, "2", null, new ArrayList<String>(){{add("MATA58");
					add("MATA59");}});

		fluxogramaSI.add(MATC90);
		
		Disciplina MATD04 = new Disciplina( "MATD04", "ESTRUTURAS DE DADOS", 68, "Obrigatória", null, "2", new ArrayList<String>(){{add("MATA37"); 
						add("MATA42");}}, new ArrayList<String>(){{	add("MATA60"); 
						add("MATA55"); 
						add("MATA56"); 
						add("MATA64"); }});
		
		fluxogramaSI.add(MATD04);
		
		Disciplina MATC73 = new Disciplina( "MATC73", "INTRODUÇÃO À LÓGICA MATEMÁTICA", 68, "Obrigatória", null, "2", new ArrayList<String>(){{add("MATA42");}}, new ArrayList<String>(){{add("MATA64"); }}  );
		
		fluxogramaSI.add(MATC73);
		
		Disciplina MATC92 = new Disciplina( "MATC92", "FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO", 68, "Obrigatória", null, "2", null, new ArrayList<String>(){{add("MATC82"); }}  );
		
		fluxogramaSI.add(MATC92);
		
		Disciplina ADME99 = new Disciplina( "ADME99", "ECONOMIA DA INOVAÇÃO", 68, "Obrigatória", null, "2", null, null);
		
		fluxogramaSI.add(ADME99);
		
		// ===== 3º semestre ==== //
		
		Disciplina MATA58 = new Disciplina( "MATA58", "SISTEMAS OPERACIONAIS", 68, "Obrigatória", null, "3", new ArrayList<String>(){{add("MATC90"); }}, new ArrayList<String>(){{add("MATC82");}}  );
		
		fluxogramaSI.add(MATA58);
		
		Disciplina MATA55 = new Disciplina( "MATA55", "PROGRAMAÇÃO ORIENTADA A OBJETOS", 68, "Obrigatória", null, "3", new ArrayList<String>(){
					{add("MATD04"); }}, new ArrayList<String>(){{add("MATA62"); 
						add("MATA56"); 
						add("MATC84"); 
						add("MATC89"); 
						add("MATB19"); }});
		
		fluxogramaSI.add(MATA55);
		
		Disciplina MATC94 = new Disciplina( "MATC94", "INTRODUÇÃO AS LINGUAGENS FORMAIS E TEORIA DA COMPUTAÇÃO", 68, "Obrigatória", null, "3", new ArrayList<String>(){{add("MATA42"); }}, null);

		fluxogramaSI.add(MATC94);
		
		Disciplina MATA07 = new Disciplina( "MATA07", "ÁLGEBRA LINEAR A", 68, "Obrigatória", null, "3", null, new ArrayList<String>(){{add("MAT236");
		add("MATC99");}}  );
		
		fluxogramaSI.add(MATA07);
		
		Disciplina ADM001 = new Disciplina( "ADM001", "INTRODUCAO À ADMINISTRACAO", 68, "Obrigatória", null, "3", null, new ArrayList<String>(){{add("ADM211");}}  );
		
		fluxogramaSI.add(ADM001);
		
		// ===== 4º semestre ==== //
		
		Disciplina LETA09 = new Disciplina( "LETA09", "OFICINA DE LEITURA E PRODUÇÃO DE TEXTOS", 68, "Obrigatória", null,"4", null, new ArrayList<String>(){{add("MATC97"); }});
		
		fluxogramaSI.add(LETA09);
		
		Disciplina MATA59 = new Disciplina( "MATA59", "REDES DE COMPUTADORES I", 68, "Obrigatória",  null, "4", new ArrayList<String>(){{add("MATC90");}} , new ArrayList<String>(){{add("MATC89");}});
		
		fluxogramaSI.add(MATA59);
		
		Disciplina MATA62 = new Disciplina( "MATA62", "ENGENHARIA DE SOFTWARE I", 68, "Obrigatória", null, "4", new ArrayList<String>(){{add("MATA55");}}, new ArrayList<String>(){{add("MATA63");
				add("MATC72");}});
		
		fluxogramaSI.add(MATA62);
		
		Disciplina MAT236 = new Disciplina( "MAT236", "MÉTODOS ESTATÍSTICOS", 68, "Obrigatória", null, "4", new ArrayList<String>(){{add("MATA07");
		add("MATA02");}}, new ArrayList<String>(){{add("ADM211");}});
		
		fluxogramaSI.add(MAT236);
		
		Disciplina MATC82 = new Disciplina( "MATC82", "SISTEMAS WEB", 68, "Obrigatória", null, "4", new ArrayList<String>(){{ add("MATA58");
					add("MATC92");}}, null);
		
		fluxogramaSI.add(MATC82);
		
		// ===== 5º semestre ==== //
		
		Disciplina MATA60 = new Disciplina( "MATA60", "BANCO DE DADOS", 68, "Obrigatória", null, "5", new ArrayList<String>(){{ add("MATD04");}}, new ArrayList<String>(){{ add("MATB09");}});
		
		fluxogramaSI.add(MATA60);
		
		Disciplina MATA56 = new Disciplina( "MATA56", "PARADIGMAS DE LINGUAGENS DE PROGRAMAÇÃO", 68, "Obrigatória", null, "5", new ArrayList<String>(){{add("MATA55");
				add("MATD04");}}, null);
		
		fluxogramaSI.add(MATA56);
		
		Disciplina MATA63 = new Disciplina( "MATA63", "ENGENHARIA DE SOFTWARE II", 68, "Obrigatória", null, "5", new ArrayList<String>(){{add("MATA62");}}, new ArrayList<String>(){{add("MATB02"); }});
		
		fluxogramaSI.add(MATA63);
		
		Disciplina ADM211 = new Disciplina( "ADM211", "MÉTODOS QUANTITATIVOS APLICADOS À ADMINISTRAÇÃO", 68, "Obrigatória", null, "5", new ArrayList<String>(){{add("ADM001");
					add("MAT236"); }},new ArrayList<String>(){{add("ADMF01"); }});
		
		fluxogramaSI.add(ADM211);
		
		Disciplina MATC84 = new Disciplina( "MATC84", "LABORATÓRIO DE PROGRAMAÇÃO WEB", 51, "Obrigatória", null, "5", new ArrayList<String>(){{add("MATA55");}}, null);
		
		fluxogramaSI.add(MATC84);
		
		// ===== 6º semestre ==== //
		
		Disciplina MATB09 = new Disciplina( "MATB09", "LABORATÓRIO DE BANCO DE DADOS", 51, "Obrigatória", null, "6", new ArrayList<String>(){{add("MATA60");}}, null);
		
		fluxogramaSI.add(MATB09);
		
		Disciplina MATA76 = new Disciplina( "MATA76", "LINGUAGENS PARA APLICAÇÃO COMERCIAL", 68, "Obrigatória", null, "6", new ArrayList<String>(){{add("MATA37");}}, null);
		
		fluxogramaSI.add(MATA76);
		
		Disciplina MATC89 = new Disciplina( "MATC89", "APLICAÇÕES PARA DISPOSITIVOS MÓVEIS", 68, "Obrigatória", null, "6", new ArrayList<String>(){{add("MATA55");
			add("MATA59");}}, null);
		
		fluxogramaSI.add(MATC89);
		
		Disciplina ADMF01 = new Disciplina( "ADMF01", "SISTEMAS DE APOIO À DECISÃO", 85, "Obrigatória", null, "6", new ArrayList<String>(){{add("ADM211");}}, new ArrayList<String>(){{add("MATC99");}});
		
		fluxogramaSI.add(ADMF01);
		
		Disciplina MAT220 = new Disciplina( "MAT220", "EMPREENDEDORES EM INFORMATICA", 68, "Obrigatória", null, "6", null, null);
		
		fluxogramaSI.add(MAT220);
		
		// ===== 7º semestre ==== //
		
		Disciplina MATC72 = new Disciplina( "MATC72", "INTERAÇÃO HUMANO-COMPUTADOR", 68, "Obrigatória", null, "7", new ArrayList<String>(){{add("MATA62");}}, null);
		
		fluxogramaSI.add(MATC72);
		
		Disciplina MATB19 = new Disciplina( "MATB19", "SISTEMAS MULTIMÍDIA", 68, "Obrigatória", null, "7", new ArrayList<String>(){{add("MATA55");}}, null);
		
		fluxogramaSI.add(MATB19);
		
		Disciplina MATB02 = new Disciplina( "MATB02", "QUALIDADE DE SOFTWARE", 51, "Obrigatória", null, "7", new ArrayList<String>(){{add("MATA63");}}, null);
		
		fluxogramaSI.add(MATB02);
				
		Disciplina MATA64 = new Disciplina( "MATA64", "INTELIGÊNCIA ARTIFICIAL", 68, "Obrigatória", null, "7", new ArrayList<String>(){{add("MATA37"); // ILP
				add("MATD04");}}, null);
		
		fluxogramaSI.add(MATA64);
		
		Disciplina MATC99 = new Disciplina( "MATC99", "SEGURANÇA E AUDITORIA DE SISTEMAS DE INFORMAÇÃO", 68, "Obrigatória", null, "7", new ArrayList<String>(){{add("ADMF01"); // SISTEMAS DE APOIO À DECISÃO MATA07
				add("MATA07"); }}, null);
		
		fluxogramaSI.add(MATC99);
		
		// ===== 8º semestre ==== //
		
		Disciplina OPTATIVA1 = new Disciplina( null, "DISCIPLINA OPTATIVA", 68, "Optativa ", null, "8", null, null);
		
		fluxogramaSI.add(OPTATIVA1);
		
		Disciplina OPTATIVA2 = new Disciplina( null, "DISCIPLINA OPTATIVA", 68, "Optativa ", null, "8", null, null);
		
		fluxogramaSI.add(OPTATIVA2);
		
		Disciplina OPTATIVA3 = new Disciplina( null, "DISCIPLINA OPTATIVA", 68, "Optativa ", null, "8", null, null);
		
		fluxogramaSI.add(OPTATIVA3);
		
		Disciplina OPTATIVA4 = new Disciplina( null, "DISCIPLINA OPTATIVA", 68, "Optativa ", null, "8", null, null);
		
		fluxogramaSI.add(OPTATIVA4);
		
		Disciplina OPTATIVA5 = new Disciplina( null, "DISCIPLINA OPTATIVA", 68, "Optativa ", null, "8", null, null);
		
		fluxogramaSI.add(OPTATIVA5);
		
		// ===== 9º semestre ==== //
		
		Disciplina MATC97 = new Disciplina( "MATC97", "TCC BACHARELADO SISTEMAS DE INFORMAÇÃO I", 51, "Obrigatória", null, "9", new ArrayList<String>(){{add("LETA09");}},  new ArrayList<String>(){{add("MATC98");}});
		
		fluxogramaSI.add(MATC97);
		
		Disciplina OPTATIVA6 = new Disciplina( null, "DISCIPLINA OPTATIVA", 68, "Optativa ", null, "9", null, null);
		
		fluxogramaSI.add(OPTATIVA6);
		
		Disciplina OPTATIVA7 = new Disciplina( null, "DISCIPLINA OPTATIVA", 68, "Optativa ", null, "9", null, null);
		
		fluxogramaSI.add(OPTATIVA7);
		
		Disciplina OPTATIVA8 = new Disciplina( null, "DISCIPLINA OPTATIVA", 68, "Optativa ", null, "9", null, null);
		
		fluxogramaSI.add(OPTATIVA8);
		
		// ===== 10º semestre ==== //
		
		Disciplina MATC98 = new Disciplina( "MATC98", "TCC BACHARELADO SISTEMAS DE INFORMAÇÃO II", 136, "Obrigatória", null, "10", new ArrayList<String>(){{add("MATC97");}} , null);

		fluxogramaSI.add(MATC98);		
		
	}
	
	public ArrayList<Disciplina> getFluxogramaSI() {
		return fluxogramaSI;
	}

	public void setFluxogramaSI(ArrayList<Disciplina> fluxogramaSI) {
		this.fluxogramaSI = fluxogramaSI;
	}
	
	public void popularListaRequesitos(ArrayList<Disciplina> fluxogramaSI){
		for (Disciplina disciplina : fluxogramaSI) {
			if(disciplina.getCodPreRequisitosList() != null && disciplina.getCodPreRequisitosList().size() > 0){
				for (String nomeMateria : disciplina.getCodPreRequisitosList()) {
					for (Disciplina preRequisito : fluxogramaSI) {
						if(nomeMateria.equals(preRequisito.getCodigo())){
							disciplina.getRequisitosList().add(preRequisito);
						}
					}
				}
			}
		}
	}
	
	public void popularListaMateriasLiberadas(ArrayList<Disciplina> fluxogramaSI){
		for (Disciplina disciplina : fluxogramaSI) {
			if(disciplina.getCodAbertasList() != null && disciplina.getCodAbertasList().size() > 0){
				for (String nomeMateria : disciplina.getCodAbertasList()) {
					for (Disciplina materiaLiberada : fluxogramaSI) {
						if(nomeMateria.equals(materiaLiberada.getCodigo())){
							disciplina.getLiberaList().add(materiaLiberada);
						}
					}
				}
			}
		}
	}
	
	public void obterTamanhoCadeiaFluxograma(ArrayList<Disciplina> fluxogramaSI){
		double tamanho = 0.0;
		for (Disciplina disciplina : fluxogramaSI) {
			if(disciplina.getLiberaList() != null && disciplina.getLiberaList().size() > 0){				
				
				
			}else{
				tamanho = 1;
			}
			
			disciplina.setMaiorCadeiaFluxograma(tamanho);
		}
	}
	
	private double contarCadeiaFluxograma(ArrayList<Disciplina> fluxogramaSI){
		
	}
	
	
}
