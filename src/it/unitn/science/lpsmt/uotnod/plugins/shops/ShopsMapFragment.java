package it.unitn.science.lpsmt.uotnod.plugins.shops;

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.plugins.shops.ShopsShop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ShopsMapFragment extends Fragment {
	private GoogleMap googleMap;
	double latitude = 46.514249;
	double longitude = 15.080183;

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
	            
	            
	            /*

	            googleMap.setMyLocationEnabled(true);
	            
	            LatLng startPoint = new LatLng(googleMap.getMyLocation().getLatitude(),googleMap.getMyLocation().getLongitude());
	            
	            
	            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 13));

	            googleMap.addMarker(new MarkerOptions()
	                    .title("Bar via Brennero")
	                    .snippet("Questa `e una decrizione.....")
	                    .position(new LatLng(11.115339126639588,46.090971788278665)));
	            
	            */
	            
	            
	            LatLng sydney = new LatLng(46.090,11.115);

	            googleMap.setMyLocationEnabled(true);
	            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

	            googleMap.addMarker(new MarkerOptions()
	                    .title("Sydney")
	                    .snippet("The most populous city in Australia.")
	                    .position(sydney));
	            	            
	            /*
	            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
	            CameraPosition cameraPosition = new CameraPosition.Builder().target(
	                    new LatLng(latitude, longitude)).zoom(12).build();
	            
	            
	            
	            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	            */
	            
	            
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(getActivity(),
	                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                        .show();
	            }
	       }
	 }
}
