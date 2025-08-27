package yolodeploy.pwdvalidator;

import java.util.List;

/**
 * @author YOLO-DEPLOY (<a href="https://github.com/yolo-deploy">github</a>)
 */

public class ValidationResult {

    public enum Reason {
        TOO_SHORT,
        NO_DIGIT,
        NO_UPPER_OR_LOWER,
        NO_SPECIAL,
        COMMON_PASSWORD,
        NOT_ENOUGH_GROUPS,
        NULL_OR_EMPTY,
        CONTAINS_SPACE
    }

    private final boolean valid;
    private final List<Reason> reasons;

    public ValidationResult(boolean valid, List<Reason> reasons) {
        this.valid = valid;
        this.reasons = reasons;
    }

    public boolean isValid() {
        return valid;
    }

    public List<Reason> getReasons() {
        return reasons;
    }
}