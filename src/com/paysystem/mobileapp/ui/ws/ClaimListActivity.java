package com.paysystem.mobileapp.ui.ws;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.paysystem.mobileapp.R;
import com.paysystem.mobileapp.data.model.Claim;
import com.paysystem.mobileapp.data.provider.paySystemContent.Claims;
import com.paysystem.mobileapp.data.provider.util.ProviderCriteria;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import com.paysystem.mobileapp.ui.DataDroidActivity;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public final class ClaimListActivity extends DataDroidActivity implements RequestListener,
        OnClickListener,OnItemLongClickListener,CreateNdefMessageCallback, OnNdefPushCompleteCallback, ConnectionErrorDialogListener, LoaderCallbacks<Cursor> {

    private ListView mListView;
    private ClaimListAdapter mListAdapter;
    private NfcAdapter mNfcAdapter;
    private String rawNDEFClaim = "";
    
    private LayoutInflater mInflater;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.claim_list);
        bindViews();

        getSupportLoaderManager().initLoader(0, null, this);
        mInflater = getLayoutInflater();
       
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG)
					.show();
			finish();
			return;
		}
		// Register callback
		mNfcAdapter.setNdefPushMessageCallback(this, this);
		mNfcAdapter.setOnNdefPushCompleteCallback(this,this);
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
        mListView.setOnItemLongClickListener(this);
        
        mListView.setEmptyView(findViewById(android.R.id.empty));
        mListAdapter = new ClaimListAdapter(this);
        mListView.setAdapter(mListAdapter);
        
        
        
    }

    private void callClaimListWS() {
        setProgressBarIndeterminateVisibility(true);
        Request request = paySystemRequestFactory.getClaimListRequest();
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_load:
            	callClaimListWS();
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
    	callClaimListWS();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        ProviderCriteria criteria = new ProviderCriteria();
        criteria.addSortOrder(Claims.Columns.AMOUNT, true);
        return new CursorLoader(this, Claims.CONTENT_URI, Claims.PROJECTION, null, null,
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
        private TextView mTextViewTitle;
        private CharArrayBuffer mCharArrayBufferTitle;
        private TextView mTextViewType;
        private CharArrayBuffer mCharArrayBufferType;
        private TextView mTextViewExpiryDate;
        private CharArrayBuffer mCharArrayBufferExpiryDate;
        private TextView mTextViewAmount;
        
        public ViewHolder(View view) {
        	mTextViewTitle = (TextView) view.findViewById(R.id.tv_title);
        	mTextViewType = (TextView) view.findViewById(R.id.tv_type);
        	mTextViewExpiryDate = (TextView) view.findViewById(R.id.tv_expiry_date);
        	mTextViewAmount = (TextView) view.findViewById(R.id.tv_amount);
            
        	mCharArrayBufferTitle = new CharArrayBuffer(100);
        	mCharArrayBufferType = new CharArrayBuffer(8);
        	mCharArrayBufferExpiryDate = new CharArrayBuffer(100);
           
        }

        public void populateView(Cursor c) {
            
        	c.copyStringToBuffer(Claims.Columns.TITLE.getIndex(),
            		mCharArrayBufferTitle);
            mTextViewTitle.setText(mCharArrayBufferTitle.data, 0,
            		mCharArrayBufferTitle.sizeCopied);

            c.copyStringToBuffer(Claims.Columns.TYPE.getIndex(), 
            		mCharArrayBufferType);
            mTextViewType.setText(mCharArrayBufferType.data, 0,
            		mCharArrayBufferType.sizeCopied);

            c.copyStringToBuffer(Claims.Columns.EXPIRY_DATE.getIndex(), mCharArrayBufferExpiryDate);
            mTextViewExpiryDate.setText(mCharArrayBufferExpiryDate.data, 0, 
            		mCharArrayBufferExpiryDate.sizeCopied);
            
            mTextViewAmount.setText(String.valueOf(c.getInt(Claims.Columns.AMOUNT
                    .getIndex())));
            
            
                   
        }
    }

    class ClaimListAdapter extends CursorAdapter {

        public ClaimListAdapter(Context context) {
            super(context, null, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ((ViewHolder) view.getTag()).populateView(cursor);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.claim_list_item, null);
            view.setTag(new ViewHolder(view));
            return view;
        }
    }

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		 int idd = mListAdapter.getCursor().getInt(Claims.Columns.ID.getIndex());
		 int user = mListAdapter.getCursor().getInt(Claims.Columns.USER.getIndex());
		 String title = mListAdapter.getCursor().getString(Claims.Columns.TITLE.getIndex());
		 String type = mListAdapter.getCursor().getString(Claims.Columns.TYPE.getIndex());
		 String expiry_date =  mListAdapter.getCursor().getString(Claims.Columns.EXPIRY_DATE.getIndex());
		 int amount  = mListAdapter.getCursor().getInt(Claims.Columns.AMOUNT.getIndex());
		 
		 String user_temp = "";
		 String claim_temp = "";
		 if(user < 10)
		 {
			 user_temp = "0"+user;
		 }
		 else
		 {
			 user_temp = ""+user;
		 }
		 if(idd < 10)
		 {
			 claim_temp = "0"+idd;
		 }
		 else
		 {
			 claim_temp = ""+idd;
		 }
		 
		 if (type.startsWith("V"))
		 {
			 
			 rawNDEFClaim = "V"+user_temp+","+claim_temp+","+title.trim()+","+expiry_date+","+amount+"\0";
		 }
		 else
		 {
			 rawNDEFClaim = "T"+user_temp+","+claim_temp+","+title.trim()+","+expiry_date+","+amount+"\0";
		 }
		 Toast.makeText(this, "Hold device near the Controller Module to proceed", Toast.LENGTH_LONG)
			.show();
		 Log.i("String for NFC", ""+ rawNDEFClaim);
		 
		return false;
		
	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
		rawNDEFClaim = "";
		
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		if (rawNDEFClaim.equals(""))
		{
			Toast.makeText(this, "No item has been specified", Toast.LENGTH_LONG)
			.show();
			return new NdefMessage(new NdefRecord[] {  });
		}
		else
		{
			byte[] textBytes = rawNDEFClaim.getBytes();
			NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
					"text/plain".getBytes(), new byte[] {}, textBytes);
			return new NdefMessage(new NdefRecord[] { textRecord });
		}
		
		
	}
}
