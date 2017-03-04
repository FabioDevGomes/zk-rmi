package br.com.modelo;

import java.io.Serializable;
import java.rmi.Remote;

public class TesteRMI implements Serializable{
	private static final long serialVersionUID = 1L;

	private String nome;
	
	public TesteRMI() {
		super();
	}
	
	public TesteRMI(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
