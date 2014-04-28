package org.komea.product.backend.kpi.search;

import java.util.Collection;

public interface ISearchedElement {

    Collection<String> getKeys();

    String getParameter(String key);

}
