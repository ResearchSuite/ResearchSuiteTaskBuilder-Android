package org.researchsuite.rstb.RSTBConsent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.researchstack.backbone.ResourcePathManager;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.step.ConsentVisualStep;
import org.researchstack.backbone.step.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBConsentReviewStepDescriptor;
import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentSignatureGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBInvestigatorConsentSignatureGenerator implements RSTBConsentSignatureGenerator {

    private List<String> supportedTypes;
    public RSTBInvestigatorConsentSignatureGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "investigatorConsentSignature"
        );
    }

    public boolean supportsType(String type) {
        return this.supportedTypes.contains(type);
    }

    @Override
    public ConsentSignature generate(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBConsentSignatureDescriptor descriptor = helper.getGson().fromJson(jsonObject, RSTBConsentSignatureDescriptor.class);

        if (descriptor == null) {
            return null;
        }

        ResourcePathManager resourcePathManager = helper.getResourcePathManager();
        byte[] bytes = resourcePathManager.getResourceAsBytes(helper.getContext(), descriptor.signatureImageTitle);

        //TODO: need to load image and convert to base64
        String signatureImage = Base64.encodeToString(bytes, Base64.DEFAULT);

        ConsentSignature signature = new ConsentSignature(
                descriptor.identifier,
                descriptor.title,
                descriptor.dateFormatString,
                descriptor.givenName + " " + descriptor.familyName,
                signatureImage,
                descriptor.signatureDateString
        );

        return signature;
    }

}
