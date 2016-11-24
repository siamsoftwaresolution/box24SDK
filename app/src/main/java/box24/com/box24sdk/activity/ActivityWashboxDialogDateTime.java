package box24.com.box24sdk.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import box24.com.box24sdk.R;
import box24.com.box24sdk.model.PeriodTime;
import box24.com.box24sdk.model.PickUpDate;
import box24.com.box24sdk.model.ServiceBag;
import box24.com.box24sdk.utils.JsonParserWashbox;
import box24.com.box24sdk.utils.ServiceConnection;
import box24.com.box24sdk.utils.UtilsApp;
import box24.com.box24sdk.utils.VariableWashbox;


public class ActivityWashboxDialogDateTime extends FragmentActivity {


    private AdapterSpinnerTime adapterSpinnerTime1;

    Context context;
    private Spinner spn_delivery_date;
    private Spinner spn_delivery_time;
    private ServiceConnection serviceConnection;
    private AdapterSpinnerDate adapterSpinnerDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_time);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);
        getWindow().setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        context = this;

        final ServiceBag Bag = (ServiceBag) getIntent().getSerializableExtra("date");
        serviceConnection = new ServiceConnection(this);
        adapterSpinnerTime1 = new AdapterSpinnerTime(this);
        adapterSpinnerDate = new AdapterSpinnerDate(this);
        spn_delivery_time = (Spinner) findViewById(R.id.tv_delivery_time);
        spn_delivery_date = (Spinner) findViewById(R.id.tv_delivery_date);
        TextView btn_ok = (TextView) findViewById(R.id.btn_ok);
        TextView btn_cancel = (TextView) findViewById(R.id.btn_cancel);
        getDeliveryDate(Bag.RequestID);
        spn_delivery_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                getDeliveryTime(Bag.RequestID, adapterSpinnerDate.getItem(i).dateVal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PeriodTime periodTime = (PeriodTime) spn_delivery_time.getSelectedItem();
                if (periodTime.Period_Str.contains("full")) {
//                    Toast.makeText(context, "Period time full", Toast.LENGTH_SHORT).show();
                    UtilsApp.alerDialog(context,"Period time full");
                } else {

                    new AlertDialog.Builder(context).setTitle(getString(R.string.Notice))
                            .setMessage(getString(R.string.AfterConfirmation))
                            .setPositiveButton(getString(R.string.Confirm), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    final PickUpDate pickUpDate = (PickUpDate) spn_delivery_date.getSelectedItem();
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("service_id", Bag.service_id);
                                    map.put("RequestID", Bag.RequestID);
                                    map.put("delivery_date", pickUpDate.dateVal);
                                    map.put("delivery_time_id", periodTime.TimeID);


                                    serviceConnection.post(true, VariableWashbox.URL_WASHBOX_UPDATE_TIME, map, new ServiceConnection.CallBackListener() {
                                        @Override
                                        public void callback(String result) {

                                            dialogSuccess(pickUpDate,periodTime);
                                        }

                                        @Override
                                        public void fail(String result) {

                                        }
                                    });


                                }
                            })
                            .setNegativeButton(getString(R.string.Back), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .show();

                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void dialogSuccess(final PickUpDate pickUpDate, final PeriodTime periodTime) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.DeliveryTimeHasBeenUpdated)).setTitle(getString(R.string.Success))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent in = getIntent();
                        in.putExtra("date", pickUpDate.dateStr);
                        in.putExtra("time", periodTime.Period_Str);
                        setResult(RESULT_OK, in);
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    void getDeliveryDate(final String time) {
        Map<String, Object> map = new HashMap<>();
        map.put("RequestID", time);


        serviceConnection.post(true, VariableWashbox.URL_WASHBOX_GET_DELIVERY_DATE_UPDATE, map, new ServiceConnection.CallBackListener() {
            @Override
            public void callback(String result) {

                adapterSpinnerDate.clear();
                adapterSpinnerDate.addAll(JsonParserWashbox.parsePickupDate(result));
                spn_delivery_date.setAdapter(adapterSpinnerDate);
//                getDeliveryTime(date, time, adapterSpinnerDeliveryDate.getItem(0).dateVal);
                getDeliveryTime(time, adapterSpinnerDate.getItem(0).dateVal);
            }

            @Override
            public void fail(String result) {

            }
        });
    }

    void getDeliveryTime(String request, String dateDelivery) {
        Map<String, Object> map = new HashMap<>();
        map.put("RequestID", request);
        map.put("deliveryDate", dateDelivery);

        serviceConnection.post(true, VariableWashbox.URL_WASHBOX_GET_DELIVERY_TIME_UPDATE, map, new ServiceConnection.CallBackListener() {
            @Override
            public void callback(String result) {


                adapterSpinnerTime1.clear();
                adapterSpinnerTime1.addAll(JsonParserWashbox.parsePeriodTime(result));
                spn_delivery_time.setAdapter(adapterSpinnerTime1);
            }

            @Override
            public void fail(String result) {

            }
        });

    }

    class AdapterSpinnerTime extends ArrayAdapter<PeriodTime> {

        public AdapterSpinnerTime(Context context) {
            super(context, R.layout.row_time, R.id.tv_name);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = super.getView(position, convertView, parent);
            PeriodTime clothing = getItem(position);
            TextView tv_name = (TextView) vi.findViewById(R.id.tv_name);
            tv_name.setText(clothing.Period_Str);


            return vi;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }

    class AdapterSpinnerDate extends ArrayAdapter<PickUpDate> {

        public AdapterSpinnerDate(Context context) {
            super(context, R.layout.row_time, R.id.tv_name);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = super.getView(position, convertView, parent);
            PickUpDate clothing = getItem(position);
            TextView tv_name = (TextView) vi.findViewById(R.id.tv_name);
            tv_name.setText(clothing.dateStr);


            return vi;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }

}
