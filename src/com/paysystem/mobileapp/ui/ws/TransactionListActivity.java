package com.paysystem.mobileapp.ui.ws;

import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.paysystem.mobileapp.R;
import com.paysystem.mobileapp.data.provider.paySystemContent.Transactions;
import com.paysystem.mobileapp.data.provider.util.ProviderCriteria;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import com.paysystem.mobileapp.ui.DataDroidActivity;


public final class TransactionListActivity extends DataDroidActivity implements RequestListener,
	OnClickListener, ConnectionErrorDialogListener, LoaderCallbacks<Cursor> {

	private Spinner mSpinnerReturnFormat;
	private ListView mListView;
	private TransactionListAdapter mListAdapter;

	private LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.transaction_list);
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

				// Get the number of transactions in the database
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
		mListView.setEmptyView(findViewById(android.R.id.empty));
		mListAdapter = new TransactionListAdapter(this);
		mListView.setAdapter(mListAdapter);
	}

	private void callTransactionListWS() {
		setProgressBarIndeterminateVisibility(true);
		Request request = paySystemRequestFactory.getTransactionListRequest();
		mRequestManager.execute(request, this);
		mRequestList.add(request);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.b_load:
			callTransactionListWS();
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
	public void onRequestCustomError(Request request, Bundle resultData) {
		// Never called.
	}

	@Override
	public void connectionErrorDialogCancel(Request request) {
	}

	@Override
	public void connectionErrorDialogRetry(Request request) {
		callTransactionListWS();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		ProviderCriteria criteria = new ProviderCriteria();
		criteria.addSortOrder(Transactions.Columns.AMOUNT, true);
		return new CursorLoader(this, Transactions.CONTENT_URI, Transactions.PROJECTION, null, null,
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
		private TextView mTextViewProcessedDate;
		private CharArrayBuffer mCharArrayBufferProcessedDate;
		private TextView mTextViewAmount;
		private TextView mTextViewDebitCredit;
		private CharArrayBuffer mCharArrayBufferDebitCredit;

		public ViewHolder(View view) {
			mTextViewInvoiceId = (TextView) view.findViewById(R.id.tv_invoice_id);
			mTextViewProcessedDate = (TextView) view.findViewById(R.id.tv_processed_date);
			mTextViewAmount = (TextView) view.findViewById(R.id.tv_amount);
			mTextViewDebitCredit = (TextView) view.findViewById(R.id.tv_debit_credit);
    
			mCharArrayBufferProcessedDate = new CharArrayBuffer(100);
			mCharArrayBufferDebitCredit = new CharArrayBuffer(6);
	
   
		}

		public void populateView(Cursor c) {
    
			mTextViewInvoiceId.setText(String.valueOf(c.getInt(Transactions.Columns.INVOICE_ID.getIndex())));
			c.copyStringToBuffer(Transactions.Columns.PROCESSED_DATE.getIndex(),
					mCharArrayBufferProcessedDate);
			mTextViewProcessedDate.setText(mCharArrayBufferProcessedDate.data, 0,
					mCharArrayBufferProcessedDate.sizeCopied);
			mTextViewAmount.setText(String.valueOf(c.getInt(Transactions.Columns.AMOUNT.getIndex())));
			c.copyStringToBuffer(Transactions.Columns.DEBIT_CREDIT.getIndex(), 
					mCharArrayBufferDebitCredit);
			mTextViewDebitCredit.setText(mCharArrayBufferDebitCredit.data, 0,
					mCharArrayBufferDebitCredit.sizeCopied);

			
		}
	}

	class TransactionListAdapter extends CursorAdapter {

		public TransactionListAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			((ViewHolder) view.getTag()).populateView(cursor);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = mInflater.inflate(R.layout.transaction_list_item, null);
			view.setTag(new ViewHolder(view));
			return view;
		}	
	}
}
