package org.researchsuite.rstb

import com.google.gson.JsonObject
import org.researchstack.backbone.answerformat.AnswerFormat

class RSTBAnswerFormatGeneratorService(val generators: List<RSTBAnswerFormatGenerator>) {

    fun generateAnswerFormat(
            type: String,
            jsonObject: JsonObject,
            helper: RSTBTaskBuilderHelper
    ): AnswerFormat? {

        for (generator in generators) {
            if (generator.supportsType(type)) {
                val answerFormat = generator.generateAnswerFormat(helper, type, jsonObject)
                if (answerFormat != null) {
                    return answerFormat
                }

            }
        }

        return null
    }


}