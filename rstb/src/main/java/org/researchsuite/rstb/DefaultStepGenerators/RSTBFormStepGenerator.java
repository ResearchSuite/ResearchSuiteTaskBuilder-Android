package org.researchsuite.rstb.DefaultStepGenerators;

import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.researchstack.backbone.step.FormStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBConsentReviewStepDescriptor;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBFormStepDescriptor;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBStepDescriptor;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/29/17.
 */

public class RSTBFormStepGenerator extends RSTBBaseStepGenerator {

    public RSTBFormStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "form"
        );
    }

    @Nullable
    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBFormStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, RSTBFormStepDescriptor.class);

        if (stepDescriptor == null) {
            return null;
        }

        List<QuestionStep> formItems = new ArrayList<>();

        for (JsonElement formItemJSON : stepDescriptor.items) {

            RSTBStepDescriptor formItemDescriptor = helper.getGson().fromJson(jsonObject, RSTBStepDescriptor.class);

            //TODO: figure out how to handle sections w/ titles
            //Probably need to update RS to support this

            List<Step> steps = helper.getTaskBuilder().stepsForElement(formItemJSON);
            if (steps != null && steps.size() == 1 && steps.get(0) instanceof QuestionStep) {
                QuestionStep formItem = (QuestionStep) steps.get(0);
                formItems.add(formItem);
            }
        }

        FormStep step = new FormStep(stepDescriptor.identifier, stepDescriptor.title, stepDescriptor.text);
        step.setFormSteps(formItems);

        return step;
    }
}
