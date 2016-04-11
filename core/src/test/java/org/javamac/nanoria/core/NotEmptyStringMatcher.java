package org.javamac.nanoria.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class NotEmptyStringMatcher extends BaseMatcher<String> {

    public static final NotEmptyStringMatcher INSTANCE = new NotEmptyStringMatcher();

    @Override
    public boolean matches(Object item) {
        if (item != null && item instanceof String) {
            String current = (String) item;
            return current.length() > 0;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("<not empty String>");
    }
}
