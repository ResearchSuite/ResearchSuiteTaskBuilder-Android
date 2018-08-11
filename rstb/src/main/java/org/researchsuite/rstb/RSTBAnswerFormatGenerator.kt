package org.researchsuite.rstb

import com.google.gson.JsonObject
import org.researchstack.backbone.answerformat.AnswerFormat

public interface RSTBAnswerFormatGenerator {

    public fun supportsType(type: String): Boolean
    public fun generateAnswerFormat(helper: RSTBTaskBuilderHelper, type: String, jsonObject: JsonObject): AnswerFormat?

}