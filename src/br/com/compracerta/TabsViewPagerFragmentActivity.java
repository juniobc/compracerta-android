package br.com.compracerta;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.compracerta.auxiliar.LinhaAdapter;
import br.com.entidade.NotaFiscal;

import br.com.util.ParametrosGlobais;

import br.com.compracerta.auxiliar.Retorno;
import br.com.rede.BuscaCapt;
import br.com.rede.BuscaNfe;
import br.com.entidade.*;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;
  

public class TabsViewPagerFragmentActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener, Retorno {
 
	private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabsViewPagerFragmentActivity.TabInfo>();
    private PagerAdapter mPagerAdapter;
    private Fragment currentFragment;
    private String responseText = null;
    private ListView produtos_nfe;
    
    private BuscaCapt buscaCapt;
    private BuscaNfe buscaNfe;

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
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    					prod.setPreco(Float.parseFloat(jLinha.getString("valor").replace(",", ".")));
    					//prod.setQuantidade(Integer.parseInt(jLinha.getString("quantidade").replace(",0000", "")));
    					
    					produto[i] = prod;
    					
    				}
    				
    				//NotaFiscal nfe = new NotaFiscal(emp, produto, Float.parseFloat(json.getString("vl_total")), json.getString("dt_emis"));
    				NotaFiscal nfe = new NotaFiscal(emp, produto, 5, json.getString("dt_emis"));

    				LinhaAdapter adapter = new LinhaAdapter(currentFragment.getActivity(),R.layout.linha_row,nfe.getProd());         

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
        		
        	}
					
		}catch(JSONException e){
			
			builder.setMessage(e.getMessage())
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   buscarCapt();
	           }
			}).create().show();
			
			//builder.setMessage(e.getMessage()).create().show();
			
		}
		
	}
 
}
