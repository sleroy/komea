package org.komea.events.dsl.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.komea.events.dsl.eventDsl.Event;
import org.komea.events.dsl.eventDsl.EventDslPackage;
import org.komea.events.dsl.eventDsl.EventFactory;
import org.komea.events.dsl.eventDsl.ListProperty;
import org.komea.events.dsl.eventDsl.SimpleProperty;
import org.komea.events.dsl.services.EventDslGrammarAccess;

@SuppressWarnings("all")
public class EventDslSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private EventDslGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == EventDslPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case EventDslPackage.EVENT:
				if(context == grammarAccess.getEventRule()) {
					sequence_Event(context, (Event) semanticObject); 
					return; 
				}
				else break;
			case EventDslPackage.EVENT_FACTORY:
				if(context == grammarAccess.getEventFactoryRule()) {
					sequence_EventFactory(context, (EventFactory) semanticObject); 
					return; 
				}
				else break;
			case EventDslPackage.LIST_PROPERTY:
				if(context == grammarAccess.getEventPropertyRule() ||
				   context == grammarAccess.getListPropertyRule()) {
					sequence_ListProperty(context, (ListProperty) semanticObject); 
					return; 
				}
				else break;
			case EventDslPackage.SIMPLE_PROPERTY:
				if(context == grammarAccess.getEventPropertyRule() ||
				   context == grammarAccess.getSimplePropertyRule()) {
					sequence_SimpleProperty(context, (SimpleProperty) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (package=QualifiedName? path=STRING? name=ID events+=Event*)
	 */
	protected void sequence_EventFactory(EObject context, EventFactory semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID properties+=EventProperty*)
	 */
	protected void sequence_Event(EObject context, Event semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type=QualifiedName name=ID (values+=STRING values+=STRING*)?)
	 */
	protected void sequence_ListProperty(EObject context, ListProperty semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type=QualifiedName name=ID value=STRING?)
	 */
	protected void sequence_SimpleProperty(EObject context, SimpleProperty semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
