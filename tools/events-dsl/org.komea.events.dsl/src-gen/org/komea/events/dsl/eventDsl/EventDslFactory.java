/**
 */
package org.komea.events.dsl.eventDsl;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.komea.events.dsl.eventDsl.EventDslPackage
 * @generated
 */
public interface EventDslFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  EventDslFactory eINSTANCE = org.komea.events.dsl.eventDsl.impl.EventDslFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Event Factory</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event Factory</em>'.
   * @generated
   */
  EventFactory createEventFactory();

  /**
   * Returns a new object of class '<em>Event</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event</em>'.
   * @generated
   */
  Event createEvent();

  /**
   * Returns a new object of class '<em>Event Property</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event Property</em>'.
   * @generated
   */
  EventProperty createEventProperty();

  /**
   * Returns a new object of class '<em>Simple Property</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Simple Property</em>'.
   * @generated
   */
  SimpleProperty createSimpleProperty();

  /**
   * Returns a new object of class '<em>List Property</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>List Property</em>'.
   * @generated
   */
  ListProperty createListProperty();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  EventDslPackage getEventDslPackage();

} //EventDslFactory
