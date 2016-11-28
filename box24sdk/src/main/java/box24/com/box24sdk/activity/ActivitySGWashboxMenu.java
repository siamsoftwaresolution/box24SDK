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
import box24.com.box24sdk.utils.SPUtils;

//import com.pheonec.mobbox.washbox.tourguide.FrameLayoutWithHoleApp;

public class ActivitySGWashboxMenu extends Activity {


    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        blind();

        if (!SPUtils.getBoolean(this, "checkBox")) {
            Intent in = new Intent(this, ActivityIntroduce.class);
            startActivity(in);
        }

    }

    void blind() {
        setContentView(R.layout.activity_sg_washbox_menu);
        context = this;

        FrameLayout layoutHeader = (FrameLayout) findViewById(R.id.layout_header);

        if (Settings.Header != 0) {
            View view = LayoutInflater.from(this).inflate(Settings.Header, null);
            layoutHeader.addView(view);

        }
        LinearLayout layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
        if(Settings.ColorMain!=0){
            layoutTitle.setBackgroundColor(Settings.ColorMain);
        }

        TextView btn_send = (TextView) findViewById(R.id.btn_send);
        TextView btn_receive = (TextView) findViewById(R.id.btn_receive);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,
                        ActivitySGWashboxDrop.class));

            }
        });
        btn_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,
                        ActivityWashboxServiceLocker.class));

            }
        });




    }

    public void onBack(View v){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


}
