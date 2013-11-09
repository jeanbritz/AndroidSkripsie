package com.paysystem.mobileapp.ui.feature;

import org.json.JSONException;
import org.json.JSONObject;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.paysystem.mobileapp.R;
import com.paysystem.mobileapp.data.operation.AuthenticationOperation;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import com.paysystem.mobileapp.ui.DataDroidActivity;
import com.paysystem.mobileapp.ui.HomeActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public final class AuthenticationActivity extends DataDroidActivity implements RequestListener,
OnClickListener, ConnectionErrorDialogListener {

private EditText mUsername;
private EditText mPassword;

boolean loginStatus = false;

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
		mUsername = (EditText) findViewById(R.id.username);
		mPassword = (EditText) findViewById(R.id.password);
	}

	

	private void callAuthenticationWSWith() {
		
		setProgressBarIndeterminateVisibility(true);
		Request request = paySystemRequestFactory.authenticationRequest(true);
		
		request.put("username", mUsername.getText().toString());
		request.put("password", mPassword.getText().toString());
		mRequestManager.execute(request, this);
		mRequestList.add(request);
		//if (request.contains(AuthenticationOperation.PARAM_WITH_AUTHENTICATE))
		//{
		//	request.getString(AuthenticationOperation.PARAM_WITH_AUTHENTICATE);
		//}
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.b_load:
			callAuthenticationWSWith();
        break;
        }
	}

	@SuppressLint("ShowToast")
	@Override
	public void onRequestFinished(Request request, Bundle resultData) {
		if (mRequestList.contains(request)) {
			setProgressBarIndeterminateVisibility(false);
			mRequestList.remove(request);
			
			try {
				Log.i("resultData", (String) resultData.get(paySystemRequestFactory.BUNDLE_EXTRA_AUTHENTICATION_RESULT));
				JSONObject json = new JSONObject(""+resultData.get(paySystemRequestFactory.BUNDLE_EXTRA_AUTHENTICATION_RESULT));
				loginStatus = json.has("detail");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!loginStatus)
			{
				Intent intent = new Intent(this, HomeActivity.class);
				startActivity(intent);
			}
			else
			{
				Toast.makeText(this, "Incorrect username/password", Toast.LENGTH_LONG).show();
			}
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
