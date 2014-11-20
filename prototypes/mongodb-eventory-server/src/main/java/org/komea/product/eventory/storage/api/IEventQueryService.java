package org.komea.product.eventory.storage.api;

public interface IEventQueryService {

	int countWithFieldPredicate(IFieldPredicate fieldPredicate);

	int getNumberEvents();

}
