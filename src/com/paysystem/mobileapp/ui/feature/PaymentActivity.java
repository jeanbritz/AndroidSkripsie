package com.paysystem.mobileapp.ui.feature;

import com.foxykeep.datadroid.requestmanager.Request;
import com.paysystem.mobileapp.R;
import com.paysystem.mobileapp.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressLint("NewApi")
public class PaymentActivity extends FragmentActivity implements OnClickListener,
OnItemLongClickListener, CreateNdefMessageCallback, OnNdefPushCompleteCallback, ConnectionErrorDialogListener {
	
	private NfcAdapter mNfcAdapter;
	private TextView mInvoiceId;
	private TextView mAmountPayable;
	private TextView mTypeLabel;
	private EditText mEditText;
	private Button mBtnMakePayment;
	String rawNDEFPayment = "P";
	 int invoice_id = 0 ;
	 int amount_payable = 0;
	 int user_id = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_payment);
        invoice_id = extras.getInt("invoice_id");
        amount_payable = extras.getInt("amount_payable");
        user_id = extras.getInt("user_id");
        mInvoiceId = (TextView) findViewById(R.id.tv_invoice_id);
        mInvoiceId.setText("Invoice ID : " + invoice_id + " | User ID : "+ user_id);
        mAmountPayable = (TextView) findViewById(R.id.tv_amount_payable);
        mAmountPayable.setText("Amount payable : "+ amount_payable);
        mTypeLabel = (TextView) findViewById(R.id.tv_type_amount_label);
        mTypeLabel.setText("Type in the amount below :");
        mEditText = (EditText) findViewById(R.id.et_amount);
        mBtnMakePayment = (Button) findViewById(R.id.btn_make_payment);
        mBtnMakePayment.setOnClickListener(this);
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
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public void connectionErrorDialogCancel(Request request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionErrorDialogRetry(Request request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNdefPushComplete(NfcEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		if (rawNDEFPayment.equals("0") | rawNDEFPayment.equals(""))
		{
			Toast.makeText(this, "No amount has been specified", Toast.LENGTH_LONG)
			.show();
			return new NdefMessage(new NdefRecord[] {  });
		}
		else
		{
			byte[] textBytes = rawNDEFPayment.getBytes();
			NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
					"text/plain".getBytes(), new byte[] {}, textBytes);
			return new NdefMessage(new NdefRecord[] { textRecord });
		}
	}

	@Override
	public void onClick(View view) {
		int amount = Integer.parseInt(mEditText.getText().toString());
		String amount_fix = "";
		String user_id_fix = "";
		String invoice_id_fix = "";
		
		switch (view.getId()) {
        case R.id.btn_make_payment:
        	rawNDEFPayment = "P";
        	if(user_id < 10)
   		 	{
        		user_id_fix = "0"+user_id;
   		 	}
        	else
        	{
        		user_id_fix = ""+user_id;
   		 	}
   		 	if(invoice_id < 10)
   		 	{
   		 		invoice_id_fix = "0"+invoice_id;
   		 	}
   		 	else
   		 	{
   		 		invoice_id_fix = ""+invoice_id;
   		 	}
   		 	if(amount >= 1000)
   		 	{
   		 		amount_fix = ""+amount;
   		 	}
   		 	else if(amount < 1000 && amount >= 100)
   		 	{
   		 		amount_fix = "0"+amount;
   		 	}
   		 	else if(amount < 100 && amount >= 10)
   		 	{
   		 		amount_fix = "00"+amount;
   		 	}
   		 	else
   		 	{
   		 		amount_fix = "000"+amount;
   		 	}
   		 	rawNDEFPayment += user_id_fix;
   		 	rawNDEFPayment += ",";
   		 	rawNDEFPayment += invoice_id_fix;
   		 	rawNDEFPayment += ",";
   		 	rawNDEFPayment  += amount_fix;
   		 	rawNDEFPayment += '\0';
   		 	Toast.makeText(this, "Hold device near the Controller Module to proceed", Toast.LENGTH_LONG)
   		 	.show();
   		 	Log.i("String for NFC", ""+ rawNDEFPayment);
        	break;
   		 
   		 }
   		 
        	
        
    }
		
	

}
