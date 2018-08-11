package org.researchsuite.rstb;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;

import java.util.List;

/**
 * Created by jameskizer on 12/6/16.
 */

public interface RSTBStepGenerator {
//    @Deprecated
//    @Nullable
//    Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject);
//
//    @Deprecated
//    @Nullable
//    List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject);

    @Nullable
    List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject, String identifierPrefix);

    boolean supportsType(String type);
//    List<String> supportedStepTypes();
}