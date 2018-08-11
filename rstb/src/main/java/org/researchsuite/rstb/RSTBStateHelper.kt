package org.researchsuite.rstb

import android.content.Context

interface RSTBStateHelper {
    fun get(context: Context, key: String): Any?
    fun set(context: Context, key: String, value: Any?)
}