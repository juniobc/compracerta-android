package br.com.entidade;

public class NotaFiscal {
	
	private Empresa emp;
	private Produto[] prod;
	private float vl_nfe;
	private String dt_emis;
	
	public NotaFiscal(){}
	
	public NotaFiscal(Empresa emp, Produto[] prod, float vl_nfe, String dt_emis){
		
		this.emp = emp;
		this.prod = prod;
		this.vl_nfe = vl_nfe;
		this.dt_emis = dt_emis;
		
	}

}
