package org.komea.product.backend.kpi.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SearchUtils {

    private SearchUtils() {
    }

    public static List<Search> convert(final String searchs) {
        final List<String> searchsString = StringUtils.splitAndTrimWithoutEmpty(searchs, "#");
        final List<Search> bzSearchs = new ArrayList<Search>(searchsString.size());
        for (final String searchString : searchsString) {
            final Search bzSearch = Search.fromString(searchString);
            bzSearchs.add(bzSearch);
        }
        return bzSearchs;
    }

    public static String convert(final List<Search> Searchs) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Search bzSearch : Searchs) {
            stringBuilder.append(bzSearch.getString()).append("#");
        }
        if (!Searchs.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private static boolean isBugMatchesSearch(final ISearchedElement searchedElement, final Search search) {
        for (final String key : search.getParameterkeys()) {
            final String parameter = searchedElement.getParameter(key);
            final Collection<String> keys = searchedElement.getKeys();
            if (StringUtils.containsIgnoreCase(keys, key)) {
                final List<String> values = search.getValues(key);
                final Boolean accept = search.isAccept(key);
                if (accept != StringUtils.containsIgnoreCase(values, parameter)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int nbElementsMatchesSearches(final List<ISearchedElement> searchedElements,
            final List<Search> searchs) {
        int cpt = 0;
        for (final ISearchedElement searchedElement : searchedElements) {
            if (isBugMatchesAtLeastOneFilter(searchedElement, searchs)) {
                cpt++;
            }
        }
        return cpt;
    }

    private static boolean isBugMatchesAtLeastOneFilter(final ISearchedElement searchedElement,
            final List<Search> searchs) {
        for (final Search search : searchs) {
            if (isBugMatchesSearch(searchedElement, search)) {
                return true;
            }
        }
        return searchs.isEmpty();
    }
}
