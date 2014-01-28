package it.unitn.science.lpsmt.uotnod.plugins.family;

import java.util.List;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FamilyOrgFragment extends Fragment {
	
	private List<FamilyOrg> orgs;
	private UotnodDAO dao; 
	

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.family_org_fragment, container, false);
        
        
		dao = new UotnodDAO_DB();
		dao.open();
		
		
		this.orgs = dao.getAllFamilyOrgs();
		
		dao.close();
		
		//ArrayAdapter<FamilyOrg> adapter = new ArrayAdapter<FamilyOrg>(this,android.R.layout.simple_list_item_1,this.orgs);
		MyClassAdapter adapter = new MyClassAdapter(rootView.getContext(),R.layout.list_item,this.orgs);
		
		//UotnodFamilyOrg org = new UotnodFamilyOrg();
		
		ListView listView = (ListView) rootView.findViewById(R.id.listview1);
		listView.setAdapter(adapter);
		
//		ListView lv = getListView();


        return rootView;
    }
	
	
	private class MyClassAdapter extends ArrayAdapter<FamilyOrg> {

		Context context;

	    public MyClassAdapter(Context context, int textViewResourceId, List<FamilyOrg> items) {
	        super(context, textViewResourceId, items);
	        this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.list_item, null);
	        }

	        FamilyOrg item = getItem(position);
	        if (item!= null) {
	            // My layout has only one TextView
	            TextView itemTitle = (TextView) view.findViewById(R.id.title);
	            if (itemTitle != null) {
	                // do whatever you want with your string and long
	                itemTitle.setText(item.getName());
	            }
	            TextView itemDesc = (TextView) view.findViewById(R.id.desc);
	            if (itemDesc != null) {
	                // do whatever you want with your string and long
	                itemDesc.setText(item.getWebsite());
	            }
	         }

	        return view;
	    }
	}
}