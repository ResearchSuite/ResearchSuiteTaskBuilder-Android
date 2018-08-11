package org.researchsuite.rstb.Localization

class RSTBLocalizationHelper(
        val baseMap: Map<String, String>? = null,
        val languageMap: RSTBLocalizationHelperLanguageMap? = null
) {

    val configuredLanguage: String?
        get() = this.languageMap?.language

    fun localizedString(stringOpt: String?): String? {
        return stringOpt?.let {

            this.languageMap?.get(it) ?:
                this.baseMap?.get(it) ?:
                it

        }
    }
}