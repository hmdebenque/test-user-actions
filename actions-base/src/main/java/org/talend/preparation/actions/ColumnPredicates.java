package org.talend.preparation.actions;

import org.talend.dataprep.api.dataset.ColumnMetadata;
import org.talend.dataprep.api.type.Type;
import org.talend.dataquality.semantic.classifier.SemanticCategoryEnum;

import java.util.function.Predicate;

public final class ColumnPredicates {

    private ColumnPredicates() {
    }

    /**
     * A helper to filter {@link Type#NUMERIC} columns.
     */
    private static final Predicate<ColumnMetadata> IS_NUMERIC = columnMetadata -> {
        final Type type = Type.get(columnMetadata.getType());
        return Type.NUMERIC.isAssignableFrom(type);
    };

    /**
     * A helper to filter {@link Type#STRING} columns.
     */
    private static final Predicate<ColumnMetadata> IS_STRING = columnMetadata -> {
        final Type type = Type.get(columnMetadata.getType());
        return Type.STRING.equals(type);
    };

    /**
     * A helper to filter {@link Type#DATE} columns (including column where semantic domain is 'date').
     */
    private static final Predicate<ColumnMetadata> IS_DATE = columnMetadata -> {
        final Type type = Type.get(columnMetadata.getType());
        return Type.DATE.isAssignableFrom(type) || "date".equalsIgnoreCase(columnMetadata.getDomain());
    };

    /**
     * A helper to filter columns where semantic domain=email.
     */
    private static final Predicate<ColumnMetadata> IS_EMAIL = columnMetadata -> SemanticCategoryEnum.EMAIL.getId()
            .equalsIgnoreCase(columnMetadata.getDomain());

    /**
     * A helper to filter columns where semantic domain=url.
     */
    private static final Predicate<ColumnMetadata> IS_URL = columnMetadata -> SemanticCategoryEnum.URL.getId()
            .equalsIgnoreCase(columnMetadata.getDomain());

    /**
     * A helper to filter columns where semantic domain=phone.
     */
    private static final Predicate<ColumnMetadata> IS_PHONE = columnMetadata -> columnMetadata.getDomain().toLowerCase()
            .endsWith("phone");

    public static Predicate<ColumnMetadata> isNumeric() {
        return IS_NUMERIC;
    }

    public static Predicate<ColumnMetadata> isString() {
        return IS_STRING;
    }

    public static Predicate<ColumnMetadata> isDate() {
        return IS_DATE;
    }

    public static Predicate<ColumnMetadata> isEmail() {
        return IS_EMAIL;
    }

    public static Predicate<ColumnMetadata> isUrl() {
        return IS_URL;
    }

    public static Predicate<ColumnMetadata> isPhone() {
        return IS_PHONE;
    }
}
