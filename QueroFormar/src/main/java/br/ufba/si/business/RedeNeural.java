package br.ufba.si.business;

public class RedeNeural {

	private static double TAXA_APRENDIZADO = 0.4;

	private int epocas = 0;
	/*Danilo*/
	private double peso1;
	private double peso2;
	private double bias;
	

	public RedeNeural() {
		peso1 = 0.9;
		peso2 = 0.1;
		bias = 1;
	}
	

	//Danilo
		public void treinar(double[][] conjuntoTreinamento, double[] valoresEsperados) {
			double erro = 1.0;
			while ((Math.abs(erro) > 0.05) && (epocas < 100000)) {
				
				double[] vetor  = new double[2];
				
				for (int i = 0; i < conjuntoTreinamento[0].length; i++) {
					vetor[0] = conjuntoTreinamento[0][i];
					vetor[1] = conjuntoTreinamento[1][i]; 
					double valorSaida = saidaFuncaoU(vetor);
					valorSaida = Math.round(valorSaida);
					erro = calcularErro(valoresEsperados, valorSaida, i);
					if (! (valorSaida == valoresEsperados[i])) {
						this.aprender(valorSaida,valoresEsperados[i], vetor[0], vetor[1]);
					}		
				}
				epocas++;
			}
		}
		
		//Danilo
		private double calcularErro(double[] valoresEsperados, double valorSaida, int i) {
			return valoresEsperados[i] - valorSaida;
		}
		
		//Danilo
		private void aprender(Double saida, double esperados, double X, double Y) {
			peso1 = peso1+ TAXA_APRENDIZADO * X * (esperados - saida);
			peso2 = peso2+ TAXA_APRENDIZADO * Y * (esperados - saida);
			bias =  bias * TAXA_APRENDIZADO * (esperados - saida);
		}
		
		//Danilo
		private double saidaFuncaoU(double[] vetor) {
			double saida;
			saida =  (vetor[0] * peso1) + (vetor[1] * peso2) + bias;	
			
			return saida;
		}
		
		//Danilo
		public String classificar(double[] entrada) {
			if (epocas > 99999) {
				System.out.println("Nao foi possivel atingir um ponto de convergencia, verifique os parametros e a estrutura da rede.");
			} else {
				double valorSaida = Math.round(saidaFuncaoU(entrada));
				
				System.out.println("Saída: "+ valorSaida);
				return categorizar(valorSaida);
				
			}
			return "Não Classificado";
		}

	private String categorizar (double valor) {
				
		if(valor == 7.0){
			return "Categoria 01";
		}else if(valor == 6.0){
			return "Categoria 02";
		}else if(valor == 5.0){
			return "Categoria 03";
		}else if(valor == 4.0){
			return "Categoria 04";
		}else if(valor == 3.0){
			return "Categoria 05";
		}else if(valor == 2.0) {
			return "Categoria 06";
		}else{
			return "Categoria 06";
		}
		
	}

	public double getPeso1() {
		return peso1;
	}

	public void setPeso1(double peso1) {
		this.peso1 = peso1;
	}

	public double getPeso2() {
		return peso2;
	}

	public void setPeso2(double peso2) {
		this.peso2 = peso2;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}
}