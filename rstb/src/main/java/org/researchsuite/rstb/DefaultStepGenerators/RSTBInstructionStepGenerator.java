package org.researchsuite.rstb.DefaultStepGenerators;

import android.util.Log;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.Step;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject, String identifierPrefix) {
        try {
            RSTBInstructionStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, RSTBInstructionStepDescriptor.class);
            String identifier = this.combineIdentifiers(stepDescriptor.identifier, identifierPrefix);
            Step step = new InstructionStep(identifier, helper.getLocalizationHelper().localizedString(stepDescriptor.title), helper.getLocalizationHelper().localizedString(stepDescriptor.text));
            return Collections.singletonList(step);
        }
        catch(Exception e) {
            Log.w(this.getClass().getSimpleName(), "malformed element: " + jsonObject.getAsString(), e);
            return null;
        }
    }
}
