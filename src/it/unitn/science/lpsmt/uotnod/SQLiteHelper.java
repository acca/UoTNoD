package it.unitn.science.lpsmt.uotnod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{

	// **** Plugins ****
	// Plugins constants
	public static final String TABLE_PLUGIN="plugin";
	public static final String PLUGIN_COL_ID="id";
	public static final String PLUGIN_COL_NAME="name";
	public static final String PLUGIN_COL_LAUNCHER="launcher";
	public static final String PLUGIN_COL_DESCRIPTION="description";
	public static final String PLUGIN_COL_STATUS="status";
	public static final String PLUGIN_COL_DATASRC="datasrc";
	public static final String PLUGIN_COL_EMPTY="empty";
	public static String[] TABLE_PLUGIN_ALL_COLUMNS = { PLUGIN_COL_ID,
		PLUGIN_COL_NAME,PLUGIN_COL_LAUNCHER,
		PLUGIN_COL_STATUS,PLUGIN_COL_DESCRIPTION,
		PLUGIN_COL_DATASRC,PLUGIN_COL_EMPTY };
	
	// Table plugin creation statement
	private static final String PLUGIN_TABLE_CREATE="create table "
			+ TABLE_PLUGIN + "("
			+ PLUGIN_COL_ID + " integer primary key autoincrement,"
			+ PLUGIN_COL_NAME + " text not null,"
			+ PLUGIN_COL_LAUNCHER + " text not null,"
			+ PLUGIN_COL_STATUS + " integer not null,"
			+ PLUGIN_COL_DESCRIPTION + " text not null,"
			+ PLUGIN_COL_DATASRC + " text not null,"
			+ PLUGIN_COL_EMPTY + " integer not null"
			+ ")";
	
	// Table plugins initialization sql statement
	private static final String PLUGIN_TABLE_INITIALIZE="insert into "
			+ TABLE_PLUGIN + " ("
			+ PLUGIN_COL_NAME + ","
			+ PLUGIN_COL_LAUNCHER + ","
			+ PLUGIN_COL_STATUS + ","
			+ PLUGIN_COL_DESCRIPTION + ","
			+ PLUGIN_COL_DATASRC + ","
			+ PLUGIN_COL_EMPTY + ")"
			+ " SELECT "
			+ "'Attività per famiglie'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'Family'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'family desc'" + " AS " + PLUGIN_COL_DESCRIPTION + ","
			+ "'http://dati.trentino.it/storage/f/2013-05-08T083538/Estate-giovani-e-famiglia_2013.xml'" + " AS " + PLUGIN_COL_DATASRC + ","
			+ "1" + " AS " + PLUGIN_COL_EMPTY
			+ " "
			+ " UNION SELECT "
			+ "'Esercizi pubblici'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'Shops'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'eser pub desc'" + " AS " + PLUGIN_COL_DESCRIPTION + ","
			+ "''" + " AS " + PLUGIN_COL_DATASRC + ","
			+ "1" + " AS " + PLUGIN_COL_EMPTY
			+ " "
			+ " UNION SELECT "
			+ "'Meteo'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'Weather'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'meteo desc'" + " AS " + PLUGIN_COL_DESCRIPTION + ","
			+ "''" + " AS " + PLUGIN_COL_DATASRC + ","
			+ "1" + " AS " + PLUGIN_COL_EMPTY
			+ " "
			+ " UNION SELECT "
			+ "'Devel activity'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'DOMParser'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "0" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'Just a starting poin for devel activities'" + " AS " + PLUGIN_COL_DESCRIPTION + ","
			+ "''" + " AS " + PLUGIN_COL_DATASRC + ","
			+ "1" + " AS " + PLUGIN_COL_EMPTY
			+ " ";	
	
	// **** Family plugin ****
	// Organization constants
	public static final String TABLE_FAMILY_ORG="uotnodFamilyOrg";
	public static final String FAMILY_ORG_COL_ID="orgId";
	public static final String FAMILY_ORG_COL_NAME="name";
	public static final String FAMILY_ORG_COL_PHONE="phone";
	public static final String FAMILY_ORG_COL_MOBILE="mobile";
	public static final String FAMILY_ORG_COL_WEBSITE="website";
	public static final String FAMILY_ORG_COL_EMAIL="email";

	// Organization table, creation statement
	private static final String FAMILY_ORG_CREATE="create table "
			+ TABLE_FAMILY_ORG + "("
			+ FAMILY_ORG_COL_ID + " integer primary key,"
			+ FAMILY_ORG_COL_NAME + " text not null,"
			+ FAMILY_ORG_COL_PHONE + " text,"
			+ FAMILY_ORG_COL_MOBILE + " text,"
			+ FAMILY_ORG_COL_WEBSITE + " text,"
			+ FAMILY_ORG_COL_EMAIL + " text );";

	// Activities constants
	public static final String TABLE_FAMILY_ACT="familyAct";
	public static final String FAMILY_ACT_COL_ID="id";
	public static final String FAMILY_ACT_COL_ORGID = "orgId";
	public static final String FAMILY_ACT_COL_NAME = "name";
	public static final String FAMILY_ACT_COL_TYPE = "type";
	public static final String FAMILY_ACT_COL_DESC = "desc";
	public static final String FAMILY_ACT_COL_DATESTART = "dateStart";
	public static final String FAMILY_ACT_COL_DATEEND = "dateEnd";
	public static final String FAMILY_ACT_COL_FREQ = "freq";
	public static final String FAMILY_ACT_COL_TIMES = "times";
	public static final String FAMILY_ACT_COL_DAYS = "days";
	public static final String FAMILY_ACT_COL_ADDRESS = "address";
	public static final String FAMILY_ACT_COL_TYPEDR = "typeDR";
	public static final String FAMILY_ACT_COL_PRICETYPE = "pricetype";
	public static final String FAMILY_ACT_COL_PRICE = "price";
	public static final String FAMILY_ACT_COL_AGE = "age";
	public static final String FAMILY_ACT_COL_AGENOTES = "ageNotes";
	public static final String FAMILY_ACT_COL_VINRES = "vinRes";
	public static final String FAMILY_ACT_COL_REF = "ref";
	public static final String FAMILY_ACT_COL_REG = "reg";
	public static final String FAMILY_ACT_COL_FAMILYCERT = "familyCert";
	public static final String FAMILY_ACT_COL_INFOLINK = "infoLink";
	public static final String[] TABLE_FAMILY_ACT_ALL_COLUMNS = {
		FAMILY_ACT_COL_ID,FAMILY_ACT_COL_ORGID,FAMILY_ACT_COL_NAME,FAMILY_ACT_COL_TYPE,FAMILY_ACT_COL_DESC,
		FAMILY_ACT_COL_DATESTART,FAMILY_ACT_COL_DATEEND,FAMILY_ACT_COL_FREQ,FAMILY_ACT_COL_TIMES,FAMILY_ACT_COL_DAYS,FAMILY_ACT_COL_ADDRESS,
		FAMILY_ACT_COL_TYPEDR,FAMILY_ACT_COL_PRICETYPE,FAMILY_ACT_COL_PRICE,FAMILY_ACT_COL_AGE,FAMILY_ACT_COL_AGENOTES,FAMILY_ACT_COL_VINRES,
		FAMILY_ACT_COL_REF,FAMILY_ACT_COL_REG,FAMILY_ACT_COL_FAMILYCERT,FAMILY_ACT_COL_INFOLINK
	};

	// Activities table, creation statement
	private static final String FAMILY_ACT_CREATE="create table "
			+ TABLE_FAMILY_ACT + "("
			+ FAMILY_ACT_COL_ID + " integer primary key,"
			+ FAMILY_ACT_COL_ORGID + " integer,"
			+ FAMILY_ACT_COL_NAME + " text,"
			+ FAMILY_ACT_COL_TYPE + " text,"
			+ FAMILY_ACT_COL_DESC + " text,"
			+ FAMILY_ACT_COL_DATESTART + " text,"
			+ FAMILY_ACT_COL_DATEEND + " text,"
			+ FAMILY_ACT_COL_FREQ + " text,"
			+ FAMILY_ACT_COL_TIMES + " text,"
			+ FAMILY_ACT_COL_DAYS + " text,"
			+ FAMILY_ACT_COL_ADDRESS + " text,"
			+ FAMILY_ACT_COL_TYPEDR + " text,"
			+ FAMILY_ACT_COL_PRICETYPE + " text,"
			+ FAMILY_ACT_COL_PRICE + " text,"
			+ FAMILY_ACT_COL_AGE + " text,"
			+ FAMILY_ACT_COL_AGENOTES + " text,"
			+ FAMILY_ACT_COL_VINRES + " text,"
			+ FAMILY_ACT_COL_REF + " text,"
			+ FAMILY_ACT_COL_REG + " text,"
			+ FAMILY_ACT_COL_FAMILYCERT + " integer,"
			+ FAMILY_ACT_COL_INFOLINK + " text );";
		
	// **** Shared constants ****
	public static final String DATABASE_NAME="uotnod.db";
	public static final int DATABASE_VERSION = 19;

	// Database creation sql statement
	private static final String DATABASE_CREATE = PLUGIN_TABLE_CREATE		
			+ "\n" + FAMILY_ORG_CREATE
			+ "\n" + FAMILY_ACT_CREATE;

	// Database creation sql statement
	private static final String DATABASE_INITIALIZE = PLUGIN_TABLE_INITIALIZE;

	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(DATABASE_CREATE);
		db.execSQL(PLUGIN_TABLE_CREATE);
		db.execSQL(FAMILY_ORG_CREATE);
		db.execSQL(FAMILY_ACT_CREATE);
		db.execSQL(DATABASE_INITIALIZE);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(),"Upgrading database from version "+ oldVersion + " to "
				+ newVersion + ", which will destroy all data");
	// TODO ciclare per rimuovere tutte le tabelle
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PLUGIN);
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAMILY_ORG);
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAMILY_ACT);
		onCreate(db);		
	}
	
}
