package box24.com.box24sdk.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import box24.com.box24sdk.R;

public  class FragmentIntro extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_introduce,
                    container, false);

            int title = getArguments().getInt("title");
            int des = getArguments().getInt("des");
            int image = getArguments().getInt("image");

            TextView tvtitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvdes = (TextView) view.findViewById(R.id.tv_des);
            ImageView imagea = (ImageView) view.findViewById(R.id.image);
            try {
                tvtitle.setText(getString(title));
                tvdes.setText(getString(des));
                imagea.setImageResource(image);
            } catch (Exception e) {

            }
            return view;
        }

    }