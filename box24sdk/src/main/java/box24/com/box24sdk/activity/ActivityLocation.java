package box24.com.box24sdk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import box24.com.box24sdk.R;
import box24.com.box24sdk.Settings;
import box24.com.box24sdk.fragment.FragmentLocationFavorite;
import box24.com.box24sdk.fragment.FragmentLocationList;
import box24.com.box24sdk.fragment.FragmentLocationMap;
import box24.com.box24sdk.model.LocationBox24;

/**
 * Created by ERROR on 12/25/2014.
 */
public class ActivityLocation extends FragmentActivity {


    // private MyAdapter mAdapter;
    // private ViewPager mPager;

    private FragmentLocationList fragmentList;
    private FragmentLocationFavorite fragmentFav;
    private FragmentLocationMap fragmentMap;
    int possition = 1;

    private EditText edtSearch;
    public static String SEARCH = "";
    private LinearLayout btnFav;
    private LinearLayout btnList;
    private LinearLayout btnMap;
    private View line1A;
    private View line2A;
    private View line3A;

    public interface LocaiotnListener {
        // you can define any parameter as per your requirement
        public void callback(LocationBox24 result);
    }

    Context context;
    boolean isFav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_location);
        context = this;
        // Variable.ListLocation.clear();
        int locker = getIntent().getIntExtra("locker", 0);
        isFav = getIntent().getBooleanExtra("isFav", false);

        LinearLayout layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
        if (Settings.ColorMain != 0) {
            layoutTitle.setBackgroundColor(Settings.ColorMain);
        }


        SEARCH = "";
        // mPager = (ViewPager) findViewById(R.id.pager);
        // mPager.requestTransparentRegion(mPager);
        // mAdapter = new MyAdapter(getSupportFragmentManager());
        // mPager.setAdapter(mAdapter);
        edtSearch = (EditText) findViewById(R.id.edt_search);
        fragmentFav = new FragmentLocationFavorite();
        fragmentList = new FragmentLocationList();
        fragmentMap = new FragmentLocationMap();
        Bundle b = new Bundle();
        b.putInt("locker", locker);


        fragmentFav.setArguments(b);
        fragmentList.setArguments(b);
        fragmentMap.setArguments(b);


        final ImageView btnSearch = (ImageView) findViewById(R.id.btn_search);
        final ImageView btnClear = (ImageView) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                edtSearch.setText("");
                if (possition == 0) {
                    fragmentFav.asyncJson("");
                } else if (possition == 1) {
                    fragmentList.asyncJson("");
                } else {
                    fragmentMap.asyncJson("");
                }
                SEARCH = edtSearch.getText().toString();
            }
        });
        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (!edtSearch.getText().toString().equals("")) {
                    // asyncJson(edtSearch.getText().toString());
                    if (possition == 0) {
                        fragmentFav.asyncJson(edtSearch.getText().toString());
                    } else if (possition == 1) {
                        fragmentList.asyncJson(edtSearch.getText().toString());
                    } else {
                        fragmentMap.asyncJson(edtSearch.getText().toString());
                    }

                    SEARCH = edtSearch.getText().toString();
                }

            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (possition == 0) {
                        fragmentFav.asyncJson(edtSearch.getText().toString());
                    } else if (possition == 1) {
                        fragmentList.asyncJson(edtSearch.getText().toString());
                    } else {
                        fragmentMap.asyncJson(edtSearch.getText().toString());
                    }
                    SEARCH = edtSearch.getText().toString();
                    return true;
                }
                return false;
            }
        });
        // asyncJson("");
        // mPager.setCurrentItem(1);
        // def();
        // aq.id(R.id.line1).visible();

        btnFav = (LinearLayout) findViewById(R.id.btn_fav);
        btnList = (LinearLayout) findViewById(R.id.btn_list);
        btnMap = (LinearLayout) findViewById(R.id.btn_map);
        line1A = findViewById(R.id.line0);
        line2A = findViewById(R.id.line1);
        line3A = findViewById(R.id.line2);
        btnFav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                def();
                line1A.setVisibility(View.VISIBLE);
                replaceFragment(fragmentFav);
                possition = 0;
            }
        });
        btnList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                def();
                line2A.setVisibility(View.VISIBLE);
                replaceFragment(fragmentList);
                possition = 1;
            }
        });
        btnMap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                def();
                line3A.setVisibility(View.VISIBLE);
                replaceFragment(fragmentMap);
                possition = 2;
            }
        });


        if (!isFav) {
            def();
            line2A.setVisibility(View.VISIBLE);
            replaceFragment(fragmentList);
            possition = 1;
        } else {
            def();
            line1A.setVisibility(View.VISIBLE);
            replaceFragment(fragmentFav);
            possition = 0;
        }

        fragmentList.setOnLocationListener(new LocaiotnListener() {
            @Override
            public void callback(LocationBox24 result) {
                Intent in = new Intent();
                in.putExtra("model", result);
                setResult(RESULT_OK, in);
                finish();
            }
        });
        fragmentFav.setOnLocationListener(new LocaiotnListener() {
            @Override
            public void callback(LocationBox24 result) {
                Intent in = new Intent();
                in.putExtra("model", result);
                setResult(RESULT_OK, in);
                finish();
            }
        });
        fragmentMap.setOnLocationListener(new LocaiotnListener() {
            @Override
            public void callback(LocationBox24 result) {
                Intent in = new Intent();
                in.putExtra("model", result);
                setResult(RESULT_OK, in);
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (possition == 0) {
            fragmentFav.asyncJson("");
        }
    }

    private void replaceFragment(Fragment frag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();

        fragmentTransaction.replace(R.id.frame, frag);
        // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    // public void asyncJson(String keyword) {
    // TransparentProgressDialog pd = new TransparentProgressDialog(this);
    // // String url = String.format(Variable.URL_WASHBOX_INFO_MAP, keyword);
    // Map<String, String> param = new HashMap<String, String>();
    // param.put("keyword", keyword);
    //
    // aq.progress(pd).ajax(Variable.URL_WASHBOX_INFO_MAP, param,
    // JSONObject.class, new AjaxCallback<JSONObject>() {
    //
    // @Override
    // public void callback(String url, JSONObject json,
    // AjaxStatus status) {
    //
    // if (json != null) {
    // Variable.ListLocation = JsonParser.parseMap(
    // ActivityWashboxRequestPin.this, json);
    //
    //
    //
    // } else {
    //
    // Toast.makeText(aq.getContext(),
    // "Error:" + status.getCode(),
    // Toast.LENGTH_LONG).show();
    // }
    // }
    // });
    //
    // }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return fragmentFav;
                case 1:
                    return fragmentList;
                case 2:
                    return fragmentMap;

                default:
                    return null;
            }
        }
    }

    void def() {

        line1A.setVisibility(View.INVISIBLE);
        line2A.setVisibility(View.INVISIBLE);
        line3A.setVisibility(View.INVISIBLE);

    }

    public void back(View v) {
        finish();
    }
}
