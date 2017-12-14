package org.researchsuite.rstb.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rstb.Elements.descriptors.RSTBElementFileDescriptor;
import org.researchsuite.rstb.Elements.RSTBBaseElementGenerator;

/**
 * Created by jameskizer on 12/22/16.
 */
public class RSTBElementFileGenerator  extends RSTBBaseElementGenerator {

    public RSTBElementFileGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "elementFile"
        );
    }

    @Override
    public JsonArray generateElements(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBElementFileDescriptor elementFileDescriptor = helper.getGson().fromJson(jsonObject, RSTBElementFileDescriptor.class);

        JsonElement element = helper.getJsonElementForFilename(elementFileDescriptor.elementFileName);

        if (element.isJsonArray()) {
            return element.getAsJsonArray();
        }
        else if (element.isJsonObject()) {
            JsonArray elements = new JsonArray();
            elements.add(element);
            return elements;
        }
        else {
            return null;
        }

    }
}

