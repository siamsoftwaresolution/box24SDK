package box24.com.box24sdk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.Settings;
import box24.com.box24sdk.model.Locker;
import box24.com.box24sdk.model.Result;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.VariableWashbox;

public class ActivityBox24Locker extends Activity {

	private ListView listview;
	AdapterM adapter;
//	private boolean tracking;
	Context context  ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_washbox_service_locker);
//		tracking = getIntent().getBooleanExtra("tracking", false);
		context = this;
		listview = (ListView) findViewById(R.id.list);
		LinearLayout layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
		if(Settings.ColorMain!=0){
			layoutTitle.setBackgroundColor(Settings.ColorMain);
		}
		FrameLayout layoutHeader = (FrameLayout) findViewById(R.id.layout_header);

		if (Settings.Header != 0) {
			View view = LayoutInflater.from(this).inflate(Settings.Header, null);
			layoutHeader.addView(view);

		}

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub

				Intent in = new Intent(context, ActivityBox24DropQuickPin.class);
				in.putExtra("promo", adapter.getItem(arg2).QP_Pickup);
				in.putExtra("image",  adapter.getItem(arg2).Des);
				in.putExtra("page", 1);
				startActivity(in);

			}
		});

		ServiceConnection serviceConnection = new ServiceConnection(this);


		Map<String, Object> maps = new HashMap<String, Object>();
		serviceConnection.post(true, VariableWashbox.URL_WASHBOX_SERVICE_LOCKER, maps, new ServiceConnection.CallBackListener() {
			@Override
			public void callback(String result) {
				Result result1 = JsonParserWashbox.parseResult(result);

				List<Locker> a = JsonParserWashbox.parseServiceLocker(
						ActivityBox24Locker.this, result);

//
				adapter = new AdapterM(
						ActivityBox24Locker.this);
				adapter.addAll(a);
				listview.setAdapter(adapter);
			}

			@Override
			public void fail(String result) {

			}
		});

	}

	private class AdapterM extends ArrayAdapter<box24.com.box24sdk.model.Locker> {

		public AdapterM(Context context) {
			super(context, R.layout.row_washbox_service_locker, R.id.tv_name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = super.getView(position, convertView, parent);
//			if(tracking){
//				if(position==0){
//					vi.setBackgroundColor(getResources().getColor(R.color.red_alpha));
//				}else{
//					vi.setBackgroundColor(0);
//				}
//			}
			Locker bag = getItem(position);
			TextView tv_name = (TextView) vi.findViewById(R.id.tv_name);
			TextView tv_id = (TextView) vi.findViewById(R.id.tv_id);
			TextView tv_quick = (TextView) vi.findViewById(R.id.tv_quick);
			TextView tv_date = (TextView) vi.findViewById(R.id.tv_date);
			ImageView point = (ImageView) vi.findViewById(R.id.point);

			tv_name.setText(bag.LocaEn);
			tv_quick.setText(bag.QP_Pickup);
			tv_id.setText(bag.LockID);
			tv_date.setText(bag.dateDropToLocker);
			if(bag.statusNumber.equals("1")){
				point.setImageResource(R.drawable.point_yellow2);
			}else if(bag.statusNumber.equals("1")){
				point.setImageResource(R.drawable.point_blue);
			}else {
				point.setImageResource(R.drawable.point_red);
			}


			return vi;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
//		asyncJson();
	}
	public void back(View v){
		finish();
	}
}
