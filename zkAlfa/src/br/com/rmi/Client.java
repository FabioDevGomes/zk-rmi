package br.com.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import br.com.modelo.Conta;
import br.com.service.ContaService;

public class Client extends UnicastRemoteObject{
	private static final long serialVersionUID = 1L;

	protected Client() throws RemoteException {
		super();
	}

	public static void main(String[] args) {
		ContaService objRemoto = null;
		Conta retorno = null;
		try {
//			objRemoto = (Ola) Naming.lookup("//127.0.0.1/OlaServidor");
			
			java.rmi.registry.Registry registry = LocateRegistry.getRegistry("127.0.0.1",2001);
			objRemoto = (ContaService) registry.lookup("ServiceImpl");
			
//			final ContaService listener = new UpdateListenerImpl();
//			UnicastRemoteObject.export(listener);
			
			Conta conta = new Conta();
			conta.setNomeTitular("Fábio Alves Gomes 4");
			conta.setBanco("bc 44");
			conta.setAgencia("ag 33");
			conta.setNumero("1234");
			
			
			retorno = objRemoto.criarConta(conta);
			System.out.println("Retorno: "+ retorno);
			
			List<Conta> contas = objRemoto.listarContas();
			
			for (Conta conta2 : contas) {
				System.out.println(conta2.getNomeTitular());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Client exception: " + e.getMessage());
		}
	}
}