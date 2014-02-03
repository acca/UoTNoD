package it.unitn.science.lpsmt.uotnod.plugins.shops;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.plugins.shops.ShopsShop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ShopsMapFragment extends Fragment {
	private GoogleMap googleMap;
	private HashMap<Marker, ShopsShop> shopMarkerMap;
	private UotnodDAO dao;

	//private ShopAdapter adapter;

	List<ShopsShop> shops;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View myFragmentView = inflater.inflate(R.layout.shops_map_fragment, container, false);
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			Log.e("ERROR", "ERROR IN CODE: " + e.toString());
			e.printStackTrace();
		}
		return myFragmentView;
	}

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getActivity(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
			
			googleMap.setMyLocationEnabled(true);

			// Trento
			LatLng startPoint = new LatLng(46.06736,11.12085);

			googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition position) {
					drawMarkers();
				}
			});
			
						
			googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				
				@Override
				public void onInfoWindowClick(Marker arg0) {
					Intent i = new Intent(MyApplication.SHOPSPLUGINPKG + "ShopsShopView");
					long shopId = shopMarkerMap.get(arg0).getId();
					i.putExtra("index", (int)shopId);
					startActivity(i);
					Log.d(MyApplication.DEBUGTAG, "Sono qui");					
				}
			});

			googleMap.setMyLocationEnabled(true);
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 17));

			this.shopMarkerMap = new HashMap<Marker, ShopsShop>(); 
			
			getShops();
			drawMarkers();
		}
	}
	
	
	private void getShops(){
		this.dao = new UotnodDAO_DB();
		this.dao.open();		
		this.shops = dao.getAllShopsShops();		
	}

	private void drawMarkers(){

		final LatLngBounds screenBounds = googleMap.getProjection().getVisibleRegion().latLngBounds;

		Iterator<ShopsShop> i = this.shops.iterator();
		while(i.hasNext()){
			ShopsShop s = i.next();
			
			if (screenBounds.contains(s.getPoint())) {
				// Retrieve services for shop
				String services = "";
				Iterator<ShopsType> ii = s.getShopsType().iterator();
				while(ii.hasNext()){
					services += "- " + ((ShopsType)ii.next()).getTypeFormatted() + "\n";
				}				
				// Create marker and draw marker
				Marker m = googleMap.addMarker(new MarkerOptions()
				.title(s.getName())
				.snippet(s.getStreet() + "\n" + services)
				.position(s.getPoint()));
				
				// Create map link between shop and marker
				shopMarkerMap.put(m, s);								           
			}
		}

	}

	@Override
	public void onDestroy() {	
		super.onDestroy();
		this.dao.close();
	}
}
