package org.researchsuite.rstb.Localization

class RSTBLocalizationHelperLanguageMap(
        val language: String,
        val map: Map<String, String>
) {

    fun get(key: String): String? {
        return this.map.get(key)
    }

}