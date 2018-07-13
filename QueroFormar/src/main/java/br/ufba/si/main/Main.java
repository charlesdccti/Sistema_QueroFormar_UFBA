/**
 * 
 */
package br.ufba.si.main;

import java.util.ArrayList;

import br.ufba.si.entidade.Disciplina;
import br.ufba.si.utils.ResultadoEnum;

/**
 * @author charles
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//(String codigo, String nome, String cargaHoraria, String natureza, ResultadoEnum resultado, String semestre, ArrayList<String> codPreRequisitosList, ArrayList<String> codAbertasList)

		//(String codigo, String nome, int cargaHoraria, String natureza, ResultadoEnum resultado, String semestre, ArrayList<String> codPreRequisitosList, ArrayList<String> codAbertasList);
		Disciplina MATA68 = new Disciplina( "MATA68", "COMPUTADOR, ÉTICA E SOCIEDADE", "51h", "Obrigatória", null, "1", null, null);
		
		Disciplina MATA37 = new Disciplina( "MATA37", "INTRODUÇÃO À LÓGICA DE PROGRAMAÇÃO", "68h", "Obrigatória", null,"1", null,  new ArrayList<String>(){
				{
					add("MATA76");
					//add("MATD04"); // estrtura de dados, vai ser que abri ou podera abrir?
				}
			} 
		);
		
		Disciplina MATA42 = new Disciplina( "MATA42", "MATEMÁTICA DISCRETA I", "68h", "Obrigatória", null, "1", null, new ArrayList<String>(){
				{
					add("MATC73"); //ILM
					//add("MATD04"); // estrtura de dados
					add("MATC94"); // M.formais
				}
			} 
		);
	
		Disciplina MATA02 = new Disciplina( "MATA02", "CÁLCULO A", "102h", "Obrigatória",  null,"1", null, null);
		
		Disciplina MATA39 = new Disciplina( "MATA39", "SEMINÁRIOS DE INTRODUÇÃO AO CURSO", "51h", "Obrigatória", null,"1", null, null);

		// ===== 2 semestre ==== //
		
		Disciplina MATC90 = new Disciplina( "MATC90", "CIRCUITOS DIGITAIS E ARQUITETURA DE COMPUTADORES", "68h", "Obrigatória", null, "2", null, new ArrayList<String>(){
				{
					add("MATA58"); // S.Operacionais
					//add("MATA59"); // redes 1
				}
			} 
		);

		Disciplina MATD04 = new Disciplina( "MATD04", "ESTRUTURAS DE DADOS", "68h", "Obrigatória", null, "2", new ArrayList<String>(){
					{
						add("MATA37"); // ILP
						add("MATA42"); // Discreta 1
					}
				}, 
				new ArrayList<String>(){
					{
						add("MATA60"); // banco de dados
						add("MATA55"); // POO
					}
				} 
		);
		
		Disciplina MATC73 = new Disciplina( "MATC73", "INTRODUÇÃO À LÓGICA MATEMÁTICA", "68h", "Obrigatória", null, "2", new ArrayList<String>(){
			{
				add("MATA42"); // Discreta 1
			}
		}, null );
		
		Disciplina MATC92 = new Disciplina( "MATC92", "FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO", "68h", "Obrigatória", null, "2", null, null);
		
		Disciplina ADME99 = new Disciplina( "ADME99", "ECONOMIA DA INOVAÇÃO", "68h", "Obrigatória", null, "2", null, null);
		
		// ===== 3º semestre ==== //
		
		Disciplina MATA58 = new Disciplina( "MATA58", "SISTEMAS OPERACIONAIS", "68h", "Obrigatória", null, "3", new ArrayList<String>(){
			{
				add("MATC90"); // circuitos
			}
		}, null );
		
		
		Disciplina MATA55 = new Disciplina( "MATA55", "PROGRAMAÇÃO ORIENTADA A OBJETOS", "68h", "Obrigatória", null, "3", new ArrayList<String>(){
					{
						add("MATD04"); // EDA
					}
				}, 
				new ArrayList<String>(){
					{
						add("MATA62"); // eng 1
						add("MATA56"); // paradigmas
					}
				} 
		);
		
		Disciplina MATC94 = new Disciplina( "MATC94", "INTRODUÇÃO AS LINGUAGENS FORMAIS E TEORIA DA COMPUTAÇÃO", "68h", "Obrigatória", null, "3", null, null);

		Disciplina MATA07 = new Disciplina( "MATA07", "ÁLGEBRA LINEAR A", "68h", "Obrigatória", null, "3", null, null);
		
		Disciplina ADM001 = new Disciplina( "ADM001", "INTRODUCAO À ADMINISTRACAO", "68h", "Obrigatória", null, "3", null, null);
		
		// ===== 4º semestre ==== //
		
		Disciplina LETA09 = new Disciplina( "LETA09", "OFICINA DE LEITURA E PRODUÇÃO DE TEXTOS", "68h", "Obrigatória", null, "4", null, new ArrayList<String>(){
				{
					add("MATC97"); // TCC 1
				}
			} 
		);
		
		
		Disciplina MATA59 = new Disciplina( "MATA59", "REDES DE COMPUTADORES I", "68h", "Obrigatória", null, "4", new ArrayList<String>(){
			{
				add("MATC90"); // TCC 1
			}
		} , null);
		
		
		Disciplina MATA62 = new Disciplina( "MATA62", "ENGENHARIA DE SOFTWARE I", "68h", "Obrigatória", null, "4", new ArrayList<String>(){
				{
					add("MATA55"); // POO
				}
			}, 
			new ArrayList<String>(){
				{
					add("MATA63"); // eng II
					add("MATC72"); // IHC
				}
			} 
		);
		
		
		Disciplina MAT236 = new Disciplina( "MAT236", "MÉTODOS ESTATÍSTICOS", "68h", "Obrigatória", null, "4", new ArrayList<String>(){
				{
					add("MATA07"); // Álgebra
				}
			}, null);
		
		
		Disciplina MATC82 = new Disciplina( "MATC82", "SISTEMAS WEB", "68h", "Obrigatória", null, "4", new ArrayList<String>(){
				{
					add("MATC90"); // SO
					add("MATC92"); // Fundamentos de SI
				}
			}, null);
		
		
		
		// ===== 5º semestre ==== //
		
		Disciplina MATA60 = new Disciplina( "MATA60", "BANCO DE DADOS", "68h", "Obrigatória", null, "5", new ArrayList<String>(){
				{
					add("MATD04"); // EDA
				}
			}, 
			new ArrayList<String>(){
				{
					add("MATB09"); // lab de banco de dados
				}
			} 
		);
		
		
		Disciplina MATA56 = new Disciplina( "MATA56", "PARADIGMAS DE LINGUAGENS DE PROGRAMAÇÃO", "68h", "Obrigatória", null, "5", new ArrayList<String>(){
				{
					add("MATA55"); // POO
				}
			}, null);
		
		
		
		Disciplina MATA63 = new Disciplina( "MATA63", "ENGENHARIA DE SOFTWARE II", "68h", "Obrigatória", null, "5", new ArrayList<String>(){
				{
					add("MATA62"); // ENG I
				}
			}, 
			new ArrayList<String>(){
				{
					add("MATB02"); // QUALIDADE
				}
			} 
		);
		
		
		
		Disciplina ADM211 = new Disciplina( "ADM211", "MÉTODOS QUANTITATIVOS APLICADOS À ADMINISTRAÇÃO", "68h", "Obrigatória", null,"5", new ArrayList<String>(){
				{
					add("ADM001"); // intro. ADM
					add("MAT236"); // M. estatísticos
				}
			}, 
			new ArrayList<String>(){
				{
					add("ADMF01"); // SISTEMAS DE APOIO À DECISÃO
				}
			} 
		);
		
		
		Disciplina MATC84 = new Disciplina( "MATC84", "LABORATÓRIO DE PROGRAMAÇÃO WEB", "51h", "Obrigatória", null,"5", new ArrayList<String>(){
			{
				add("MATA55"); // POO
			}
		}, null);
		
		
		// ===== 6º semestre ==== //
		
		Disciplina MATB09 = new Disciplina( "MATB09", "LABORATÓRIO DE BANCO DE DADOS", "51h", "Obrigatória", null, "6", new ArrayList<String>(){
			{
				add("MATA60"); // banco de dados
			}
		}, null);
		

		Disciplina MATA76 = new Disciplina( "MATA76", "LINGUAGENS PARA APLICAÇÃO COMERCIAL", "68h", "Obrigatória", null, "6", new ArrayList<String>(){
			{
				add("MATA37"); // ILP
			}
		}, null);
		
		
		Disciplina MATC89 = new Disciplina( "MATC89", "APLICAÇÕES PARA DISPOSITIVOS MÓVEIS", "68h", "Obrigatória", null, "6", new ArrayList<String>(){
			{
				add("MATA55"); // POO
				add("MATA59"); // redes 1
			}
		}, null);
		
		
		Disciplina ADMF01 = new Disciplina( "ADMF01", "SISTEMAS DE APOIO À DECISÃO", "85h", "Obrigatória", null, "6", new ArrayList<String>(){
				{
					add("ADM211"); // MÉTODOS QUANTITATIVOS APLICADOS À ADMINISTRAÇÃO
				}
			}, 
			new ArrayList<String>(){
				{
					add("MATC99"); // SEGURANÇA E AUDITORIA DE SISTEMAS DE INFORMAÇÃO	
				}
			} 
		);
				
		Disciplina MAT220 = new Disciplina( "MAT220", "EMPREENDEDORES EM INFORMATICA", "68h", "Obrigatória", null, "6", null, null);
		
		
		
		// ===== 7º semestre ==== //
		
		Disciplina MATC72 = new Disciplina( "MATC72", "INTERAÇÃO HUMANO-COMPUTADOR", "68h", "Obrigatória", null, "7", new ArrayList<String>(){
			{
				add("MATA62"); // ENG I
			}
		}, null);
		
		
		Disciplina MATB19 = new Disciplina( "MATB19", "SISTEMAS MULTIMÍDIA", "68h", "Obrigatória", null, "7", new ArrayList<String>(){
			{
				add("MATA55"); // POO
			}
		}, null);
		
		
		Disciplina MATB02 = new Disciplina( "MATB02", "QUALIDADE DE SOFTWARE", "51h", "Obrigatória", null, "7", new ArrayList<String>(){
			{
				add("MATA63"); // ENG II
			}
		}, null);
		
				
		Disciplina MATA64 = new Disciplina( "MATA64", "INTELIGÊNCIA ARTIFICIAL", "68h", "Obrigatória", null, "7", new ArrayList<String>(){
			{
				add("MATA37"); // ILP
				add("MATD04"); // EDA
			}
		}, null);
		
		
		Disciplina MATC99 = new Disciplina( "MATC99", "SEGURANÇA E AUDITORIA DE SISTEMAS DE INFORMAÇÃO", "68h", "Obrigatória", null, "7", new ArrayList<String>(){
			{
				add("ADMF01"); // SISTEMAS DE APOIO À DECISÃO
			}
		}, null);
		
		
		// ===== 8º semestre ==== //
		
		Disciplina OPTATIVA = new Disciplina( "OP", "DISCIPLINA OPTATIVA", "68h", "Optativa ", null, "8", null, null);

		
		
		// ===== 9º semestre ==== //
		
		Disciplina MATC97 = new Disciplina( "MATC97", "TCC BACHARELADO SISTEMAS DE INFORMAÇÃO I", "51h", "Obrigatória", null, "9", new ArrayList<String>(){
				{
					add("LETA09"); // OFICINA DE LEITURA E PRODUÇÃO DE TEXTOS
				}
			}, 
			new ArrayList<String>(){
				{
					add("MATC98"); // TCC 2
				}
			} 
		);
		
		
		
		// ===== 10º semestre ==== //
		
		Disciplina MATC98 = new Disciplina( "MATC98", "TCC BACHARELADO SISTEMAS DE INFORMAÇÃO II", "136h", "Obrigatória", null, "10", new ArrayList<String>(){
			{
				add("MATC97"); // TCC 1
			}
		} , null);
		
		
		System.out.println("#== DISCIPLINAS INSTANCIADAS ==#");
	
		
=======
		
		//		Disciplina MATA68 = new Disciplina( "MATA68", "COMPUTADOR, ÉTICA E SOCIEDADE", "51h", "Obrigatória", null, "1", null, null);
		//		
		//		Disciplina MATA37 = new Disciplina( "MATA37", "INTRODUÇÃO À LÓGICA DE PROGRAMAÇÃO", "68h", "Obrigatória", null,"1", null,  new ArrayList<String>(){
		//				{
		//					add("MATA76");
		//					//add("MATD04"); // estrtura de dados, vai ser que abri ou podera abrir?
		//				}
		//			} 
		//		);
		//		
		//		Disciplina MATA42 = new Disciplina( "MATA42", "MATEMÁTICA DISCRETA I", "68h", "Obrigatória", null, "1", null, new ArrayList<String>(){
		//				{
		//					add("MATC73"); //ILM
		//					//add("MATD04"); // estrtura de dados
		//					add("MATC94"); // M.formais
		//				}
		//			} 
		//		);
		//	
		//		Disciplina MATA02 = new Disciplina( "MATA02", "CÁLCULO A", "102h", "Obrigatória",  null,"1", null, null);
		//		
		//		Disciplina MATA39 = new Disciplina( "MATA39", "SEMINÁRIOS DE INTRODUÇÃO AO CURSO", "51h", "Obrigatória", null,"1", null, null);
		//
		//		// ===== 2 semestre ==== //
		//		
		//		Disciplina MATC90 = new Disciplina( "MATC90", "CIRCUITOS DIGITAIS E ARQUITETURA DE COMPUTADORES", "68h", "Obrigatória", null, "2", null, new ArrayList<String>(){
		//				{
		//					add("MATA58"); // S.Operacionais
		//					//add("MATA59"); // redes 1
		//				}
		//			} 
		//		);
		//
		//		Disciplina MATD04 = new Disciplina( "MATD04", "ESTRUTURAS DE DADOS", "68h", "Obrigatória", null, "2", new ArrayList<String>(){
		//					{
		//						add("MATA37"); // ILP
		//						add("MATA42"); // Discreta 1
		//					}
		//				}, 
		//				new ArrayList<String>(){
		//					{
		//						add("MATA60"); // banco de dados
		//						add("MATA55"); // POO
		//					}
		//				} 
		//		);
		//		
		//		Disciplina MATC73 = new Disciplina( "MATC73", "INTRODUÇÃO À LÓGICA MATEMÁTICA", "68h", "Obrigatória", null, "2", new ArrayList<String>(){
		//			{
		//				add("MATA42"); // Discreta 1
		//			}
		//		}, null );
		//		
		//		Disciplina MATC92 = new Disciplina( "MATC92", "FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO", "68h", "Obrigatória", null, "2", null, null);
		//		
		//		Disciplina ADME99 = new Disciplina( "ADME99", "ECONOMIA DA INOVAÇÃO", "68h", "Obrigatória", null, "2", null, null);
		//		
		//		// ===== 3º semestre ==== //
		//		
		//		Disciplina MATA58 = new Disciplina( "MATA58", "SISTEMAS OPERACIONAIS", "68h", "Obrigatória", null, "3", new ArrayList<String>(){
		//			{
		//				add("MATC90"); // circuitos
		//			}
		//		}, null );
		//		
		//		
		//		Disciplina MATA55 = new Disciplina( "MATA55", "PROGRAMAÇÃO ORIENTADA A OBJETOS", "68h", "Obrigatória", null, "3", new ArrayList<String>(){
		//					{
		//						add("MATD04"); // EDA
		//					}
		//				}, 
		//				new ArrayList<String>(){
		//					{
		//						add("MATA62"); // eng 1
		//						add("MATA56"); // paradigmas
		//					}
		//				} 
		//		);
		//		
		//		Disciplina MATC94 = new Disciplina( "MATC94", "INTRODUÇÃO AS LINGUAGENS FORMAIS E TEORIA DA COMPUTAÇÃO", "68h", "Obrigatória", null, "3", null, null);
		//
		//		Disciplina MATA07 = new Disciplina( "MATA07", "ÁLGEBRA LINEAR A", "68h", "Obrigatória", null, "3", null, null);
		//		
		//		Disciplina ADM001 = new Disciplina( "ADM001", "INTRODUCAO À ADMINISTRACAO", "68h", "Obrigatória", null, "3", null, null);
		//		
		//		// ===== 4º semestre ==== //
		//		
		//		Disciplina LETA09 = new Disciplina( "LETA09", "OFICINA DE LEITURA E PRODUÇÃO DE TEXTOS", "68h", "Obrigatória", null, "4", null, new ArrayList<String>(){
		//				{
		//					add("MATC97"); // TCC 1
		//				}
		//			} 
		//		);
		//		
		//		
		//		Disciplina MATA59 = new Disciplina( "MATA59", "REDES DE COMPUTADORES I", "68h", "Obrigatória", null, "4", new ArrayList<String>(){
		//			{
		//				add("MATC90"); // TCC 1
		//			}
		//		} , null);
		//		
		//		
		//		Disciplina MATA62 = new Disciplina( "MATA62", "ENGENHARIA DE SOFTWARE I", "68h", "Obrigatória", null, "4", new ArrayList<String>(){
		//				{
		//					add("MATA55"); // POO
		//				}
		//			}, 
		//			new ArrayList<String>(){
		//				{
		//					add("MATA63"); // eng II
		//					add("MATC72"); // IHC
		//				}
		//			} 
		//		);
		//		
		//		
		//		Disciplina MAT236 = new Disciplina( "MAT236", "MÉTODOS ESTATÍSTICOS", "68h", "Obrigatória", null, "4", new ArrayList<String>(){
		//				{
		//					add("MATA07"); // Álgebra
		//				}
		//			}, null);
		//		
		//		
		//		Disciplina MATC82 = new Disciplina( "MATC82", "SISTEMAS WEB", "68h", "Obrigatória", null, "4", new ArrayList<String>(){
		//				{
		//					add("MATC90"); // SO
		//					add("MATC92"); // Fundamentos de SI
		//				}
		//			}, null);
		//		
		//		
		//		
		//		// ===== 5º semestre ==== //
		//		
		//		Disciplina MATA60 = new Disciplina( "MATA60", "BANCO DE DADOS", "68h", "Obrigatória", null, "5", new ArrayList<String>(){
		//				{
		//					add("MATD04"); // EDA
		//				}
		//			}, 
		//			new ArrayList<String>(){
		//				{
		//					add("MATB09"); // lab de banco de dados
		//				}
		//			} 
		//		);
		//		
		//		
		//		Disciplina MATA56 = new Disciplina( "MATA56", "PARADIGMAS DE LINGUAGENS DE PROGRAMAÇÃO", "68h", "Obrigatória", null, "5", new ArrayList<String>(){
		//				{
		//					add("MATA55"); // POO
		//				}
		//			}, null);
		//		
		//		
		//		
		//		Disciplina MATA63 = new Disciplina( "MATA63", "ENGENHARIA DE SOFTWARE II", "68h", "Obrigatória", null, "5", new ArrayList<String>(){
		//				{
		//					add("MATA62"); // ENG I
		//				}
		//			}, 
		//			new ArrayList<String>(){
		//				{
		//					add("MATB02"); // QUALIDADE
		//				}
		//			} 
		//		);
		//		
		//		
		//		
		//		Disciplina ADM211 = new Disciplina( "ADM211", "MÉTODOS QUANTITATIVOS APLICADOS À ADMINISTRAÇÃO", "68h", "Obrigatória", null,"5", new ArrayList<String>(){
		//				{
		//					add("ADM001"); // intro. ADM
		//					add("MAT236"); // M. estatísticos
		//				}
		//			}, 
		//			new ArrayList<String>(){
		//				{
		//					add("ADMF01"); // SISTEMAS DE APOIO À DECISÃO
		//				}
		//			} 
		//		);
		//		
		//		
		//		Disciplina MATC84 = new Disciplina( "MATC84", "LABORATÓRIO DE PROGRAMAÇÃO WEB", "51h", "Obrigatória", null,"5", new ArrayList<String>(){
		//			{
		//				add("MATA55"); // POO
		//			}
		//		}, null);
		//		
		//		
		//		// ===== 6º semestre ==== //
		//		
		//		Disciplina MATB09 = new Disciplina( "MATB09", "LABORATÓRIO DE BANCO DE DADOS", "51h", "Obrigatória", null, "6", new ArrayList<String>(){
		//			{
		//				add("MATA60"); // banco de dados
		//			}
		//		}, null);
		//		
		//
		//		Disciplina MATA76 = new Disciplina( "MATA76", "LINGUAGENS PARA APLICAÇÃO COMERCIAL", "68h", "Obrigatória", null, "6", new ArrayList<String>(){
		//			{
		//				add("MATA37"); // ILP
		//			}
		//		}, null);
		//		
		//		
		//		Disciplina MATC89 = new Disciplina( "MATC89", "APLICAÇÕES PARA DISPOSITIVOS MÓVEIS", "68h", "Obrigatória", null, "6", new ArrayList<String>(){
		//			{
		//				add("MATA55"); // POO
		//				add("MATA59"); // redes 1
		//			}
		//		}, null);
		//		
		//		
		//		Disciplina ADMF01 = new Disciplina( "ADMF01", "SISTEMAS DE APOIO À DECISÃO", "85h", "Obrigatória", null, "6", new ArrayList<String>(){
		//				{
		//					add("ADM211"); // MÉTODOS QUANTITATIVOS APLICADOS À ADMINISTRAÇÃO
		//				}
		//			}, 
		//			new ArrayList<String>(){
		//				{
		//					add("MATC99"); // SEGURANÇA E AUDITORIA DE SISTEMAS DE INFORMAÇÃO	
		//				}
		//			} 
		//		);
		//				
		//		Disciplina MAT220 = new Disciplina( "MAT220", "EMPREENDEDORES EM INFORMATICA", "68h", "Obrigatória", null, "6", null, null);
		//		
		//		
		//		
		//		// ===== 7º semestre ==== //
		//		
		//		Disciplina MATC72 = new Disciplina( "MATC72", "INTERAÇÃO HUMANO-COMPUTADOR", "68h", "Obrigatória", null, "7", new ArrayList<String>(){
		//			{
		//				add("MATA62"); // ENG I
		//			}
		//		}, null);
		//		
		//		
		//		Disciplina MATB19 = new Disciplina( "MATB19", "SISTEMAS MULTIMÍDIA", "68h", "Obrigatória", null, "7", new ArrayList<String>(){
		//			{
		//				add("MATA55"); // POO
		//			}
		//		}, null);
		//		
		//		
		//		Disciplina MATB02 = new Disciplina( "MATB02", "QUALIDADE DE SOFTWARE", "51h", "Obrigatória", null, "7", new ArrayList<String>(){
		//			{
		//				add("MATA63"); // ENG II
		//			}
		//		}, null);
		//		
		//				
		//		Disciplina MATA64 = new Disciplina( "MATA64", "INTELIGÊNCIA ARTIFICIAL", "68h", "Obrigatória", null, "7", new ArrayList<String>(){
		//			{
		//				add("MATA37"); // ILP
		//				add("MATD04"); // EDA
		//			}
		//		}, null);
		//		
		//		
		//		Disciplina MATC99 = new Disciplina( "MATC99", "SEGURANÇA E AUDITORIA DE SISTEMAS DE INFORMAÇÃO", "68h", "Obrigatória", null, "7", new ArrayList<String>(){
		//			{
		//				add("ADMF01"); // SISTEMAS DE APOIO À DECISÃO
		//			}
		//		}, null);
		//		
		//		
		//		// ===== 8º semestre ==== //
		//		
		//		Disciplina OPTATIVA = new Disciplina( "OP", "DISCIPLINA OPTATIVA", "68h", "Optativa ", null, "8", null, null);
		//
		//		
		//		
		//		// ===== 9º semestre ==== //
		//		
		//		Disciplina MATC97 = new Disciplina( "MATC97", "TCC BACHARELADO SISTEMAS DE INFORMAÇÃO I", "51h", "Obrigatória", null, "9", new ArrayList<String>(){
		//				{
		//					add("LETA09"); // OFICINA DE LEITURA E PRODUÇÃO DE TEXTOS
		//				}
		//			}, 
		//			new ArrayList<String>(){
		//				{
		//					add("MATC98"); // TCC 2
		//				}
		//			} 
		//		);
		//		
		//		
		//		
		//		// ===== 10º semestre ==== //
		//		
		//		Disciplina MATC98 = new Disciplina( "MATC98", "TCC BACHARELADO SISTEMAS DE INFORMAÇÃO II", "136h", "Obrigatória", null, "10", new ArrayList<String>(){
		//			{
		//				add("MATC97"); // TCC 1
		//			}
		//		} , null);
		//		
		//		
		//		System.out.println("#== DISCIPLINAS INSTANCIADAS ==#");
		//	
		//		
>>>>>>> busca
	}
}
