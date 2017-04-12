package box24.com.box24sdk.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import box24.com.box24sdk.R;

public class UtilsApp {

//    public static final String CUSTOMERID="13721";
//    public static final String SECRETTOKEN="4E87AD58-2BFD-8629-5E87-8B0B40249195";


    public static void setSelectorCruve(View v, int def, int ac) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ac);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(45);

        GradientDrawable drawable2 = new GradientDrawable();
        drawable2.setColor(def);
        drawable2.setShape(GradientDrawable.RECTANGLE);
        drawable2.setCornerRadius(45);

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, drawable);
        states.addState(new int[]{android.R.attr.state_focused}, drawable);
        states.addState(new int[]{android.R.attr.state_selected}, drawable);
        states.addState(new int[]{android.R.attr.state_checked}, drawable);
        states.addState(new int[]{android.R.attr.state_activated}, drawable);
        states.addState(new int[]{}, drawable2);

        v.setBackgroundDrawable(states);
    }


    public static Bitmap getImageBase64(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);
        return decodedByte;
    }

    public static void setLanguage(Context c, String header) {
        SPUtils.set(c, "language", header);
    }

    public static String getLanguage(Context c) {
        return SPUtils.getString(c, "language");
    }

    public static void language(Context cx, String a) {
        // String a = getLanguage(cx);
        Resources res = cx.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(a);
        res.updateConfiguration(conf, dm);
        UtilsApp.setLanguage(cx, a);
    }

    public static void setLocale(Activity x, Class<?> c, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = x.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent i = x.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(x.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        x.startActivity(i);
    }

    public static String getMD5EncryptedString(String encTarget) {
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        } // Encryption algorithm
        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }
        return md5;
    }

    public static String getTimeAmPm(String date) {

        String startTime = date.split(" ")[1];
        StringTokenizer tk = new StringTokenizer(startTime);

        String time = tk.nextToken();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        Date dt;
        try {
            dt = sdf.parse(time);
            System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got
            // result
            // here
            return sdfs.format(dt);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }


    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    public static String getSecretToken(Context c) {

        return SPUtils.getString(c, VariableMain.SECRET_TOKEN);
    }

    public static int getLanguageID(Context c) {
        if (getLanguage(c).equals("")) {
            return 1;
        } else if (getLanguage(c).equals("th")) {
            return 2;
        } else if (getLanguage(c).equals("ja")) {
            return 3;
        } else if (getLanguage(c).equals("fr")) {
            return 1;
        } else {
            return 4;
        }
    }


    public static String getCustomerID(Context c) {

        return SPUtils.getString(c, VariableMain.CUSTOMER_ID);
    }

    public static boolean isLogin(Context c) {

        return SPUtils.getBoolean(c, VariableMain.IS_LOGIN);
    }

    public static void setLogin(Context c, boolean a) {
        SPUtils.set(c, VariableMain.IS_LOGIN, a);
    }



    public static String getExprieDateInstore(Context c) {

        return SPUtils.getString(c, "exprieInstore");
    }

    public static void setExprieDateInstore(Context c, String a) {

        SPUtils.set(c, "exprieInstore", a);
    }

    public static String getApp(Context c) {

        return SPUtils.getString(c, "app");
    }

    public static void setApp(Context c, String a) {

        SPUtils.set(c, "app", a);
    }

    public static String getMobile(Context c) {

        return SPUtils.getString(c, "mobile");
    }

    public static void setMobile(Context c, String a) {

        SPUtils.set(c, "mobile", a);
    }

    public static void setCredtisInstore(Context c, String a) {

        SPUtils.set(c, "creditsInstore", a);
    }

    public static String getExprieDate(Context c) {

        return SPUtils.getString(c, VariableMain.EXPRIEDATE);
    }

    public static String getProfileName(Context c) {

        return SPUtils.getString(c, VariableMain.PROFILE_NAME);
    }

    // public static String getProfileImage(Context c) {
    //
    // return SPUtils.getString(c, Variable.PROFILE_IMAGE);
    // }

    public static String getProfile(Context c) {
        return SPUtils.getString(c, VariableMain.PROFILE);
    }

    // ///////////////////////////////////////////////////////////
    public static String getEmail(Context c) {
        return SPUtils.getString(c, "email");
    }

    public static String getPassword(Context c) {
        return SPUtils.getString(c, "password");
    }

    public static String getFacebookID(Context c) {
        return SPUtils.getString(c, VariableMain.FACEBOOK_ID);
    }

    public static void setEmail(Context c, String a) {
        SPUtils.set(c, "email", a);
    }

    public static void setPassword(Context c, String a) {
        SPUtils.set(c, "password", a);
    }

    public static void setFacebookID(Context c, String a) {
        SPUtils.set(c, VariableMain.FACEBOOK_ID, a);
    }

    public static void setCountry(Context c, int a) {
        SPUtils.set(c, "Country", a);
    }

    public static int getCountry(Context c) {
//        return 0;
        return SPUtils.getInt(c, "Country");
    }

    public static String getVersion(Context c) {

        return SPUtils.getString(c, "Version");
    }

    public static void setVersion(Context c, String a) {

        SPUtils.set(c, "Version", a);
    }


    public static void setNoti(Context c, int a) {
        SPUtils.set(c, "Noti", a);
    }

    public static int getNoti(Context c) {
        return SPUtils.getInt(c, "Noti");
    }


    public static void setMainTutor(Context c, boolean a) {
        SPUtils.set(c, "MainTutor", a);
    }

    public static boolean getMainTutor(Context c) {
        return SPUtils.getBoolean(c, "MainTutor");
    }

    public static void setWashTutor(Context c, boolean a) {
        SPUtils.set(c, "WashTutor", a);
    }

    public static boolean getWashTutor(Context c) {
        return SPUtils.getBoolean(c, "WashTutor");
    }

    public static void setWashTutor2(Context c, boolean a) {
        SPUtils.set(c, "WashTutor2", a);
    }

    public static boolean getWashTutor2(Context c) {
        return SPUtils.getBoolean(c, "WashTutor2");
    }

    public static void setMoveTutor(Context c, boolean a) {
        SPUtils.set(c, "MoveTutor", a);
    }

    public static boolean getMoveTutor(Context c) {
        return SPUtils.getBoolean(c, "MoveTutor");
    }

    public static void setShopTutor(Context c, boolean a) {
        SPUtils.set(c, "ShopTutor", a);
    }

    public static boolean getShopTutor(Context c) {
        return SPUtils.getBoolean(c, "ShopTutor");
    }

    // //////////////////////////////////////////////////////////////
    public static void alerDialog(Context c, String message) {
        new AlertDialog.Builder(c)
                .setMessage(message)
                .setPositiveButton(c.getString(R.string.OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                            }
                        }).show();
    }

    public static void alerDialogTitle(Context c, String title, String message) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(c.getString(R.string.OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                            }
                        }).show();
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    // public static Profile getProfile(Context c) {
    // List<Profile> aa = Profile.find(Profile.class, "customerId=?",
    // getCustomerID(c));
    // return aa.get(0);
    // }

    // public static boolean isProfile(String a){
    // List<Profile> aa = Profile.find(Profile.class, "customerId=?", a);
    // if(aa.isEmpty()){
    // return false;
    // }else{
    // return true;
    // }
    // }

    // public static void updateProfile(Context c, Profile a) {
    // List<Profile> book;
    // try {
    // book = Profile
    // .find(Profile.class, "customerId=?", getCustomerID(c));
    //
    // if (!book.isEmpty()) {
    // Profile bb = book.get(0);
    // bb = a;
    // bb.save();
    // } else {
    // a.save();
    // }
    // } catch (Exception e) {
    // }
    //
    // }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static TransparentProgressDialog progressDialog(Context context) {
        TransparentProgressDialog dia = new TransparentProgressDialog(context);
        return dia;
    }


}
