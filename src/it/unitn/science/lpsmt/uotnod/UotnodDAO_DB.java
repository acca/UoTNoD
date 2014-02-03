package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.*;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyOrg;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyAct;
import it.unitn.science.lpsmt.uotnod.plugins.shops.ShopsShop;
import it.unitn.science.lpsmt.uotnod.plugins.shops.ShopsType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UotnodDAO_DB implements UotnodDAO {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;	
	
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
// **** Plugins
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
	public Boolean pluginSetEmpty(Plugin plugin) {
		long insertId = plugin.getId();
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.PLUGIN_COL_EMPTY, 0);
		int row = database.update(SQLiteHelper.TABLE_PLUGIN, values, SQLiteHelper.PLUGIN_COL_ID + " =?", new String[]{""+insertId});		
		if (row >0) return true;
		else return false;
	}
	
	@Override
	public Plugin getPluginById(long id) {	
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN, SQLiteHelper.TABLE_PLUGIN_ALL_COLUMNS, SQLiteHelper.PLUGIN_COL_ID+"=?", new String[]{""+id}, null, null, null);
		cursor.moveToFirst();
		Plugin p = null;
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			p = cursorToPlugin(cursor);			
		}	
		cursor.close();
		return p;
	}
	
	@Override
	public List<Plugin> getAllPlugins(Boolean active) {
		List<Plugin> plugins = new ArrayList<Plugin>();
		String filter = "-1";
		if (active) filter = "0";
		//Cursor cursor = database.rawQuery("select * from "+SQLiteHelper.TABLE_PLUGIN+";", null);
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN, SQLiteHelper.TABLE_PLUGIN_ALL_COLUMNS, SQLiteHelper.PLUGIN_COL_STATUS+">?", new String[]{""+filter}, null, null, null);
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
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN,SQLiteHelper.TABLE_PLUGIN_ALL_COLUMNS,SQLiteHelper.PLUGIN_COL_ID + " =?",new String[]{""+insertId},null,null,null);
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
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN,SQLiteHelper.TABLE_PLUGIN_ALL_COLUMNS,SQLiteHelper.PLUGIN_COL_ID + " =?",new String[]{""+insertId},null,null,null);
		cursor.moveToFirst();
		Plugin p = cursorToPlugin(cursor);
		cursor.close();
		return p;
	}

	@SuppressLint("DefaultLocale")
	public Plugin cursorToPlugin(Cursor cursor) {
		long id = cursor.getLong(0);
		String name = cursor.getString(1);
		String className = cursor.getString(2);
		Boolean status = false;
		if ( cursor.getInt(3) == 1 ) {
			status = true;
		}
		String description = cursor.getString(4);
		String dataSrc = cursor.getString(5);
		Boolean isEmpty = false;
		if ( cursor.getString(6).equals("1")) {
			isEmpty = true;
		}
		Plugin plugin = null;
		try {
			Class<?> pluginClass = null;
			pluginClass = Class.forName("it.unitn.science.lpsmt.uotnod.plugins."+ new String (className).toLowerCase() + "." + className + "Plugin");			
			plugin = (Plugin)pluginClass.getDeclaredConstructor(new Class[] {long.class,String.class,String.class,Boolean.class,String.class,String.class,Boolean.class}).newInstance(id,name,className,status,description,dataSrc,isEmpty);			
			}		
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			return null;			
		}		
		return plugin;
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
// **** Family (Org)
	@Override
	public FamilyOrg insertFamilyOrg(FamilyOrg organization) {
		long insertId = organization.getOrgId();
		FamilyOrg myOrg = getFamilyOrgById(insertId);
		if (myOrg != null){
			//Log.d(MyApplication.DEBUGTAG,"Updating DB, replacing: " + myOrg.toString() + "with: " + organization.toString());
			insertId = database.delete(SQLiteHelper.TABLE_FAMILY_ORG, SQLiteHelper.FAMILY_ORG_COL_ID + " =?",new String[]{""+myOrg.getOrgId()});
			//insertId = database.update(SQLiteHelper.TABLE_FAMILY_ORG, orgToValuesNoId(organization), SQLiteHelper.FAMILY_ORG_COL_ID + " =?", new String[]{""+myOrg.getOrgId()});
		}
		insertId = database.insert(SQLiteHelper.TABLE_FAMILY_ORG, null, orgToValues(organization));
		// Try to insert the activities for this organization
		List<FamilyAct> activities = organization.getFamilyAct();
		Iterator<FamilyAct> iterator = activities.iterator();
		while(iterator.hasNext()){
			FamilyAct activity = iterator.next();
			activity = insertFamilyAct(activity);
			//Log.d(MyApplication.DEBUGTAG,"Inserita: " + activity.toString());
		}
		// Now read from DB the inserted organization and return it
		Cursor cursor = database.query(SQLiteHelper.TABLE_FAMILY_ORG,SQLiteHelper.TABLE_FAMILY_ORG_ALL_COLUMNS,SQLiteHelper.FAMILY_ORG_COL_ID + " =?",new String[]{""+insertId},null,null,null);		
		cursor.moveToFirst();
		FamilyOrg o = cursorToOrg(cursor);
		cursor.close();
		return o;
	}
	
	@Override
	public FamilyOrg getFamilyOrgById(long id) {
		Cursor cursor = database.query(SQLiteHelper.TABLE_FAMILY_ORG,SQLiteHelper.TABLE_FAMILY_ORG_ALL_COLUMNS,SQLiteHelper.FAMILY_ORG_COL_ID + " =?",new String[]{""+id},null,null,null);
		FamilyOrg o = null;		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			o = cursorToOrg(cursor);
			o.setFamilyAct(getFamilyActByOrgId(o.getOrgId()));
			cursor.close();
		}		
		return o;
	}
	
	@Override
	public List<FamilyOrg> getAllFamilyOrgs() {
		List<FamilyOrg> organizations = new ArrayList<FamilyOrg>();
		//Cursor cursor = database.rawQuery("select * from "+SQLiteHelper.TABLE_PLUGIN+";", null);
		Cursor cursor = database.query(SQLiteHelper.TABLE_FAMILY_ORG, SQLiteHelper.TABLE_FAMILY_ORG_ALL_COLUMNS, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			FamilyOrg organization = cursorToOrg(cursor);
			organization.setFamilyAct(getFamilyActByOrgId(organization.getOrgId()));
			organizations.add(organization);
			cursor.moveToNext();
		}
		cursor.close(); // Always remember to close the cursor
		return organizations;
	}

	private FamilyOrg cursorToOrg(Cursor cursor) {
		long id = cursor.getLong(0);
		String name = cursor.getString(1);
		String phone = cursor.getString(2);
		String mobile = cursor.getString(3);
		String website = cursor.getString(4);
		String email = cursor.getString(5);		
		return new FamilyOrg(id, name, phone, mobile, website, email);
	}

	private ContentValues orgToValues(FamilyOrg organization) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.FAMILY_ORG_COL_EMAIL, organization.getEmail());
		values.put(SQLiteHelper.FAMILY_ORG_COL_ID, organization.getOrgId());
		values.put(SQLiteHelper.FAMILY_ORG_COL_MOBILE, organization.getMobile());
		values.put(SQLiteHelper.FAMILY_ORG_COL_NAME, organization.getName());
		values.put(SQLiteHelper.FAMILY_ORG_COL_PHONE, organization.getPhone());
		values.put(SQLiteHelper.FAMILY_ORG_COL_WEBSITE, organization.getWebsite());		
		return values;
	}
		
