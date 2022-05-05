package dk.sdu.mmmi.swe.gtg.common.utilities;

import java.util.Objects;

public class Utility {
    public static boolean containsNull(Object... objects) {
        for (Object o : objects) {
            if (Objects.isNull(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean notContainsNull(Object... objects) {
        return !containsNull(objects);
    }

}
