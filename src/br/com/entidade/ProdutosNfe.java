package br.com.entidade;

public class ProdutosNfe {
	
	public int id;
	public float preco;
	public String nome;
	public int quantidade;
	
	public ProdutosNfe(){}
	public ProdutosNfe(int id, float preco, String nome, int quantidade) {
		super();
		this.id = id;
		this.preco = preco;
		this.nome = nome;
		this.quantidade = quantidade;
		
	}
}
