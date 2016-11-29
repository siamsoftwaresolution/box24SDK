package box24.com.box24sdk.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import box24.com.box24sdk.R;
import box24.com.box24sdk.Settings;
import box24.com.box24sdk.fragment.FragmentIntro;
import box24.com.box24sdk.utils.SPUtils;

public class ActivityBox24Introduce extends FragmentActivity {

    int[] imageSG = {R.drawable.drop_off_sg, R.drawable.notify_india,
            R.drawable.pick_up_india, R.drawable.schedule_india};
    int[] des = {R.string.Intro2Detail,
            R.string.Intro3Detail, R.string.Intro4Detail, R.string.Intro5Detail};
    int[] title = {R.string.Intro2Header,
            R.string.Intro3Header, R.string.Intro4Header, R.string.Intro5Header};

    boolean isSetting = false;
    private ViewPager mViewPager;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        FrameLayout layoutHeader = (FrameLayout) findViewById(R.id.layout_header);
        if (Settings.Header != 0) {
            View view = LayoutInflater.from(this).inflate(Settings.Header, null);
            layoutHeader.addView(view);

        }
        ImageView btnClose = (ImageView) findViewById(R.id.btn_close);
        final CirclePageIndicator indicate = (CirclePageIndicator) findViewById(R.id.indicator);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        indicate.setFillColor(Settings.ColorAccent);

        final ArrayList<Fragment> listFrag = new ArrayList<Fragment>();

        for (int i = 0; i < imageSG.length; i++) {
            FragmentIntro frag = new FragmentIntro();

            Bundle b = new Bundle();
            b.putInt("title", title[i]);
            b.putInt("image", imageSG[i]);
            b.putInt("des", des[i]);
            frag.setArguments(b);
            listFrag.add(frag);
        }

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager(), listFrag);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        indicate.setViewPager(mViewPager);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (checkBox.isChecked()) {
            SPUtils.set(this, "checkBox", true);
        }

        super.onBackPressed();

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
            if (isSetting) {
                return list.size() - 1;
            } else {
                return list.size();
            }
        }


    }
}
