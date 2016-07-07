package org.talend.dataprep.transformation.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talend.dataprep.transformation.api.action.metadata.common.ActionMetadata;

import java.util.Collection;
import java.util.List;

@Component
@RestController
@RequestMapping("/api")
public class WebFront {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private List<ActionMetadata> actionMetadatas;

    @RequestMapping("/components")
    public Collection<String> getComponents() {
        return context.getBeansOfType(ActionMetadata.class).keySet();
    }

    @RequestMapping("/actions/{actionName}/label")
    public String getLabel(@PathVariable("actionName") String actionName) {
        ActionMetadata actionMetadata = getActionMetadata(actionName);
        return actionMetadata == null ? "Action not found" : actionMetadata.getLabel();
    }

    private ActionMetadata getActionMetadata(String actionName) {
        ActionMetadata actionMetadata = null;
        for (ActionMetadata action : actionMetadatas) {
            if (action.getName().equals(actionName)) {
                actionMetadata = action;
            }
        }
        return actionMetadata;
    }

}
