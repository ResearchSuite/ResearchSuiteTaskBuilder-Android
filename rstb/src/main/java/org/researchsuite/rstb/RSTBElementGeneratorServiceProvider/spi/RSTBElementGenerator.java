package org.researchsuite.rstb.RSTBElementGeneratorServiceProvider.spi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 12/22/16.
 */
public interface RSTBElementGenerator {
    JsonArray generateElements(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject);
    boolean supportsType(String type);
    List<String> supportedStepTypes();
}
