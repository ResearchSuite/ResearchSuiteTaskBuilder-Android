package org.researchsuite.rstb.RSTBConsent;

import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.step.ConsentDocumentStep;

/**
 * Created by jameskizer on 8/29/17.
 */

public class RSTBConsentDocumentStep extends ConsentDocumentStep {

    private ConsentDocument consentDocument;

    public RSTBConsentDocumentStep(String identifier, ConsentDocument consentDocument) {
        super(identifier);

        this.consentDocument = consentDocument;
    }

    @Override
    public String getConsentHTML() {
        if ( consentDocument instanceof RSTBConsentDocument ) {
            return ((RSTBConsentDocument)this.consentDocument).getHTML(true, this.getTitle(), this.getText());
        }
        else {
            return super.getConsentHTML();
        }
    }
}
