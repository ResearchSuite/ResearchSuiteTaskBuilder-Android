package org.researchsuite.rstb.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;

import org.researchsuite.rstb.RSTBHelpers;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rstb.Elements.descriptors.RSTBElementListDescriptor;

/**
 * Created by jameskizer on 12/22/16.
 */
public class RSTBElementListGenerator extends RSTBBaseElementGenerator {

    public RSTBElementListGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "elementList"
        );
    }

    @Override
    public JsonArray generateElements(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBElementListDescriptor elementListDescriptor = helper.getGson().fromJson(jsonObject, RSTBElementListDescriptor.class);

        JsonArray elements = elementListDescriptor.elements;
        if (elementListDescriptor.shuffleElements) {
            elements = RSTBHelpers.shuffled(elements);
        }

        return elements;
    }
}
