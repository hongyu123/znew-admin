package com.hfw.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.engine.messageinterpolation.util.InterpolationHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

public class PatternValidator implements ConstraintValidator<Pattern, CharSequence> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private java.util.regex.Pattern pattern;
    private String escapedRegexp;

    public PatternValidator() {
    }

    public void initialize(Pattern parameters) {
        Pattern.Flag[] flags = parameters.flags();
        int intFlag = 0;
        Pattern.Flag[] var4 = flags;
        int var5 = flags.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Pattern.Flag flag = var4[var6];
            intFlag |= flag.getValue();
        }

        try {
            this.pattern = java.util.regex.Pattern.compile(parameters.regexp(), intFlag);
        } catch (PatternSyntaxException var8) {
            throw LOG.getInvalidRegularExpressionException(var8);
        }

        this.escapedRegexp = InterpolationHelper.escapeMessageParameter(parameters.regexp());
    }

    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        } else {
            if (constraintValidatorContext instanceof HibernateConstraintValidatorContext) {
                ((HibernateConstraintValidatorContext)constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)).addMessageParameter("regexp", this.escapedRegexp);
            }

            Matcher m = this.pattern.matcher(value);
            return m.matches();
        }
    }
}
