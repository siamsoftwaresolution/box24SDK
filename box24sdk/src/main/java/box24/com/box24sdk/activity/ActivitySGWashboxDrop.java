package box24.com.box24sdk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.Settings;
import box24.com.box24sdk.model.Result;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.UtilsApp;
import box24.com.box24sdk.utils.VariableWashbox;


//import com.pheonec.mobbox.washbox.tourguide.FrameLayoutWithHoleApp;

public class ActivitySGWashboxDrop extends Activity {


    private Context context;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serviceConnection = new ServiceConnection(this);
        blind();
    }

    void blind() {
        setContentView(R.layout.activity_sg_washbox_drop);
        context = this;

        final LinearLayout btn_send = (LinearLayout) findViewById(R.id.btn_normal);
        final LinearLayout btn_receive = (LinearLayout) findViewById(R.id.btn_express);
        TextView btn_next = (TextView) findViewById(R.id.btn_next);
        final EditText edt_promo_code = (EditText) findViewById(R.id.edt_promo_code);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(true);
                btn_receive.setActivated(false);
            }
        });
        btn_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(true);
                btn_send.setActivated(false);

            }
        });

        LinearLayout layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
        if(Settings.ColorMain!=0){
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
                if (!btn_send.isActivated() && !btn_receive.isActivated()) {

                    UtilsApp.alerDialog(context, getString(R.string.PleaseSelectTurn));
                    return;
                }


                Map<String, Object> maps = new HashMap<String, Object>();
                maps.put("PromotionCode", edt_promo_code.getText().toString());

                serviceConnection.post(true, VariableWashbox.URL_WASHBOX_QUICKPIN, maps, new ServiceConnection.CallBackListener() {
                    @Override
                    public void callback(String result) {

                        Result aaa = JsonParserWashbox.parseResult(result);
                        if (aaa.Status == 0) {
                            UtilsApp.alerDialog(context, aaa.Message);
                            return;
                        }


                        String a = null;
                        String b = "";
                        try {
                            JSONObject json = new JSONObject(result);
                            a = json.getJSONObject("data")
                                    .getString("QuickPin");
                            b = json.getJSONObject("data")
                                    .getString("QuickPin_Image");

                            Intent in = new Intent(context, ActivitySGWashboxDropQuickPin.class);
                            in.putExtra("promo", a);
                            in.putExtra("image", b);
                            startActivity(in);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void fail(String result) {

                    }
                });


            }
        });

    }
    public void back(View v){
        finish();
    }
}
