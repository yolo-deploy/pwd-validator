package yolodeploy.pwdvalidator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author YOLO-DEPLOY (<a href="https://github.com/yolo-deploy">github</a>)
 */

class ValidationTest {

    static Stream<Arguments> passwordProvider() {
        String allowedSpecials = "!@#$%^&*()_+-=[]{}|;':\",./<>?";
        char[] generated = PwdGenerator.generateSecurePassword(8, allowedSpecials);
        return Stream.of(
                Arguments.of("Secure1$", true),
                Arguments.of("S1$d", false),
                Arguments.of("123456", false),
                Arguments.of("ABCDE1$", false),
                Arguments.of("Secure 1$", false),
                Arguments.of(new String(generated), true)
        );
    }

    @ParameterizedTest
    @MethodSource("passwordProvider")
    void validatePassword(String password, boolean expectedValid) {
        ValidationResult validationResult = Validation.validate(password.toCharArray());
        boolean actualValid = validationResult.isValid();
        assertEquals(
                expectedValid,
                actualValid,
                () -> "Password validation mismatch. Reasons: " + validationResult.getReasons()
        );
    }

}