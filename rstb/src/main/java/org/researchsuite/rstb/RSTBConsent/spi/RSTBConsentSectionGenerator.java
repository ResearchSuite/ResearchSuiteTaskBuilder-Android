package org.researchsuite.rstb.RSTBConsent.spi;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/28/17.
 */

public interface RSTBConsentSectionGenerator {

    @Nullable
    ConsentSection generate(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject);
    boolean supportsType(String type);

}
