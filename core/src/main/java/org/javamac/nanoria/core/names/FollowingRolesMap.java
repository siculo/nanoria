package org.javamac.nanoria.core.names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FollowingRolesMap {
    public List<Role[]> getFollowingRoles(Role[] roles) {
        List<Role[]> followingRoles;
        switch (roles[1]) {
            case FIRST:
                switch (roles[0]) {
                    case INSET:
                        return rolesList(new Role[]{Role.NUCLEUS, Role.FIRST});
                    case NUCLEUS:
                        return rolesList(new Role[]{Role.CODA, Role.FIRST});
                    case CODA:
                        return rolesList(
                                new Role[]{Role.INSET, Role.MIDDLE},
                                new Role[]{Role.INSET, Role.LAST}
                        );
                }
                break;
            case MIDDLE:
                switch (roles[0]) {
                    case INSET:
                        return rolesList(new Role[]{Role.NUCLEUS, Role.MIDDLE});
                    case NUCLEUS:
                        return rolesList(new Role[]{Role.CODA, Role.MIDDLE});
                    case CODA:
                        return rolesList(
                                new Role[]{Role.INSET, Role.MIDDLE},
                                new Role[]{Role.INSET, Role.LAST}
                        );
                }
                break;
            case LAST:
                switch (roles[0]) {
                    case INSET:
                        return rolesList(new Role[]{Role.NUCLEUS, Role.LAST});
                    case NUCLEUS:
                        return rolesList(new Role[]{Role.CODA, Role.LAST});
                }
                break;
        }
        return Collections.emptyList();
    }

    private List<Role[]> rolesList(Role[]... roles) {
        return Arrays.asList(roles);
    }
}
