package box24.com.box24sdk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import box24.com.box24sdk.R;

//import com.pheonec.mobbox.washbox.tourguide.FrameLayoutWithHoleApp;

public class ActivitySGWashboxDropQuickPin extends Activity {


    private Context context;
    int lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        blind();

    }

    void blind() {
        setContentView(R.layout.activity_sg_washbox_drop_quickpin);
        context = this;
        String promo = getIntent().getStringExtra("promo");
        String image = getIntent().getStringExtra("image");

        ImageView imageView = (ImageView) findViewById(R.id.image);

        final TextView tv_quick = (TextView) findViewById(R.id.tv_quick);
        final TextView btn_next = (TextView) findViewById(R.id.btn_next);

//        aq.id(imageView).image(image, true, true);
        Picasso.with(this).load(image).into(imageView);

        tv_quick.setText(promo);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ActivitySGWashboxMenu.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });

    }

}
