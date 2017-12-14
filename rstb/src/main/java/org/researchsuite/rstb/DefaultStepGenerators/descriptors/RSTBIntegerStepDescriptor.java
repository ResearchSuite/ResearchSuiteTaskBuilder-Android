package org.researchsuite.rstb.DefaultStepGenerators.descriptors;

import org.researchstack.backbone.answerformat.TextAnswerFormat;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSTBIntegerStepDescriptor extends RSTBQuestionStepDescriptor {

    public class RSTBIntegerStepRangeDescriptor {
        public int min = Integer.MIN_VALUE;
        public int max = Integer.MAX_VALUE;

        RSTBIntegerStepRangeDescriptor() {

        }
    }

    public RSTBIntegerStepRangeDescriptor range;

    RSTBIntegerStepDescriptor() {

    }

}
