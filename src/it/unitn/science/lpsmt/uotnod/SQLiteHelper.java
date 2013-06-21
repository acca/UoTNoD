package it.unitn.science.lpsmt.uotnod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{

	// Shared constants
	public static final String DATABASE_NAME="uotnod.db";
	public static final int DATABASE_VERSION=2;
	
	// Plugins constants
	public static final String TABLE_PLUGIN="plugin";
	public static final String COLUMN_ID="id";
	public static final String COLUMN_NAME="name";
	public static final String COLUMN_LAUNCHER="launcher";
	public static final String COLUMN_DESCRIPTION="description";
	public static final String COLUMN_STATUS="status";
	
	// Database creation sql statement
	private static final String DATABASE_CREATE="create table "
				+ TABLE_PLUGIN + "("
				+ COLUMN_ID + " integer primary key autoincrement,"
				+ COLUMN_NAME + " text not null,"
				+ COLUMN_LAUNCHER + " text not null,"
				+ COLUMN_STATUS + " integer not null,"
				+ COLUMN_DESCRIPTION + " text not null );";
	
	// Database plugins initialization sql statement
	private static final String PLUGIN_INITIALIZE="insert into "
			+ TABLE_PLUGIN + " ("
			+ COLUMN_NAME + ","
			+ COLUMN_LAUNCHER + ","
			+ COLUMN_STATUS + ","
			+ COLUMN_DESCRIPTION + ")"
			+ " SELECT "
			+ "'Attivita per famiglie'" + " AS " + COLUMN_NAME + ","
			+ "'fam_act'" + " AS " + COLUMN_LAUNCHER + ","
			+ "1" + " AS " + COLUMN_STATUS + ","
			+ "'family desc'" + " AS " + COLUMN_DESCRIPTION
			+ " "
			+ " UNION SELECT "
			+ "'Esercizi pubblici'" + " AS " + COLUMN_NAME + ","
			+ "'com_act'" + " AS " + COLUMN_LAUNCHER + ","
			+ "1" + " AS " + COLUMN_STATUS + ","
			+ "'eser pub desc'" + " AS " + COLUMN_DESCRIPTION
			+ " "
			+ " UNION SELECT "
			+ "'Meteo'" + " AS " + COLUMN_NAME + ","
			+ "'wheater'" + " AS " + COLUMN_LAUNCHER + ","
			+ "1" + " AS " + COLUMN_STATUS + ","
			+ "'meteo desc'" + " AS " + COLUMN_DESCRIPTION
			+ " ";
			
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		db.execSQL(PLUGIN_INITIALIZE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(),"Upgrading database from version "+ oldVersion + " to "
				+ newVersion + ", which will destroy all data");
	
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PLUGIN);
		onCreate(db);		
	}
	
	// METHOD TO REMOVE
	public void logConstants(){
		Log.w("UoTNoD",DATABASE_CREATE);
		Log.w("UoTNoD",PLUGIN_INITIALIZE);
	}

}
