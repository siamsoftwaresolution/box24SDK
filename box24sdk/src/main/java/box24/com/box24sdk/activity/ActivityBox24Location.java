package box24.com.box24sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.Settings;
import box24.com.box24sdk.model.LocationBox24;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.VariableWashbox;

/**
 * Created by ERROR on 12/25/2014.
 */
public class ActivityBox24Location extends Activity {


    //	boolean person;
    Activity context;

    private LocationBox24 locationBox24;
    private TextView tv_locker;
    private EditText edt_promo_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_box24_location);
        context = this;

        ServiceConnection serviceConnection = new ServiceConnection(this);

        final TextView btn_next = (TextView) findViewById(R.id.btn_next);
        LinearLayout layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
        if (Settings.ColorMain != 0) {
            layoutTitle.setBackgroundColor(Settings.ColorMain);
        }
        FrameLayout layoutHeader = (FrameLayout) findViewById(R.id.layout_header);

        if (Settings.Header != 0) {
            View view = LayoutInflater.from(this).inflate(Settings.Header, null);
            layoutHeader.addView(view);

        }

        edt_promo_code = (EditText) findViewById(R.id.edt_promo_code);
        tv_locker = (TextView) findViewById(R.id.tv_locker);


//        List<LocationBox24> aa = LocationBox24.listAll(LocationBox24.class);
//        if (!aa.isEmpty()) {
//            locationBox24 = aa.get(0);
//            tv_locker.setText(aa.get(0).location_name_for_api_use);
//        }

        tv_locker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ActivityLocation.class);
                in.putExtra("locker", 0);
                startActivityForResult(in, 0001);
            }
        });

//        laundyNotes = LaundyNote.listAll(LaundyNote.class);

        btn_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (locationBox24 == null || locationBox24.location_id == null) {
                    return;
                }
                Intent in = new Intent(context, ActivityBox24Laundry.class);
                in.putExtra("locationID", locationBox24.location_id);
                in.putExtra("promo", edt_promo_code.getText().toString());
                startActivity(in);

            }
        });

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("keyword", "");
        param.put("isFavorite", 1);
        param.put("ContactMobile", Settings.PARAM_PHONE);
//		param.put("LanguageID", UtilsApp.getLanguageID(getActivity()));

        serviceConnection.post(false, VariableWashbox.URL_WASHBOX_LOCATION_FAV, param, new ServiceConnection.CallBackListener() {
            @Override
            public void callback(String result) {
                final List<LocationBox24> listLocationBox24 = JsonParserWashbox.parseMap(result);
                if (!listLocationBox24.isEmpty()) {
                    locationBox24 = listLocationBox24.get(0);
                    tv_locker.setText(listLocationBox24.get(0).location_name_for_api_use);
                } else {
                    locationBox24 = null;
                    tv_locker.setText("");
                }

                tv_locker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(context, ActivityLocation.class);
                        in.putExtra("locker", 0);
                        if (listLocationBox24.isEmpty()) {
                            in.putExtra("isFav", false);
                        } else {
                            in.putExtra("isFav", true);
                        }
                        startActivityForResult(in, 0001);
                    }
                });
            }

            @Override
            public void fail(String result) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 0001:
                    locationBox24 = (LocationBox24) data.getSerializableExtra("model");
                    tv_locker.setText(locationBox24.location_name_for_api_use);
                    break;

            }

        }
    }

    public void back(View v) {
        finish();
    }
}
