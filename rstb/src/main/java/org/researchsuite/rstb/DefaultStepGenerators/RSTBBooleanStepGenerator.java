package org.researchsuite.rstb.DefaultStepGenerators;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.BooleanAnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;

import java.util.Arrays;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBBooleanStepDescriptor;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBTextFieldStepDescriptor;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/29/17.
 */

public class RSTBBooleanStepGenerator extends RSTBQuestionStepGenerator {

    public RSTBBooleanStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "boolean"
        );
    }

    public AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        RSTBBooleanStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, RSTBBooleanStepDescriptor.class);
        if (stepDescriptor == null) {
            return null;
        }
        return new BooleanAnswerFormat(stepDescriptor.trueString, stepDescriptor.falseString);
    }

}
