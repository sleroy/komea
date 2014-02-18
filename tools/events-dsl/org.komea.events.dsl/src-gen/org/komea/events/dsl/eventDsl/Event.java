/**
 */
package org.komea.events.dsl.eventDsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.komea.events.dsl.eventDsl.Event#getName <em>Name</em>}</li>
 *   <li>{@link org.komea.events.dsl.eventDsl.Event#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.komea.events.dsl.eventDsl.EventDslPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.komea.events.dsl.eventDsl.EventDslPackage#getEvent_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.komea.events.dsl.eventDsl.Event#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
   * The list contents are of type {@link org.komea.events.dsl.eventDsl.EventProperty}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Properties</em>' containment reference list.
   * @see org.komea.events.dsl.eventDsl.EventDslPackage#getEvent_Properties()
   * @model containment="true"
   * @generated
   */
  EList<EventProperty> getProperties();

} // Event
