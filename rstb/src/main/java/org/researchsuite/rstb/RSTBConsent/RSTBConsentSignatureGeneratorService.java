package org.researchsuite.rstb.RSTBConsent;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.researchstack.backbone.model.ConsentSignature;

import java.util.Iterator;
import java.util.List;

import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentSignatureGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBConsentSignatureGeneratorService {

    private List<RSTBConsentSignatureGenerator> generators;

    public RSTBConsentSignatureGeneratorService(List<RSTBConsentSignatureGenerator> generators) {
        this.generators = generators;
    }

    @Nullable
    public ConsentSignature generate(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        Iterator<RSTBConsentSignatureGenerator> i = this.generators.iterator();
        while (i.hasNext()) {
            RSTBConsentSignatureGenerator generator = i.next();
            if (generator.supportsType(type)) {

                ConsentSignature signature = generator.generate(helper, type, jsonObject);
                if (signature != null) {
                    return signature;
                }
            }
        }
        return null;
    }

}
