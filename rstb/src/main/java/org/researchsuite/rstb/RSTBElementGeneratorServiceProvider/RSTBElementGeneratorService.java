package org.researchsuite.rstb.RSTBElementGeneratorServiceProvider;

import android.support.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.researchsuite.rstb.RSTBElementGeneratorServiceProvider.spi.RSTBElementGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 12/22/16.
 */
public class RSTBElementGeneratorService {
    private static RSTBElementGeneratorService service;
    private ServiceLoader<RSTBElementGenerator> loader;

    private RSTBElementGeneratorService() {
        this.loader = ServiceLoader.load(RSTBElementGenerator.class);
    }

    public static synchronized RSTBElementGeneratorService getInstance() {
        if (service == null) {
            service = new RSTBElementGeneratorService();
        }
        return service;
    }

    @Nullable
    public JsonArray generateElements(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        JsonArray elements = null;

        try {
            Iterator<RSTBElementGenerator> elementGenerators = loader.iterator();
            while (elements == null && elementGenerators.hasNext()) {
                RSTBElementGenerator elementGenerator = elementGenerators.next();
                if (elementGenerator.supportsType(type)) {
                    elements = elementGenerator.generateElements(helper, type, jsonObject);
                }
            }
        } catch (ServiceConfigurationError serviceError) {
            elements = null;
            serviceError.printStackTrace();
        }

        return elements;
    }


    public List<String> supportedStepTypes() {
        List<String> supportedTypes = new ArrayList<>();

        try {
            Iterator<RSTBElementGenerator> elementGenerators = loader.iterator();
            while (elementGenerators.hasNext()) {
                RSTBElementGenerator elementGenerator = elementGenerators.next();
                supportedTypes.addAll(elementGenerator.supportedStepTypes());
            }

        } catch (ServiceConfigurationError serviceError) {
            supportedTypes = new ArrayList<>();;
            serviceError.printStackTrace();
        }

        return supportedTypes;
    }

    public boolean supportsType(String type) {

        try {
            Iterator<RSTBElementGenerator> elementGenerators = loader.iterator();
            while (elementGenerators.hasNext()) {
                RSTBElementGenerator elementGenerator = elementGenerators.next();
                if (elementGenerator.supportsType(type)) {
                    return true;
                }
            }

        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
            return false;
        }

        return false;
    }
}
