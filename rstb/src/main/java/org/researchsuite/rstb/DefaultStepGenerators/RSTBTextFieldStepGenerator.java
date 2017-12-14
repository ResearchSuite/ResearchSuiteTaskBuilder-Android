package org.researchsuite.rstb.DefaultStepGenerators;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;

import java.util.Arrays;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBTextFieldStepDescriptor;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSTBTextFieldStepGenerator extends RSTBQuestionStepGenerator {

    public RSTBTextFieldStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "textfield"
        );
    }

    public AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBTextFieldStepDescriptor textFieldStepDescriptor = helper.getGson().fromJson(jsonObject, RSTBTextFieldStepDescriptor.class);

        TextAnswerFormat answerFormat = new TextAnswerFormat(textFieldStepDescriptor.maximumLength);
        answerFormat.setIsMultipleLines(textFieldStepDescriptor.multipleLines);

        return answerFormat;

    }

}
