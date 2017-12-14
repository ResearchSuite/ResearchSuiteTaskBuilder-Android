package org.researchsuite.rstb.DefaultStepGenerators.descriptors;

import android.support.annotation.Nullable;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSTBStepDescriptor extends RSTBElementDescriptor {

    public boolean optional = true;
    @Nullable
    public String title;

    @Nullable
    public String text;
    public RSTBStepDescriptor() {

    }

}
