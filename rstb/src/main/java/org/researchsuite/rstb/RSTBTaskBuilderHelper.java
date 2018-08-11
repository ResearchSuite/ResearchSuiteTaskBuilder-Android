package org.researchsuite.rstb;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.researchstack.backbone.ResourcePathManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBCustomStepDescriptor;

/**
 * Created by jameskizer on 12/8/16.
 */
public class RSTBTaskBuilderHelper {

    private Context context;
    private RSTBResourcePathManager resourcePathManager;
    private Gson gson;
    private JsonParser jsonParser;
    private RSTBTaskBuilder taskBuilder;
    private RSTBStateHelper stateHelper;
    private int defaultResourceType;

    public int getDefaultResourceType() {
        return defaultResourceType;
    }

    public void setDefaultResourceType(int defaultResourceType) {
        this.defaultResourceType = defaultResourceType;
    }

    public RSTBTaskBuilderHelper(Context context, RSTBResourcePathManager resourcePathManager, RSTBTaskBuilder taskBuilder, RSTBStateHelper stateHelper) {
        this(context, resourcePathManager, new Gson(), taskBuilder, stateHelper);
    }

    public RSTBTaskBuilderHelper(Context context, RSTBResourcePathManager resourcePathManager, Gson gson, RSTBTaskBuilder taskBuilder, RSTBStateHelper stateHelper) {
        super();
        this.context = context;
        this.resourcePathManager = resourcePathManager;
        this.gson = gson;
        this.jsonParser = new JsonParser();
        this.stateHelper = stateHelper;
        this.taskBuilder = taskBuilder;
        this.defaultResourceType = ResourcePathManager.Resource.TYPE_JSON;
    }

    public Context getContext() {
        return this.context;
    }

    public ResourcePathManager getResourcePathManager() {
        return this.resourcePathManager;
    }

    public Gson getGson() {
        return this.gson;
    }

    protected String pathForFilename(String filename, String assetDirectoryPath, String extension) {
        return this.resourcePathManager.generatePath(filename, assetDirectoryPath, extension);
    }

    //utilities
    @Nullable
    public JsonElement getJsonElementForFilename(String filename) {
        String jsonPath = this.pathForFilename(filename, "json", "json");
        InputStream stream = this.resourcePathManager.getResouceAsInputStream(this.context, jsonPath);
        Reader reader = null;
        try
        {
            reader = new InputStreamReader(stream, "UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }

        JsonElement element = null;
        try {
            element = this.jsonParser.parse(reader);
            reader.close();
        }
        catch(Exception e) {
            Log.w(this.getClass().getSimpleName(), "could not parse json element", e);
            return null;
        }
        return element;
    }

    @Nullable
    public JsonElement getJsonElementForURL(URL url) throws IOException {

        InputStream stream;
        if (url.toString().startsWith("file:///android_asset/")) {
            String assetPath = url.toString().replace("file:///android_asset/", "");
            stream = context.getAssets().open(assetPath);
        }
        else {
            stream = url.openStream();
        }

        Reader reader = null;
        try
        {
            reader = new InputStreamReader(stream, "UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }

        JsonElement element = null;
        try {
            element = this.jsonParser.parse(reader);
            reader.close();
        }
        catch(Exception e) {
            Log.w(this.getClass().getSimpleName(), "could not parse json element", e);
            return null;
        }
        return element;

    }

    @Nullable
    public String getTextForFilename(String filename, String assetType) {
        String filePath = this.pathForFilename(filename, assetType, assetType);
        return ResourcePathManager.getResourceAsString(context, filePath);
    }

//    @Nullable
//    public JsonElement getJsonElementForFilename(String filename) {
//        return this.getJsonElementForFilename(filename, this.defaultResourceType);
//    }

    @Nullable
    public RSTBCustomStepDescriptor getCustomStepDescriptor(JsonObject jsonObject) {
        RSTBCustomStepDescriptor stepDescriptor = this.getGson().fromJson(jsonObject, RSTBCustomStepDescriptor.class);
        if (stepDescriptor.parameters == null &&
                stepDescriptor.parameterFileName != null &&
                !stepDescriptor.parameterFileName.isEmpty()) {
            JsonElement element = this.getJsonElementForFilename(stepDescriptor.parameterFileName);
            if (element.isJsonObject()) {
                stepDescriptor.parameters = element.getAsJsonObject();
            }
        }
        return stepDescriptor;
    }

    public RSTBStateHelper getStateHelper() {
        return this.stateHelper;
    }

    public RSTBTaskBuilder getTaskBuilder() {
        return taskBuilder;
    }
}
