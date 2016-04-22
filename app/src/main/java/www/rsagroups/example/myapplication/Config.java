package www.rsagroups.example.myapplication;

/**
 * Created by RSA on 4/13/2016.
 */
public class Config  {


    public static boolean isStringNullOrWhiteSpace(String value) {
        if (value == null) {
            return true;
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
