package it.unitn.science.lpsmt.uotnod;



import it.unitn.science.lpsmt.uotnod.plugins.Entry;
import it.unitn.science.lpsmt.uotnod.plugins.Plugin;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyOrg;

import java.util.List;

public interface UotnodDAO {

	public void open();
	public void close();
	
	public Plugin insertPlugin(Plugin plugin);
	public void deletePlugin(Plugin plugin);
	public Plugin enablePlugin(Plugin plugin);
	public Plugin disablePlugin(Plugin plugin);
	public List<Plugin> getAllPlugins(Boolean active);
	
	public FamilyOrg insertFamilyOrg(FamilyOrg organization);
	public List<FamilyOrg> getAllFamilyOrgs();
	public FamilyOrg getFamilyOrgById(long id);
	public Boolean pluginSetEmpty(Plugin plugin);
}
