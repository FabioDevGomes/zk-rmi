package br.com.service;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import br.com.modelo.Conta;
import br.com.modelo.Movimentacao;

public interface ContaService extends Remote, Serializable{
	Conta criarConta(Conta msg) throws RemoteException;
	List<Conta> listarContas() throws RemoteException;
	
	Movimentacao criarMovimentacao(Movimentacao movimentacao) throws RemoteException;
	List<Movimentacao> listarMovimentacoes() throws RemoteException;
}
