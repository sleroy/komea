/**
 */
package org.komea.events.dsl.eventDsl.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.komea.events.dsl.eventDsl.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EventDslFactoryImpl extends EFactoryImpl implements EventDslFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static EventDslFactory init()
  {
    try
    {
      EventDslFactory theEventDslFactory = (EventDslFactory)EPackage.Registry.INSTANCE.getEFactory(EventDslPackage.eNS_URI);
      if (theEventDslFactory != null)
      {
        return theEventDslFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new EventDslFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EventDslFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case EventDslPackage.EVENT_FACTORY: return createEventFactory();
      case EventDslPackage.EVENT: return createEvent();
      case EventDslPackage.EVENT_PROPERTY: return createEventProperty();
      case EventDslPackage.SIMPLE_PROPERTY: return createSimpleProperty();
      case EventDslPackage.LIST_PROPERTY: return createListProperty();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EventFactory createEventFactory()
  {
    EventFactoryImpl eventFactory = new EventFactoryImpl();
    return eventFactory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Event createEvent()
  {
    EventImpl event = new EventImpl();
    return event;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EventProperty createEventProperty()
  {
    EventPropertyImpl eventProperty = new EventPropertyImpl();
    return eventProperty;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SimpleProperty createSimpleProperty()
  {
    SimplePropertyImpl simpleProperty = new SimplePropertyImpl();
    return simpleProperty;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ListProperty createListProperty()
  {
    ListPropertyImpl listProperty = new ListPropertyImpl();
    return listProperty;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EventDslPackage getEventDslPackage()
  {
    return (EventDslPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static EventDslPackage getPackage()
  {
    return EventDslPackage.eINSTANCE;
  }

} //EventDslFactoryImpl
