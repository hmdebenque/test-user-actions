package com.user.custom.actions.second;
//  ============================================================================
//
//  Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
//  This source code is available under agreement available at
//  https://github.com/Talend/data-prep/blob/master/LICENSE
//
//  You should have received a copy of the agreement
//  along with this program; if not, write to Talend SA
//  9 rue Pages 92150 Suresnes, France
//
//  ============================================================================

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.talend.dataprep.api.dataset.ColumnMetadata;
import org.talend.dataprep.api.dataset.DataSetRow;
import org.talend.dataprep.api.type.Type;
import org.talend.dataprep.parameters.Parameter;
import org.talend.dataprep.parameters.ParameterType;
import org.talend.dataprep.transformation.actions.category.ActionCategory;
import org.talend.dataprep.transformation.actions.common.ActionMetadata;
import org.talend.dataprep.transformation.actions.common.ColumnAction;
import org.talend.dataprep.transformation.api.action.context.ActionContext;
import org.talend.preparation.actions.DataprepAction;
import org.talend.preparation.actions.UserActionMetadataTemplate;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * This method pose two problems:
 * * First, it depends on many dataprep packages.
 * * Second, it gives too many occasions to the implementor to make mistakes:
 * - there is no control/try-catch whatsoever
 * - overridden method must call super => this will be forgotten for sure
 **/
@DataprepAction
public class UserAction2 extends UserActionMetadataTemplate implements ColumnAction {

    private static final Logger LOG = LoggerFactory.getLogger(UserAction2.class);

    protected static final String USER_ACTION_ACTION_NAME = "userDefinedAction2"; //$NON-NLS-1$

    private static final String APPENDIX_PARAM_NAME = "appendix_param";

    public UserAction2() {
//        super(initMessageSource());
    }

    private static MessageSource initMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        ClassLoader classLoader = UserAction2.class.getClassLoader();
        resourceBundleMessageSource.setBundleClassLoader(classLoader);
        resourceBundleMessageSource.setBasename("user_action");
        resourceBundleMessageSource.setFallbackToSystemLocale(false);
        resourceBundleMessageSource.setDefaultEncoding(Charsets.UTF_8.name());
        return resourceBundleMessageSource;
    }

    @Override
    public String getName() {
        return USER_ACTION_ACTION_NAME;
    }

    @Override
    public String getCategory() {
        return ActionCategory.STRINGS.getDisplayName();
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = super.getParameters();
        Parameter appendixParameter = new Parameter(APPENDIX_PARAM_NAME, ParameterType.STRING, " lol");
        parameters.add(appendixParameter);
        return parameters;
    }

    @Override
    public boolean acceptColumn(ColumnMetadata column) {
        return Type.STRING.isAssignableFrom(Type.get(column.getType()));
    }

    @Override
    public void compile(ActionContext actionContext) {
        LOG.info("Compile action {}", getClass());
    }

    @Override
    public void applyOnColumn(DataSetRow row, ActionContext context) {
        final String columnId = context.getColumnId();
        final String toAppendTo = row.get(columnId);
        if (toAppendTo != null) {
            String appendix = context.getParameters().get(APPENDIX_PARAM_NAME);
            row.set(columnId, toAppendTo + appendix);
        }
    }

    @Override
    public Set<ActionMetadata.Behavior> getBehavior() {
        return EnumSet.of(ActionMetadata.Behavior.VALUES_COLUMN);
    }

}
