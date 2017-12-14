package org.researchsuite.rstb.DefaultStepGenerators.descriptors;

import java.util.List;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSTBChoiceStepDescriptor extends RSTBQuestionStepDescriptor {

    public List<RSTBChoiceStepItemDescriptor> items;
    public boolean shuffleItems = false;

    public RSTBChoiceStepDescriptor() {

    }
}
