package org.researchsuite.rstb.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    private JsonElement loadJSONElement(RSTBElementFileDescriptor descriptor, RSTBTaskBuilderHelper helper) {

        if (descriptor.elementURLBaseKey != null) {
            String urlBase = (String) helper.getStateHelper().get(helper.getContext(), descriptor.elementURLBaseKey);
            if (urlBase != null && descriptor.elementURLPath != null) {

                String urlString = urlBase + descriptor.elementURLPath;
                try {
                    URL url = new URL(urlString);
                    return helper.getJsonElementForURL(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        }

        if (descriptor.elementFileName != null) {
            return helper.getJsonElementForFilename(descriptor.elementFileName);
        }

        return null;


    }

    @Override
    public JsonArray generateElements(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBElementFileDescriptor elementFileDescriptor = helper.getGson().fromJson(jsonObject, RSTBElementFileDescriptor.class);

        JsonElement element = this.loadJSONElement(elementFileDescriptor, helper);

        if (element == null) {
            return null;
        }

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

//    fun loadJson(context: Context, descriptor: RSJsonFileDescriptor, state: RSState): JsonElement? {
//
//        return (RSStateSelectors.getValue(state, descriptor.urlBaseKey) as? String)?.let { urlBase ->
//                descriptor.urlPath?.let { urlPath ->
//                val url = URL(urlBase + urlPath)
//            this.getJSON(context, url)
//        }
//        } ?: descriptor.filename?.let {
//            TODO("Loading JSON from filename is not supported")
//        }
//
//    }
}

