package org.researchsuite.rstb.DefaultStepGenerators;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.DateAnswerFormat;

import java.util.Arrays;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSTBTimeOfDayStepGenerator extends RSTBQuestionStepGenerator {
    public RSTBTimeOfDayStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "timePicker"
        );
    }

    public AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        return new DateAnswerFormat(AnswerFormat.DateAnswerStyle.TimeOfDay);

    }
}
