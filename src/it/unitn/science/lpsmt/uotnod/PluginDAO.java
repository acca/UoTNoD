package it.unitn.science.lpsmt.uotnod;

import java.util.List;

public interface PluginDAO {

	public void open();
	public void close();
	
	public Plugin insertPlugin(Plugin person);
	public void deletePlugin(Plugin person);
	public List<Plugin> getAllPlugin();
}
