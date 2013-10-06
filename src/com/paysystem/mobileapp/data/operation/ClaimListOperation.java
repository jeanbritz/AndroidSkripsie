package com.paysystem.mobileapp.data.operation;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

import com.paysystem.mobileapp.config.WSConfig;
import com.paysystem.mobileapp.data.factory.ClaimListJsonFactory;
import com.paysystem.mobileapp.data.model.Claim;
import com.paysystem.mobileapp.data.provider.paySystemContent.Claims;
import com.paysystem.mobileapp.data.provider.paySystemProvider;

import java.util.ArrayList;

public final class ClaimListOperation implements Operation {

    @Override
    public Bundle execute(Context context, Request request) throws ConnectionException,
            DataException {
        

        String url = WSConfig.WS_CLAIM_LIST_URL_JSON;
        NetworkConnection networkConnection = new NetworkConnection(context, url);
        ConnectionResult result = networkConnection.execute();

        ArrayList<Claim> claimList = ClaimListJsonFactory.parseResult(result.body);
        

        // Clear the table
        context.getContentResolver().delete(Claims.CONTENT_URI, null, null);

        // Adds the persons in the database
        int claimListSize = claimList.size();
        if (claimListSize > 0) {
            ArrayList<ContentProviderOperation> operationList =
                    new ArrayList<ContentProviderOperation>();

            for (int i = 0; i < claimListSize; i++) {
                operationList.add(ContentProviderOperation.newInsert(Claims.CONTENT_URI)
                        .withValues(claimList.get(i).toContentValues()).build());
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
