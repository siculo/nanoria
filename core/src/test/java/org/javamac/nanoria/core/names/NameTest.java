package org.javamac.nanoria.core.names;

import org.junit.Ignore;
import org.junit.Test;

public class NameTest {
    @Ignore
    @Test
    public void names() {
        for (int n = 0; n < 100; n++) {
            System.out.println(new Name().toString());
        }
    }

}
