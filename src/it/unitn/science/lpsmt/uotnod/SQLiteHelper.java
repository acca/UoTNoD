package it.unitn.science.lpsmt.uotnod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper{

	// Shared constants
	public static final String DATABASE_NAME="uotnod.db";
	public static final int DATABASE_VERSION=1;
	
	// Plugins constants
	public static final String TABLE_PLUGIN="plugin";
	public static final String COLUMN_ID="id";
	public static final String COLUMN_NAME="name";
	public static final String COLUMN_LAUNCHER="launcher";
	public static final String COLUMN_DESCRIPTION="description";
	
	// Database creation sql statement
	private static final String DATABASE_CREATE="create table "
				+ TABLE_PLUGIN + "("
				+ COLUMN_ID + " integer primary key autoincrement,"
				+ COLUMN_NAME + " text not null,"
				+ COLUMN_LAUNCHER + " text not null,"
				+ COLUMN_DESCRIPTION + " text not null);";
	
	// Database plugins initialization sql statement
	private static final String PLUGIN_INITIALIZE="insert into "
			+ TABLE_PLUGIN + "("
			+ COLUMN_NAME + ","
			+ COLUMN_LAUNCHER + ","
			+ COLUMN_DESCRIPTION + ")"
			+ " VALUES "
			+ "('Attivitˆ per famiglie','fam_act','family desc'),"
			+ "('Esercizi pubblici','com_act','eser pub desc'),"
			+ "('Meteo','wheater','meteo desc')"
			+ ";";

	
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
