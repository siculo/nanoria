package org.javamac.nanoria.core.names;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SymbolSetValidator {
    private final FollowingRolesMap followingRolesMap = new FollowingRolesMap();
    private final SymbolSet symbols;
    private final List<Symbol> notValidSymbols;

    private boolean valid;

    public SymbolSetValidator(SymbolSet symbols) {
        this.symbols = symbols;
        this.notValidSymbols = new ArrayList<Symbol>();
        this.valid = false;
        validate();
    }

    public boolean isValid() {
        return valid;
    }

    public List<Symbol> getNotValidSymbols() {
        return notValidSymbols;
    }

    private void validate() {
        List<Symbol> allSymbols = symbols.getAllSymbols();
        this.valid = allSymbols.size() > 0;
        for (Symbol symbol : allSymbols) {
            if (!validate(symbol)) {
                notValidSymbols.add(symbol);
                valid = false;
            }
        }
    }

    private boolean validate(Symbol symbol) {
        Iterator<Role[]> i = new CompatibleRolesIterator(symbol.getRoles());
        while (i.hasNext()) {
            if (!validateForRoles(symbol, i.next())) {
                return false;
            }
        }
        return true;
    }

    private boolean validateForRoles(Symbol symbol, Role[] compatibleRoles) {
        for (Role[] followingRoles : followingRolesMap.getFollowingRoles(compatibleRoles)) {
            List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(symbol, followingRoles);
            if (matchingSymbols.size() == 0) {
                return false;
            }
        }
        return true;
    }
}
