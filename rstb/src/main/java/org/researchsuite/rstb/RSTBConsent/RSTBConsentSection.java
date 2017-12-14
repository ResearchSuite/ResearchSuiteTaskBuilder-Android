package org.researchsuite.rstb.RSTBConsent;

import android.net.Uri;

import org.researchstack.backbone.model.ConsentSection;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBConsentSection extends ConsentSection {

    protected String formalTitle;
    protected String customImageName;
    protected String customLearnMoreButtonTitle;

    /**
     * Returns an initialized consent section using the specified type.
     *
     * @param type The consent section type.
     */
    public RSTBConsentSection(Type type) {
        super(type);
    }

    @Override
    public String getFormalTitle() {
        return formalTitle;
    }

    public void setFormalTitle(String formalTitle) {
        this.formalTitle = formalTitle;
    }

    @Override
    public String getCustomImageName() {
        return customImageName;
    }

    public void setCustomImageName(String customImageName) {
        this.customImageName = customImageName;
    }

    @Override
    public String getCustomLearnMoreButtonTitle() {
        return customLearnMoreButtonTitle;
    }

    public void setCustomLearnMoreButtonTitle(String customLearnMoreButtonTitle) {
        this.customLearnMoreButtonTitle = customLearnMoreButtonTitle;
    }
}
