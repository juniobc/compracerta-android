package br.com.entidade;

public class Produto {
	
	private int cdProduto;
	private int cdNmc;
	private float preco;
	private String nome;
	private int quantidade;
	private float icms;
	private long cdBarra;
	
	public Produto(){}
	public Produto(int cd_produto, int cd_nmc, long cd_barra, float preco, String nome, int quantidade, float icms) {
		
		super();
		
		this.cdProduto = cd_produto;
		this.cdNmc = cd_nmc;
		this.cdBarra = cd_barra;
		this.preco = preco;
		this.nome = nome;
		this.quantidade = quantidade;
		this.icms = icms;
		
	}
	
	public int getCdProduto(){
		
		return cdProduto;
		
	}	
	
	public int getCdNmc(){
		
		return cdNmc;
		
	}
	
	public float getPreco(){
		
		return preco;
		
	}
	
	public String getNome(){
		
		return nome;
		
	}
	
	public int getQuantidade(){
		
		return quantidade;
		
	}
	
	public float getIcms(){
		
		return icms;
		
	}
	
	public long getCdBarra(){
		
		return cdBarra;
		
	}
	
	
	public int setCdProduto(int cdProduto){
		
		this.cdProduto = cdProduto;
		
		return cdProduto;
		
	}	
	
	public int setCdNmc(int cdNmc){
		
		this.cdNmc = cdNmc;
		
		return cdNmc;
		
	}
	
	public float setPreco(float preco){
		
		this.preco = preco;
		
		return preco;
		
	}
	
	public String setNome(String nome){
		
		this.nome = nome;
		
		return nome;
		
	}
	
	public int setQuantidade(int quantidade){
		
		this.quantidade = quantidade;
		
		return quantidade;
		
	}
	
	public float setIcms(float icms){
		
		this.icms= icms; 
		
		return icms;
		
	}
	
	public long setCdBarra(long cdBarra){
		
		this.cdBarra = cdBarra;
		
		return cdBarra;
		
	}
	
	
	
}
