package org.researchsuite.rstb.DefaultStepGenerators;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.researchstack.backbone.step.Step;

import java.util.Collections;
import java.util.List;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rstb.RSTBStepGenerator;

/**
 * Created by jameskizer on 12/6/16.
 */

public abstract class RSTBBaseStepGenerator implements RSTBStepGenerator {
    protected List<String> supportedTypes;

    public boolean supportsType(String type) {
        return this.supportedTypes.contains(type);
    }

    public String combineIdentifiers(String identifier, String identifierPrefix) {
        return new StringBuilder(identifierPrefix)
                .append(".")
                .append(identifier)
                .toString();
    }

//    public List<String> supportedStepTypes() {
//        return this.supportedTypes;
//    }

//    @Override
//    @Nullable
//    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
//        Step step = this.generateStep(helper, type, jsonObject);
//        if (step != null) {
//            return Collections.singletonList(step);
//        }
//        else {
//            return null;
//        }
//    }

//    private JsonObject getSplicedJson(JsonObject jsonObject, String identifierPrefix) {
//        if (identifierPrefix == null || identifierPrefix == "") {
//            return jsonObject;
//        }
//        else {
//            if (jsonObject.has("identifier") && jsonObject.get("identifier").isJsonPrimitive()) {
//
//                String newIdentifier = new StringBuilder(identifierPrefix)
//                        .append(".")
//                        .append(jsonObject.get("identifier").getAsString())
//                        .toString();
//
//                JsonObject newJsonObject = jsonObject.deepCopy();
//                newJsonObject.addProperty("identifier", newIdentifier);
//                return newJsonObject;
//            }
//            else {
//                return null;
//            }
//        }
//    }

    @Nullable
    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject, String identifierPrefix) {

//        JsonObject splicedJson = this.getSplicedJson(jsonObject, identifierPrefix);

        return null;
    }
}
