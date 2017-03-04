package br.com.rmi;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.modelo.Conta;

public class JPAUtil {
	private static EntityManagerFactory factory;

	public static EntityManagerFactory getFactory() {
		if (factory == null || !factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory("contas-mysql");
		}
		
		return factory;
	}
	public static EntityManager getEntityManager() {
		return getFactory().createEntityManager();
	}

//	public static List<Conta> listarTodos() {
//        return getEntityManager().createQuery("Select t from " + Conta.class.getName() + " t").getResultList();
//	}
	
//	public static void salvar(Conta conta) {
//		EntityManager em = getEntityManager();
//		em.getTransaction().begin();
//
//		em.persist(conta);
//
//		em.getTransaction().commit();
//		em.close();
//	}
}
