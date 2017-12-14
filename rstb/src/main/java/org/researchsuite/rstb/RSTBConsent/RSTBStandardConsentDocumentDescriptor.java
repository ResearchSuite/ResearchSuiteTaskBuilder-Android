package org.researchsuite.rstb.RSTBConsent;

import com.google.gson.JsonElement;

import java.util.List;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBElementDescriptor;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBStandardConsentDocumentDescriptor extends RSTBElementDescriptor {

    public List<JsonElement> sections;
    public List<JsonElement> signatures;
    public String title;
    public String mobileStyleSheet;
    public String PDFStyleSheet;

    public RSTBStandardConsentDocumentDescriptor() {

    }

}
