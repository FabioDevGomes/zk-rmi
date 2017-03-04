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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;

import br.com.modelo.Conta;
import br.com.modelo.Movimentacao;
import br.com.modelo.TipoMovimentacao;
import br.com.rmi.JPAUtil;
import br.com.service.ContaService;

public class MovimentacaoController extends GenericForwardComposer {
	private static final long serialVersionUID = 1L;
	private ArrayList<LinkedHashMap<String, Object>> listagemMovimentacoes = new ArrayList<LinkedHashMap<String, Object>>();

	private Conta conta;
	private List<Conta> contas;
	private Movimentacao movimentacao;
	private DataBinder binder;
	private Intbox intbxMultiplicacao;
	private Listbox lstbxHistorico;
	private Combobox cmbContas;
	private String tipo;
	ContaService objRemoto = null;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2001);
		objRemoto = (ContaService) registry.lookup("ServiceImpl");
		this.binder = new AnnotateDataBinder(comp);
//		setConta(new Conta());
		movimentacao = new Movimentacao();
		contas = objRemoto.listarContas();
		listarMovimentacoes();
		
		this.binder.loadAll();
	}

	public void onClick$btnGerarMovimentacao() {
		try {	
			if(tipo.equals(TipoMovimentacao.ENTRADA.toString())){
				movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
			}else{
				movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
			}
			Conta contaCombo = new Conta();
			contaCombo.setId(cmbContas.getSelectedItem().getValue());
			movimentacao.setConta(contaCombo);
			objRemoto.criarMovimentacao(movimentacao);
			listarMovimentacoes();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void listarMovimentacoes() {
		try {
			List<Movimentacao> movimentacao = objRemoto.listarMovimentacoes();
			for (Movimentacao mov : movimentacao) {
				LinkedHashMap<String, Object> hashContas = new LinkedHashMap<String, Object>();
				hashContas.put("valor", mov.getValor());
				hashContas.put("tipoMovimentacao", mov.getTipoMovimentacao());
				hashContas.put("data", mov.getData());
				hashContas.put("descricao", mov.getDescricao());
				hashContas.put("conta", mov.getConta().getNomeTitular());
				this.getListagemMovimentacoes().add(hashContas);
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

	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public ArrayList<LinkedHashMap<String, Object>> getListagemMovimentacoes() {
		return listagemMovimentacoes;
	}

	public void setListagemMovimentacoes(ArrayList<LinkedHashMap<String, Object>> listagemMovimentacoes) {
		this.listagemMovimentacoes = listagemMovimentacoes;
	}

}
