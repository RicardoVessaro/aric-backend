package rv.aric.ipsum.service.validator;

import rv.aric.ipsum.rest.exception.IpsumException;
import rv.aric.ipsum.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegexValidator {

    String message = "{0} must have at least {1}.";
    List<Pair<String, String>> regexPair = new ArrayList<>();
    String passwordMissingPatterns = "";
    private Integer minSize;

    public RegexValidator verifyMinSize(int size) {
        this.minSize = size;
        return this;
    }

    public RegexValidator verify(String regex, String displayText) {
        regexPair.add(new Pair<>(displayText, regex));
        return this;
    }

    public void validate(String valueToValidate, String valueText) {
        String value = valueToValidate;
        if(value == null) {
            value = "";
        }

        if(minSize != null && (value.isEmpty() || value.length() < minSize)) {
            addPasswordMissingPatterns(minSize + " characters");
        }

        for (Pair<String, String> pair : regexPair) {
            String displayText = pair.getKey();
            String regex = pair.getValue();

            if(!isPatternMatched(regex, value)) {
                addPasswordMissingPatterns(displayText);
            }
        }

        if(!passwordMissingPatterns.isEmpty()) {
            throw new IpsumException(message, valueText, passwordMissingPatterns);
        }
    }

    public RegexValidator setMessage(String message) {
        this.message = message;
        return this;
    }

    private void addPasswordMissingPatterns(String missingPattern) {
        if(!passwordMissingPatterns.isEmpty()) {
            passwordMissingPatterns += ", ";
        }

        passwordMissingPatterns += missingPattern;
    }

    private boolean isPatternMatched(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).find();
    }

}
