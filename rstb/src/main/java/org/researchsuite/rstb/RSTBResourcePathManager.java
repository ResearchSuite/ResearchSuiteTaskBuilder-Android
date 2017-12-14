package org.researchsuite.rstb;

import org.researchstack.backbone.ResourcePathManager;

/**
 * Created by jameskizer on 8/29/17.
 */

public abstract class RSTBResourcePathManager extends ResourcePathManager {

    public abstract String generatePath(String name, String assetDirectoryPath, String extension);

}
