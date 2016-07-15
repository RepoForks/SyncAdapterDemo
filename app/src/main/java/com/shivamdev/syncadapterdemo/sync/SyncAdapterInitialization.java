package com.shivamdev.syncadapterdemo.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.shivamdev.syncadapterdemo.network.Constants;


public class SyncAdapterInitialization {
    Account mAccount;
    // A content resolver for accessing the provider
    private ContentResolver mResolver;
    private Context mcontext;


    public SyncAdapterInitialization(Context mcontext) {
        this.mcontext = mcontext;
        init();
    }

    private void init() {
        mAccount = createSyncAccount(mcontext);
        mResolver = mcontext.getContentResolver();
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    private Account createSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                Constants.ACCOUNT, Constants.ACCOUNT_TYPE);
        // Get an instance of the Android account manager
//        AccountManager accountManager =
//                (AccountManager) context.getSystemService(
//                        ACCOUNT_SERVICE);
        AccountManager accountManager = AccountManager.get(context);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {

        }
        return newAccount;
    }

    /**
     * PASSING BUNDEL IS MANDATORY
     * IF DONT HAVE ANYTHING TO PASS IN BUNDEL JUST CREATE THE INSTANCE OF THE BUNDEL AND PASS
     * IF PASS BUNDEL AS NULL WILL THROW EXCEPTION
     *
     * @param bundle
     */

    public void startForceSyncing(Bundle bundle) {
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        Account account = new Account(Constants.ACCOUNT, Constants.ACCOUNT_TYPE);
        ContentResolver.requestSync(account, Constants.AUTHORITY, bundle);
    }


}
