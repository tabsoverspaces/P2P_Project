package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * This class is used to verify if various inputs are in order
 * with the format that they must respect.
 * For example :
 * - date
 * - time
 * - ip address
 * - number formatting
 * - etc.
 */
public class FormatVerifier {

    public static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    public static Pattern VALID_IPV4_PATTERN = null;

    /**
     * Empty constructor, for the sake of having one
     */
    public FormatVerifier() {

        // try to compile all patterns
        // manually

        try {
            VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            e.getDescription();
        }

    }

    /**
     * This method is used when we want to verify an IPv4 address
     *
     * @param address IP address in string type
     * @return boolean value
     */
    public boolean verifyIPAddress(String address) {
        boolean result = false;

        Matcher m = VALID_IPV4_PATTERN.matcher(address);

        if (m.matches()) {
            return true;
        } else {
            System.out.println("Format false");
            return false;
        }


    }


}
