package org.researchsuite.rstb.DefaultStepGenerators.descriptors;

import org.researchstack.backbone.answerformat.TextAnswerFormat;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSTBTextFieldStepDescriptor extends RSTBQuestionStepDescriptor {

    public int maximumLength = TextAnswerFormat.UNLIMITED_LENGTH;
    public boolean multipleLines = false;

    RSTBTextFieldStepDescriptor() {

    }

}
