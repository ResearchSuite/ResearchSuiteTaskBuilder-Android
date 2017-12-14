package org.researchsuite.rstb.DefaultStepGenerators;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;

import java.util.List;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rstb.RSTBStepGeneratorServiceProvider.spi.RSTBStepGenerator;

/**
 * Created by jameskizer on 12/6/16.
 */
public abstract class RSTBBaseStepGenerator implements RSTBStepGenerator {
    protected List<String> supportedTypes;

    public boolean supportsType(String type) {
        return this.supportedTypes.contains(type);
    }

    public List<String> supportedStepTypes() {
        return this.supportedTypes;
    }

    @Override
    @Nullable
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        return null;
    }

    @Nullable
    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        return null;
    }
}
