package org.researchsuite.rstb.RSTBConsent;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import org.researchstack.backbone.model.ConsentSignature;

import java.util.Arrays;
import java.util.List;

import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentSignatureGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBParticipantConsentSignatureGenerator implements RSTBConsentSignatureGenerator {

    private List<String> supportedTypes;
    public RSTBParticipantConsentSignatureGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "participantConsentSignature"
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

//        public ConsentSignature(@NonNull String identifier, String title, String dateFormat)

        ConsentSignature signature = new ConsentSignature(
                descriptor.identifier,
                descriptor.title,
                descriptor.dateFormatString
        );

        return signature;
    }
}
