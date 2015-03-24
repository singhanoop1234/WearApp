package com.tcs.wearapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.tcs.wearapp.R;

public class SplashActivity extends Activity {

	private TextView tvAppName;
	private TextView tvAppFullName;

	private TranslateAnimation mTranslateAnim1;
	private ScaleAnimation mScaleAnimation2;
	private TranslateAnimation mTranslateAnim2;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();

        setContentView(R.layout.activity_splash);

		initViews();
		startAnimation();
	}

	/**
	 * Initialises views on activity.
	 */
	private void initViews() {
		tvAppFullName = (TextView) findViewById(R.id.tv_app_full_name);
		tvAppName = (TextView) findViewById(R.id.tv_app_name);
	}

	/**
	 * Starts adding views on activity.
	 */
	private void startAnimation() {
		loadAnimation();
		addAnimationListener();

	}

	private void loadAnimation() {

		mTranslateAnim1 = new TranslateAnimation(0, 0, 0, 0);
		mTranslateAnim1.setDuration(1000);
		mTranslateAnim1.setFillAfter(true);
		tvAppName.setAnimation(mTranslateAnim1);

		mScaleAnimation2 = new ScaleAnimation(0.2f, 1.0f, 0.2f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mScaleAnimation2.setDuration(1000);
		mScaleAnimation2.setFillAfter(true);

		mTranslateAnim2 = new TranslateAnimation(0, 0, 0, 0);
		mTranslateAnim2.setDuration(1500);
		mTranslateAnim2.setFillAfter(true);
	}

	private void addAnimationListener() {

		mTranslateAnim1.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(final Animation animation) {

				tvAppFullName.setAnimation(mScaleAnimation2);

			}

			@Override
			public void onAnimationRepeat(final Animation animation) {
			}

			@Override
			public void onAnimationStart(final Animation animation) {

			}
		});

		mScaleAnimation2.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(final Animation animation) {
				tvAppName.setAnimation(mTranslateAnim2);
			}

			@Override
			public void onAnimationRepeat(final Animation animation) {
			}

			@Override
			public void onAnimationStart(final Animation animation) {
                tvAppFullName.setVisibility(View.VISIBLE);
			}
		});

		mTranslateAnim2.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(final Animation animation) {

				navigateToUserActionActivity();
			}

			@Override
			public void onAnimationRepeat(final Animation animation) {
			}

			@Override
			public void onAnimationStart(final Animation animation) {

			}
		});
	}
	


	/**
	 * Navigates user to UserActionActivity.
	 */
	protected void navigateToUserActionActivity() {

		Intent nextActivityIntent = new Intent(SplashActivity.this,UserActionActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(nextActivityIntent);
		finish();

	}

}
