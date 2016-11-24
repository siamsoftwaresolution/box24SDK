package box24.com.box24sdk.utils;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import box24.com.box24sdk.R;


public class TransparentProgressDialog extends Dialog {

	private ImageView iv;

	public TransparentProgressDialog(Context context) {
		super(context, R.style.TransparentProgressDialog);
		WindowManager.LayoutParams wlmp = getWindow().getAttributes();
		wlmp.gravity = Gravity.CENTER;
		getWindow().setAttributes(wlmp);
		setTitle(null);
		setCancelable(false);
		setOnCancelListener(null);

		View overlayView = View.inflate(context, R.layout.dialog_loading, null);

		// LinearLayout layout = new LinearLayout(context);
		// layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// iv = new ImageView(context);
		// iv.setImageResource(resourceIdOfImage);
		// layout.addView(iv, params);

		iv = (ImageView) overlayView.findViewById(R.id.image);

		addContentView(overlayView, params);
	}

	@Override
	public void show() {
		super.show();
		RotateAnimation anim = new RotateAnimation(0.0f, 360.0f,
				Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
				.5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(1000);
		iv.setAnimation(anim);
		iv.startAnimation(anim);
	}
}
