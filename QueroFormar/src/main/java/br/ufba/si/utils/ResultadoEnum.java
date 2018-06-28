package br.ufba.si.utils;

public enum ResultadoEnum {

	NãoIdentificado(0, "Não Identificado"),
	Aprovado(1, "AP"),
	Dispensado(2, "DI"),
	DispensaUFBA(3, "DU"),
	ReprovadoFrequencia(4, "RF"),
	ReprovadoRR(5, "RR"),
	Trancamento(6, "TR");
	

    private int id;
    private String nome;

    private ResultadoEnum(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    

    public ResultadoEnum comparaEnum(String opcao){
        if(opcao.equalsIgnoreCase(ResultadoEnum.Aprovado.toString())){
            return ResultadoEnum.Aprovado;
        }else if(opcao.equalsIgnoreCase(ResultadoEnum.Dispensado.toString())){
            return ResultadoEnum.Dispensado;
        }else if(opcao.equalsIgnoreCase(ResultadoEnum.DispensaUFBA.toString())){
            return ResultadoEnum.DispensaUFBA;
        }else if(opcao.equalsIgnoreCase(ResultadoEnum.ReprovadoFrequencia.toString())){
            return ResultadoEnum.ReprovadoFrequencia;
        }else if(opcao.equalsIgnoreCase(ResultadoEnum.ReprovadoRR.toString())){
            return ResultadoEnum.ReprovadoRR;
        }else if(opcao.equalsIgnoreCase(ResultadoEnum.Trancamento.toString())){
            return ResultadoEnum.Trancamento;
        }else{
        	return ResultadoEnum.NãoIdentificado;
        }
    }
    
    public int getValue() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