// **** Family (Act)
	@Override
	public FamilyAct insertFamilyAct(FamilyAct activity) {
		long insertId = activity.getId();
		FamilyAct myAct = getFamilyActById(insertId);
		if (myAct != null){
			//Log.d(MyApplication.DEBUGTAG,"Updating DB, replacing: " + myAct.toString() + "with: " + activity.toString());
			insertId = database.delete(SQLiteHelper.TABLE_FAMILY_ACT, SQLiteHelper.FAMILY_ACT_COL_ID + " =?",new String[]{""+myAct.getId()});
		}		
		insertId = database.insert(SQLiteHelper.TABLE_FAMILY_ACT, null, actToValues(activity));
		Cursor cursor = database.query(SQLiteHelper.TABLE_FAMILY_ACT,SQLiteHelper.TABLE_FAMILY_ACT_ALL_COLUMNS,SQLiteHelper.FAMILY_ACT_COL_ID + " =?",new String[]{""+insertId},null,null,null);		
		cursor.moveToFirst();
		FamilyAct o = cursorToAct(cursor);
		cursor.close();
		return o;
	}
	
	@Override
	public FamilyAct getFamilyActById(long id) {
		Cursor cursor = database.query(SQLiteHelper.TABLE_FAMILY_ACT,SQLiteHelper.TABLE_FAMILY_ACT_ALL_COLUMNS,SQLiteHelper.FAMILY_ACT_COL_ID + " =?",new String[]{""+id},null,null,null);
		FamilyAct o = null;		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			o = cursorToAct(cursor);
			cursor.close();
		}		
		return o;
	}
	
	@Override
	public List<FamilyAct> getFamilyActByOrgId(long actOrgId) {
		List<FamilyAct> activities = new ArrayList<FamilyAct>();
		Cursor cursor = database.query(SQLiteHelper.TABLE_FAMILY_ACT,SQLiteHelper.TABLE_FAMILY_ACT_ALL_COLUMNS,SQLiteHelper.FAMILY_ACT_COL_ORGID + " =?",new String[]{""+actOrgId},null,null,null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				FamilyAct activity = cursorToAct(cursor);
				activities.add(activity);
				cursor.moveToNext();
			}
			cursor.close();			
		}
		return activities;		
	}
	
	@Override
	public List<FamilyAct> getAllFamilyActs() {
		List<FamilyAct> activities = new ArrayList<FamilyAct>();
		Cursor cursor = database.query(SQLiteHelper.TABLE_FAMILY_ACT, SQLiteHelper.TABLE_FAMILY_ACT_ALL_COLUMNS, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			FamilyAct activity = cursorToAct(cursor);
			activities.add(activity);
			cursor.moveToNext();
		}
		cursor.close(); // Always remember to close the cursor
		return activities;
	}
	
	@Override
	public List<String> getAllActTypes() {
		Cursor cursor = database.query(true, SQLiteHelper.TABLE_FAMILY_ACT, new String[] {SQLiteHelper.FAMILY_ACT_COL_TYPE}, null, null, null, null, null, null);
		List<String> types = new ArrayList<String>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();			
			while(!cursor.isAfterLast()){
				types.add(cursor.getString(0));
				cursor.moveToNext();
			}
			cursor.close();			
		}
		return types;
	}
	

	@Override
	public List<FamilyAct> getAllFamilyActByType(String type) {
		List<FamilyAct> activities = new ArrayList<FamilyAct>();
		Cursor cursor = database.query(SQLiteHelper.TABLE_FAMILY_ACT, SQLiteHelper.TABLE_FAMILY_ACT_ALL_COLUMNS,SQLiteHelper.FAMILY_ACT_COL_TYPE + " LIKE?",new String[]{"%"+type+"%"}, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			FamilyAct activity = cursorToAct(cursor);
			activities.add(activity);
			cursor.moveToNext();
		}
		cursor.close(); // Always remember to close the cursor
		return activities;
	}


	private FamilyAct cursorToAct(Cursor cursor) {		
		long actId = cursor.getLong(0);
		long actOrgId = cursor.getLong(1);
		String actName = cursor.getString(2);
		String actType = cursor.getString(3);
		String actDesc = cursor.getString(4);
		String actDateStart = cursor.getString(5);
		String actDateEnd = cursor.getString(6);
		String actFreq = cursor.getString(7);
		String actTimes = cursor.getString(8);
		String actDays = cursor.getString(9);
		String actAddress = cursor.getString(10);
		String actTypeDR = cursor.getString(11);
		String actPriveType = cursor.getString(12);
		String actPrice = cursor.getString(13);
		String actAge = cursor.getString(14);
		String actAgeNotes = cursor.getString(15);
		String actVinRes = cursor.getString(16);
		String actRef = cursor.getString(17);
		String actReg = cursor.getString(18);
		Boolean actFamilyCert = false;		
		if ( cursor.getInt(19) ==  1) {
			actFamilyCert = true;
		}		
		String actInfoLink = cursor.getString(20);		
		return new FamilyAct(actId, actOrgId, actName, actType, actDesc, actDateStart, actDateEnd, actFreq, actTimes, actDays, actAddress, actTypeDR, actPriveType, actPrice, actAge, actAgeNotes, actVinRes, actRef, actReg, actFamilyCert, actInfoLink);
	}

	private ContentValues actToValues(FamilyAct activity) {
		ContentValues values = new ContentValues();		
		values.put(SQLiteHelper.FAMILY_ACT_COL_ID, activity.getId());
		values.put(SQLiteHelper.FAMILY_ACT_COL_ORGID, activity.getOrgId());
		values.put(SQLiteHelper.FAMILY_ACT_COL_NAME, activity.getName());
		values.put(SQLiteHelper.FAMILY_ACT_COL_TYPE, activity.getType());
		values.put(SQLiteHelper.FAMILY_ACT_COL_DESC, activity.getDesc());
		values.put(SQLiteHelper.FAMILY_ACT_COL_DATESTART, activity.getDateStart());
		values.put(SQLiteHelper.FAMILY_ACT_COL_DATEEND, activity.getDateEnd());
		values.put(SQLiteHelper.FAMILY_ACT_COL_FREQ, activity.getFreq());
		values.put(SQLiteHelper.FAMILY_ACT_COL_TIMES, activity.getTimes());
		values.put(SQLiteHelper.FAMILY_ACT_COL_DAYS, activity.getDays());
		values.put(SQLiteHelper.FAMILY_ACT_COL_ADDRESS, activity.getAddress());
		values.put(SQLiteHelper.FAMILY_ACT_COL_TYPEDR, activity.getTypeDR());
		values.put(SQLiteHelper.FAMILY_ACT_COL_PRICETYPE, activity.getPriceType());
		values.put(SQLiteHelper.FAMILY_ACT_COL_PRICE, activity.getPrice());
		values.put(SQLiteHelper.FAMILY_ACT_COL_AGE, activity.getAge());
		values.put(SQLiteHelper.FAMILY_ACT_COL_AGENOTES, activity.getAgeNotes());
		values.put(SQLiteHelper.FAMILY_ACT_COL_VINRES, activity.getVinRes());
		values.put(SQLiteHelper.FAMILY_ACT_COL_REF, activity.getRef());
		values.put(SQLiteHelper.FAMILY_ACT_COL_REG, activity.getReg());
		if (activity.getFamilyCert()) {
			values.put(SQLiteHelper.FAMILY_ACT_COL_FAMILYCERT, 1);
		}
		else {	
			values.put(SQLiteHelper.FAMILY_ACT_COL_FAMILYCERT, 0);
		}
		values.put(SQLiteHelper.FAMILY_ACT_COL_INFOLINK, activity.getInfoLink());
		return values;
	}

	// **** Shops (Sohp info)
	
		@Override
		public ShopsShop insertShopsShop(ShopsShop shop) {
			long insertId = shop.getId();
			ShopsShop myShop = getShopsShopById(insertId);
			if (myShop != null){
				//(MyApplication.DEBUGTAG,"Updating DB, replacing: " + myShop.toString() + "with: " + shop.toString());
				insertId = database.delete(SQLiteHelper.TABLE_SHOPS_INFO, SQLiteHelper.SHOPS_INFO_COL_ID + " =?",new String[]{""+myShop.getId()});
				//insertId = database.update(SQLiteHelper.TABLE_FAMILY_ORG, orgToValuesNoId(shop), SQLiteHelper.FAMILY_ORG_COL_ID + " =?", new String[]{""+myOrg.getOrgId()});
			}
			insertId = database.insert(SQLiteHelper.TABLE_SHOPS_INFO, null, shopToValues(shop));
			// Try to insert the activities for this shop
			List<ShopsType> types = shop.getShopsType();
			Iterator<ShopsType> iterator = types.iterator();
			while(iterator.hasNext()){
				ShopsType type = iterator.next();
				type = insertShopsType(type);
				//Log.d(MyApplication.DEBUGTAG,"Inserito: " + type.toString());
			}
			// Now read from DB the inserted shop and return it
			Cursor cursor = database.query(SQLiteHelper.TABLE_SHOPS_INFO,SQLiteHelper.TABLE_SHOPS_INFO_ALL_COLUMNS,SQLiteHelper.SHOPS_INFO_COL_ID + " =?",new String[]{""+insertId},null,null,null);		
			cursor.moveToFirst();
			ShopsShop o = cursorToShop(cursor);
			cursor.close();
			return o;
		}
		
		@Override
		public ShopsShop getShopsShopById(long id) {
			Cursor cursor = database.query(SQLiteHelper.TABLE_SHOPS_INFO,SQLiteHelper.TABLE_SHOPS_INFO_ALL_COLUMNS,SQLiteHelper.SHOPS_INFO_COL_ID + " =?",new String[]{""+id},null,null,null);
			ShopsShop o = null;		
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				o = cursorToShop(cursor);
				o.setShopsType(getShopsTypeByShopId(o.getId()));
				cursor.close();
			}		
			return o;
		}

		@Override
		public List<ShopsShop> getAllShopsShops() {			
			List<ShopsShop> shops = new ArrayList<ShopsShop>();
			//Cursor cursor = database.rawQuery("select * from "+SQLiteHelper.TABLE_PLUGIN+";", null);
			Cursor cursor = database.query(SQLiteHelper.TABLE_SHOPS_INFO, SQLiteHelper.TABLE_SHOPS_INFO_ALL_COLUMNS, null, null, null, null, null);
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				ShopsShop shop = cursorToShop(cursor);
				shop.setShopsType(getShopsTypeByShopId(shop.getId()));
				shops.add(shop);
				cursor.moveToNext();
			}
			cursor.close(); // Always remember to close the cursor
			return shops;			
		}

		private ShopsShop cursorToShop(Cursor cursor) {
			long id = cursor.getLong(0);
			String name = cursor.getString(1);
			String street = cursor.getString(2);
			long streetId = cursor.getLong(3);
			String streetNum = cursor.getString(4);
			String gpsPoint = cursor.getString(5);		
			return new ShopsShop(id, name, street, streetId, streetNum, gpsPoint);
		}

		private ContentValues shopToValues(ShopsShop shop) {
			ContentValues values = new ContentValues();
			values.put(SQLiteHelper.SHOPS_INFO_COL_ID, shop.getId());
			values.put(SQLiteHelper.SHOPS_INFO_COL_NAME, shop.getName());
			values.put(SQLiteHelper.SHOPS_INFO_COL_STREET, shop.getStreet());
			values.put(SQLiteHelper.SHOPS_INFO_COL_STREETNUM, shop.getStreetNum());
			values.put(SQLiteHelper.SHOPS_INFO_COL_STREETID, shop.getStreetId());
			values.put(SQLiteHelper.SHOPS_INFO_COL_GPSPOINT, shop.getGpsPoint());		
			return values;
		}	


