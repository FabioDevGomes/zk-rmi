package br.com.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.modelo.Conta;
import br.com.modelo.Movimentacao;
import br.com.service.ContaService;

public class ServiceImpl extends UnicastRemoteObject implements ContaService {
	private static final long serialVersionUID = 1L;

	public ServiceImpl() throws RemoteException {
		super();
	}

	public static void main(String args[]) {
		try {
			ServiceImpl obj = new ServiceImpl();

			Registry registry = LocateRegistry.createRegistry(2001);
			registry.rebind("ServiceImpl", obj);

			System.out.println("Servidor carregado no registry");
		} catch (Exception e) {
			System.out.println("ContaServiceImpl RMI erro: " + e.getMessage());
		}
	}

	public String showMsg(String msg) {
		return ("msg: " + msg);
	}

	public Conta criarConta(Conta conta) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(conta);
		em.getTransaction().commit();
		em.close();

		return conta;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Conta> listarContas() throws RemoteException {
		return JPAUtil.getEntityManager().createQuery("Select t from " + Conta.class.getName() + " t").getResultList();
	}

	@Override
	public Movimentacao criarMovimentacao(Movimentacao movimentacao) throws RemoteException {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		movimentacao.setConta(em.find(Conta.class, movimentacao.getConta().getId()));
		em.persist(movimentacao);
		em.getTransaction().commit();
		em.close();

		return movimentacao;
	}

	@Override
	public List<Movimentacao> listarMovimentacoes() throws RemoteException {
		return JPAUtil.getEntityManager().createQuery("Select t from " + Movimentacao.class.getName() + " t").getResultList();
	}
}
