package org.researchsuite.rstb.DefaultStepGenerators;

import java.util.Arrays;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSTBMultipleChoiceStepGenerator extends RSTBChoiceStepGenerator {

    public RSTBMultipleChoiceStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "multipleChoiceText"
        );
    }

    @Override
    protected boolean allowsMultiple() { return true; }
}
