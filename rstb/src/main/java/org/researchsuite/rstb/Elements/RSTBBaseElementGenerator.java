package org.researchsuite.rstb.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import org.researchsuite.rstb.RSTBElementGenerator;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 12/22/16.
 */
public abstract class RSTBBaseElementGenerator implements RSTBElementGenerator {

    protected List<String> supportedTypes;

    public boolean supportsType(String type) {
        return this.supportedTypes.contains(type);
    }

    public List<String> supportedStepTypes() {
        return this.supportedTypes;
    }

    public abstract JsonArray generateElements(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject);

}
