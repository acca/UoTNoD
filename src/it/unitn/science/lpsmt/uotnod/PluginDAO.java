package it.unitn.science.lpsmt.uotnod;

import java.util.List;

public interface PluginDAO {

	public void open();
	public void close();
	
	public Plugin insertPlugin(Plugin plugin);
	public void deletePlugin(Plugin plugin);
	public Plugin enablePlugin(Plugin plugin);
	public Plugin disablePlugin(Plugin plugin);
	public List<Plugin> getAllPlugins();
}
