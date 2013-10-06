package com.paysystem.mobileapp.data.requestmanager;


import com.foxykeep.datadroid.requestmanager.RequestManager;
import com.paysystem.mobileapp.data.service.paySystemRequestService;

import android.content.Context;

/**
 * This class is used as a proxy to call the Service. It provides easy-to-use methods to call the
 * service and manages the Intent creation. It also assures that a request will not be sent again if
 * an exactly identical one is already in progress.
 *
 * @author Foxykeep
 */
public final class paySystemRequestManager extends RequestManager {

    // Singleton management
    private static paySystemRequestManager sInstance;

    public synchronized static paySystemRequestManager from(Context context) {
        if (sInstance == null) {
            sInstance = new paySystemRequestManager(context);
        }

        return sInstance;
    }

    private paySystemRequestManager(Context context) {
        super(context, paySystemRequestService.class);
    }
}


