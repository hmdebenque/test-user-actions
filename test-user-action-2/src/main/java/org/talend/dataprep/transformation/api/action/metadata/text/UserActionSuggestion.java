package org.talend.dataprep.transformation.api.action.metadata.text;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.talend.dataprep.transformation.api.transformer.suggestion.SuggestionEngineRule;
import org.talend.dataprep.transformation.api.transformer.suggestion.rules.BasicRules;

import static org.talend.dataprep.transformation.api.transformer.suggestion.rules.GenericRule.GenericRuleBuilder.forActions;

@Configuration
public class UserActionSuggestion extends BasicRules {

    @Bean
    public SuggestionEngineRule userActionRule() {
        return forActions(UserAction.USER_ACTION_ACTION_NAME)
                .when(IS_STRING)
                .then((cm) -> SuggestionEngineRule.LOW)
                .build();
    }


}
