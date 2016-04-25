package org.javamac.nanoria.core.names;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SymbolSetValidator {
    private final FollowingRolesMap followingRolesMap = new FollowingRolesMap();
    private final SymbolSet symbols;
    private final boolean valid;

    public SymbolSetValidator(SymbolSet symbols) {
        this.symbols = symbols;
        this.valid = validate();
    }

    public boolean isValid() {
        return valid;
    }

    private boolean validate() {
        List<Symbol> allSymbols = symbols.getAllSymbols();
        for (Symbol symbol : allSymbols) {
            if (!validate(symbol)) {
                return false;
            }
        }
        return allSymbols.size() > 0;
    }

    private boolean validate(Symbol symbol) {
        Iterator<Role[]> i = new CompatibleRolesIterator(symbol.getRoles());
        while (i.hasNext()) {
            if (validateForRoles(symbol, i.next())) return false;
        }
        return true;
    }

    private boolean validateForRoles(Symbol symbol, Role[] compatibleRoles) {
        for(Role[] followingRoles: followingRolesMap.getFollowingRoles(compatibleRoles)) {
            List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(symbol, followingRoles);
            if (matchingSymbols.size() == 0) {
                return true;
            }
        }
        return false;
    }
}
