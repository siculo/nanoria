package org.javamac.nanoria.core.names;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompatibleRolesIteratoTest {
    @Test
    public void iterateAllRoles() {
        Iterator<Role[]> iterator = new CompatibleRolesIterator();

        Role expectedRoles[] = {
                Role.INSET, Role.FIRST, Role.NUCLEUS, Role.FIRST, Role.CODA, Role.FIRST,
                Role.INSET, Role.MIDDLE, Role.NUCLEUS, Role.MIDDLE, Role.CODA, Role.MIDDLE,
                Role.INSET, Role.LAST, Role.NUCLEUS, Role.LAST, Role.CODA, Role.LAST
        };
        Role[] actualRoles = extractAllIterableRoles(iterator);

        Assert.assertArrayEquals(expectedRoles, actualRoles);
    }

    @Test
    public void iterateAllSyllableRoles() {
        Iterator<Role[]> iterator = new CompatibleRolesIterator(Role.INSET);

        Role expectedRoles[] = {
                Role.INSET, Role.FIRST, Role.INSET, Role.MIDDLE, Role.INSET, Role.LAST
        };
        Role[] actualRoles = extractAllIterableRoles(iterator);

        Assert.assertArrayEquals(expectedRoles, actualRoles);
    }

    @Test
    public void iterateAllSegmentRoles() {
        Iterator<Role[]> iterator = new CompatibleRolesIterator(Role.MIDDLE);

        Role expectedRoles[] = {
                Role.INSET, Role.MIDDLE, Role.NUCLEUS, Role.MIDDLE, Role.CODA, Role.MIDDLE
        };
        Role[] actualRoles = extractAllIterableRoles(iterator);

        Assert.assertArrayEquals(expectedRoles, actualRoles);
    }

    @Test
    public void iterateSomeSegmentRoles() {
        Iterator<Role[]> iterator = new CompatibleRolesIterator(Role.FIRST, Role.MIDDLE);

        Role expectedRoles[] = {
                Role.INSET, Role.FIRST, Role.NUCLEUS, Role.FIRST, Role.CODA, Role.FIRST,
                Role.INSET, Role.MIDDLE, Role.NUCLEUS, Role.MIDDLE, Role.CODA, Role.MIDDLE
        };
        Role[] actualRoles = extractAllIterableRoles(iterator);

        Assert.assertArrayEquals(expectedRoles, actualRoles);
    }

    private Role[] extractAllIterableRoles(Iterator<Role[]> iterator) {
        List<Role> iteratedRoles = new ArrayList<Role>();
        while (iterator.hasNext()) {
            Role roles[] = iterator.next();
            for (Role r : roles) {
                iteratedRoles.add(r);
            }
        }
        return iteratedRoles.toArray(new Role[iteratedRoles.size()]);
    }
}
