package box24.com.box24sdk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import box24.com.box24sdk.R;
import box24.com.box24sdk.Settings;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.UtilsApp;


//import com.pheonec.mobbox.washbox.tourguide.FrameLayoutWithHoleApp;

public class ActivityBox24Laundry extends Activity {


    private Context context;
    private ServiceConnection serviceConnection;
//    String promo = "";
    String locationID = "";
String laundry = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        promo = getIntent().getStringExtra("promo");
        locationID = getIntent().getStringExtra("locationID");
        serviceConnection = new ServiceConnection(this);
        blind();
    }

    void blind() {
        setContentView(R.layout.activity_laundry);
        context = this;

        final LinearLayout btn1 = (LinearLayout) findViewById(R.id.btn_1);
        final LinearLayout btn2 = (LinearLayout) findViewById(R.id.btn_2);
        final LinearLayout btn3 = (LinearLayout) findViewById(R.id.btn_3);
        TextView btn_next = (TextView) findViewById(R.id.btn_next);
//        final EditText edt_promo_code = (EditText) findViewById(R.id.edt_promo_code);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(true);
                btn2.setActivated(false);
                btn3.setActivated(false);
                laundry = "L";
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(true);
                btn1.setActivated(false);
                btn3.setActivated(false);
                laundry = "D";

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(true);
                btn1.setActivated(false);
                btn2.setActivated(false);
                laundry = "LD";
            }
        });
        LinearLayout layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
        if (Settings.ColorMain != 0) {
            layoutTitle.setBackgroundColor(Settings.ColorMain);
        }
        FrameLayout layoutHeader = (FrameLayout) findViewById(R.id.layout_header);

        if (Settings.Header != 0) {
            View view = LayoutInflater.from(this).inflate(Settings.Header, null);
            layoutHeader.addView(view);

        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btn1.isActivated() && !btn2.isActivated()&& !btn3.isActivated()) {
                    UtilsApp.alerDialogTitle(context,getString(R.string.Notice),getString(R.string.Pleaseselectmodeofservice));
                    return;
                }


                Intent in = new Intent(context, ActivityBox24Drop.class);
                in.putExtra("locationID", locationID);
//                in.putExtra("promo", promo);
                in.putExtra("laundry", laundry);
                startActivity(in);
            }
        });

    }

    public void back(View v) {
        finish();
    }
}
