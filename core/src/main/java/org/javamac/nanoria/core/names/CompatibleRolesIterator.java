package org.javamac.nanoria.core.names;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompatibleRolesIterator implements Iterator<Role[]> {
    private Role[] compatibleSyllableRoles;
    private int currentSyllableRole;

    private Role[] compatibleSegmentRoles;
    private int currentSegmentRole;

    public CompatibleRolesIterator(Role... requiredRoles) {
        compatibleSyllableRoles = getCompatibleRoles(new Role[]{Role.FIRST, Role.MIDDLE, Role.LAST}, requiredRoles);
        currentSyllableRole = 0;

        compatibleSegmentRoles = getCompatibleRoles(new Role[]{Role.INSET, Role.NUCLEUS, Role.CODA}, requiredRoles);
        currentSegmentRole = 0;
    }

    private Role[] getCompatibleRoles(Role[] rolesSubset, Role[] requiredRoles) {
        List<Role> roles = new ArrayList<Role>();
        for (Role requiredRole : requiredRoles) {
            if (isRoleInArray(requiredRole, rolesSubset)) {
                roles.add(requiredRole);
            }
        }
        if (roles.size() == 0) {
            return rolesSubset;
        } else {
            return roles.toArray(new Role[roles.size()]);
        }
    }

    private boolean isRoleInArray(Role requiredRole, Role[] allRoles) {
        for (Role r : allRoles) {
            if (r == requiredRole) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasNext() {
        return currentSyllableRole < compatibleSyllableRoles.length;
    }

    @Override
    public Role[] next() {
        Role[] roles = new Role[]{compatibleSegmentRoles[currentSegmentRole], compatibleSyllableRoles[currentSyllableRole]};
        currentSegmentRole++;
        if (currentSegmentRole >= compatibleSegmentRoles.length) {
            currentSegmentRole = 0;
            currentSyllableRole++;
        }
        return roles;
    }
}
