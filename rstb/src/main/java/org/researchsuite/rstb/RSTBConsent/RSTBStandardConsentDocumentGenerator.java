package org.researchsuite.rstb.RSTBConsent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.researchstack.backbone.ResourcePathManager;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentDocumentGenerator;
import org.researchsuite.rstb.RSTBTaskBuilder;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBStandardConsentDocumentGenerator implements RSTBConsentDocumentGenerator {

    private List<String> supportedTypes;
    public RSTBStandardConsentDocumentGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "standardConsentDocument"
        );
    }

    public boolean supportsType(String type) {
        return this.supportedTypes.contains(type);
    }

    @Override
    public ConsentDocument generate(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBStandardConsentDocumentDescriptor descriptor = helper.getGson().fromJson(jsonObject, RSTBStandardConsentDocumentDescriptor.class);

        if (descriptor == null) {
            return null;
        }

        RSTBTaskBuilder taskBuilder = helper.getTaskBuilder();
        if (taskBuilder == null) {
            return null;
        }

        List<ConsentSection> sections = new ArrayList<>();
        for (JsonElement sectionJSON: descriptor.sections) {
            if (sectionJSON.getAsJsonObject() != null &&
                    sectionJSON.getAsJsonObject().get("type") != null &&
                    sectionJSON.getAsJsonObject().get("type").getAsString() != null
                    ) {
                String sectionType = sectionJSON.getAsJsonObject().get("type").getAsString();

                ConsentSection section = taskBuilder.generateConsentSection(helper, sectionType, sectionJSON.getAsJsonObject());
                if (section != null) {
                    sections.add(section);
                }
            }
        }

        List<ConsentSignature> signatures = new ArrayList<>();
        for (JsonElement signatureJSON: descriptor.signatures) {
            if (signatureJSON.getAsJsonObject() != null &&
                    signatureJSON.getAsJsonObject().get("type") != null &&
                    signatureJSON.getAsJsonObject().get("type").getAsString() != null
                    ) {
                String signatureType = signatureJSON.getAsJsonObject().get("type").getAsString();

                ConsentSignature signature = taskBuilder.generateConsentSignature(helper, signatureType, signatureJSON.getAsJsonObject());
                if (signature != null) {
                    signatures.add(signature);
                }
            }
        }

        ResourcePathManager resourcePathManager = helper.getResourcePathManager();

        String mobileCSS = helper.getTextForFilename(descriptor.mobileStyleSheet, "css");
        String pdfCSS = helper.getTextForFilename(descriptor.PDFStyleSheet, "css");

        ConsentDocument document = new RSTBConsentDocument(
                descriptor.title,
                sections,
                signatures,
                mobileCSS,
                pdfCSS
        );

        return document;

    }

}
