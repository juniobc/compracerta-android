package br.com.compracerta.db;

import android.provider.BaseColumns;

public final class TabelasInfo {
	
	public TabelasInfo(){}
	
	public static abstract class EntradaProduto implements BaseColumns{
		
		public static final String TABELA_NOME = "produto";
        public static final String COLUNA_ID = "id";
        public static final String COLUNA_NOME_PRODUTO = "nm_produto";
        public static final String COLUNA_VALOR_PRODUTO = "vl_produto";
        public static final String COLUNA_CODIGO_BARRA = "cd_barra";
        public static final String COLUNA_IMG = "img_produto";
        
        public static final String TEXT_TYPE = " TEXT";
        public static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EntradaProduto.TABELA_NOME + " (" +
            EntradaProduto._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EntradaProduto.COLUNA_NOME_PRODUTO + TEXT_TYPE + COMMA_SEP +
            EntradaProduto.COLUNA_VALOR_PRODUTO + TEXT_TYPE + COMMA_SEP +
            EntradaProduto.COLUNA_CODIGO_BARRA + TEXT_TYPE + COMMA_SEP +
            EntradaProduto.COLUNA_IMG + TEXT_TYPE +
            " )";

        public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EntradaProduto.TABELA_NOME;
		
	}

}
