package br.com.entidade;

public class NotaFiscal {
	
	private Empresa emp;
	private Produto[] prod;
	private float valorNfe;
	private String dtEmis;
	
	public NotaFiscal(){}
	
	public NotaFiscal(Empresa emp, Produto[] prod, float vl_nfe, String dt_emis){
		
		this.emp = emp;
		this.prod = prod;
		this.valorNfe = vl_nfe;
		this.dtEmis = dt_emis;
		
	}
	
	public Empresa getEmp() {
		return emp;
	}

	public void setEmp(Empresa emp) {
		this.emp = emp;
	}

	public Produto[] getProd() {
		return prod;
	}

	public void setProd(Produto[] prod) {
		this.prod = prod;
	}

	public float getValorNfe() {
		return valorNfe;
	}

	public void setValorNfe(float valorNfe) {
		this.valorNfe = valorNfe;
	}

	public String getDtEmis() {
		return dtEmis;
	}

	public void setDt_emis(String dtEmis) {
		this.dtEmis = dtEmis;
	}
	
	

}
