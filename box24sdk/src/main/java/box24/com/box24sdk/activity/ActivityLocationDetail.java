package box24.com.box24sdk.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.Settings;
import box24.com.box24sdk.model.LocationBox24;
import box24.com.box24sdk.utils.SPUtils;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.VariableWashbox;

public class ActivityLocationDetail extends FragmentActivity implements
        OnMapReadyCallback, LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not
    // available.
    LocationBox24 loca;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    Activity context;
    private int locker;
    private BitmapDescriptor pin;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        context = this;
        locker = getIntent().getIntExtra("locker", 0);
        loca = (LocationBox24) getIntent().getSerializableExtra("model");

        LinearLayout layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
        if (Settings.ColorMain != 0) {
            layoutTitle.setBackgroundColor(Settings.ColorMain);
        }
        TextView tv_locker = (TextView) findViewById(R.id.tv_locker);


        tv_locker.setText(loca.location_avilable_locker);
        pin = BitmapDescriptorFactory.fromResource(R.drawable.pin_icon);

        serviceConnection = new ServiceConnection(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the
        // map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

//            if (mMap != null) {

//            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera. In this case, we just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */

    private void setUpMap() {
        Log.i("", "SET UP MAP");
        mMap.setMyLocationEnabled(true);
        mMap.clear();

        MarkerOptions marker = new MarkerOptions();
        marker.title(loca.location_name_for_api_use);
        marker.snippet(loca.location_address_for_api_use);

        marker.icon(pin);
        LatLng lt = new LatLng(loca.latitude, loca.longitude);
        marker.position(lt);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, 14));

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView id = (TextView) findViewById(R.id.tv_name);
        TextView type = (TextView) findViewById(R.id.tv_des);
        TextView phone = (TextView) findViewById(R.id.tv_phone);
        TextView tv_send = (TextView) findViewById(R.id.tv_send);
        final ImageView btn_fav = (ImageView) findViewById(R.id.btn_fav);
        id.setText(loca.location_name_for_api_use);
        type.setText(loca.location_address_for_api_use);
        phone.setText("Tel : " + loca.location_tel_for_api_use);
        phone.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(Intent.ACTION_CALL);
                String uri = "tel:"
                        + loca.location_tel_for_api_use.replaceAll("-", "")
                        .trim();
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });
//		Log.i("", UtilsApp.getMD5EncryptedString("abcdef"));
        try {
            if (loca.location_tel_for_api_use.equals("")) {
                phone.setVisibility(View.GONE);
            } else {
                phone.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            phone.setVisibility(View.GONE);
        }

//        if (locker == 0) {
//            tv_title.setText(getString(R.string.LockerSelectTerminalLocker));
//            tv_send.setText(getString(R.string.SelectTerminalLocker));
//        } else {
//            tv_title.setText(getString(R.string.SelectStartingLocker));
//            tv_send.setText(getString(R.string.SelectStartingLocker));
//        }

        if (loca.fav == 1) {
            btn_fav.setActivated(true);
        } else {
            btn_fav.setActivated(false);
        }

        btn_fav.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (btn_fav.isActivated()) {
                    fav(loca.location_id, 0);
                    btn_fav.setActivated(false);
                    loca.fav = 0;
//                    UtilsApp.delete(loca);
                } else {
                    fav(loca.location_id, 1);
                    btn_fav.setActivated(true);
                    loca.fav = 1;
//                    UtilsApp.add(loca);
                }
            }

        });

        tv_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = SPUtils.getString(context, "receiverLocation");
                if (!a.equals(loca.location_id)) {
                    Intent in = new Intent();
                    in.putExtra("model", loca);
                    setResult(RESULT_OK, in);
                    finish();
                } else {
                    alert();
                }


            }
        });
    }

    void alert() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.Notice))
                .setMessage(getString(R.string.TheStartingAndTerminal))
                .setPositiveButton(getString(R.string.OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }

                        }).show();
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
//        LatLng latLng = new LatLng(location.getLatitude(),
//                location.getLongitude());
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
//                10);
//        mMap.animateCamera(cameraUpdate);
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

    void fav(String id, int fav) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("location_id", id);
        maps.put("Status", fav);
        maps.put("ContactMobile", Settings.PARAM_PHONE);
        serviceConnection.post(true, VariableWashbox.URL_WASHBOX_LOCATION_UPDATE_FAV, maps, new ServiceConnection.CallBackListener() {
            @Override
            public void callback(String result) {

            }

            @Override
            public void fail(String result) {

            }
        });


    }
    public void back(View v) {
        finish();
    }
}
