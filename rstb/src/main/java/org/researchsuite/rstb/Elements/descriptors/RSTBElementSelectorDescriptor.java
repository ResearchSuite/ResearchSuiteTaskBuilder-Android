package org.researchsuite.rstb.Elements.descriptors;

import com.google.gson.JsonArray;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBElementDescriptor;

/**
 * Created by jameskizer on 12/22/16.
 */
public class RSTBElementSelectorDescriptor extends RSTBElementDescriptor {


    public JsonArray elements;
    public String selectorType;
    public RSTBElementSelectorDescriptor() {

    }

}