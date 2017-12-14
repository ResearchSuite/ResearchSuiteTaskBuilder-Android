package org.researchsuite.rstb.RSTBConsent;

import android.net.Uri;
import android.support.annotation.Nullable;

import org.researchstack.backbone.model.ConsentSection;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBElementDescriptor;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBStandardConsentSectionDescriptor extends RSTBElementDescriptor {

    public ConsentSection.Type sectionType;

    @Nullable
    public String title;

    @Nullable
    public String formalTitle;

    @Nullable
    public String summary;

    @Nullable
    public String content;

    @Nullable
    public String htmlContent;

    @Nullable
    public Uri contentURL;

    public Boolean omitFromDocument = false;

    @Nullable
    public String customImageTitle;

    @Nullable
    public String customLearnMoreButtonTitle;

    @Nullable
    public Uri customAnimationURL;

    public RSTBStandardConsentSectionDescriptor() {

    }

}
