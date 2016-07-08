package org.talend.preparation.actions;

import org.talend.dataprep.api.dataset.ColumnMetadata;
import org.talend.dataprep.api.dataset.RowMetadata;
import org.talend.dataprep.parameters.Parameter;
import org.talend.dataprep.transformation.actions.category.ScopeCategory;
import org.talend.dataprep.transformation.actions.common.*;
import org.talend.dataprep.transformation.api.action.context.ActionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Template to ease the implementation of an action by a third-party. It will be deleted when templates are unified after i18n system
 * is refactored.
 */
public abstract class UserActionMetadataTemplate implements ActionMetadata {

    private static final String USER_ACTIONS_CATEGORY = "user defined";

    private ActionI18NSupport actionI18NSupport = new ActionI18NSupport(getClass());

    @Override
    public UserActionMetadataTemplate adapt(ColumnMetadata column) {
        return this;
    }

    @Override
    public UserActionMetadataTemplate adapt(final ScopeCategory scope) {
        return this;
    }

    @Override
    public String getCategory() {
        return USER_ACTIONS_CATEGORY;
    }

    @Override
    public String getLabel() {
        return getLocalizedActionMessage("label");
    }

    @Override
    public String getDescription() {
        return getLocalizedActionMessage("desc");
    }

    @Override
    public String getDocUrl() {
        return getLocalizedActionMessage("url");
    }

    @Override
    public List<String> getActionScope() {
        return new ArrayList<>();
    }

    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public boolean acceptScope(final ScopeCategory scope) {
        switch (scope) {
        case CELL:
            return this instanceof CellAction;
        case LINE:
            return this instanceof RowAction;
        case COLUMN:
            return this instanceof ColumnAction;
        case DATASET:
            return this instanceof DataSetAction;
        default:
            return false;
        }
    }

    @Override
    public void compile(ActionContext actionContext) {
        final RowMetadata input = actionContext.getRowMetadata();
        final ScopeCategory scope = actionContext.getScope();
        if (scope != null) {
            switch (scope) {
            case CELL:
            case COLUMN:
                // Stop action if: there's actually column information in input AND column is not found
                if (input != null && !input.getColumns().isEmpty() && input.getById(actionContext.getColumnId()) == null) {
                    actionContext.setActionStatus(ActionContext.ActionStatus.CANCELED);
                    return;
                }
                break;
            case LINE:
            case DATASET:
            default:
                break;
            }
        }
        actionContext.setActionStatus(ActionContext.ActionStatus.OK);
    }

    @Override
    public boolean implicitFilter() {
        return true;
    }

    @Override
    public List<Parameter> getParameters() {
        return ImplicitParameters.getParameters();
    }

    protected String getLocalizedActionMessage(String code, Object... parameters) {
        return actionI18NSupport.getLocalizedActionMessage(code, parameters);
    }

}
