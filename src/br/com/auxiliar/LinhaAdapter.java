package br.com.auxiliar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.entidade.NotaFiscal;

public class LinhaAdapter extends ArrayAdapter<NotaFiscal> {
	Context context;
	int resource;
	NotaFiscal data[] = null;
	public LinhaAdapter(Context context, int resource, NotaFiscal[] objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		this.data = objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(resource, parent,false);
		}
		/*LinearLayout root = (LinearLayout) convertView.findViewById(R.id.row);
		if((position % 2) == 0){
			root.setBackgroundColor(convertView.getResources().getColor(R.color.white));
		}	
		else{
			root.setBackgroundColor(convertView.getResources().getColor(R.color.cinza));
		}
		NotaFiscal object = data[position];
		
		TextView nm_empresa = (TextView) convertView.findViewById(R.id.nm_empresa);
		nm_empresa.setText(object.nome);

		/*TextView tv_numero = (TextView) convertView.findViewById(R.id.tv_numero);
		tv_numero.setText(object.numero);

		TextView tv_tempo = (TextView) convertView.findViewById(R.id.tv_tempo);
		tv_tempo.setText(String.valueOf(object.cont));*/
		
		return convertView;
	}

}
