package it.unitn.science.lpsmt.uotnod;


import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UotnodDAO_DB implements UotnodDAO {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = { SQLiteHelper.PLUGIN_COL_ID,SQLiteHelper.PLUGIN_COL_NAME,SQLiteHelper.PLUGIN_COL_LAUNCHER,SQLiteHelper.PLUGIN_COL_STATUS,SQLiteHelper.PLUGIN_COL_DESCRIPTION };
	
	@Override
	public void open() {
		if (dbHelper == null){
			dbHelper = new SQLiteHelper(MyApplication.getAppContext());
		}
		database = dbHelper.getWritableDatabase();
	}

	@Override
	public void close() {
		dbHelper.close();
		
	}

	@Override
	public Plugin insertPlugin(Plugin person) {		
		Log.w("WARNING", "insertPlugin: Not implemented");
		return null;
	}

	@Override
	public void deletePlugin(Plugin person) {
		Log.w("WARNING", "deletePlugin: Not implemented");
	}

	@Override
	public List<Plugin> getAllPlugins() {
		List<Plugin> plugins = new ArrayList<Plugin>();
		//Cursor cursor = database.rawQuery("select * from "+SQLiteHelper.TABLE_PLUGIN+";", null);
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Plugin plugin = cursorToPlugin(cursor);
			plugins.add(plugin);
			cursor.moveToNext();
		}
		cursor.close(); // Always remember to close the cursor
		return plugins;
	}

	@Override
	public Plugin enablePlugin(Plugin plugin) {		
		plugin.setStatus(true);		
		long insertId = database.update(SQLiteHelper.TABLE_PLUGIN, pluginToValues(plugin), SQLiteHelper.PLUGIN_COL_ID+"=?", new String[]{String.valueOf(plugin.getId())});	
		// Now read from DB the inserted person and return it
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN,allColumns,SQLiteHelper.PLUGIN_COL_ID + " =?",new String[]{""+insertId},null,null,null);
		cursor.moveToFirst();
		Plugin p = cursorToPlugin(cursor);
		cursor.close();
		return p;		
	}

	@Override
	public Plugin disablePlugin(Plugin plugin) {
		plugin.setStatus(false);		
		long insertId = database.update(SQLiteHelper.TABLE_PLUGIN, pluginToValues(plugin), SQLiteHelper.PLUGIN_COL_ID+"=?", new String[]{String.valueOf(plugin.getId())});
		// Now read from DB the inserted person and return it
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN,allColumns,SQLiteHelper.PLUGIN_COL_ID + " =?",new String[]{""+insertId},null,null,null);
		cursor.moveToFirst();
		Plugin p = cursorToPlugin(cursor);
		cursor.close();
		return p;
	}

	public Plugin cursorToPlugin(Cursor cursor) {
		long id = cursor.getLong(0);
		String name = cursor.getString(1);
		String className = cursor.getString(2);
		Boolean status = false;
		if ( cursor.getString(3) == "1" ) {
			status = true;
		}		
		String description = cursor.getString(4);
		return new Plugin(id,name,className,status,description);
	}
	
	public ContentValues pluginToValues(Plugin plugin) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.PLUGIN_COL_NAME, plugin.getName());
		values.put(SQLiteHelper.PLUGIN_COL_ID, plugin.getId());
		Integer status = 0;
		if ( plugin.getStatus() ) {
			status = 1;
		}
		values.put(SQLiteHelper.PLUGIN_COL_STATUS, status);
		return values;
	}

}
