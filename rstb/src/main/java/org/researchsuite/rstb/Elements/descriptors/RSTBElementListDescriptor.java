package org.researchsuite.rstb.Elements.descriptors;

import com.google.gson.JsonArray;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBElementDescriptor;

/**
 * Created by jameskizer on 12/22/16.
 */
public class RSTBElementListDescriptor extends RSTBElementDescriptor {


    public JsonArray elements;
    public boolean shuffleElements;
    public RSTBElementListDescriptor() {

    }

}
