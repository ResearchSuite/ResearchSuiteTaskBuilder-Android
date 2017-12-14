package org.researchsuite.rstb.DefaultStepGenerators.descriptors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;

/**
 * Created by jameskizer on 12/8/16.
 */
public class RSTBCustomStepDescriptor extends RSTBStepDescriptor {

    public JsonObject parameters;
    public String parameterFileName;
    RSTBCustomStepDescriptor() {

    }

}
