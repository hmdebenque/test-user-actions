package org.talend.preparation.actions;

import com.google.common.base.CaseFormat;
import com.google.common.base.Charsets;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.talend.dataprep.transformation.actions.common.ActionMetadata;

public class ActionI18NSupport {

    private static final String DEFAULT_ACTION_CODE_PREFIX = "action";

    private MessageSource messageSource;

    private String actionCode;

    public ActionI18NSupport(Class<? extends ActionMetadata> actionClass, MessageSource messageSource) {
        actionCode = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE)
                .convert(actionClass.getSimpleName());
        if (messageSource == null) {
            String bundleBasename = actionClass.getPackage().getName() + "." + actionCode;
            ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
            resourceBundleMessageSource.setBasename(bundleBasename);
            resourceBundleMessageSource.setFallbackToSystemLocale(false);
            resourceBundleMessageSource.setDefaultEncoding(Charsets.UTF_8.name());
            setMessageSource(resourceBundleMessageSource);
        }
    }

    /**
     * Create the action specific message source as a {@link ResourceBundleMessageSource} as if located at
     * {action_class_location}/{action_class_name_lowercase}.
     * For instance com.user.custom.actions.first.MyAction would have its resource bundle placed at:
     * {@code com/user/custom/actions/first/my_action.properties}
     *
     * @param actionClass
     */
    public ActionI18NSupport(Class<? extends ActionMetadata> actionClass) {
        this(actionClass, null);
    }

    private String getActionCodePrefix() {
        return DEFAULT_ACTION_CODE_PREFIX;
    }

    private String getActionCode() {
        return actionCode;
    }

    private MessageSource getMessageSource() {
        return messageSource;
    }

    private final void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected String getLocalizedActionMessage(String code, Object... parameters) {
        return getLocalizedMessage(getActionCodePrefix() + "." + getActionCode() + "." + code, parameters);
    }

    protected String getLocalizedMessage(String code, Object... parameters) {
        return getMessageSource().getMessage(code, parameters, LocaleContextHolder.getLocale());
    }
}
