package it.unitn.science.lpsmt.uotnod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{

	
	// Plugins constants
	public static final String TABLE_PLUGIN="plugin";
	public static final String PLUGIN_COL_ID="id";
	public static final String PLUGIN_COL_NAME="name";
	public static final String PLUGIN_COL_LAUNCHER="launcher";
	public static final String PLUGIN_COL_DESCRIPTION="description";
	public static final String PLUGIN_COL_STATUS="status";
	
	// Table plugin creation statement
	private static final String PLUGIN_TABLE_CREATE="create table "
			+ TABLE_PLUGIN + "("
			+ PLUGIN_COL_ID + " integer primary key autoincrement,"
			+ PLUGIN_COL_NAME + " text not null,"
			+ PLUGIN_COL_LAUNCHER + " text not null,"
			+ PLUGIN_COL_STATUS + " integer not null,"
			+ PLUGIN_COL_DESCRIPTION + " text not null );";
		
	// Table plugins initialization sql statement
	private static final String PLUGIN_TABLE_INITIALIZE="insert into "
			+ TABLE_PLUGIN + " ("
			+ PLUGIN_COL_NAME + ","
			+ PLUGIN_COL_LAUNCHER + ","
			+ PLUGIN_COL_STATUS + ","
			+ PLUGIN_COL_DESCRIPTION + ")"
			+ " SELECT "
			+ "'Attivitˆ per famiglie'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'UotnodFamily'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'family desc'" + " AS " + PLUGIN_COL_DESCRIPTION
			+ " "
			+ " UNION SELECT "
			+ "'Esercizi pubblici'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'UotnodShops'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'eser pub desc'" + " AS " + PLUGIN_COL_DESCRIPTION
			+ " "
			+ " UNION SELECT "
			+ "'Meteo'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'UotnodWheater'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'meteo desc'" + " AS " + PLUGIN_COL_DESCRIPTION
			+ " ";
	
	// Uotnod family plugin constants
	// Organization (Uotnod family plugin) constants
	public static final String TABLE_UOTNODFAMILIY_ORG="outnodFamilyOrg";
	public static final String UOTNODFAMILIY_ORG_COL_ID="orgId";
	public static final String UOTNODFAMILIY_ORG_COL_NAME="name";
	public static final String UOTNODFAMILIY_ORG_COL_PHONE="phone";
	public static final String UOTNODFAMILIY_ORG_COL_MOBILE="mobile";
	public static final String UOTNODFAMILIY_ORG_COL_WEBSITE="website";
	public static final String UOTNODFAMILIY_ORG_COL_EMAIL="email";
	
	// Table organization (Uotnod family plugin) creation statement
		private static final String UOTNODFAMILIY_ORG_CREATE="create table "
				+ TABLE_UOTNODFAMILIY_ORG + "("
				+ UOTNODFAMILIY_ORG_COL_ID + " integer primary key,"
				+ UOTNODFAMILIY_ORG_COL_NAME + " text not null,"
				+ UOTNODFAMILIY_ORG_COL_PHONE + " text,"
				+ UOTNODFAMILIY_ORG_COL_MOBILE + " text,"
				+ UOTNODFAMILIY_ORG_COL_WEBSITE + " text,"
				+ UOTNODFAMILIY_ORG_COL_EMAIL + " text );";
			
		// Table plugins initialization sql statement
		/*private static final String UOTNODFAMILIY_ORG_INITIALIZE="insert into "
				+ TABLE_PLUGIN + " ("
				+ PLUGIN_COL_NAME + ","
				+ PLUGIN_COL_LAUNCHER + ","
				+ PLUGIN_COL_STATUS + ","
				+ PLUGIN_COL_DESCRIPTION + ")"
				+ " SELECT "
				+ "'Attivitˆ per famiglie'" + " AS " + PLUGIN_COL_NAME + ","
				+ "'UotnodFamily'" + " AS " + PLUGIN_COL_LAUNCHER + ","
				+ "1" + " AS " + PLUGIN_COL_STATUS + ","
				+ "'family desc'" + " AS " + PLUGIN_COL_DESCRIPTION
				+ " "
				+ " UNION SELECT "
				+ "'Esercizi pubblici'" + " AS " + PLUGIN_COL_NAME + ","
				+ "'UotnodShops'" + " AS " + PLUGIN_COL_LAUNCHER + ","
				+ "1" + " AS " + PLUGIN_COL_STATUS + ","
				+ "'eser pub desc'" + " AS " + PLUGIN_COL_DESCRIPTION
				+ " "
				+ " UNION SELECT "
				+ "'Meteo'" + " AS " + PLUGIN_COL_NAME + ","
				+ "'UotnodWheater'" + " AS " + PLUGIN_COL_LAUNCHER + ","
				+ "1" + " AS " + PLUGIN_COL_STATUS + ","
				+ "'meteo desc'" + " AS " + PLUGIN_COL_DESCRIPTION
				+ " ";
		*/
	
	// Shared constants
	public static final String DATABASE_NAME="uotnod.db";
	public static final int DATABASE_VERSION = 5;

	// Database creation sql statement
	private static final String DATABASE_CREATE = PLUGIN_TABLE_CREATE
			+ UOTNODFAMILIY_ORG_CREATE;

	// Database creation sql statement
	private static final String DATABASE_INITIALIZE = PLUGIN_TABLE_INITIALIZE;

	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		db.execSQL(DATABASE_INITIALIZE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(),"Upgrading database from version "+ oldVersion + " to "
				+ newVersion + ", which will destroy all data");
	// TODO ciclare per rimuovere tutte le tabelle
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PLUGIN);
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_UOTNODFAMILIY_ORG);		
		onCreate(db);		
	}
	
}
