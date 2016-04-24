package org.javamac.nanoria.core.names;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FollowingRolesMapTest {
    private FollowingRolesMap map = new FollowingRolesMap();

    @Test
    public void basicInterface() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.CODA, Role.LAST
        });
        Assert.assertNotNull(followingRolesList);
    }

    // [Role.INSET, Role.FIRST]    -> [[Role.NUCLEUS, Role.FIRST]]
    @Test
    public void rolesFollowingInsetFirst() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.INSET, Role.FIRST
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList(new Role[]{Role.NUCLEUS, Role.FIRST}))
        );
    }

    // [Role.NUCLEUS, Role.FIRST]  -> [[Role.CODA, Role.FIRST]]
    @Test
    public void rolesFollowingNucleusFirst() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.NUCLEUS, Role.FIRST
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList(new Role[]{Role.CODA, Role.FIRST}))
        );
    }
    // [Role.CODA, Role.FIRST]     -> [[Role.INSET, Role.MIDDLE], [Role.INSET, Role.LAST]]
    @Test
    public void rolesFollowingCodaFirst() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.CODA, Role.FIRST
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList(new Role[]{Role.INSET, Role.MIDDLE}, new Role[]{Role.INSET, Role.LAST}))
        );
    }

    // [Role.INSET, Role.MIDDLE]   -> [[Role.NUCLEUS, Role.MIDDLE]]
    @Test
    public void rolesFollowingInsetMiddle() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.INSET, Role.MIDDLE
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList(new Role[]{Role.NUCLEUS, Role.MIDDLE}))
        );
    }

    // [Role.NUCLEUS, Role.MIDDLE] -> [[Role.CODA, Role.MIDDLE]]
    @Test
    public void rolesFollowingNucleusMiddle() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.NUCLEUS, Role.MIDDLE
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList(new Role[]{Role.CODA, Role.MIDDLE}))
        );
    }

    // [Role.CODA, Role.MIDDLE]    -> [[Role.INSET, Role.MIDDLE], [Role.INSET, Role.LAST]]
    @Test
    public void rolesFollowingCodaMiddle() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.CODA, Role.MIDDLE
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList(new Role[]{Role.INSET, Role.MIDDLE}, new Role[]{Role.INSET, Role.LAST}))
        );
    }

    // [Role.INSET, Role.LAST]     -> [[Role.NUCLEUS, Role.LAST]]
    @Test
    public void rolesFollowingInsetLast() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.INSET, Role.LAST
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList(new Role[]{Role.NUCLEUS, Role.LAST}))
        );
    }

    // [Role.NUCLEUS, Role.LAST]   -> [[Role.CODA, Role.LAST]]
    @Test
    public void rolesFollowingNucleusLast() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.NUCLEUS, Role.LAST
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList(new Role[]{Role.CODA, Role.LAST}))
        );
    }

    // [Role.CODA, Role.LAST]      -> []
    @Test
    public void rolesFollowingCodaLast() {
        List<Role[]> followingRolesList = map.getFollowingRoles(new Role[]{
                Role.CODA, Role.LAST
        });
        Assert.assertThat(followingRolesList, new RolesListMatcher(
                rolesList())
        );
    }

    private List<Role[]> rolesList(Role[]... roles) {
        return Arrays.asList(roles);
    }

    private static class RolesListMatcher extends BaseMatcher<List<Role[]>> {
        private final List<Role[]> expected;

        public RolesListMatcher(List<Role[]> expected) {
            this.expected = expected;
        }

        @Override
        public void describeTo(Description description) {

        }

        @Override
        public boolean matches(Object o) {
            final List<Role[]> actual = (List<Role[]>) o;
            if (actual.size() != expected.size()) {
                return false;
            }
            for (int i = 0; i < actual.size(); i++) {
                Role[] actualRoles = actual.get(i);
                Role[] expectedRoles = expected.get(i);
                if (!Arrays.equals(actualRoles, expectedRoles)) {
                    return false;
                }
            }
            return true;
        }
    }
}
