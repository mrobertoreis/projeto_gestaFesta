package com.gestaofesta.bean;

public class GestaoFesta {
	
	private String nome;
	private String acompanhante;
	private String opcaoVallet;
	
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getAcompanhante() {
		return acompanhante;
	}
	
	public void setAcompanhante(String acompanhante) {
		this.acompanhante = acompanhante;
	}

	public String getOpcaoVallet() {
		return opcaoVallet;
	}

	public void setOpcaoVallet(String opcaoVallet) {
		this.opcaoVallet = opcaoVallet;
	}

	@Override
	public String toString() {
		return "GestaoFesta [nome=" + nome + ", acompanhante=" + acompanhante + ", opcaoVallet=" + opcaoVallet + "]";
	}
	
	

}
