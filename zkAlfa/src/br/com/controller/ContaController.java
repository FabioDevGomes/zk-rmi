package br.com.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;

import br.com.modelo.Conta;
import br.com.rmi.JPAUtil;
import br.com.service.ContaService;

public class ContaController extends GenericForwardComposer {
	private static final long serialVersionUID = 1L;
	private ArrayList<LinkedHashMap<String, Object>> listagemContas = new ArrayList<LinkedHashMap<String, Object>>();

	private Conta conta;
	private DataBinder binder;
	private Intbox intbxMultiplicacao; 
	private Listbox lstbxHistorico;
	ContaService objRemoto = null;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2001);
		objRemoto = (ContaService) registry.lookup("ServiceImpl");
		this.binder = new AnnotateDataBinder(comp);
		setConta(new Conta());
		listarContas();
		
		this.binder.loadAll();
	}

	public void onClick$btnCriarConta() {
		try {
			objRemoto.criarConta(conta);
			listarContas();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void listarContas() {
		try {
			List<Conta> contas = objRemoto.listarContas();
			for (Conta conta : contas) {
				LinkedHashMap<String, Object> hashContas = new LinkedHashMap<String, Object>();
				hashContas.put("nomeTitular", conta.getNomeTitular());
				hashContas.put("numero", conta.getNumero());
				hashContas.put("banco", conta.getBanco());
				hashContas.put("agencia", conta.getAgencia());
				this.getListagemContas().add(hashContas);
			}

			this.binder.loadComponent(this.lstbxHistorico);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Client exception: " + e.getMessage());
		}
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public ArrayList<LinkedHashMap<String, Object>> getListagemContas() {
		return listagemContas;
	}

	public void setListagemContas(ArrayList<LinkedHashMap<String, Object>> listagemContas) {
		this.listagemContas = listagemContas;
	}

}
