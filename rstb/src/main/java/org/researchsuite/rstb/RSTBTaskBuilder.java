package org.researchsuite.rstb;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.researchstack.backbone.ResourcePathManager;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.step.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBElementDescriptor;
import org.researchsuite.rstb.Localization.RSTBLocalizationHelper;
import org.researchsuite.rstb.RSTBConsent.RSTBConsentDocumentGeneratorService;
import org.researchsuite.rstb.RSTBConsent.RSTBConsentSectionGeneratorService;
import org.researchsuite.rstb.RSTBConsent.RSTBConsentSignatureGeneratorService;
import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentDocumentGenerator;
import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentSectionGenerator;
import org.researchsuite.rstb.RSTBConsent.spi.RSTBConsentSignatureGenerator;

/**
 * Created by jameskizer on 12/6/16.
 */
public class RSTBTaskBuilder {

    private static final String TAG = "RSTBTaskBuilder";
    private static RSTBTaskBuilder instance;
    private RSTBTaskBuilderHelper stepBuilderHelper;
    private Context context;

    private RSTBStepGeneratorService stepGeneratorService;

    public RSTBStepGeneratorService getStepGeneratorService() {
        return stepGeneratorService;
    }

    private RSTBElementGeneratorService elementGeneratorService;
    private RSTBAnswerFormatGeneratorService answerFormatGeneratorService;

    private RSTBConsentDocumentGeneratorService consentDocumentGeneratorService;
    private RSTBConsentSectionGeneratorService consentSectionGeneratorService;
    private RSTBConsentSignatureGeneratorService consentSignatureGeneratorService;

    public RSTBTaskBuilder(
            Context context,
            RSTBResourcePathManager resourcePathManager,
            RSTBStateHelper stateHelper,
            @Nullable RSTBLocalizationHelper localizationHelper,
            List<RSTBStepGenerator> stepGenerators,
            List<RSTBElementGenerator> elementGenerators,
            List<RSTBAnswerFormatGenerator> answerFormatGenerators,
            List<RSTBConsentDocumentGenerator> consentDocumentGenerators,
            List<RSTBConsentSectionGenerator> consentSectionGenerators,
            List<RSTBConsentSignatureGenerator> consentSignatureGenerators
    ) {
        this(
                context,
                resourcePathManager,
                new Gson(),
                stateHelper,
                localizationHelper,
                stepGenerators,
                elementGenerators,
                answerFormatGenerators,
                consentDocumentGenerators,
                consentSectionGenerators,
                consentSignatureGenerators
        );
    }

    public RSTBTaskBuilder(
            Context context,
            RSTBResourcePathManager resourcePathManager,
            Gson gson,
            RSTBStateHelper stateHelper,
            @Nullable RSTBLocalizationHelper localizationHelper,
            List<RSTBStepGenerator> stepGenerators,
            List<RSTBElementGenerator> elementGenerators,
            List<RSTBAnswerFormatGenerator> answerFormatGenerators,
            List<RSTBConsentDocumentGenerator> consentDocumentGenerators,
            List<RSTBConsentSectionGenerator> consentSectionGenerators,
            List<RSTBConsentSignatureGenerator> consentSignatureGenerators
    ) {
        this.stepBuilderHelper = new RSTBTaskBuilderHelper(
                context,
                resourcePathManager,
                gson,
                this,
                stateHelper,
                localizationHelper
        );

        this.stepGeneratorService = new RSTBStepGeneratorService(stepGenerators);
        this.elementGeneratorService = new RSTBElementGeneratorService(elementGenerators);
        this.answerFormatGeneratorService = new RSTBAnswerFormatGeneratorService(answerFormatGenerators);
        this.consentDocumentGeneratorService = new RSTBConsentDocumentGeneratorService(consentDocumentGenerators);
        this.consentSectionGeneratorService = new RSTBConsentSectionGeneratorService(consentSectionGenerators);
        this.consentSignatureGeneratorService = new RSTBConsentSignatureGeneratorService(consentSignatureGenerators);
    }

    @Nullable
    public
    List<Step> stepsForElement(JsonElement element) {
        if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            return this.generateSteps(jsonArray);
        }
        else if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();
            return this.generateSteps(jsonObject);
        }
        else {
            return null;
        }
    }

    @Nullable
    public
    List<Step> stepsForElementFilename(String elementFilename) {
        JsonElement element = this.stepBuilderHelper.getJsonElementForFilename(elementFilename);

        System.out.print("got ordered task!!");

        List<Step> stepList = null;
        try {
            stepList = this.stepsForElement(element);
        }
        catch(Exception e) {
            Log.w(this.TAG, "could not create steps from task json", e);
            return null;
        }

        return new ArrayList<>(stepList);
    }


    @Nullable
    private
    List<Step> generateSteps(JsonObject element) {

        RSTBElementDescriptor elementDescriptor = this.stepBuilderHelper.getGson().fromJson(element, RSTBElementDescriptor.class);
        String type = elementDescriptor.type;

        if(StringUtils.isEmpty(type)) {
            return null;
        }

        if(this.elementGeneratorService.supportsType(type)) {

            JsonArray elements = this.elementGeneratorService.generateElements(this.stepBuilderHelper, type, element);
            if (elements != null) {
                return this.generateSteps(elements);
            }
            else {
                return null;
            }

        }
        else {
            List<Step> steps = this.createStepsForObject(type, element);
            if (steps != null) {
                return steps;
            }
            else {
                return null;
            }
        }
    }

    @Nullable
    private
    List<Step> generateSteps(JsonArray arrayOfElements) {

        List<Step> stepList = new ArrayList<>();

        Iterator<JsonElement> elementIter = arrayOfElements.iterator();
        while (elementIter.hasNext()){

            JsonElement element = elementIter.next();

            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                List<Step> addlStepList = this.generateSteps(jsonObject);
                if (addlStepList != null) {
                    stepList.addAll(addlStepList);
                }
            }
            else if (element.isJsonArray()) {
                JsonArray jsonArray = element.getAsJsonArray();
                List<Step> addlStepList = this.generateSteps(jsonArray);
                if (addlStepList != null) {
                    stepList.addAll(addlStepList);
                }
            }
            else {
                assert(false);
            }
        }

        return stepList;
    }

    @Nullable
    public
    List<Step> createStepsForObject(String type, JsonObject jsonObject) {
        return this.stepGeneratorService.generateSteps(type, jsonObject, this.stepBuilderHelper, "");
    }

    @Nullable
    public
    List<Step> createStepsForObject(String type, JsonObject jsonObject, String identifierPrefix) {
        return this.stepGeneratorService.generateSteps(type, jsonObject, this.stepBuilderHelper, identifierPrefix);
    }

    @Nullable
    public AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        return this.answerFormatGeneratorService.generateAnswerFormat(type, jsonObject, helper);
    }


    public RSTBTaskBuilderHelper getStepBuilderHelper() {
        return stepBuilderHelper;
    }

    @Nullable
    public ConsentDocument generateConsentDocument(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        return this.consentDocumentGeneratorService.generate(helper, type, jsonObject);
    }

    @Nullable
    public ConsentSection generateConsentSection(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        return this.consentSectionGeneratorService.generate(helper, type, jsonObject);
    }

    @Nullable
    public ConsentSignature generateConsentSignature(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        return this.consentSignatureGeneratorService.generate(helper, type, jsonObject);
    }
}
