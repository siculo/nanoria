package org.javamac.nanoria.core.names;

import org.junit.Test;

public class NameTest {
    @Test
    public void names() {
        for (int n = 0; n < 100; n++) {
            System.out.println(new Name().toString());
        }
    }

}
