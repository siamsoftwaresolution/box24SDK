package box24.com.box24sdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import box24.com.box24sdk.Settings;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ERROR on 6/9/2015.
 */


public class ServiceConnection {

    public interface CallBackListener {
        public void callback(String result);

        public void fail(String result);
    }


//    private CallBackListener mTheListener;


    //    public void setPostListener(CallBackListener listen) {
//        mTheListener = listen;
//    }

    String Folder = "QPer";
    final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

    private static final MediaType CONTENT_TYPE =
            MediaType.parse("application/x-www-form-urlencoded");

    private final OkHttpClient client;
    private Context context;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType PARAMS
            = MediaType.parse("text/html; charset=utf-8");
    TransparentProgressDialog dia;
    private static int MAX_IDLE_CONNECTIONS = 30;
    private static int KEEP_ALIVE_DURATION_MS = 3 * 60 * 1000;

    //    private static int KEEP_ALIVE_DURATION_MS = 10000;
    public ServiceConnection(final Context c) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        client = new OkHttpClient();
//        client.setConnectionPool(new com.squareup.okhttp.ConnectionPool(
//                MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION_MS));
//        client.setConnectTimeout(1, TimeUnit.MINUTES);
//        client.setReadTimeout(1, TimeUnit.MINUTES);
//        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .tlsVersions(TlsVersion.TLS_1_2)
//                .build();

        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
//                .authenticator(new Authenticator() {
//                    @Override
//                    public Request authenticate(Route route, Response response) throws IOException {
////                        System.out.println("Authenticating for response: " + response);
////                        System.out.println("Challenges: " + response.challenges());
//
//                        String credential = Credentials.basic("edoc", "E28a97e91");
//                        Log.i("AUTHEN", credential);
//                        return response.request().newBuilder()
//                                .header("Authorization", credential)
//                                .build();
//                    }
//                })
                .build();
//        client = getUnsafeOkHttpClient();
        context = c;
        dia = new TransparentProgressDialog(c);
//        dia.setMessage("Loading..");
        dia.setCancelable(false);
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");

    public void postLogin(final boolean progress, final String url, Map<String, Object> params, final CallBackListener mTheListener) {
//        params.put("language", UtilApps.getLangPost(context));
        File file = null;
//        FormBody.Builder formBody = new FormBody.Builder();
//
//        for (Map.Entry<String,String> entry : params.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            // do stuff
//            formBody.add(key,value);
//        }


        final MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // do stuff
            if (value instanceof Bitmap) {
//                Log.i("instanceof","File");

//                Bitmap bb = resizeImageForImageView(((File) value).getAbsolutePath());

                String path = uploadImagePath((Bitmap) value);

                file = new File(path);
                builder.addFormDataPart(key, "image.jpg", RequestBody.create(MEDIA_TYPE_JPG, file));
            } else {
//                Log.i("instanceof","อื่นๆ");
                builder.addFormDataPart(key, String.valueOf(value));
            }


        }

        final RequestBody body = builder.build();
        final File finalFile = file;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (progress) {
                    try {
                        dia.show();
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            protected String doInBackground(Void... voids) {


                Request request = new Request.Builder()
                        .url(url).post(body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        if (finalFile != null) {
                            finalFile.delete();
                        }
                        return response.body().string();
                    } else {
                        return "Not Success" + response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error - " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                System.out.println(string);
                if (progress) {
                    try {
                        dia.dismiss();
                    } catch (Exception e) {

                    }
                }

                if (string.contains("Not Success")) {
                    mTheListener.fail(string.replace("Not Success", ""));
                } else {
                    mTheListener.callback(string);
                }

            }
        }.execute();
    }


    public void post(final boolean progress, final String url, Map<String, Object> params, final CallBackListener mTheListener) {
//        params.put("language", UtilApps.getLangPost(context));
        File file = null;
//        FormBody.Builder formBody = new FormBody.Builder();
//
//        for (Map.Entry<String,String> entry : params.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            // do stuff
//            formBody.add(key,value);
//        }
        params.put("customer_id", Settings.PARAM_PHONE);
        params.put("secretToken", "1");
        params.put("LanguageID","1");
        params.put("CountryID", 3);

        final MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);


        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // do stuff
            if (value instanceof Bitmap) {

                String path = uploadImagePath((Bitmap) value);

                file = new File(path);
                builder.addFormDataPart(key, "Picture", RequestBody.create(MEDIA_TYPE_JPG, file));
            } else {
                builder.addFormDataPart(key, String.valueOf(value));
            }


        }


