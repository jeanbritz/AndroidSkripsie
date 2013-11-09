package com.paysystem.mobileapp.ui.ws;

import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.paysystem.mobileapp.R;
import com.paysystem.mobileapp.data.provider.paySystemContent.Claims;
import com.paysystem.mobileapp.data.provider.paySystemContent.Invoices;
import com.paysystem.mobileapp.data.provider.util.ProviderCriteria;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import com.paysystem.mobileapp.ui.DataDroidActivity;
import com.paysystem.mobileapp.ui.feature.PaymentActivity;
import com.paysystem.mobileapp.ui.ws.InvoiceListActivity.InvoiceListAdapter;
import com.paysystem.mobileapp.ui.ws.InvoiceListActivity.ViewHolder;

public final class InvoiceListActivity extends DataDroidActivity implements RequestListener,
OnClickListener, OnItemClickListener, ConnectionErrorDialogListener, LoaderCallbacks<Cursor> {

	private ListView mListView;
	private InvoiceListAdapter mListAdapter;

	private LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.claim_list);
		bindViews();

		getSupportLoaderManager().initLoader(0, null, this);
		mInflater = getLayoutInflater();
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
				mRequestList.remove(request);
				i--;

				// Get the number of persons in the database
				int number = mListAdapter.getCursor().getCount();

				if (number < 1) {
					// In this case, we don't have a way to know if the request was correctly
					// executed with 0 result or if an error occurred. Here I choose to display an
					// error but it's up to you
					ConnectionErrorDialogFragment.show(this, request, this);
				}

				// Nothing to do if it works as the cursor is automatically updated
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


		mListView = (ListView) findViewById(android.R.id.list);
		mListView.setOnItemClickListener(this);
		mListView.setEmptyView(findViewById(android.R.id.empty));
		mListAdapter = new InvoiceListAdapter(this);
		mListView.setAdapter(mListAdapter);
	}

	private void callInvoiceListWS() {
		setProgressBarIndeterminateVisibility(true);
		Request request = paySystemRequestFactory.getInvoiceListRequest();
		mRequestManager.execute(request, this);
		mRequestList.add(request);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.b_load:
			callInvoiceListWS();
        break;
    
		}
	}

	@Override
	public void onRequestFinished(Request request, Bundle resultData) {
		if (mRequestList.contains(request)) {
			setProgressBarIndeterminateVisibility(false);
			mRequestList.remove(request);

			// Nothing to do if it works as the cursor is automatically updated
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		 int idd = mListAdapter.getCursor().getInt(Invoices.Columns.ID.getIndex());
		 int amount_payable = mListAdapter.getCursor().getInt(Invoices.Columns.AMOUNT_PAYABLE.getIndex());
		 int user_id = mListAdapter.getCursor().getInt(Invoices.Columns.USER.getIndex());
		 
		 Intent intent = new Intent(this, PaymentActivity.class);
		 intent.putExtra("invoice_id", idd);
		 intent.putExtra("amount_payable", amount_payable);
		 intent.putExtra("user_id", user_id);
			startActivity(intent);
		 		 
		
		
	}
	
	@Override
	public void onRequestCustomError(Request request, Bundle resultData) {
		// Never called.
	}

	@Override
	public void connectionErrorDialogCancel(Request request) {
	
	}

	@Override
	public void connectionErrorDialogRetry(Request request) {
		callInvoiceListWS();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		ProviderCriteria criteria = new ProviderCriteria();
		criteria.addSortOrder(Invoices.Columns.AMOUNT_PAYABLE, true);
		return new CursorLoader(this, Invoices.CONTENT_URI, Invoices.PROJECTION, null, null,
        criteria.getOrderClause());
		}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mListAdapter.changeCursor(data);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mListAdapter.changeCursor(null);
	}

	class ViewHolder {
		private TextView mTextViewInvoiceId;
		private TextView mTextViewAmountPayable;
		private TextView mTextViewIssuedDate;
		private CharArrayBuffer mCharArrayBufferIssuedDate;
		
		

		public ViewHolder(View view) {
			mTextViewInvoiceId = (TextView) view.findViewById(R.id.tv_invoice_id);
			mTextViewAmountPayable = (TextView) view.findViewById(R.id.tv_amount_payable);
			mTextViewIssuedDate = (TextView) view.findViewById(R.id.tv_issued_date);
			
    		mCharArrayBufferIssuedDate = new CharArrayBuffer(100);
			
	
   
		}

		public void populateView(Cursor c) {
    
			mTextViewInvoiceId.setText("Invoice ID : "+String.valueOf(c.getInt(Invoices.Columns.ID.getIndex())));
			mTextViewAmountPayable.setText(String.valueOf(c.getInt(Invoices.Columns.AMOUNT_PAYABLE.getIndex())));
			c.copyStringToBuffer(Invoices.Columns.ISSUED_DATE.getIndex(),
					mCharArrayBufferIssuedDate);
			
			mTextViewIssuedDate.setText(mCharArrayBufferIssuedDate.data, 0,
					mCharArrayBufferIssuedDate.sizeCopied);
					
		}
	}

	class InvoiceListAdapter extends CursorAdapter {

		public InvoiceListAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			((ViewHolder) view.getTag()).populateView(cursor);
			
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = mInflater.inflate(R.layout.invoice_list_item, null);
    	view.setTag(new ViewHolder(view));
    	return view;
		}
	}
}
