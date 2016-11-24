package box24.com.box24sdk.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.model.ServiceItem;
import box24.com.box24sdk.model.ServicePic;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.UtilsApp;
import box24.com.box24sdk.utils.VariableWashbox;

public class ActivityWashboxServicePic extends FragmentActivity {

    private TextView tv_name;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_washbox_service_pic);


        ServiceItem BagItem = (ServiceItem) getIntent().getSerializableExtra(
                "model");

         serviceConnection = new ServiceConnection(this);

        asyncJson(BagItem.service_id, BagItem.barcode);

    }

    public void asyncJson(String id, String barcode) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("service_id", id);
        maps.put("barcode", barcode);

        serviceConnection.post(true, VariableWashbox.URL_WASHBOX_SERVICE_PIC, maps, new ServiceConnection.CallBackListener() {
            @Override
            public void callback(String result) {



                List<ServicePic> a = JsonParserWashbox.parseServicePic(ActivityWashboxServicePic.this, result);

                final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
                final ArrayList<Fragment> listFrag = new ArrayList<Fragment>();
                for (int i = 0; i < a.size(); i++) {
                    FragmentIntro frag = new FragmentIntro();
                    Bundle b = new Bundle();
                    b.putSerializable("model", a.get(i));
                    frag.setArguments(b);
                    listFrag.add(frag);
                }
                SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(
                        getSupportFragmentManager(), listFrag);
                mViewPager.setAdapter(mSectionsPagerAdapter);
            }

            @Override
            public void fail(String result) {

            }
        });



    }

    @SuppressLint("ValidFragment")
    private class FragmentIntro extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_service_pic,
                    container, false);
            ServicePic pic = (ServicePic) getArguments().getSerializable(
                    "model");
            // TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            // TextView tvColor = (TextView) view.findViewById(R.id.tv_color);
            ImageView imageView = (ImageView)view.findViewById(R.id.image);
            if (!pic.picture.equals("")) {
//                aq.id(R.id.image).image(UtilsApp.getImageBase64(pic.picture));
                imageView.setImageBitmap(UtilsApp.getImageBase64(pic.picture));
            } else {
//                try {
//                    String query = URLEncoder.encode("https://3.bp.blogspot.com/-W__wiaHUjwI/Vt3Grd8df0I/AAAAAAAAA78/7xqUNj8ujtY/s1600/image02.png", "utf-8");
//                    Picasso.with(getContext())
//                            .load(query)
//                            .into(imageView);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }


//                aq.id(R.id.image).image(pic.picture_path, true, true);
                Picasso.with(getActivity()).load(pic.picture_path).into(imageView);
            }

            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(pic.item_remark);
            // tvName.setText(pic.item_name);
            // tvColor.setText(pic.item_color);

            return view;
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> list;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class
            // below).
            return list.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return list.size();
        }

    }

}
