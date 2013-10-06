package com.paysystem.mobileapp.data.service;

import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService;

import com.paysystem.mobileapp.data.exception.MyCustomRequestException;
import com.paysystem.mobileapp.data.operation.AuthenticationOperation;
import com.paysystem.mobileapp.data.operation.ClaimListOperation;
import com.paysystem.mobileapp.data.operation.TransactionListOperation;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;

import android.content.Intent;
import android.os.Bundle;

/**
 * This class is called by the {@link paySystemRequestManager} through the {@link Intent} system.
 *
 * @author Foxykeep
 */
public final class paySystemRequestService extends RequestService {

    @Override
    protected int getMaximumNumberOfThreads() {
        return 3;
    }

    @Override
    public Operation getOperationForType(int requestType) {
        switch (requestType) {
            case paySystemRequestFactory.REQUEST_TYPE_CLAIM_LIST:
                return new ClaimListOperation();
            case paySystemRequestFactory.REQUEST_TYPE_TRANSACTION_LIST:
            	return new TransactionListOperation();
            case paySystemRequestFactory.REQUEST_TYPE_AUTHENTICATION:
                return new AuthenticationOperation();
            
                        
        }
        return null;
    }

    @Override
    protected Bundle onCustomRequestException(Request request, CustomRequestException exception) {
        if (exception instanceof MyCustomRequestException) {
            Bundle bundle = new Bundle();
            //bundle.putString(paySystemRequestFactory.BUNDLE_EXTRA_ERROR_MESSAGE,
               //     "MyCustomRequestException thrown.");
            return bundle;
        }
        return super.onCustomRequestException(request, exception);
    }
}
