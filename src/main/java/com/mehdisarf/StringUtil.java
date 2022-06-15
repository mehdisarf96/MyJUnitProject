package com.mehdisarf;

import java.util.Objects;

public class StringUtil {

    private StringUtil() {
    }

    public static boolean isName(String str) {
        if (Objects.isNull(str))
            return false;
        return str.matches("([a-z]+(-|\\s))*[a-z]+");
    }

    public static boolean isNationalId(String str) {
        if (Objects.isNull(str))
            return false;
        if (!str.matches("\\d{10}"))
            return false;

        int checksum = str.charAt(9) - '0'; // tu code melli, be raqame samte rast migim raqam e controlli
        int s = 0;

        // har raqam zarbdar arzeshe moqeiyatesh va bad hame ba ham jam she.
        for (int i = 0; i < 9; i++) {
            char c = str.charAt(i);
            s += (c - '0') * (10 - i);
        }
        s = s % 11;

        // baqimunde age bishtare 2 bud, 11 menhaye baaimunde bayad beshe adade controlli (checksum)
        // age kamtare 2 bud, adade controlli (checksum) hamun baqi munde ast.
        return (s < 2 && checksum == s) || (s >= 2 && (checksum == (11 - s)));
    }
}