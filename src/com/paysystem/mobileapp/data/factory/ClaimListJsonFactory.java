package com.paysystem.mobileapp.data.factory;

import com.foxykeep.datadroid.exception.DataException;
import com.paysystem.mobileapp.config.JSONTag;
import com.paysystem.mobileapp.data.model.Claim;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class ClaimListJsonFactory {

    private static final String TAG = ClaimListJsonFactory.class.getSimpleName();

    private ClaimListJsonFactory() {
        // No public constructor
    }

    public static ArrayList<Claim> parseResult(String wsResponse) throws DataException {
        ArrayList<Claim> claimList = new ArrayList<Claim>();

        try {
        	JSONArray jsonClaimArray =  new JSONArray(wsResponse);
            
            int size = jsonClaimArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonClaim = jsonClaimArray.getJSONObject(i);
                
                Claim claim = new Claim();
                
                claim.claimed = jsonClaim.getBoolean(JSONTag.CLAIM_LIST_ELEM_CLAIMED);
                
                /**
                 *  Filters the claim(s) that has already been claimed. If it has not been claimed don't add it
                 *	To-do: If it has expired also don't add it
                 */
                
                if(!claim.claimed)
                {
                	claim.id = jsonClaim.getInt(JSONTag.CLAIM_LIST_ELEM_ID);
                	claim.username = jsonClaim.getString(JSONTag.CLAIM_LIST_ELEM_USERNAME);
                	claim.title = jsonClaim.getString(JSONTag.CLAIM_LIST_ELEM_TITLE);
                	claim.type = jsonClaim.getString(JSONTag.CLAIM_LIST_ELEM_TYPE);
                	claim.expiry_date = jsonClaim.getString(JSONTag.CLAIM_LIST_ELEM_EXPIRY_DATE);
                   	claim.amount = jsonClaim.getInt(JSONTag.CLAIM_LIST_ELEM_AMOUNT);
                	claimList.add(claim);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            throw new DataException(e);
        }

        return claimList;
    }
}
