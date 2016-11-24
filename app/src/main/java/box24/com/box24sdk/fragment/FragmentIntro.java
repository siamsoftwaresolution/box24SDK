package box24.com.box24sdk.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import box24.com.box24sdk.R;
import box24.com.box24sdk.model.ServicePic;
import box24.com.box24sdk.utils.UtilsApp;


/**
 * Created by error on 11/24/2016 AD.
 */

public class FragmentIntro extends Fragment {
    private TextView tv_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service_pic,
                container, false);
        ServicePic pic = (ServicePic) getArguments().getSerializable(
                "model");
        // TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        // TextView tvColor = (TextView) view.findViewById(R.id.tv_color);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        if (!pic.picture.equals("")) {
//                aq.id(R.id.image).image(UtilsApp.getImageBase64(pic.picture));
            imageView.setImageBitmap(UtilsApp.getImageBase64(pic.picture));
        } else {
//                try {
//                    String query = URLEncoder.encode("https://3.bp.blogspot.com/-W__wiaHUjwI/Vt3Grd8df0I/AAAAAAAAA78/7xqUNj8ujtY/s1600/image02.png", "utf-8");
//                    Picasso.with(getContext())
//                            .load(query)
//                            .into(imageView);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }


//                aq.id(R.id.image).image(pic.picture_path, true, true);
            Picasso.with(getActivity()).load(pic.picture_path).into(imageView);
        }

        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(pic.item_remark);
        // tvName.setText(pic.item_name);
        // tvColor.setText(pic.item_color);

        return view;
    }

}
