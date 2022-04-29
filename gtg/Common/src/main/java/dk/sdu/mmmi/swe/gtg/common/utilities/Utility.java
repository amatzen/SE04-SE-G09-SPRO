package dk.sdu.mmmi.swe.gtg.common.utilities;

public class Utility {
    public static boolean containsNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean notContainsNull(Object... objects) {
        return !containsNull(objects);
    }

}