        final RequestBody body = builder.build();
        final File finalFile = file;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (progress) {
                    try {
                        dia.show();
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            protected String doInBackground(Void... voids) {


                Request request = new Request.Builder()
                        .url(url).post(body)//.header("Authorization", "Digest username=\"edoc\", realm=\"REST_API_0a694211edf4\", nonce=\"\", uri=\"/api/user/info?user_id=1&API-Key=f66df3c6bc6ee8824dbef4b364d6620f\", qop=auth, nc=, cnonce=\"\", response=\"24b4512e3cf7d1cb7c753250bd35f4e2\", opaque=\"\"")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        if (finalFile != null) {
                            finalFile.delete();
                        }
                        return response.body().string();
                    } else {
                        if (finalFile != null) {
                            finalFile.delete();
                        }
                        return "Not Success :" + response.message() + "   " + response.code() + "   " + response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (finalFile != null) {
                        finalFile.delete();
                    }
                    return "Error - " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                System.out.println(string);
                if (progress) {
                    try {
                        dia.dismiss();
                    } catch (Exception e) {

                    }
                }

                if (string.contains("Not Success")) {
                    mTheListener.fail(string.replace("Not Success", ""));
                } else {
                    mTheListener.callback(string);
                }

            }
        }.execute();
    }


    public void get(final boolean progress, final String url, final CallBackListener mTheListener) {


        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (progress) {
                    dia.show();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
//                String asd = url;
//                if (url.contains("?")) {
//                    asd += "&API-Key=" + UtilApps.getToken(context);
//                } else {
//                    asd += "?API-Key=" + UtilApps.getToken(context);
//                }

                Request request = new Request.Builder()
                        .url(url)//.header("Authorization", "Digest username=\"edoc\", realm=\"REST_API_0a694211edf4\", nonce=\"\", uri=\"/api/user/info?user_id=1&API-Key=f66df3c6bc6ee8824dbef4b364d6620f\", qop=auth, nc=, cnonce=\"\", response=\"24b4512e3cf7d1cb7c753250bd35f4e2\", opaque=\"\"")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return "Not Success" + response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error - " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                System.out.println(string);
                if (progress) {
                    dia.dismiss();
                }

                if (string.contains("Not Success")) {
                    mTheListener.fail(string.replace("Not Success", ""));
                } else {
                    mTheListener.callback(string);
                }

            }
        }.execute();
    }



    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(100, TimeUnit.SECONDS);
            builder.writeTimeout(100, TimeUnit.SECONDS);
            builder.readTimeout(300, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    int scaleSize = 600;

    public Bitmap resizeImageForImageView(String path) {

        Bitmap b = BitmapFactory.decodeFile(path);

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(path);

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    b = rotateImage(b, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    b = rotateImage(b, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    b = rotateImage(b, 270);
                    break;
                // etc.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Bitmap resizedBitmap = null;
        int originalWidth = b.getWidth();
        int originalHeight = b.getHeight();
        if (originalHeight < scaleSize) {
            return b;
        } else if (originalWidth < scaleSize) {
            return b;
        }


        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;
        if (originalHeight > originalWidth) {
            newHeight = scaleSize;
            multFactor = (float) originalWidth / (float) originalHeight;
            newWidth = (int) (newHeight * multFactor);
        } else if (originalWidth > originalHeight) {
            newWidth = scaleSize;
            multFactor = (float) originalHeight / (float) originalWidth;
            newHeight = (int) (newWidth * multFactor);
        } else if (originalHeight == originalWidth) {
            newHeight = scaleSize;
            newWidth = scaleSize;
        }
        resizedBitmap = Bitmap.createScaledBitmap(b, newWidth, newHeight, false);


        return resizedBitmap;
    }

    Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    String uploadImagePath(Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File myDir = new File(Environment.getExternalStorageDirectory() + "/" + Folder);
        myDir.mkdirs();

        String fname = "upload"  + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


}
