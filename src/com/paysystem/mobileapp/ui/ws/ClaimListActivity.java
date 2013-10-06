package com.paysystem.mobileapp.ui.ws;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.paysystem.mobileapp.R;
import com.paysystem.mobileapp.data.provider.paySystemContent.Claims;
import com.paysystem.mobileapp.data.provider.util.ProviderCriteria;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import com.paysystem.mobileapp.ui.DataDroidActivity;

import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public final class ClaimListActivity extends DataDroidActivity implements RequestListener,
        OnClickListener, ConnectionErrorDialogListener, LoaderCallbacks<Cursor> {

    private Spinner mSpinnerReturnFormat;
    private ListView mListView;
    private ClaimListAdapter mListAdapter;

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
          
            //  mTextViewAge.setText(getString(R.string.person_list_item_tv_age_format,
            //        c.getInt(Claims.Columns.AGE.getIndex())));

           
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
}
