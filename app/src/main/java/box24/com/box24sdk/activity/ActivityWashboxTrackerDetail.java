package box24.com.box24sdk.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.model.ServiceBag;
import box24.com.box24sdk.model.ServiceItem;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.TransparentProgressDialog;
import box24.com.box24sdk.utils.UtilsApp;
import box24.com.box24sdk.utils.VariableWashbox;


public class ActivityWashboxTrackerDetail extends Activity {


    Activity context;

    private AdapterM adapter;
    //    private boolean isLoadMore = false;
    private ServiceConnection serviceConnection;
    private TextView tv_total;
    private ListView listview;
    private ServiceBag Bag;
    private TextView tv_address;
    private TextView tv_pickup_time;
    private TextView tv_pickup_date;
    private TextView tv_delivery_time;
    private TextView tv_delivery_date;
    private TextView tv_bag_id;
    private TextView btn_edit;
    private TextView tv_note;
    private TextView btn_cancel;
    private TextView btn_feedback;
    private TextView tv_laundry_note;
//    private View footerView;
//    int pageIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washbox_tracker_detail);
        context = this;

        Bag = (ServiceBag) getIntent().getSerializableExtra("model");
        String id = getIntent().getStringExtra("id");
        String RequestID = getIntent().getStringExtra("RequestID");
        serviceConnection = new ServiceConnection(this);
        if (Bag == null) {
            TransparentProgressDialog pd = new TransparentProgressDialog(this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("service_id", id);
            maps.put("RequestID", RequestID);


            serviceConnection.post(true, VariableWashbox.URL_WASHBOX_SERVICE_ITEM, maps, new ServiceConnection.CallBackListener() {
                @Override
                public void callback(String result) {
                    Bag = JsonParserWashbox.parseServiceBag2(result);
                    blind();
                }

                @Override
                public void fail(String result) {

                }
            });


        } else {
            blind();
        }


    }

    void blind() {


        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_note = (TextView) findViewById(R.id.tv_note);
        tv_laundry_note = (TextView) findViewById(R.id.tv_laundry_note);
        tv_pickup_time = (TextView) findViewById(R.id.tv_pickup_time);
        tv_pickup_date = (TextView) findViewById(R.id.tv_pickup_date);
        tv_delivery_time = (TextView) findViewById(R.id.tv_delivery_time);
        tv_delivery_date = (TextView) findViewById(R.id.tv_delivery_date);
        tv_bag_id = (TextView) findViewById(R.id.tv_bag_id);
        btn_edit = (TextView) findViewById(R.id.btn_edit);
        btn_cancel = (TextView) findViewById(R.id.btn_cancel);
        btn_feedback = (TextView) findViewById(R.id.btn_feedback);

        tv_pickup_time.setText(Bag.start_time_show);
        tv_pickup_date.setText(Bag.start_date_show);
        tv_delivery_time.setText(Bag.end_time_show);
        tv_delivery_date.setText(Bag.end_date_show);
        tv_total.setText(Bag.money_pay);
        tv_bag_id.setText(getString(R.string.Bag) + "# " + Bag.bar_bag);
        tv_address.setText(Bag.address);
        tv_note.setText(Bag.note);
        tv_laundry_note.setText(Bag.laundry_note);
        if (Bag.canEdit == 0) {
            btn_edit.setVisibility(View.INVISIBLE);
        } else {
            btn_edit.setVisibility(View.VISIBLE);
        }



        adapter = new AdapterM(this);
        listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent in = new Intent(context,
                        ActivityWashboxServicePic.class);
                in.putExtra("model", adapter.getItem(arg2));
                startActivity(in);
            }
        });
        asyncJson(Bag.service_id);



        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, ActivityWashboxDialogDateTime.class);
                in.putExtra("date", Bag);
                startActivityForResult(in, 0001);
            }
        });
        if (Bag.isCancel == 0) {
            btn_cancel.setVisibility(View.GONE);
        }else {
            btn_cancel.setVisibility(View.VISIBLE);
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfrim();
            }
        });

//        if(Bag.isFeedback==0){
//            btn_feedback.setVisibility(View.VISIBLE);
//        }else{
//            btn_feedback.setVisibility(View.GONE);
//        }
//        btn_feedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(context, ActivityWashboxFeedback.class);
//                in.putExtra("model", Bag);
//                startActivity(in);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0001) {
            if (resultCode == RESULT_OK) {
//                Bag = (ServiceBag) data.getSerializableExtra("model");

                String date = data.getStringExtra("date");
                String time = data.getStringExtra("time");

//                tv_pickup_time.setText(Bag.start_time_show);
//                tv_pickup_date.setText(Bag.start_date_show);
                tv_delivery_time.setText(time);
                tv_delivery_date.setText(date);
//                tv_total.setText(Bag.money_pay);
//                tv_bag_id.setText(getString(R.string.Bag) + "# " + Bag.bar_bag);
//                tv_address.setText(Bag.address);

            }
        }


    }

    public void asyncJson(String serviceID) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("service_id", serviceID);
        maps.put("RequestID", Bag.RequestID);


        serviceConnection.post(true, VariableWashbox.URL_WASHBOX_SERVICE_ITEM, maps, new ServiceConnection.CallBackListener() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    String image = json.getJSONObject("data").getJSONObject("bag").getJSONObject("closetStep").getString("i6");
                    ImageView imageView = (ImageView)findViewById(R.id.image_step);
                    Picasso.with(context).load(image).into(imageView);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                List<ServiceItem> a = JsonParserWashbox.parseServiceItem(result);


                adapter.addAll(a);
            }

            @Override
            public void fail(String result) {

            }
        });

    }

    private class AdapterM extends ArrayAdapter<ServiceItem> {

        public AdapterM(Context context) {
            super(context, R.layout.row_service_item, R.id.tv_serviceid);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = super.getView(position, convertView, parent);

            ServiceItem bag = getItem(position);
            TextView id = (TextView) vi.findViewById(R.id.tv_serviceid);
            TextView type = (TextView) vi.findViewById(R.id.tv_type);
            TextView price = (TextView) vi.findViewById(R.id.tv_price);
            ImageView point = (ImageView) vi.findViewById(R.id.point);
            id.setText(bag.barcode);
            type.setText(bag.service_name);
            if (bag.money_pay.equals("null")) {
                price.setText("");
            } else {
                price.setText(bag.money_pay);
            }
            if (Bag.service_success.equals("0")) {
                point.setImageResource(R.drawable.point_blue);
            } else {
                point.setImageResource(R.drawable.point_green);
            }
            return vi;
        }

    }

    private void dialogConfrim() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.Doyouwanttoancel))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Confirm), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Map<String, Object> maps = new HashMap<String, Object>();
                        maps.put("RequestID", Bag.RequestID);
                        maps.put("service_id", Bag.service_id);


                        serviceConnection.post(true,VariableWashbox.URL_WASHBOX_CANCEL_ORDER, maps, new ServiceConnection.CallBackListener() {
                            @Override
                            public void callback(String result) {
                                dialogSuccess();
                            }

                            @Override
                            public void fail(String result) {

                            }
                        });
                    }
                })
                .setNegativeButton(getString(R.string.Back), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void dialogSuccess() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.ThisOrderCancel)).setTitle(getString(R.string.Success))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
