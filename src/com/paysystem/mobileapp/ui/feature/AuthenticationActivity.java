package com.paysystem.mobileapp.ui.feature;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.paysystem.mobileapp.R;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import com.paysystem.mobileapp.ui.DataDroidActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public final class AuthenticationActivity extends DataDroidActivity implements RequestListener,
OnClickListener, ConnectionErrorDialogListener {

private TextView mTVResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.authentication);

		bindViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		for (int i = 0; i < mRequestList.size(); i++) {
			Request request = mRequestList.get(i);

			if (mRequestManager.isRequestInProgress(request)) {
				mRequestManager.addRequestListener(this, request);
				setProgressBarIndeterminateVisibility(true);
			} else {
				mRequestManager.callListenerWithCachedData(this, request);
				i--;
				mRequestList.remove(request);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (!mRequestList.isEmpty()) {
			mRequestManager.removeRequestListener(this);
		}
	}

	private void bindViews() {
		((Button) findViewById(R.id.b_load)).setOnClickListener(this);
		
		mTVResult = (TextView) findViewById(R.id.tv_result);
	}

	private void callAuthenticationWSWithout() {
		mTVResult.setText("");
		setProgressBarIndeterminateVisibility(true);
		Request request = paySystemRequestFactory.authenticationRequest(false);
		mRequestManager.execute(request, this);
		mRequestList.add(request);
	}

	private void callAuthenticationWSWith() {
		mTVResult.setText("");
		setProgressBarIndeterminateVisibility(true);
		Request request = paySystemRequestFactory.authenticationRequest(true);
		mRequestManager.execute(request, this);
		mRequestList.add(request);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.b_load:
			callAuthenticationWSWithout();
        break;
        }
	}

	@Override
	public void onRequestFinished(Request request, Bundle resultData) {
		if (mRequestList.contains(request)) {
			setProgressBarIndeterminateVisibility(false);
			mRequestList.remove(request);

			mTVResult.setText(resultData
					.getString(paySystemRequestFactory.BUNDLE_EXTRA_AUTHENTICATION_RESULT));
		}
	}

	@Override
	public void onRequestConnectionError(Request request, int statusCode) {
		if (mRequestList.contains(request)) {
			setProgressBarIndeterminateVisibility(false);
			mRequestList.remove(request);

			ConnectionErrorDialogFragment.show(this, request, this);
		}
	}

	@Override
	public void onRequestDataError(Request request) {
		if (mRequestList.contains(request)) {
			setProgressBarIndeterminateVisibility(false);
			mRequestList.remove(request);

			showBadDataErrorDialog();
		}
	}
	
	@Override
	public void onRequestCustomError(Request request, Bundle resultData) {
		// Never called.
	}

	@Override
	public void connectionErrorDialogCancel(Request request) {}

	@Override
	public void connectionErrorDialogRetry(Request request) {
		mRequestManager.execute(request, this);
		mRequestList.add(request);
	}
}
