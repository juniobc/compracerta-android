package br.com.compracerta.auxiliar;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.compracerta.R;
import br.com.compracerta.db.*;
import br.com.entidade.Produto;

public class ListaCodigoBarra extends ArrayAdapter<Produto> {
	Context context;
	int resource;
	Produto data[] = null;
	public ListaCodigoBarra(Context context, int resource, Produto[] object) {
		super(context, resource, object);
		this.context = context;
		this.resource = resource;
		this.data = object;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(resource, parent,false);
		}
		LinearLayout root = (LinearLayout) convertView.findViewById(R.id.row);
		if((position % 2) == 0){
			root.setBackgroundColor(convertView.getResources().getColor(R.color.white));
		}	
		else{
			root.setBackgroundColor(convertView.getResources().getColor(R.color.white));
		}
		
		Produto object = data[position];
		
		if(object.getImg() != null){
		
			ImageView img = (ImageView) convertView.findViewById(R.id.img_produto);
			
			img.setImageBitmap(object.getImg());
		
		}
		
		TextView nm_produto = (TextView) convertView.findViewById(R.id.nm_produto2);
		nm_produto.setText(object.getNome());
		
		TextView vl_produto = (TextView) convertView.findViewById(R.id.vl_produto2);
		vl_produto.setText(String.valueOf(object.getPreco()));

		/*TextView tv_numero = (TextView) convertView.findViewById(R.id.tv_numero);
		tv_numero.setText(object.numero);

		TextView tv_tempo = (TextView) convertView.findViewById(R.id.tv_tempo);
		tv_tempo.setText(String.valueOf(object.cont));*/
		
		return convertView;
	}

}
