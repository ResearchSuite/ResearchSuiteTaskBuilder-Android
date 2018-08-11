package org.researchsuite.rstb.DefaultStepGenerators;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.Step;

import java.util.Arrays;
import java.util.List;

import org.researchsuite.rstb.RSTBStateHelper;
import org.researchsuite.rstb.RSTBStepGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 12/6/16.
 */
public class RSTBDefaultStepGenerator implements RSTBStepGenerator {

    private static final String TAG = "RSTBDefaultGen";

    private List<String> supportedTypes;

    public RSTBDefaultStepGenerator()
    {
        this.supportedTypes = Arrays.asList(
        );
    }

    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject, String identifierPrefix) {
        try {
            String identifier = jsonObject.get("identifier").getAsString();

            Step instructionStep;
            instructionStep = new InstructionStep(identifier, identifier, type);
            return Arrays.asList(instructionStep);
        }
        catch(Exception e) {
            Log.w(this.TAG, "malformed element: " + jsonObject.getAsString(), e);
            return null;
        }
    }

    public boolean supportsType(String type) {
        return this.supportedTypes.contains(type);
    }

}

