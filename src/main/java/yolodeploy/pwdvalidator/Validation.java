package yolodeploy.pwdvalidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author YOLO-DEPLOY (<a href="https://github.com/yolo-deploy">github</a>)
 */

public class Validation {

    private static final char[] ALLOWED_SPECIALS =
            "!@#$%^&*()_+-=[]{}|;':\",./<>?".toCharArray();

    private static final char[][] COMMON_PASSWORDS = {
            "123456".toCharArray(),
            "password".toCharArray(),
            "123456789".toCharArray(),
            "12345".toCharArray(),
            "12345678".toCharArray(),
            "qwerty".toCharArray(),
            "1234567".toCharArray(),
            "111111".toCharArray(),
            "123123".toCharArray(),
            "abc123".toCharArray()
    };

    public static ValidationResult validate(char[] password) {
        List<ValidationResult.Reason> reasons = new ArrayList<>();
        if (password == null || password.length == 0) {
            reasons.add(ValidationResult.Reason.NULL_OR_EMPTY);
        } else {
            if (!hasMinimumLength(password, 8)) reasons.add(ValidationResult.Reason.TOO_SHORT);
            if (!containsDigit(password)) reasons.add(ValidationResult.Reason.NO_DIGIT);
            if (!containsUpperAndLower(password)) reasons.add(ValidationResult.Reason.NO_UPPER_OR_LOWER);
            if (!hasSpecialCharacters(password)) reasons.add(ValidationResult.Reason.NO_SPECIAL);
            if (isCommonPassword(password)) reasons.add(ValidationResult.Reason.COMMON_PASSWORD);
            if (!hasAtLeastNGroups(password, 3)) reasons.add(ValidationResult.Reason.NOT_ENOUGH_GROUPS);
            for (char ch : password) {
                if (Character.isWhitespace(ch)) {
                    reasons.add(ValidationResult.Reason.CONTAINS_SPACE);
                    break;
                }
            }
        }
        boolean valid = reasons.isEmpty();
        return new ValidationResult(valid, reasons);
    }

    private static boolean hasMinimumLength(char[] password, int minimumLength) {
        return password.length >= minimumLength;
    }

    private static boolean containsDigit(char[] password) {
        for (char ch : password) {
            if (ch >= '0' && ch <= '9') return true;
        }
        return false;
    }

    private static boolean hasSpecialCharacters(char[] password) {
        for (char ch : password) {
            if (inSet(ch, ALLOWED_SPECIALS)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCommonPassword(char[] password) {
        for (char[] weak : COMMON_PASSWORDS) {
            if (Arrays.equals(password, weak)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasAtLeastNGroups(char[] password, int requiredGroups) {
        boolean lower = false;
        boolean upper = false;
        boolean digit = false;
        boolean special = false;

        int groups = 0;
        for (char ch : password) {
            if (!lower && ch >= 'a' && ch <= 'z') {
                lower = true;
                groups++;
            } else if (!upper && ch >= 'A' && ch <= 'Z') {
                upper = true;
                groups++;
            } else if (!digit && ch >= '0' && ch <= '9') {
                digit = true;
                groups++;
            } else if (!special && inSet(ch, ALLOWED_SPECIALS)) {
                special = true;
                groups++;
            }
            if (groups >= requiredGroups) {
                return true;
            }
        }
        return groups >= requiredGroups;
    }

    private static boolean containsUpperAndLower(char[] password) {
        boolean hasUpper = false;
        boolean hasLower = false;
        for (char ch : password) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            }
            if (Character.isLowerCase(ch)) {
                hasLower = true;
            }
            if (hasUpper && hasLower) {
                return true;
            }
        }
        return false;
    }

    private static boolean inSet(char ch, char[] set) {
        for (char c : set) if (c == ch) return true;
        return false;
    }

}