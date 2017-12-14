package org.researchsuite.rstb;

import android.content.Context;

/**
 * Created by jameskizer on 2/1/17.
 */
public interface RSTBStateHelper {

    byte[] valueInState(Context context, String key);
    void setValueInState(Context context, String key, byte[] value);

}
