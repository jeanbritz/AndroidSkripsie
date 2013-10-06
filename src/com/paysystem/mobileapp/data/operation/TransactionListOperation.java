package com.paysystem.mobileapp.data.operation;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

import com.paysystem.mobileapp.config.WSConfig;
import com.paysystem.mobileapp.data.factory.TransactionListJsonFactory;
import com.paysystem.mobileapp.data.model.Transaction;
import com.paysystem.mobileapp.data.provider.paySystemContent.Transactions;
import com.paysystem.mobileapp.data.provider.paySystemProvider;

import java.util.ArrayList;

public final class TransactionListOperation implements Operation {

    @Override
    public Bundle execute(Context context, Request request) throws ConnectionException,
            DataException {
        

        String url = WSConfig.WS_TRANSACTION_LIST_URL_JSON;
        NetworkConnection networkConnection = new NetworkConnection(context, url);
        ConnectionResult result = networkConnection.execute();
        Log.i("HTTP REQUEST", "GET" + url);
        ArrayList<Transaction> transactionList = TransactionListJsonFactory.parseResult(result.body);
        

        // Clear the table
        context.getContentResolver().delete(Transactions.CONTENT_URI, null, null);

        // Adds the persons in the database
        int transactionListSize = transactionList.size();
        if (transactionListSize > 0) {
            ArrayList<ContentProviderOperation> operationList =
                    new ArrayList<ContentProviderOperation>();

            for (int i = 0; i < transactionListSize; i++) {
                operationList.add(ContentProviderOperation.newInsert(Transactions.CONTENT_URI)
                        .withValues(transactionList.get(i).toContentValues()).build());
            }

            try {
                context.getContentResolver().applyBatch(paySystemProvider.AUTHORITY, operationList);
            } catch (RemoteException e) {
                throw new DataException(e);
            } catch (OperationApplicationException e) {
                throw new DataException(e);
            }
        }

        return null;
    }
}

