package org.researchsuite.rstb.DefaultStepGenerators;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.BirthDateAnswerFormat;
import org.researchstack.backbone.answerformat.DateAnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.step.ConsentDocumentStep;
import org.researchstack.backbone.step.ConsentSignatureStep;
import org.researchstack.backbone.step.ConsentVisualStep;
import org.researchstack.backbone.step.FormStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.step.layout.ConsentSignatureStepLayout;
import org.researchstack.backbone.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBConsentReviewStepDescriptor;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 8/28/17.
 */

public class RSTBVisualConsentStepGenerator extends RSTBBaseStepGenerator {

    public RSTBVisualConsentStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "visualConsent"
        );
    }

    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

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

        List<Step> steps = new ArrayList<>();

        Context context = helper.getContext();

        for (int i = 0, size = consentDocument.getSections().size(); i < size; i++) {
            ConsentSection section = consentDocument.getSections().get(i);

            if (section.getType() == ConsentSection.Type.OnlyInDocument) {
                continue;
            }

            //this assumes that ConsentSection has their HTML set OR returns HTML based on their section content

            ConsentVisualStep step = new ConsentVisualStep(stepDescriptor.identifier + "." + section.getTitle());
            step.setSection(section);

            steps.add(step);
        }

        //do second pass for button titles
        for (int i = 0, size = steps.size(); i < size; i++) {
            ConsentVisualStep cvs = (ConsentVisualStep)steps.get(i);

            String nextString = context.getString(org.researchstack.backbone.R.string.rsb_next);
            if(i == 0)
            {
                nextString = context.getString(org.researchstack.backbone.R.string.rsb_get_started);
            }
            else if(i == size - 1)
            {
                nextString = context.getString(org.researchstack.backbone.R.string.rsb_done);
            }
            cvs.setNextButtonString(nextString);
        }

        return steps;
    }
}
