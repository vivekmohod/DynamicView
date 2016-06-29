package com.klouddata.dynamicview.ui_generator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.klouddata.dymamicview.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class LoadableImageView extends FrameLayout {

	private ImageView imageView;
	private ProgressBar loadingBar;
	public DisplayImageOptions sOptions = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565).build();

	public LoadableImageView(Context context) {
		super(context);
		initView(context);
	}

	public LoadableImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public LoadableImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		imageView = new ImageView(context);
		addView(imageView);
		loadingBar = new ProgressBar(context);
		addView(loadingBar);
		hideLoading();
	}

	public void showLoading() {
		loadingBar.setVisibility(View.VISIBLE);
	}

	public void hideLoading() {
		loadingBar.setVisibility(View.GONE);
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void loadImageFromURL(String url, final int defaultImageId) {
		loadImageFromURL(url, defaultImageId, false, false);
		// if (TextUtils.isEmpty(url)) {
		// imageView.setBackgroundResource(defaultImageId);
		// return;
		// }
		// ImageLoader.getInstance().displayImage(url.trim(), imageView,
		// DarshanConstants.sOptions, new ImageLoadingListener() {
		//
		// @Override
		// public void onLoadingStarted(String imageUri, View view) {
		// showLoading();
		// }
		//
		// @Override
		// public void onLoadingFailed(String imageUri, View view,
		// FailReason failReason) {
		// imageView.setImageResource(defaultImageId);
		// }
		//
		// @Override
		// public void onLoadingComplete(String imageUri, View view,
		// Bitmap loadedImage) {
		// hideLoading();
		// if (loadedImage != null) {
		// imageView.setBackgroundDrawable(new BitmapDrawable(
		// getContext().getResources(), loadedImage));
		// } else
		// imageView.setBackgroundResource(defaultImageId);
		// imageView.setImageDrawable(null);
		// }
		//
		// @Override
		// public void onLoadingCancelled(String imageUri, View view) {
		// hideLoading();
		// }
		//
		// });

	}

	public void loadImageFromURL(String url, final int defaultImageId,
			final boolean isRounded, final boolean isStoreImage) {
		if (TextUtils.isEmpty(url)) {
			imageView.setBackgroundResource(defaultImageId);
			imageView.setImageDrawable(null);
			return;
		}

		ImageLoader.getInstance().displayImage(url.trim(), imageView,
				sOptions, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						showLoading();
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						hideLoading();
						((ImageView) view).setImageResource(defaultImageId);
					}

					@SuppressWarnings("deprecation")
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						hideLoading();
						if (loadedImage != null) {
							if (isRounded) {
								int radius = 0;
								if(isStoreImage){
									radius = 145; 	//for Store Image
								}
								else{
									radius = getContext().getResources()
											.getDimensionPixelSize(R.dimen.image_width) / 2;
								}

								loadedImage = getCroppedBitmap(
										loadedImage, radius);
								
							}
							((ImageView) view)
									.setBackgroundDrawable(new BitmapDrawable(
											getContext().getResources(),
											loadedImage));
						} else
							((ImageView) view)
									.setBackgroundResource(defaultImageId);
						((ImageView) view).setImageDrawable(null);

					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
					}

				});
	}

	public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius)
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		else
			sbmp = bmp;
		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
				sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.0f, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(sbmp, rect, rect, paint);

		return output;
	}
}
