package org.researchsuite.rstb

import com.google.gson.JsonArray
import com.google.gson.JsonObject

class RSTBElementGeneratorService(val generators: List<RSTBElementGenerator>) {

    fun generateElements(helper: RSTBTaskBuilderHelper, type: String, jsonObject: JsonObject): JsonArray? {

        for (generator in generators) {
            val elements = if (generator.supportsType(type)) generator.generateElements(helper, type, jsonObject) else null
            if (elements != null) {
                return elements
            }
        }

        return null

    }

    fun supportsType(type: String): Boolean {
        return generators.any { it.supportsType(type) }
    }

}