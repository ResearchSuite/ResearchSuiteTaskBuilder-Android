package org.researchsuite.rstb.DefaultStepGenerators;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.BirthDateAnswerFormat;
import org.researchstack.backbone.answerformat.DateAnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.step.ConsentDocumentStep;
import org.researchstack.backbone.step.ConsentSignatureStep;
import org.researchstack.backbone.step.FormStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.step.layout.ConsentSignatureStepLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBConsentReviewStepDescriptor;
import org.researchsuite.rstb.RSTBConsent.RSTBConsentDocumentStep;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/23/17.
 */

public class RSTBConsentReviewStepGenerator extends RSTBBaseStepGenerator {

    public static final String ID_CONSENT_DOC    = "consent_review_doc";
    public static final String ID_FORM           = "ID_FORM";
    public static final String ID_FORM_NAME      = "ID_FORM_NAME";
    public static final String ID_FORM_DOB       = "ID_FORM_DOB";
    public static final String ID_FORM_BIRTHDATE = "ID_FORM_BIRTHDATE";
    public static final String ID_SIGNATURE      = "ID_SIGNATURE";

    public RSTBConsentReviewStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "consentReview"
        );
    }



    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject, String identifierPrefix) {

        RSTBConsentReviewStepDescriptor stepDescriptor = helper.getGson().fromJson(jsonObject, RSTBConsentReviewStepDescriptor.class);

        if (stepDescriptor == null) {
            return null;
        }

        JsonElement element = helper.getJsonElementForFilename(stepDescriptor.consentDocumentFilename);
        if (element == null || !element.isJsonObject()) {
            return null;
        }

        JsonObject consentDocumentJSON = element.getAsJsonObject();
        if (consentDocumentJSON.get("type") == null || consentDocumentJSON.get("type").getAsString() == null) {
            return null;
        }

        String consentDocType = consentDocumentJSON.get("type").getAsString();

        ConsentDocument consentDocument = helper.getTaskBuilder().generateConsentDocument(helper, consentDocType, consentDocumentJSON);
        if (consentDocument == null) {
            return null;
        }

        ConsentSignature participantSignature = consentDocument.getSignature(0);
        if (participantSignature == null) {
            return null;
        }

        List<Step> steps = new ArrayList<>();

        Context context = helper.getContext();
        //RStack breaks this into mulitple steps
        //1) HTML version of the PDF doc
//        StringBuilder docBuilder = new StringBuilder(
//                "</br><div style=\"padding: 10px 10px 10px 10px;\" class='header'>");
//        String title = (stepDescriptor.title != null) ? stepDescriptor.title : "Review";
//        docBuilder.append(String.format(
//                "<h1 style=\"text-align: center; font-family:sans-serif-light;\">%1$s</h1>",
//                title));
//        String text = (stepDescriptor.text != null) ? stepDescriptor.text : "Review the form below, and tap Agree if you're ready to continue.";
//        docBuilder.append(String.format("<p style=\"text-align: center\">%1$s</p>", text));
//        docBuilder.append("</div></br>");
//        docBuilder.append(consentDocument.getHtmlReviewContent());
//
//        ConsentDocumentStep step = new ConsentDocumentStep(stepDescriptor.identifier + "." + ID_CONSENT_DOC);
//        step.setConsentHTML(docBuilder.toString());

        String identifier = this.combineIdentifiers(stepDescriptor.identifier, identifierPrefix);
        RSTBConsentDocumentStep step = new RSTBConsentDocumentStep(
                identifier + "." + ID_CONSENT_DOC,
                consentDocument
        );

        step.setConfirmMessage(stepDescriptor.reasonForConsent);
        step.setTitle((stepDescriptor.title != null) ? stepDescriptor.title : "Review");
        step.setText((stepDescriptor.text != null) ? stepDescriptor.text : "Review the form below, and tap Agree if you're ready to continue.");
        steps.add(step);

        //2) Name / Birthday  form


        // Add full-name input
        boolean requiresName = participantSignature.requiresName();
        boolean requiresBirthDate = participantSignature.requiresBirthDate();
        if(requiresName || requiresBirthDate)
        {
            List<QuestionStep> formSteps = new ArrayList<>();
            if(requiresName)
            {
                TextAnswerFormat format = new TextAnswerFormat();
                format.setIsMultipleLines(false);

//                String placeholder = context.getResources()
//                        .getString(org.researchstack.backbone.R.string.rsb_consent_name_placeholder);
                String nameText = context.getResources().getString(org.researchstack.backbone.R.string.rsb_consent_name_full);
                formSteps.add(new QuestionStep(ID_FORM_NAME, nameText, format));
            }

            if(requiresBirthDate)
            {
                Calendar maxDate = Calendar.getInstance();
                maxDate.add(Calendar.YEAR, - 18);
                DateAnswerFormat dobFormat = new BirthDateAnswerFormat(null, 18, 0);
                String dobText = context.getResources().getString(org.researchstack.backbone.R.string.rsb_consent_dob_full);
                formSteps.add(new QuestionStep(ID_FORM_DOB, dobText, dobFormat));
            }

            String formTitle = context.getString(org.researchstack.backbone.R.string.rsb_consent_form_title);
            FormStep formStep = new FormStep(identifier + "." + ID_FORM, formTitle, step.getText());
            formStep.setStepTitle(org.researchstack.backbone.R.string.rsb_consent);
            formStep.setOptional(false);
            formStep.setFormSteps(formSteps);
            steps.add(formStep);
        }

        //3) Get signature
        // Add signature input
        if(participantSignature.requiresSignatureImage())
        {
            ConsentSignatureStep signatureStep = new ConsentSignatureStep(identifier + "." + ID_SIGNATURE);
            signatureStep.setStepTitle(org.researchstack.backbone.R.string.rsb_consent);
            signatureStep.setTitle(context.getString(org.researchstack.backbone.R.string.rsb_consent_signature_title));
            signatureStep.setText(context.getString(org.researchstack.backbone.R.string.rsb_consent_signature_instruction));
            signatureStep.setOptional(false);
            signatureStep.setSignatureDateFormat(participantSignature
                    .getSignatureDateFormatString());
            signatureStep.setStepLayoutClass(ConsentSignatureStepLayout.class);
            steps.add(signatureStep);
        }

        return steps;
    }


}
