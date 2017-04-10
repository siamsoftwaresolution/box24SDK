package box24.com.box24sdk.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.activity.ActivityLocation;
import box24.com.box24sdk.activity.ActivityLocationDetail;
import box24.com.box24sdk.model.LocationBox24;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.VariableWashbox;

/**
 * Created by ERROR on 12/25/2014.
 */
public class FragmentLocationList extends Fragment {
    ListView list;
    private List<LocationBox24> listLocationBox24;
    private AdapterM adapter;


    private ActivityLocation.LocaiotnListener mOnEventListener;
    private ServiceConnection serviceConnection;

    public void setOnLocationListener(ActivityLocation.LocaiotnListener listener) {
        mOnEventListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_washbox_info_location_list, container, false);

        final int locker = getArguments().getInt("locker");
        list = (ListView) view.findViewById(R.id.list);
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
//                if (mOnEventListener != null)
//                    mOnEventListener.callback(adapter.getItem(arg2));

                Intent in = new Intent(getActivity(), ActivityLocationDetail.class);
                in.putExtra("model", adapter.getItem(arg2));
                in.putExtra("locker", locker);
                startActivityForResult(in, 0001);


            }
        });

        serviceConnection = new ServiceConnection(getActivity());
        adapter = new AdapterM(getActivity());
        list.setAdapter(adapter);

        asyncJson(ActivityLocation.SEARCH);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 0001) {
                LocationBox24 locationBox24 = (LocationBox24) data.getSerializableExtra("model");
                if (mOnEventListener != null)
                    mOnEventListener.callback(locationBox24);
            }
        }


    }

    public void setKeyword(String keyword) {
        asyncJson(keyword);
    }

    public void asyncJson(String keyword) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("keyword", keyword);
        param.put("isFavorite", 0);

        serviceConnection.post(true, VariableWashbox.URL_WASHBOX_LOCATION_FAV, param, new ServiceConnection.CallBackListener() {
            @Override
            public void callback(String result) {
                listLocationBox24 = JsonParserWashbox.parseMap(result);
                adapter.addAll(listLocationBox24);

            }

            @Override
            public void fail(String result) {

            }
        });
    }

        private class AdapterM extends ArrayAdapter<LocationBox24> {

            public AdapterM(Context context) {
                super(context, R.layout.row_map_list, R.id.tv_name);
                // TODO Auto-generated constructor stub

            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View vi = super.getView(position, convertView, parent);
                LocationBox24 bag = getItem(position);
                TextView id = (TextView) vi.findViewById(R.id.tv_name);
                TextView type = (TextView) vi.findViewById(R.id.tv_des);
                TextView phone = (TextView) vi.findViewById(R.id.tv_phone);
//            TextView tv_status = (TextView) vi.findViewById(R.id.tv_status);
                TextView tv_locker = (TextView) vi.findViewById(R.id.tv_locker);


//            tv_status.setText(bag.location_avilable_status);

                tv_locker.setText(bag.location_avilable_locker);
                id.setText(bag.location_name_for_api_use);
                type.setText(bag.location_address_for_api_use);
                phone.setText("Tel : " + bag.location_tel_for_api_use);
                if (bag.location_tel_for_api_use.equals("")) {
                    phone.setVisibility(View.GONE);
                } else {
                    phone.setVisibility(View.VISIBLE);
                }
                return vi;
            }

        }
    }
