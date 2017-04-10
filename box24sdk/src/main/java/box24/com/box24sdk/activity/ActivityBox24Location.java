package box24.com.box24sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import box24.com.box24sdk.R;
import box24.com.box24sdk.model.LocationBox24;

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

        final TextView btn_next = (TextView) findViewById(R.id.btn_next);


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
                Intent in = new Intent(context, ActivityBox24Drop.class);
                in.putExtra("locationID", locationBox24.location_id);
                in.putExtra("promo", edt_promo_code.getText().toString());
                startActivity(in);

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


}
