package org.researchsuite.rstb.RSTBConsent.spi;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.researchstack.backbone.model.ConsentDocument;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/23/17.
 */

public interface RSTBConsentDocumentGenerator {

    @Nullable
    ConsentDocument generate(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject);
    boolean supportsType(String type);

}