// **** Shops (Shop type)

		@Override
		public ShopsType insertShopsType(ShopsType type) {
			long insertId = type.getId();
			ShopsType myType = getShopsTypeById(insertId);
			if (myType != null){
				//(MyApplication.DEBUGTAG,"Updating DB, replacing: " + myType.toString() + "with: " + type.toString());
				//TODO: invece che cancellare e inserire qui si dovrebbe aggiornare il record, am sembra funzionare anche così - 140111
				insertId = database.delete(SQLiteHelper.TABLE_SHOPS_TYPE, SQLiteHelper.SHOPS_TYPE_COL_ID + " =?",new String[]{""+myType.getId()});
				//insertId = database.update(SQLiteHelper.TABLE_FAMILY_ORG, orgToValuesNoId(type), SQLiteHelper.FAMILY_ORG_COL_ID + " =?", new String[]{""+myOrg.getOrgId()});
			}
			insertId = database.insert(SQLiteHelper.TABLE_SHOPS_TYPE, null, typeToValues(type));
			// Now read from DB the inserted type and return it
			Cursor cursor = database.query(SQLiteHelper.TABLE_SHOPS_TYPE,SQLiteHelper.TABLE_SHOPS_TYPE_ALL_COLUMNS,SQLiteHelper.SHOPS_TYPE_COL_ID + " =?",new String[]{""+insertId},null,null,null);		
			cursor.moveToFirst();
			ShopsType o = cursorToType(cursor);
			cursor.close();
			return o;
		}

		
		@Override
		public List<ShopsType> getShopsTypeByShopId(long typeShopId) {
			List<ShopsType> types = new ArrayList<ShopsType>();
			Cursor cursor = database.query(SQLiteHelper.TABLE_SHOPS_TYPE,SQLiteHelper.TABLE_SHOPS_TYPE_ALL_COLUMNS,SQLiteHelper.SHOPS_TYPE_COL_SHOPID + " =?",new String[]{""+typeShopId},null,null,null);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				while(!cursor.isAfterLast()){
					ShopsType type = cursorToType(cursor);
					types.add(type);
					cursor.moveToNext();
				}
				cursor.close();			
			}
			return types;		
		}
		
		@Override
		public ShopsType getShopsTypeById(long id) {
			Cursor cursor = database.query(SQLiteHelper.TABLE_SHOPS_TYPE,SQLiteHelper.TABLE_SHOPS_TYPE_ALL_COLUMNS,SQLiteHelper.SHOPS_TYPE_COL_ID + " =?",new String[]{""+id},null,null,null);
			ShopsType o = null;		
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				o = cursorToType(cursor);
				cursor.close();
			}		
			return o;
		}
		
		@Override
		public List<String> getAllShopsTypes() {
			Cursor cursor = database.query(true, SQLiteHelper.TABLE_SHOPS_TYPE, new String[] {SQLiteHelper.SHOPS_TYPE_COL_TYPE}, null, null, null, null, null, null);
			List<String> types = new ArrayList<String>();
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();			
				while(!cursor.isAfterLast()){
					types.add(cursor.getString(0).substring(6));
					cursor.moveToNext();
				}
				cursor.close();			
			}
			return types;			
		}

		private ShopsType cursorToType(Cursor cursor) {
			//long id = cursor.getLong(0);
			long shopId = cursor.getLong(1);
			String type = cursor.getString(2);
			return new ShopsType(shopId, type);
		}

		private ContentValues typeToValues(ShopsType type) {
			ContentValues values = new ContentValues();
			//values.put(SQLiteHelper.SHOPS_TYPE_COL_ID, type.getId());
			values.put(SQLiteHelper.SHOPS_TYPE_COL_SHOPID, type.getShopId());
			values.put(SQLiteHelper.SHOPS_TYPE_COL_TYPE, type.getType());		
			return values;
		}

		@Override
		public List<ShopsShop> getAllShopsShopByType(String type) {
			List<ShopsShop> shops = new ArrayList<ShopsShop>();			
			final String MY_QUERY = "SELECT * FROM "+SQLiteHelper.TABLE_SHOPS_INFO+" a INNER JOIN "+SQLiteHelper.TABLE_SHOPS_TYPE+" b ON " +
					"a."+SQLiteHelper.SHOPS_INFO_COL_ID+"=b."+SQLiteHelper.SHOPS_TYPE_COL_SHOPID+" WHERE b."+SQLiteHelper.SHOPS_TYPE_COL_TYPE+" LIKE?";
			Cursor cursor = database.rawQuery(MY_QUERY, new String[]{"%"+type+"%"});
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				ShopsShop shop = cursorToShop(cursor);
				shops.add(shop);
				cursor.moveToNext();
			}
			cursor.close(); // Always remember to close the cursor
			return shops;
			
			
			
		
		}

}
