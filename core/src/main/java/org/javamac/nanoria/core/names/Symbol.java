package org.javamac.nanoria.core.names;

import java.util.Comparator;

public class Symbol implements Comparable<Symbol> {
    private final Role roles[];

    public Symbol(String key, double weight, String allows, Role... roles) {
        this.roles = roles;
    }

    public boolean matchRoles(Role... currentRoles) {
        for (Role expectedRole : roles) {
            if (!findRole(expectedRole, currentRoles)) {
                return false;
            }
        }
        return true;
    }

    private boolean findRole(Role expectedRole, Role... currentRoles) {
        for (Role role : currentRoles) {
            if (expectedRole == role) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Symbol o) {
        return Integer.compare(this.roles.length, o.roles.length);
    }

}
