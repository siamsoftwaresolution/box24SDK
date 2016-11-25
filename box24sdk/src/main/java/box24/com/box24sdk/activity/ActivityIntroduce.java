package box24.com.box24sdk.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import box24.com.box24sdk.R;
import box24.com.box24sdk.fragment.FragmentImage;
import box24.com.box24sdk.utils.SPUtils;

public class ActivityIntroduce extends FragmentActivity {

    int[] imageSG = {R.drawable.sign_up_sg, R.drawable.drop_off_sg, R.drawable.notify,
            R.drawable.pick_up_sg, R.drawable.schedule_sg, 0};
    int[] des = {R.string.Intro1Detail, R.string.Intro2Detail,
            R.string.Intro3Detail, R.string.Intro4Detail, R.string.Intro5Detail, 0};
    int[] title = {R.string.Intro1Header, R.string.Intro2Header,
            R.string.Intro3Header, R.string.Intro4Header, R.string.Intro5Header, 0};

    boolean isSetting = false;
    private ViewPager mViewPager;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        final CirclePageIndicator indicate = (CirclePageIndicator) findViewById(R.id.indicator);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        final ArrayList<Fragment> listFrag = new ArrayList<Fragment>();

        for (int i = 0; i < imageSG.length; i++) {
            FragmentImage frag = new FragmentImage();

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

    }

    @Override
    public void onBackPressed() {

        if(checkBox.isChecked()){
            SPUtils.set(this,"checkBox",true);
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
