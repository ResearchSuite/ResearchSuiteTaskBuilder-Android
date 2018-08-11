package org.researchsuite.rstb

import com.google.gson.JsonObject
import org.researchstack.backbone.step.Step

public class RSTBStepGeneratorService(val generators: List<RSTBStepGenerator>) {

    public fun generateSteps(
            type: String,
            jsonObject: JsonObject,
            helper: RSTBTaskBuilderHelper,
            identifierPrefix: String
    ): MutableList<Step>? {

        for (generator in generators) {
            val steps = if (generator.supportsType(type)) generator.generateSteps(
                    helper,
                    type,
                    jsonObject,
                    identifierPrefix
            ) else null
            if (steps != null) {
                return steps
            }
        }

        return null
    }


}