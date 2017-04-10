package box24.com.box24sdk.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import box24.com.box24sdk.model.LocationBox24;
import box24.com.box24sdk.model.Locker;
import box24.com.box24sdk.model.PeriodTime;
import box24.com.box24sdk.model.PickUpDate;
import box24.com.box24sdk.model.Result;
import box24.com.box24sdk.model.ServiceBag;
import box24.com.box24sdk.model.ServiceItem;
import box24.com.box24sdk.model.ServicePic;


public class JsonParserWashbox {
    public static Result parseRegister(Context c, JSONObject json) {
        Result re = new Result();
        try {
            re.Message = json.getString("message");
            re.Status = json.getInt("status");

            JSONObject jData = json.getJSONObject("data");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }

//    public static Profile parseUser(String json) {
//        Profile re = new Profile();
//        try {
//
//            JSONObject jData = new JSONObject(json).getJSONObject("data");
//            re.birthdate = jData.getString("birthdate").replaceAll("null", "");
//            re.building_name = jData.getString("building_name").replaceAll("null", "");
//            re.building_num = jData.getString("building_num").replaceAll("null", "");
//            re.customer_id = jData.getString("customer_id").replaceAll("null", "");
//            re.distric = jData.getString("distric").replaceAll("null", "");
//            re.email = jData.getString("email").replaceAll("null", "");
//            try {
//                re.facebook_id = jData.getString("facebook_id").replaceAll("null", "");
//            }catch (Exception e){}
//            re.id_card = jData.getString("id_card").replaceAll("null", "");
//            re.mobile = jData.getString("mobile").replaceAll("null", "");
//            re.name = jData.getString("name").replaceAll("null", "");
////            re.name_th = jData.getString("name_th").replaceAll("null", "");
//            re.username = jData.getString("username").replaceAll("null", "");
//            re.profilePhoto_path = jData.getString("profilePhoto_path").replaceAll("null", "");
//            re.username = jData.getString("username").replaceAll("null", "");
//            re.nationlity = jData.getString("nationlity").replaceAll("null", "");
////            re.password = jData.getString("password").replaceAll("null", "");
//            re.postcode = jData.getString("postcode").replaceAll("null", "");
//            re.province = jData.getString("province").replaceAll("null", "");
//            re.sex = jData.getString("sex").replaceAll("null", "");
//            re.street = jData.getString("street").replaceAll("null", "");
//            re.subdistric = jData.getString("subdistric").replaceAll("null", "");
//            re.surname = jData.getString("surname").replaceAll("null", "");
//            re.lane = jData.getString("lane").replaceAll("null", "");
//            re.location_id = jData.getString("location_id");
//
////			UtilsApp.updateProfile(re);
////			re.save();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return re;
//    }

    private String optString_1(final JSONObject json, final String key) {
        return json.isNull(key) ? null : json.optString(key);
    }

    public static List<String> parseProvince(Context c, JSONObject json) {

        List<String> list = new ArrayList<String>();

        try {

            JSONArray jData = json.getJSONArray("geonames");
            for (int i = 0; i < jData.length(); i++) {
                JSONObject jo = jData.getJSONObject(i);
                list.add(jo.getString("name").split("Mueang ")[0].split("New ")[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Result parseRegisterNew(Context c, JSONObject json) {
        Result re = new Result();
        try {

            re.Message = json.getString("message");
            re.Status = json.getInt("status");
            JSONObject jData = json.getJSONObject("data");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return re;
    }

    public static Result parseResult(String jsona) {

        Result re = new Result();
        try {
            JSONObject json = new JSONObject(jsona);
            re.Message = json.getString("message");
            re.Status = json.getInt("status");

        } catch (Exception e) {
            e.printStackTrace();

        }
        return re;
    }

    public static Result parseDiscount(JSONObject json) {
        Result re = new Result();
        try {

            JSONObject jj = json.getJSONObject("data");

            re.Message = jj.getString("detail");
            re.Status = jj.getInt("discount");

        } catch (Exception e) {
            e.printStackTrace();

        }
        return re;
    }

    public static Result parseBuyPackage(JSONObject json) {
        Result re = new Result();
        try {

            re.Status = json.getJSONObject("data").getInt("isHavePackage");
            re.Message = json.getString("message");

        } catch (Exception e) {
            e.printStackTrace();

        }
        return re;
    }


    public static List<ServiceBag> parseServiceBag(Context c, JSONObject json) {
        List<ServiceBag> list = new ArrayList<ServiceBag>();
        try {
            JSONObject n = json.getJSONObject("data");
            JSONArray j = n.getJSONArray("bag");
            for (int i = 0; i < j.length(); i++) {
                JSONObject jarray = j.getJSONObject(i);
                ServiceBag re = new ServiceBag();
                re.bar_bag = jarray.getString("bar_bag");
                re.end_date = jarray.getString("end_date");
                re.end_date_show = jarray.getString("end_date_show");
                re.money_pay = jarray.getString("money_pay");
                re.service_id = jarray.getString("service_id");
                try {
                    re.service_success = jarray.getString("service_success");
                } catch (Exception e) {

                }
                re.start_date = jarray.getString("start_date");
                re.start_date_show = jarray.getString("start_date_show");
                re.mode = jarray.getString("mode");
                re.address = jarray.getString("address");
                re.end_time_id = jarray.getInt("end_time_id");
                try {
                    re.canEdit = jarray.getInt("canEdit");
                } catch (Exception e) {
                }
                re.start_time_id = jarray.getInt("start_time_id");
                re.start_time_show = jarray.getString("start_time_show");
                re.end_time_show = jarray.getString("end_time_show");
                re.RequestID = jarray.getString("RequestID");
                re.note = jarray.getString("Note");
                re.isCancel = jarray.getInt("isCancel");
                re.numIronUse = jarray.getInt("numIronUse");
                list.add(re);
            }

            JSONObject jCredit = json.getJSONObject("credit");
            SPUtils.set(c, VariableMain.CREDITS, jCredit.getString("credits"));
            SPUtils.set(c, VariableMain.EXPRIEDATE,
                    jCredit.getString("expire_date"));
            UtilsApp.setCredtisInstore(c, jCredit.getString("instore_remain"));
            UtilsApp.setExprieDateInstore(c,
                    jCredit.getString("instore_expire_date"));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }


    public static ServiceBag parseServiceBag2(String aaa) {

        try {
            JSONObject json = new JSONObject(aaa);
            JSONObject n = json.getJSONObject("data");
//            JSONArray j = n.getJSONArray("bag");
//            for (int i = 0; i < j.length(); i++) {
            JSONObject jarray = n.getJSONObject("bag");
            ServiceBag re = new ServiceBag();
            re.bar_bag = jarray.getString("bar_bag");
            try {
                re.end_date = jarray.getString("end_date");
            } catch (Exception e) {

            }
            try {
                re.end_date_show = jarray.getString("end_date_show");
            } catch (Exception e) {

            }
            re.money_pay = jarray.getString("money_pay");
            re.service_id = jarray.getString("service_id");
            try {
                re.service_success = jarray.getString("service_success");
            } catch (Exception e) {
            }
            re.start_date = jarray.getString("start_date");
            re.start_date_show = jarray.getString("start_date_show");
            re.mode = jarray.getString("mode");
            re.address = jarray.getString("address");
            re.end_time_id = jarray.getInt("end_time_id");
            try {
                re.canEdit = jarray.getInt("canEdit");
            } catch (Exception e) {
            }
            re.start_time_id = jarray.getInt("start_time_id");
            re.start_time_show = jarray.getString("start_time_show");
            re.end_time_show = jarray.getString("end_time_show");
            try {
                re.RequestID = jarray.getString("RequestID");
            } catch (Exception e) {

            }
            re.note = jarray.getString("note");
//            re.laundry_note = jarray.getString("laundry_note");
            re.isCancel = jarray.getInt("isCancel");
            re.isFeedback = jarray.getInt("isCancel");
//                list.add(re);
//            }
            return re;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static ServiceBag parseServiceBagShop(JSONObject json) {

        try {
            JSONObject n = json.getJSONObject("data");
//            JSONArray j = n.getJSONArray("bag");
//            for (int i = 0; i < j.length(); i++) {
            JSONObject jarray = n.getJSONObject("bag");
            ServiceBag re = new ServiceBag();
            re.bar_bag = jarray.getString("bar_bag");
            try {
                re.end_date = jarray.getString("end_date");
            } catch (Exception e) {

            }
            try {
                re.end_date_show = jarray.getString("end_date_show");
            } catch (Exception e) {

            }
            re.money_pay = jarray.getString("money_pay");
            re.service_id = jarray.getString("service_id");
            try {
                re.service_success = jarray.getString("service_success");
            } catch (Exception e) {
            }
            re.start_date = jarray.getString("start_date");
            re.start_date_show = jarray.getString("start_date_show");
            re.mode = jarray.getString("mode");
            re.address = jarray.getString("address");
            re.end_time_id = jarray.getInt("end_time_id");
            try {
                re.canEdit = jarray.getInt("canEdit");
            } catch (Exception e) {
            }
            re.start_time_id = jarray.getInt("start_time_id");
            re.start_time_show = jarray.getString("start_time_show");
            re.end_time_show = jarray.getString("end_time_show");
            try {
                re.RequestID = jarray.getString("RequestID");
            } catch (Exception e) {

            }
            re.note = jarray.getString("Note");
//            re.laundry_note = jarray.getString("laundry_note");
            re.isCancel = jarray.getInt("isCancel");

//                list.add(re);
//            }
            return re;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static List<Locker> parseServiceLocker(Context c, String aaa) {
        List<Locker> list = new ArrayList<Locker>();
        try {
            JSONObject json = new JSONObject(aaa);
            JSONArray j = json.getJSONArray("data");
//            JSONArray j = n.getJSONArray("bag");
            for (int i = 0; i < j.length(); i++) {
                JSONObject jarray = j.getJSONObject(i);
                Locker re = new Locker();
                try {
                    re.bar_bag = jarray.getString("bar_bag");
                } catch (Exception e) {

                }
                re.LocaID = jarray.getString("LocaID");
                re.LocaEn = jarray.getString("LocaEn");
                re.LockID = jarray.getString("LockID");
                re.statusNumber = jarray.getString("statusNumber");
                re.Des = jarray.getString("Description");
                try {
                    re.service_id = jarray.getString("service_id");
                } catch (Exception e) {
                }
                re.QP_Pickup = jarray.getString("QP_Pickup");
                re.dateDropToLocker = jarray.getString("dateDropToLocker");
                list.add(re);
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    public static ServiceBag parseServiceBagUpdate(Context c, JSONObject json) {

        try {
            JSONObject jarray = json.getJSONObject("data");

            ServiceBag re = new ServiceBag();
            re.bar_bag = jarray.getString("bar_bag");
            re.end_date = jarray.getString("end_date");
            re.end_date_show = jarray.getString("end_date_show");
            re.money_pay = jarray.getString("money_pay");
            re.service_id = jarray.getString("service_id");
            re.service_success = jarray.getString("service_success");
            re.start_date = jarray.getString("start_date");
            re.start_date_show = jarray.getString("start_date_show");
            re.mode = jarray.getString("mode");
            re.address = jarray.getString("address");
            re.end_time_id = jarray.getInt("end_time_id");
            re.canEdit = jarray.getInt("canEdit");
            re.start_time_id = jarray.getInt("start_time_id");
            re.start_time_show = jarray.getString("start_time_show");
            re.end_time_show = jarray.getString("end_time_show");


            return re;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static List<ServiceItem> parseServiceItem(String aaa) {
        List<ServiceItem> list = new ArrayList<ServiceItem>();
        try {
            JSONObject json = new JSONObject(aaa);
            JSONObject n = json.getJSONObject("data");
            JSONArray j = n.getJSONArray("item");
            for (int i = 0; i < j.length(); i++) {
                JSONObject jarray = j.getJSONObject(i);
                ServiceItem re = new ServiceItem();
                re.barcode = jarray.getString("barcode");
                re.clean_name_en = jarray.getString("clean_name_en");
                re.money_pay = jarray.getString("money_pay");
                re.promotion_name = jarray.getString("promotion_name");
                re.service_id = jarray.getString("service_id");
                re.service_name = jarray.getString("service_name");
                re.service_type_id = jarray.getString("service_type_id");

                list.add(re);
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<ServicePic> parseServicePic(Context c, String aaa) {
        List<ServicePic> list = new ArrayList<ServicePic>();
        try {
            JSONObject json = new JSONObject(aaa);
            JSONArray j = json.getJSONArray("data");
            for (int i = 0; i < j.length(); i++) {
                JSONObject jarray = j.getJSONObject(i);
                ServicePic re = new ServicePic();
                re.item_brand = jarray.getString("item_brand");
                re.item_color = jarray.getString("item_color");
                re.item_name = jarray.getString("item_name");
                re.item_remark = jarray.getString("item_remark");
                re.item_type = jarray.getString("item_type");
                re.picture = jarray.getString("picture");
                re.picture_path = jarray.getString("picture_path");

                list.add(re);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }


    public static List<PeriodTime> parsePeriodTime(String aaa) {
        List<PeriodTime> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(aaa);
            JSONArray jsonArray = json.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                PeriodTime periodTime = new PeriodTime();
                periodTime.Period_Str = jo.getString("Period_Str");
                periodTime.TimeID = jo.getString("TimeID");
                periodTime.Period_Full = jo.getInt("Period_Full");
                list.add(periodTime);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<PickUpDate> parsePickupDate(String aaaa) {
        List<PickUpDate> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(aaaa);
            JSONArray jsonArray = json.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                PickUpDate periodTime = new PickUpDate();
                periodTime.Date_Full = jo.getInt("Date_Full");
                periodTime.dateStr = jo.getString("dateStr");
                periodTime.dateVal = jo.getString("dateVal");
                list.add(periodTime);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }


    public static boolean parseCredits(Context c, JSONObject json) {
        try {

            JSONObject jCredit = json.getJSONObject("credit");
            SPUtils.set(c, VariableMain.CREDITS, jCredit.getString("credits"));
            SPUtils.set(c, VariableMain.EXPRIEDATE,
                    jCredit.getString("expire_date"));
            UtilsApp.setCredtisInstore(c, jCredit.getString("instore_remain"));
            UtilsApp.setExprieDateInstore(c,
                    jCredit.getString("instore_expire_date"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String parseCreditsError(JSONObject json) {
        try {

            String jCredit = json.getString("message");

            return jCredit;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<LocationBox24> parseMap(String aa) {
        List<LocationBox24> list = new ArrayList<LocationBox24>();
        try {
            JSONObject json = new JSONObject(aa);
            JSONArray j = json.getJSONArray("data");
            for (int i = 0; i < j.length(); i++) {
                JSONObject jarray = j.getJSONObject(i);
//                if (!jarray.getString("latitude").equals("null") && !jarray.getString("longitude").equals("null")) {
                LocationBox24 re = new LocationBox24();
                if (!jarray.getString("latitude").isEmpty()) {
                    re.latitude = Double.parseDouble(jarray.getString("latitude"));
                    re.longitude = Double
                            .parseDouble(jarray.getString("longitude"));
                } else {
                    re.latitude = 0;
                    re.longitude = 0;
                }

                re.location_address_for_api_use = jarray
                        .getString("location_address_for_api_use");
                re.location_condo_name_for_api_use = jarray
                        .getString("location_condo_name_for_api_use");
                re.location_name_for_api_use = jarray
                        .getString("location_name_for_api_use");
                re.location_tel_for_api_use = jarray
                        .getString("location_tel_for_api_use").replaceAll("\\.", "-").replace("null", "");
                re.location_id = jarray
                        .getString("location_id");
                re.location_image = jarray
                        .getString("location_image");
                try {
                    re.fav = jarray.getInt("isFavorite");
                } catch (Exception e) {
                    re.fav = 0;
                }
                try {
                    re.location_avilable_locker = jarray
                            .getString("location_avilable_locker");
                    re.location_avilable_status = jarray
                            .getString("location_avilable_status");
                } catch (Exception e) {

                }

                list.add(re);
//                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }
}
