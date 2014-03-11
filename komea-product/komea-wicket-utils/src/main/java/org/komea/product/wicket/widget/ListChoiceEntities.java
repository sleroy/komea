package org.komea.product.wicket.widget;

import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.database.api.IEntity;

public class ListChoiceEntities<T extends IEntity> extends ListChoice<T> {

    private static final long serialVersionUID = 1L;

    private final IChoiceRenderer<T> choiceRenderer = new IChoiceRenderer<T>() {
        private static final long serialVersionUID = 1L;

        @Override
        public Object getDisplayValue(T entity) {
            return entity.getDisplayName();
        }

        @Override
        public String getIdValue(T entity, int i) {
            return String.valueOf(entity.getId());
        }
    };

    public ListChoiceEntities(final String id, final PropertyModel<T> model,
            final List<T> choices) {
        super(id, model, choices);
        setChoiceRenderer(choiceRenderer);
        setNullValid(false);
        setMaxRows(8);
        setOutputMarkupId(true);
    }

    @Override
    protected String getNullKeyDisplayValue() {
        return " ";
    }
}
