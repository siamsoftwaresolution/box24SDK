package box24.com.box24sdk.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.fragment.FragmentImage;
import box24.com.box24sdk.model.ServiceItem;
import box24.com.box24sdk.model.ServicePic;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.VariableWashbox;

public class ActivityWashboxServicePic extends FragmentActivity {


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
                    FragmentImage frag = new FragmentImage();
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
