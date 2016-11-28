package box24.com.box24sdk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.picasso.Picasso;

import java.util.EnumMap;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.Settings;

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
        ImageView imageBarcode = (ImageView) findViewById(R.id.image_barcode);

        Bitmap bitmap = null;
        try {
            bitmap = encodeAsBitmap(promo, BarcodeFormat.CODE_128, 1000, 200);
            imageBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        final TextView tv_quick = (TextView) findViewById(R.id.tv_quick);
        final TextView btn_next = (TextView) findViewById(R.id.btn_next);

//        aq.id(imageView).image(image, true, true);
        Picasso.with(this).load(image).into(imageView);

        tv_quick.setText(promo);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ActivitySGWashboxMenu.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
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
    }
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public void back(View v){
        finish();
    }
}
