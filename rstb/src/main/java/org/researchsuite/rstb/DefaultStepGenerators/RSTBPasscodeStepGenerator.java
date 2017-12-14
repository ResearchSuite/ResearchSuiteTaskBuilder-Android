package org.researchsuite.rstb.DefaultStepGenerators;

import android.util.Log;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;
// TODO: Remove dependency on Skin
import org.researchstack.skin.step.PassCodeCreationStep;

import java.util.Arrays;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBStepDescriptor;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 9/11/17.
 */

public class RSTBPasscodeStepGenerator extends RSTBBaseStepGenerator {

    public RSTBPasscodeStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "passcode"
        );
    }

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        RSTBStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, RSTBStepDescriptor.class);
        Step step = new PassCodeCreationStep(stepDescriptor.identifier, 0);
        step.setTitle(stepDescriptor.title);
        step.setText(stepDescriptor.text);
        return step;
    }

}
