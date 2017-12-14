package org.researchsuite.rstb.DefaultStepGenerators;

import android.util.Log;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.Step;

import java.util.Arrays;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBInstructionStepDescriptor;

/**
 * Created by jameskizer on 12/6/16.
 */
public class RSTBInstructionStepGenerator extends RSTBBaseStepGenerator {

    public RSTBInstructionStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "instruction"
        );
    }

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        try {
            RSTBInstructionStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, RSTBInstructionStepDescriptor.class);
            return new InstructionStep(stepDescriptor.identifier, stepDescriptor.title, stepDescriptor.text);
        }
        catch(Exception e) {
            Log.w(this.getClass().getSimpleName(), "malformed element: " + jsonObject.getAsString(), e);
            return null;
        }
    }
}
