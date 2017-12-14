package org.researchsuite.rstb.DefaultStepGenerators;

import java.util.Arrays;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSTBSingleChoiceStepGenerator extends RSTBChoiceStepGenerator {

    public RSTBSingleChoiceStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "singleChoiceText"
        );
    }

    @Override
    protected boolean allowsMultiple() { return false; }
}
