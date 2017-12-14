package org.researchsuite.rstb.DefaultStepGenerators;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.model.Choice;

import java.util.ArrayList;
import java.util.List;

import org.researchsuite.rstb.RSTBTaskBuilderHelper;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBChoiceStepDescriptor;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBChoiceStepItemDescriptor;
import org.researchsuite.rstb.RSTBHelpers;

/**
 * Created by jameskizer on 12/7/16.
 */
public abstract class RSTBChoiceStepGenerator extends RSTBQuestionStepGenerator {

    public interface ChoiceFilter {
        boolean filter(RSTBChoiceStepItemDescriptor itemDescriptor);
    }

    protected abstract boolean allowsMultiple();

    //default filter lets everything pass
    public ChoiceFilter generateFilter(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        return new ChoiceFilter() {
            @Override
            public boolean filter(RSTBChoiceStepItemDescriptor itemDescriptor) {
                return true;
            }
        };

    }

    protected Choice[] generateChoices(List<RSTBChoiceStepItemDescriptor> items, boolean shuffleItems)
    {
        Choice[] choices = new Choice[items.size()];

        List<RSTBChoiceStepItemDescriptor> choiceItems = shuffleItems ? RSTBHelpers.shuffled(items) : items;

        for(int i = 0; i < choiceItems.size(); i++)
        {
            RSTBChoiceStepItemDescriptor choice = choiceItems.get(i);
            if(choice.value instanceof String)
            {
                choices[i] = new Choice<>(choice.prompt, (String) choice.value);
            }
            else if(choice.value instanceof Number)
            {
                // if the field type is Object, gson turns all numbers into doubles. Assuming Integer
                choices[i] = new Choice<>(choice.prompt, ((Number) choice.value).intValue());
            }
            else
            {
                throw new RuntimeException(
                        "String and Integer are the only supported values for generating Choices from json");
            }
        }
        return choices;
    }

    public AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBChoiceStepDescriptor choiceStepDescriptor = helper.getGson().fromJson(jsonObject, RSTBChoiceStepDescriptor.class);

        AnswerFormat.ChoiceAnswerStyle answerStyle = this.allowsMultiple()
                ? AnswerFormat.ChoiceAnswerStyle.MultipleChoice
                : AnswerFormat.ChoiceAnswerStyle.SingleChoice;

        ChoiceFilter choiceFilter = this.generateFilter(helper, type, jsonObject);
        List<RSTBChoiceStepItemDescriptor> filteredItems = new ArrayList<>();

        for (RSTBChoiceStepItemDescriptor item : choiceStepDescriptor.items) {
            if (choiceFilter.filter(item)) {
                filteredItems.add(item);
            }
        }

        ChoiceAnswerFormat answerFormat = new ChoiceAnswerFormat(answerStyle, this.generateChoices(filteredItems, choiceStepDescriptor.shuffleItems));

        return answerFormat;

    }

}
