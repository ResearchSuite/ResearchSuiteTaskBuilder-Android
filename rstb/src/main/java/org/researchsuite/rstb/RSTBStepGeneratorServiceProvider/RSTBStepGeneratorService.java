package org.researchsuite.rstb.RSTBStepGeneratorServiceProvider;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rstb.RSTBStepGeneratorServiceProvider.spi.RSTBStepGenerator;

/**
 * Created by jameskizer on 12/6/16.
 */

public class RSTBStepGeneratorService {

    private static RSTBStepGeneratorService service;
    private ServiceLoader<RSTBStepGenerator> loader;

    private RSTBStepGeneratorService() {
        this.loader = ServiceLoader.load(RSTBStepGenerator.class);
    }

    public static synchronized RSTBStepGeneratorService getInstance() {
        if (service == null) {
            service = new RSTBStepGeneratorService();
        }
        return service;
    }

    @Nullable
    public
    List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        try {
            Iterator<RSTBStepGenerator> stepGenerators = loader.iterator();
            while (stepGenerators.hasNext()) {
                RSTBStepGenerator stepGenerator = stepGenerators.next();
                if (stepGenerator.supportsType(type)) {

                    List<Step> steps = stepGenerator.generateSteps(helper, type, jsonObject);
                    if (steps != null) {
                        return steps;
                    }

                    Step step = stepGenerator.generateStep(helper, type, jsonObject);
                    if (step != null) {
                        return Arrays.asList(step);
                    }
                }
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
            return null;
        }
        return null;
    }


    public
    List<String> supportedStepTypes() {
        List<String> supportedTypes = new ArrayList<>();

        try {
            Iterator<RSTBStepGenerator> stepGenerators = loader.iterator();
            while (stepGenerators.hasNext()) {
                RSTBStepGenerator stepGenerator = stepGenerators.next();
                supportedTypes.addAll(stepGenerator.supportedStepTypes());
            }

        } catch (ServiceConfigurationError serviceError) {
            supportedTypes = new ArrayList<>();;
            serviceError.printStackTrace();
        }

        return supportedTypes;
    }

}
