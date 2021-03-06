package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.Plugin;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyOrg;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyAct;
import it.unitn.science.lpsmt.uotnod.plugins.shops.ShopsShop;
import it.unitn.science.lpsmt.uotnod.plugins.shops.ShopsType;

import java.util.List;

public interface UotnodDAO {

	public void open();
	public void close();
	
	public Plugin insertPlugin(Plugin plugin);
	public void deletePlugin(Plugin plugin);
	public Plugin enablePlugin(Plugin plugin);
	public Plugin disablePlugin(Plugin plugin);
	public List<Plugin> getAllPlugins(Boolean active);
	public Boolean pluginSetEmpty(Plugin plugin);
	public Plugin getPluginById(long id);
	
	public FamilyOrg insertFamilyOrg(FamilyOrg organization);
	public List<FamilyOrg> getAllFamilyOrgs();
	public FamilyOrg getFamilyOrgById(long id);
	
	public FamilyAct insertFamilyAct(FamilyAct activity);
	public List<FamilyAct> getAllFamilyActs();
	public FamilyAct getFamilyActById(long id);
	public List<FamilyAct> getFamilyActByOrgId(long actOrgId);
	public List<String> getAllActTypes();
	public List<FamilyAct> getAllFamilyActByType(String type);
	
	public ShopsShop insertShopsShop(ShopsShop shop);
	public List<ShopsShop> getAllShopsShops();
	public ShopsShop getShopsShopById(long id);

	public ShopsType insertShopsType(ShopsType activity);
	public List<String> getAllShopsTypes();
	public ShopsType getShopsTypeById(long id);
	public List<ShopsType> getShopsTypeByShopId(long typeShopId);
	public List<ShopsShop> getAllShopsShopByType(String type);
}