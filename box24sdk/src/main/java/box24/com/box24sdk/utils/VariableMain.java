package box24.com.box24sdk.utils;

import android.graphics.Bitmap;


public class VariableMain {

    public final static String Header = "http://www.box24corp.com/soap/mob/";

    public static String URL_LOGIN = Header + "account_login.php";
    public static String URL_CHECK_EMAIL = Header + "account_check_email.php";
    public static String URL_CHECK_USERNAME = Header
            + "account_check_username.php";
    public static String URL_REGISTER1 = Header + "account_register.php";
    public static String URL_REGISTER2 = Header + "account_personal.php";
    public static String URL_REGISTER3 = Header + "account_contact.php";
    public static String URL_PAYMENT = Header + "account_payment_omise_success.php";
    public static String URL_PAYMENT_paypal = Header + "account_payment_paypal_success_android.php";

    public static String URL_GET_PROVINCE = "http://api.geonames.org/search?country=th&featureClass=P&featureCode=PPLC&featureCode=PPLA&lang=en&type=json&username=bancomsci";
    public static String URL_PUSH = Header + "account_update_push.php";
    public static String URL_FORGOT_PASSWORD = Header + "account_forgot_password.php";
    public static String URL_CHANGE_PASSWORD = Header + "account_change_password.php";
    public static String URL_FACEBOOK = Header + "facebook_share.php";
    public static String URL_UPDATE_NOTIFICATIONS = Header + "account_update_notification_way.php";
    public static String URL_GET_SETTING = Header + "get_notification_way.php";
    public static String URL_UPDATE_REGSITER = Header + "account_update_register.php";
    public static String URL_UPDATE_PERSONAL = Header + "account_personal.php";
    public static String URL_UPDATE_CONTACT = Header + "account_contact.php";
    public static String URL_GET_REMAINNIG = Header + "wash_remaining.php";

    public static String URL_REGISTER_LOCATION = Header + "account_register_location.php";
    public static String URL_LOGIN_NEW = Header + "account_check_login_new.php";
    public static String URL_REGISTER_NEW = Header + "account_register_new.php";
    public static String URL_UPDATE_1 = Header + "account_update_P1_new.php";
    public static String URL_UPDATE_2 = Header + "account_update_P2_new.php";
    public static String URL_UPDATE_3 = Header + "account_update_P3_new.php";


    public static String URL_GET_PROMOTION = Header + "get_popup.php";
    public static String URL_GET_USER_LOGIN = Header + "change_country_auto_login.php";


    public static Bitmap IMAGE_PROFILE;
    public static String IS_LOGIN = "isLogin";
    public static String SECRET_TOKEN = "secretToken";
    public static String CUSTOMER_ID = "customerID";
    public static String FACEBOOK_ID = "facebookID";
    public static String CREDITS = "credits";
    public static String EXPRIEDATE = "expridate";
    //	public static String PROFILE_IMAGE = "profile_image";
    public static String PROFILE_NAME = "profile_name";
    public static String PROFILE = "profile";
    public static String SenderID = "989070464885";
    public static final String PHONE = "089-221-2244";
    public static final String EMAIL = "cs@box24corp.com";
    public static final String EMAIL_SG = "cs.sg@box24corp.com";
    public static final String EMAIL_SUPPORT = "support@box24corp.com";

    public static final String TERMS_WASHBOX_TH = "http://www.box24corp.com/HTMLpage/TermCondition/term_th.html";
    public static final String TERMS_WASHBOX_EN = "http://www.box24corp.com/HTMLpage/TermCondition/term_en.html";
    public static final String TERMS_WASHBOX_JA = "http://www.box24corp.com/HTMLpage/TermCondition/term_ja.html";

    public static final String TERMS_MOVEBOX_TH = "http://www.box24corp.com/MOVE/HTMLpage/TermCondition/term_th.html";
    public static final String TERMS_MOVEBOX_EN = "http://www.box24corp.com/MOVE/HTMLpage/TermCondition/term_en.html";
    public static final String TERMS_MOVEBOX_JA = "http://www.box24corp.com/MOVE/HTMLpage/TermCondition/term_ja.html";

    public static final String TERMS_SHOPBOX_TH = "http://www.box24corp.com/ShopBox/HTMLpage/TermCondition/term_th.html";
    public static final String TERMS_SHOPBOX_EN = "http://www.box24corp.com/ShopBox/HTMLpage/TermCondition/term_en.html";
    public static final String TERMS_SHOPBOX_JA = "http://www.box24corp.com/ShopBox/HTMLpage/TermCondition/term_ja.html";

    public static final String FAQ_WASHBOX_EN = "http://www.box24corp.com/HTMLpage/FAQ/faq_en.html";
    public static final String FAQ_WASHBOX_TH = "http://www.box24corp.com/HTMLpage/FAQ/faq_th.html";
    public static final String FAQ_WASHBOX_JA = "http://www.box24corp.com/HTMLpage/FAQ/faq_ja.html";

    public static final String FAQ_MOVEBOX_EN = "http://www.box24corp.com/MOVE/HTMLpage/FAQ/faq_en.html";
    public static final String FAQ_MOVEBOX_TH = "http://www.box24corp.com/MOVE/HTMLpage/FAQ/faq_th.html";
    public static final String FAQ_MOVEBOX_JA = "http://www.box24corp.com/MOVE/HTMLpage/FAQ/faq_ja.html";

    public static final String FAQ_SHOPBOX_EN = "http://www.box24corp.com/ShopBox/HTMLpage/FAQ/faq_en.html";
    public static final String FAQ_SHOPBOX_TH = "http://www.box24corp.com/ShopBox/HTMLpage/FAQ/faq_th.html";
    public static final String FAQ_SHOPBOX_JA = "http://www.box24corp.com/ShopBox/HTMLpage/FAQ/faq_ja.html";


    public static final String FAQ_WASHBOX_EN_SG = "http://www.box24corp.com/HTMLPage/sg_FAQ/faq_en.html";
    public static final String FAQ_WASHBOX_CH_SG = "http://www.box24corp.com/HTMLPage/sg_FAQ/faq_ch.html";

    public static final String TERMS_WASHBOX_EN_SG = "http://www.box24corp.com/HTMLPage/sg_TermCondition/term_en.html";
    public static final String TERMS_WASHBOX_CH_SG = "http://www.box24corp.com/HTMLPage/sg_TermCondition/term_ch.html";

}
