package br.com.rede;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.com.compracerta.auxiliar.Retorno;
import br.com.util.ParametrosGlobais;
import br.com.util.TemConexao;

public class GravaChaveAcesso extends AsyncTask<String, Void, String> {
		
	Context context;
	Retorno ret;
	String origem;
	Fragment fragment;
	
	ProgressDialog pd;
	
	public GravaChaveAcesso(Context context, Retorno ret, String origem){
		this.context = context;
		this.ret = ret;
		this.origem = origem;
		
	}
	@Override
	protected String doInBackground(String... arg0) {
		
		String[] params = {
				"https://compreagora-juniobc.c9.io/htdocs/desenvolvimento/public/compracerta/requisicaoajax/gravachaveacesso",
				"chave_acesso;"+arg0[0]
				};
		
		if(TemConexao.ativa(context))
			return FazChamada.execute(params);
		else
			return TemConexao.erro;
	}

	@Override
	protected void onPreExecute() {
		if(origem == ParametrosGlobais.ORIGEM_ACTIVITY){
			pd = new ProgressDialog(context);
			pd.setMessage("Carregando Codigo de Barra...");
			pd.show();
		}
	}
	
	@Override
	protected void onPostExecute(String result) {
		ret.TrataJson(result, "busca_cb");
		if(origem == ParametrosGlobais.ORIGEM_ACTIVITY)
			pd.dismiss();
	}
}