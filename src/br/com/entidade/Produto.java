package br.com.entidade;

public class Produto {
	
	private int cd_produto;
	private int cd_nmc;
	private float preco;
	private String nome;
	private int quantidade;
	private float icms;
	private long cd_barra;
	
	public Produto(){}
	public Produto(int cd_produto, int cd_nmc, long cd_barra, float preco, String nome, int quantidade, float icms) {
		
		super();
		
		this.cd_produto = cd_produto;
		this.cd_nmc = cd_nmc;
		this.cd_barra = cd_barra;
		this.preco = preco;
		this.nome = nome;
		this.quantidade = quantidade;
		this.icms = icms;
		
	}
	
}
