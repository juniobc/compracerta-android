package br.com.entidade;

public class Empresa {
	
	private String razaoSocial;
	private String nmFantasia;
	
	public Empresa(){}
	
	public Empresa(String razao_social, String nm_fantasia){
		
		this.razaoSocial = razao_social;
		this.nmFantasia = nm_fantasia;
		
	}
	
	public String getRazaoSocial(){
		
		return razaoSocial;
		
	}
	
	public String getNmFantasia(){
		
		return nmFantasia;
		
	}

}
