package com.paysystem.mobileapp.ui;


import com.paysystem.mobileapp.R;
import com.paysystem.mobileapp.dialogs.SampleDescriptionDialogFragment;
import com.paysystem.mobileapp.ui.feature.AuthenticationActivity;
import com.paysystem.mobileapp.ui.feature.CustomRequestExceptionActivity;
import com.paysystem.mobileapp.ui.feature.RefreshActivity;
import com.paysystem.mobileapp.ui.ws.ClaimListActivity;
import com.paysystem.mobileapp.ui.ws.InvoiceListActivity;
import com.paysystem.mobileapp.ui.ws.TransactionListActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public final class HomeActivity extends FragmentActivity implements OnItemClickListener,
	OnItemLongClickListener {

	private LayoutInflater mInflater;

	private SampleListAdapter mListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		mInflater = getLayoutInflater();

		populateViews();
	}

	private void populateViews() {
		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);

		mListAdapter = new SampleListAdapter(this);
		listView.setAdapter(mListAdapter);

		populateAdapter();
	}

	private void populateAdapter() {
		mListAdapter.setNotifyOnChange(false);

		mListAdapter.add(new Sample(R.string.home_claim_list_title,
				R.string.home_claim_list_description, ClaimListActivity.class));
		
		mListAdapter.add(new Sample(R.string.home_transaction_list_title,
				R.string.home_transaction_list_description, TransactionListActivity.class));
		
		mListAdapter.add(new Sample(R.string.home_invoice_list_title,
				R.string.home_invoice_list_description, InvoiceListActivity.class));
		
		
		mListAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Sample sample = mListAdapter.getItem(position);

			Intent intent = new Intent(this, sample.activityKlass);
			startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Sample sample = mListAdapter.getItem(position);

		SampleDescriptionDialogFragment.show(this, sample.titleResId, sample.descriptionResId);
		return true;
	}

	private final class SampleListAdapter extends ArrayAdapter<Sample> {

		public SampleListAdapter(Context context) {
			super(context, 0);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView;
			if (convertView == null) {
				textView = (TextView) mInflater.inflate(android.R.layout.simple_list_item_1, null);
			} else {
				textView = (TextView) convertView;
			}

			Sample sample = getItem(position);
			textView.setText(sample.titleResId);

			return textView;
		}
	}

	private final class Sample {
		public int titleResId;
		public int descriptionResId;
		public Class<? extends Activity> activityKlass;

		public Sample(int titleResId, int descriptionResId,
				Class<? extends Activity> activityKlass) {
			this.titleResId = titleResId;
			this.descriptionResId = descriptionResId;
			this.activityKlass = activityKlass;
		}
	}
}