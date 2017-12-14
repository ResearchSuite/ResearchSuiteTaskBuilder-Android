package org.researchsuite.rstb.DefaultStepGenerators;

import android.util.Log;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBQuestionStepDescriptor;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 12/7/16.
 */

public abstract class RSTBQuestionStepGenerator extends RSTBBaseStepGenerator {

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        try {

            AnswerFormat answerFormat = this.generateAnswerFormat(helper, type, jsonObject);

            RSTBQuestionStepDescriptor questionStepDescriptor = helper.getGson().fromJson(jsonObject, RSTBQuestionStepDescriptor.class);

            QuestionStep questionStep = new QuestionStep(questionStepDescriptor.identifier,
                    questionStepDescriptor.title,
                    answerFormat);

            questionStep.setText(questionStepDescriptor.text);
            questionStep.setOptional(questionStepDescriptor.optional);

            return questionStep;
        }
        catch(Exception e) {
            Log.w(this.getClass().getSimpleName(), "malformed element: " + jsonObject.getAsString(), e);
            return null;
        }
    }

    public abstract AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject);

}
