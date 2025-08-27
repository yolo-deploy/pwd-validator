package yolodeploy.pwdvalidator;

import java.security.SecureRandom;

/**
 * @author YOLO-DEPLOY (<a href="https://github.com/yolo-deploy">github</a>)
 */

public class PwdGenerator {

    public static char[] generateSecurePassword(int length, String allowedSpecials) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specials = allowedSpecials;
        String all = upper + lower + digits + specials;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specials.charAt(random.nextInt(specials.length())));

        for (int i = 4; i < length; i++) {
            password.append(all.charAt(random.nextInt(all.length())));
        }

        char[] pwdArr = password.toString().toCharArray();
        for (int i = pwdArr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char tmp = pwdArr[i];
            pwdArr[i] = pwdArr[j];
            pwdArr[j] = tmp;
        }
        return pwdArr;
    }
}