package org.researchsuite.rstb.DefaultStepGenerators

import com.google.gson.JsonObject
import org.researchstack.backbone.answerformat.AnswerFormat
import org.researchstack.backbone.answerformat.DateAnswerFormat
import org.researchsuite.rstb.RSTBTaskBuilderHelper
import java.util.*

open public class RSTBDateTimePickerStepGenerator: RSTBQuestionStepGenerator() {


    init {
        this.supportedTypes = listOf("dateTimePicker")
    }

    override fun generateAnswerFormat(helper: RSTBTaskBuilderHelper, type: String, jsonObject: JsonObject): AnswerFormat? {
        return DateAnswerFormat(AnswerFormat.DateAnswerStyle.DateAndTime)
    }

}