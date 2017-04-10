package box24.com.box24sdk.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.activity.ActivityLocation;
import box24.com.box24sdk.activity.ActivityLocationDetail;
import box24.com.box24sdk.model.LocationBox24;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.UtilsApp;
import box24.com.box24sdk.utils.VariableWashbox;

/**
 * Created by ERROR on 12/25/2014.
 */
public class FragmentLocationMap extends Fragment implements
        OnMapReadyCallback, LocationListener {
	private GoogleMap mMap;
	List<LocationBox24> listLocationBox24;
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;

	private ActivityLocation.LocaiotnListener mOnEventListener;
	private int locker;
//	private int app;
	private BitmapDescriptor pin;
	private ServiceConnection serviceConnection;

	public void setOnLocationListener(ActivityLocation.LocaiotnListener listener) {
		mOnEventListener = listener;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(
				R.layout.fragment_washbox_info_location_map, container, false);
		locker = getArguments().getInt("locker");

		 serviceConnection = new ServiceConnection(getActivity());

		asyncJson(ActivityLocation.SEARCH);

		return view;
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		// Try to obtain the map from the SupportMapFragment.
		FragmentManager myFM = this.getChildFragmentManager();
		try {
			SupportMapFragment mapFragment = (SupportMapFragment) myFM
					.findFragmentById(R.id.map);
			mapFragment.getMapAsync(this);
		} catch (Exception e) {
//			SupportMapFragment mapFragment = (SupportMapFragment) getActivity()
//					.getSupportFragmentManager().findFragmentById(R.id.map);
//			mapFragment.getMapAsync(this);
			e.printStackTrace();
		}

		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the
	 * camera. In this case, we just add a marker near Africa.
	 * <p/>
	 * This should only be called once and when we are sure that {@link #mMap}
	 * is not null.
	 */
//	public void asyncJson(String keyword) {
//		TransparentProgressDialog pd = new TransparentProgressDialog(
//				getActivity());
//		// String url = String.format(Variable.URL_WASHBOX_INFO_MAP, keyword);
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("keyword", keyword);
//		param.put("LanguageID", UtilsApp.getLanguageID(getActivity()));
//		aq.progress(pd).ajax(VariableWashbox.URL_WASHBOX_INFO_MAP, param,
//				JSONObject.class, new AjaxCallback<JSONObject>() {
//
//					@Override
//					public void callback(String url, JSONObject json,
//										 AjaxStatus status) {
//
//						if (json != null) {
//							listLocationBox24 = JsonParserWashbox.parseMap(
//									json);
//
//							setUpMapIfNeeded();
//
//						} else {
//
//							Toast.makeText(aq.getContext(),
//									"Error:" + status.getCode(),
//									Toast.LENGTH_LONG).show();
//						}
//					}
//				});
//
//	}
	public void asyncJson(String keyword) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("keyword", keyword);
		param.put("isFavorite", 0);

		serviceConnection.post(true, VariableWashbox.URL_WASHBOX_LOCATION_FAV, param, new ServiceConnection.CallBackListener() {
			@Override
			public void callback(String result) {
				listLocationBox24 = JsonParserWashbox.parseMap(result);


				setUpMapIfNeeded();
			}

			@Override
			public void fail(String result) {

			}
		});


	}

	private void setUpMap() {
		Log.i("", "SET UP MAP");
		mMap.setMyLocationEnabled(true);
		mMap.clear();
		for (int i = 0; i < listLocationBox24.size(); i++) {
			MarkerOptions marker = new MarkerOptions();
			marker.title(listLocationBox24.get(i).location_name_for_api_use);
			marker.snippet(listLocationBox24.get(i).location_address_for_api_use);

			marker.icon(pin);
			LatLng lt = new LatLng(listLocationBox24.get(i).latitude,
					listLocationBox24.get(i).longitude);
			marker.position(lt);
			mMap.addMarker(marker);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, 14));
		}
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub

				for (int i = 0; i < listLocationBox24.size(); i++) {

					LatLng lt = new LatLng(listLocationBox24.get(i).latitude,
							listLocationBox24.get(i).longitude);
					if (lt.equals(arg0.getPosition())) {

						Intent in = new Intent(getActivity(), ActivityLocationDetail.class);
						in.putExtra("model", listLocationBox24.get(i));
						in.putExtra("locker", locker);
						startActivityForResult(in, 0001);

						break;
					}
				}
			}
		});

	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 0001) {
				LocationBox24 locationBox24 = (LocationBox24) data.getSerializableExtra("model");
				if (mOnEventListener != null)
					mOnEventListener.callback(locationBox24);
			}
		}

	}
	@Override
	public void onMapReady(GoogleMap arg0) {
		// TODO Auto-generated method stub
		mMap = arg0;

		setUpMap();

	}

	@Override
	public void onLocationChanged(android.location.Location location) {
		// TODO Auto-generated method stub
		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
				13);
		mMap.animateCamera(cameraUpdate);
		locationManager.removeUpdates(this);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
