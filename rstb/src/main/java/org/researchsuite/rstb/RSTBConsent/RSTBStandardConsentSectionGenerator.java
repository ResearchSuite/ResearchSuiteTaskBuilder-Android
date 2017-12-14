package org.researchsuite.rstb.RSTBConsent;

import com.google.gson.JsonObject;

import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.utils.TextUtils;

import java.util.Arrays;
import java.util.List;

import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentSectionGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBStandardConsentSectionGenerator implements RSTBConsentSectionGenerator {

    private List<String> supportedTypes;
    public RSTBStandardConsentSectionGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "standardConsentSection"
        );
    }

    public boolean supportsType(String type) {
        return this.supportedTypes.contains(type);
    }

    @Override
    public ConsentSection generate(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBStandardConsentSectionDescriptor descriptor = helper.getGson().fromJson(jsonObject, RSTBStandardConsentSectionDescriptor.class);

        if (descriptor == null) {
            return null;
        }

        RSTBConsentSection section = new RSTBConsentSection(descriptor.sectionType);

        String title;
        if (descriptor.sectionType == ConsentSection.Type.Custom ||
                descriptor.sectionType == ConsentSection.Type.OnlyInDocument ) {
            title = descriptor.title;
        }
        else {
            title = TextUtils.isEmpty(descriptor.title) ?
                    helper.getContext().getResources().getString(descriptor.sectionType.getTitleResId()) :
                    descriptor.title;
        }

        section.setTitle(title);
        section.setFormalTitle(descriptor.formalTitle);
        section.setSummary(descriptor.summary);
        section.setContent(descriptor.content);
        section.setHtmlContent(descriptor.htmlContent);
        section.setCustomImageName(descriptor.customImageTitle);
        section.setCustomLearnMoreButtonTitle(descriptor.customLearnMoreButtonTitle);

        return section;

    }

}
