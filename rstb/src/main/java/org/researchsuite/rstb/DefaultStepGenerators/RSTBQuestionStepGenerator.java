package org.researchsuite.rstb.DefaultStepGenerators;

import android.util.Log;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBQuestionStepDescriptor;
import org.researchsuite.rstb.RSTBAnswerFormatGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jameskizer on 12/7/16.
 */

public abstract class RSTBQuestionStepGenerator extends RSTBBaseStepGenerator implements RSTBAnswerFormatGenerator {

    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject, String identifierPrefix) {
        try {

            AnswerFormat answerFormat = this.generateAnswerFormat(helper, type, jsonObject);

            RSTBQuestionStepDescriptor questionStepDescriptor = helper.getGson().fromJson(jsonObject, RSTBQuestionStepDescriptor.class);

            String identifier = this.combineIdentifiers(questionStepDescriptor.identifier, identifierPrefix);
            Step questionStep = new QuestionStep(identifier,
                    helper.getLocalizationHelper().localizedString(questionStepDescriptor.title),
                    answerFormat
            );

            questionStep.setText(helper.getLocalizationHelper().localizedString(questionStepDescriptor.text));
            questionStep.setOptional(questionStepDescriptor.optional);

            return Arrays.asList(questionStep);
        }
        catch(Exception e) {
            Log.w(this.getClass().getSimpleName(), "malformed element: " + jsonObject.getAsString(), e);
            return null;
        }
    }

    @Nullable
    @Override
    public abstract AnswerFormat generateAnswerFormat(@NotNull RSTBTaskBuilderHelper helper, @NotNull String type, @NotNull JsonObject jsonObject);


}
