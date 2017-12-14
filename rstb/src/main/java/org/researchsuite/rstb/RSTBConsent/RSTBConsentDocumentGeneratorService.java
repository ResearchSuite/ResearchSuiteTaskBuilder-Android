package org.researchsuite.rstb.RSTBConsent;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.researchstack.backbone.model.ConsentDocument;
import java.util.Iterator;
import java.util.List;

import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentDocumentGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/23/17.
 */

public class RSTBConsentDocumentGeneratorService {

    private List<RSTBConsentDocumentGenerator> generators;

    public RSTBConsentDocumentGeneratorService(List<RSTBConsentDocumentGenerator> generators) {
        this.generators = generators;
    }

    @Nullable
    public ConsentDocument generate(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        Iterator<RSTBConsentDocumentGenerator> i = this.generators.iterator();
        while (i.hasNext()) {
            RSTBConsentDocumentGenerator generator = i.next();
            if (generator.supportsType(type)) {

                ConsentDocument document = generator.generate(helper, type, jsonObject);
                if (document != null) {
                    return document;
                }
            }
        }
        return null;
    }

}
