package br.com.compracerta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.compracerta.auxiliar.*;
import br.com.entidade.NotaFiscal;

import br.com.util.ParametrosGlobais;

import br.com.compracerta.auxiliar.Retorno;
import br.com.rede.*;
import br.com.entidade.*;
import br.com.compracerta.db.*;

import android.accounts.NetworkErrorException;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;
import android.widget.Toast;
  

public class TabsViewPagerFragmentActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener, Retorno {
 
	private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabsViewPagerFragmentActivity.TabInfo>();
    private PagerAdapter mPagerAdapter;
    private Fragment currentFragment;
    private String responseText = null;
    private ListView produtos_nfe;
    private ListView produtos_cb;
    
    private BuscaCapt buscaCapt;
    private BuscaNfe buscaNfe;
    private BuscaCB buscaCB;
    private Long codigoBarra;

    private class TabInfo {
         private String tag;
         private Class<?> clss;
         private Bundle args;
         private Fragment fragment;
         TabInfo(String tag, Class<?> clazz, Bundle args) {
             this.tag = tag;
             this.clss = clazz;
             this.args = args;
         }
 
    }

    class TabFactory implements TabContentFactory {
 
        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
 
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }

        this.intialiseViewPager();
		
    }
    
    public void buscarNfe(View view){
    	
    	currentFragment = mPagerAdapter.getItem(mViewPager.getCurrentItem());
    	
    	produtos_nfe = (ListView) currentFragment.getView().findViewById(R.id.produtos_nfe);
    	
    	EditText captcha = (EditText) currentFragment.getView().findViewById(R.id.cd_capt);
    	EditText chave_acesso = (EditText) currentFragment.getView().findViewById(R.id.chave_acesso);
    	TextView view_state = (TextView) currentFragment.getView().findViewById(R.id.view_state);
		TextView event_validation = (TextView) currentFragment.getView().findViewById(R.id.event_validation);
		TextView token = (TextView) currentFragment.getView().findViewById(R.id.token);
		
		buscaNfe = new BuscaNfe(TabsViewPagerFragmentActivity.this, TabsViewPagerFragmentActivity.this, ParametrosGlobais.ORIGEM_ACTIVITY);
		buscaNfe.execute(new String[]{captcha.getText().toString().toUpperCase(), chave_acesso.getText().toString(), 
				view_state.getText().toString(),event_validation.getText().toString(), token.getText().toString()});
    	
    }
    
    public void buscarCodigoBarra(View view){
    	
    	currentFragment = mPagerAdapter.getItem(mViewPager.getCurrentItem());
    	produtos_cb = (ListView) currentFragment.getView().findViewById(R.id.produtos_cb);
    	
    	Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
		intent.setPackage(getPackageName());
		intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE"); 
		startActivityForResult(intent, 0);
    	
    	//buscaCB = new BuscaCB(TabsViewPagerFragmentActivity.this, TabsViewPagerFragmentActivity.this, ParametrosGlobais.ORIGEM_ACTIVITY);
		//buscaCB.execute(new String[]{"7896098900253"});
		//buscaCB.execute(new String[]{"tv"});
    	
    }
    
    /*public void onActivityResult(int requestCode, int resultCode, Intent intent){
		
		if(requestCode == 0){
			
			if(resultCode == RESULT_OK){
				
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				Log.i("xZing", "contents: "+contents+" format: "+format);
				
				this.codigoBarra = Long.parseLong(contents);
				
				buscaCB = new BuscaCB(TabsViewPagerFragmentActivity.this, TabsViewPagerFragmentActivity.this, ParametrosGlobais.ORIGEM_ACTIVITY);
				buscaCB.execute(new String[]{contents});
				
			}else if(resultCode == RESULT_CANCELED){
				
				Log.i("xZing", "Cancelled");
				
			}
			
		}
		
	}*/
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
		
		if(requestCode == 0){
			
			if(resultCode == RESULT_OK){
				
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				Log.i("xZing", "contents: "+contents+" format: "+format);
				
				this.codigoBarra = Long.parseLong(contents);
				
				buscaCB = new BuscaCB(TabsViewPagerFragmentActivity.this, TabsViewPagerFragmentActivity.this, ParametrosGlobais.ORIGEM_ACTIVITY);
				buscaCB.execute(new String[]{contents});
				
			}else if(resultCode == RESULT_CANCELED){
				
				Log.i("xZing", "Cancelled");
				
			}
			
		}
		
	}
    
    public void buscarCapt(){

    	buscaCapt = new BuscaCapt(TabsViewPagerFragmentActivity.this, TabsViewPagerFragmentActivity.this, ParametrosGlobais.ORIGEM_ACTIVITY);
    	buscaCapt.execute();
		
    } 

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    private void intialiseViewPager() {
 
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, Tab1Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab2Fragment.class.getName()));
        //fragments.add(Fragment.instantiate(this, Tab3Fragment.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);

        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    private void initialiseTabHost(Bundle args) {
    	
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        
        View tabview = createTabView(mTabHost.getContext(), 0);
        View tabview2 = createTabView(mTabHost.getContext(), 1);
        View tabview3 = createTabView(mTabHost.getContext(), 2);
        
        TabInfo tabInfo = null;
        
        TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, 
        		this.mTabHost.newTabSpec("Tab1").setIndicator(tabview), 
        		( tabInfo = new TabInfo("Tab1", Tab1Fragment.class, args)));        
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        
        TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, 
        		this.mTabHost.newTabSpec("Tab2").setIndicator(tabview2), 
        		( tabInfo = new TabInfo("Tab2", Tab2Fragment.class, args)));        
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        
        /*TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, 
        		this.mTabHost.newTabSpec("Tab3").setIndicator(tabview3), 
        		( tabInfo = new TabInfo("Tab3", Tab3Fragment.class, args)));        
        this.mapTabInfo.put(tabInfo.tag, tabInfo);*/
        
        // Default to first tab
        //this.onTabChanged("Tab1");
        //
        mTabHost.setOnTabChangedListener(this);
    }

    
    private static View createTabView(final Context context, final int tab) {
    	
	    View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
	    //ImageButton img_btn = (ImageButton) view.findViewById(R.id.img_tab);
	    TextView txt_aba = (TextView) view.findViewById(R.id.txt_aba);
	    
	    switch(tab){
	    
		    case 0:
		    	//img_btn.setImageResource(R.drawable.consulta);
		    	txt_aba.setText("Consulta");
		    	break;
		    	
		    case 1:
		    	//img_btn.setImageResource(R.drawable.cadastro);
		    	txt_aba.setText("Cadastro");
		    	break;
	    
	    }

	    return view;
	}

 
    private static void AddTab(TabsViewPagerFragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {

        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }


    public void onTabChanged(String tag) {
        //TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }
 
    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */
    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {
        // TODO Auto-generated method stub
 
    }
 
    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);
        
        if(position==1){
        	
        	buscarCapt();
        	
        }
        
    }
 
    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub
 
    }

	@Override
	public void TrataJson(String str, String tp_consulta) {
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		currentFragment = mPagerAdapter.getItem(mViewPager.getCurrentItem());
		
		try{
			
			if(tp_consulta == "busca_capt"){	
				
				JSONObject dados;
				
				JSONObject json = new JSONObject(str);
				dados = json.getJSONObject("dados");
				
				ImageView img = (ImageView) currentFragment.getView().findViewById(R.id.img_capt);
				TextView view_state = (TextView) currentFragment.getView().findViewById(R.id.view_state);
				TextView event_validation = (TextView) currentFragment.getView().findViewById(R.id.event_validation);
				TextView token = (TextView) currentFragment.getView().findViewById(R.id.token);
	        	
	        	byte[] decodedString = Base64.decode(dados.getString("img"), Base64.DEFAULT);
	        	Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
	        	img.setImageBitmap(decodedByte);
	        	
	        	view_state.setText(dados.getString("viewState"));
	        	event_validation.setText(dados.getString("eventValidation"));
	        	token.setText(dados.getString("token"));
	        		        	
        	}else if(tp_consulta == "busca_nfe"){
        		
        		JSONObject json = new JSONObject(str);
    			if(json.getInt("ret") == 0){
    				
    				JSONObject empresa = new JSONObject(str);
    				empresa = json.getJSONObject("empresa");
    				
    				//JSONObject produto = new JSONObject(str);
    				//produto = json.getJSONObject("produtos");
    				    				
    				Empresa emp = new Empresa(empresa.getString("razao_social"), empresa.getString("nm_fantasia"));
    				
    				JSONObject prod_array = json.getJSONObject("produtos");
    				Produto[] produto = new Produto[prod_array.length()];
    				
    				for(int i=0;i<prod_array.length();i++){
    					Produto prod = new Produto();    					
    					JSONObject jLinha = prod_array.getJSONObject(""+(i+1)+"");
    					
    					prod.setNome(jLinha.getString("nome"));
    					prod.setCdBarra(Long.parseLong(jLinha.getString("cd_barra")));
    					prod.setCdNmc(Integer.parseInt(jLinha.getString("cd_nmc")));
    					//prod.setCdProduto(Integer.parseInt(jLinha.getString("cd_produto")));
    					//prod.setIcms(Float.parseFloat(jLinha.getString("icms").replace(",", ".")));
    					//prod.setPreco(Float.parseFloat(jLinha.getString("valor").replace(",", ".")));
    					prod.setPreco(jLinha.getString("valor").replace(",", "."));
    					//prod.setQuantidade(Integer.parseInt(jLinha.getString("quantidade").replace(",0000", "")));
    					    					
    					produto[i] = prod;
    					
    				}
    				
    				//NotaFiscal nfe = new NotaFiscal(emp, produto, Float.parseFloat(json.getString("vl_total")), json.getString("dt_emis"));
    				NotaFiscal nfe = new NotaFiscal(emp, produto, 5, json.getString("dt_emis"));

    				ListaNfe adapter = new ListaNfe(currentFragment.getActivity(),R.layout.lista_nfe,nfe.getProd());         

    				produtos_nfe.setAdapter(adapter);
    				
    				//builder.setMessage(produto[1].getNome()).create().show();
    				
    			}else{
    				
    				builder.setMessage("Código incorreto !")
    				.setCancelable(false)
    				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   buscarCapt();
			           }
    				}).create().show();
    				
    				//builder.setMessage("Código incorreto !").create().show();	
    				
    				
    				
    			}
        		
        	}else if(tp_consulta == "busca_cb"){
        		
        		final JSONObject json = new JSONObject(str);
        		
        		//builder.setMessage().create().show();
        		
        		final Handler handler = new Handler();
        		
        		Thread th = new Thread(new Runnable() {
        			
        			public void run() {
        				
        				final Produto[] produto = new Produto[json.length()];                		
                		Bitmap bitmap;                		
                		URL url = null; 
    		            InputStream content = null;
    		            
    		            try{
    		            	
    		            	BancoDadosHelper dbHelper = new BancoDadosHelper(currentFragment.getActivity());    		            	
    		            	SQLiteDatabase db = dbHelper.getWritableDatabase();
    		        		ContentValues values = new ContentValues();
                		
	                		for(int i=0;i<json.length();i++){
	                			
	                			final Produto prod = new Produto();
	                			
	                			url = new URL(json.getJSONObject(Integer.toString(i+1)).getString("img_produto"));
	    		                final Bitmap mIcon1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
	    		                
	    		                handler.post(new Runnable() {

        		                    public void run() {
        		                    	prod.setImg(getResizedBitmap(mIcon1, 55, 55));
        		                    }
        		                });
	    		                
	    		                values.put(TabelasInfo.EntradaProduto.COLUNA_NOME_PRODUTO, json.getJSONObject(Integer.toString(i+1)).getString("nm_produto"));
	    		                values.put(TabelasInfo.EntradaProduto.COLUNA_VALOR_PRODUTO, json.getJSONObject(Integer.toString(i+1)).getString("preco_medio"));
	    		                values.put(TabelasInfo.EntradaProduto.COLUNA_IMG, BitMapToString(getResizedBitmap(mIcon1, 55, 55)));
	    		                values.put(TabelasInfo.EntradaProduto.COLUNA_CODIGO_BARRA, codigoBarra);
	    		                
	    		                prod.setNome(json.getJSONObject(Integer.toString(i+1)).getString("nm_produto"));        			
	    	        			//prod.setPreco(Float.parseFloat(json.getJSONObject(Integer.toString(i+1)).getString("preco_medio").replace(",", ".")));
	    	        			prod.setPreco(json.getJSONObject(Integer.toString(i+1)).getString("preco_medio"));
	    	        			prod.setCdBarra(codigoBarra);
	    	        			
	    	        			produto[i] = prod;
	                			
	                		}
	                		
	                		Cursor cursor2 = db.rawQuery("select * from "+TabelasInfo.EntradaProduto.TABELA_NOME+" where "
	    	                    	+TabelasInfo.EntradaProduto.COLUNA_CODIGO_BARRA+" = " + codigoBarra,null);
	                		
	                		if(cursor2.getCount() == 0){
	                			
	                			db.insert(TabelasInfo.EntradaProduto.TABELA_NOME, null, values);
	                			
	                		}
	                    	
	                    	Cursor cursor = db.rawQuery("select * from "+TabelasInfo.EntradaProduto.TABELA_NOME+" where "
	                    	+TabelasInfo.EntradaProduto.COLUNA_CODIGO_BARRA+" != " + codigoBarra,null);
	                    	
	                    	cursor.moveToFirst();

	                    	int i=0;
	                    	
	                    	final Produto[] produto2 = new Produto[cursor.getCount()];
	                    	
	                    	for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
	                    		
	                    		final Produto prod = new Produto();

	                    	    DatabaseUtils.dumpCurrentRowToString(cursor);
	                    	    String nome = cursor.getString(cursor.getColumnIndex(TabelasInfo.EntradaProduto.COLUNA_NOME_PRODUTO));
	                    	    
	                    	    prod.setNome(nome); 
	                    	    
	                    	    DatabaseUtils.dumpCurrentRowToString(cursor);
	                    	    String valor = cursor.getString(cursor.getColumnIndex(TabelasInfo.EntradaProduto.COLUNA_VALOR_PRODUTO));
	                    	    
	                    	    prod.setPreco(valor);
	                    	    
	                    	    DatabaseUtils.dumpCurrentRowToString(cursor);
	                    	    String img = cursor.getString(cursor.getColumnIndex(TabelasInfo.EntradaProduto.COLUNA_IMG));
                    			
	                    	    prod.setImg(StringToBitMap(img));
	                    	    
	                    	    produto2[i] = prod;

	                    	}
	                    	
	                    	int aLen = produto.length;
	                    	int bLen = produto2.length;
	                    	final Produto[] all_produto= new Produto[aLen+bLen];
	                    	System.arraycopy(produto, 0, all_produto, 0, aLen);
	                    	System.arraycopy(produto2, 0, all_produto, aLen, bLen);
	                    		                		
	                		currentFragment.getActivity().runOnUiThread(new Runnable() {
	                            @Override
	                            public void run() {
	                                // This code will always run on the UI thread, therefore is safe to modify UI elements.
	                            	ListaCodigoBarra adapter = new ListaCodigoBarra(currentFragment.getActivity(),R.layout.lista_cd_barra,all_produto);         
	    	                		
	    	        	    		produtos_cb.setAdapter(adapter);
	                            }
	                        });	                		
                		
    		            }catch(JSONException e){
    		            	// TODO Auto-generated catch block
    		            	builder.setMessage(e.getMessage()).create().show();
    		            } catch (MalformedURLException e) {
							// TODO Auto-generated catch block
    		            	builder.setMessage(e.getMessage()).create().show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							builder.setMessage(e.getMessage()).create().show();
						}
        				
        			}
        			
        		});
        		
        		th.start();
				
			}
					
		}catch(JSONException e){
			
			if(tp_consulta != "busca_cb"){
				builder.setMessage(e.getMessage())
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   buscarCapt();
		           }
				}).create().show();
			}else{
				
				builder.setMessage(e.getMessage()).create().show();
				
			}
			
		}
		
	}
	
	public Bitmap StringToBitMap(String encodedString){
	     try{
	       byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
	       Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
	       return bitmap;
	     }catch(Exception e){
	       e.getMessage();
	       return null;
	     }
	}
	
	public String BitMapToString(Bitmap bitmap){
	        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
	        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
	        byte [] b=baos.toByteArray();
	        String temp=Base64.encodeToString(b, Base64.DEFAULT);
	        return temp;
	  }
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth){
		
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;

	    Matrix matrix = new Matrix();

	    matrix.postScale(scaleWidth, scaleHeight);

	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	    
	}
 
}
