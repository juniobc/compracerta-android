package br.com.entidade;

import android.graphics.Bitmap;

public class Produto {
	
	private String cdProduto;
	private int cdNmc;
	private String preco;
	private String nome;
	private int quantidade;
	private float icms;
	private long cdBarra;
	private Bitmap img;
	
	public Produto(){}
	public Produto(String cd_produto, int cd_nmc, long cd_barra, String preco, String nome, int quantidade, float icms, Bitmap img) {
		
		super();
		
		this.cdProduto = cd_produto;
		this.cdNmc = cd_nmc;
		this.cdBarra = cd_barra;
		this.preco = preco;
		this.nome = nome;
		this.quantidade = quantidade;
		this.icms = icms;
		this.img = img;
		
	}
	
	public Bitmap getImg(){
		
		return img;
		
	}	
	
	public String getCdProduto(){
		
		return cdProduto;
		
	}	
	
	public int getCdNmc(){
		
		return cdNmc;
		
	}
	
	public String getPreco(){
		
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
	
	public Bitmap setImg(Bitmap img){
		
		this.img = img;
		
		return img;
		
	}
	
	
	public String setCdProduto(String cdProduto){
		
		this.cdProduto = cdProduto;
		
		return cdProduto;
		
	}	
	
	public int setCdNmc(int cdNmc){
		
		this.cdNmc = cdNmc;
		
		return cdNmc;
		
	}
	
	public String setPreco(String preco){
		
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
