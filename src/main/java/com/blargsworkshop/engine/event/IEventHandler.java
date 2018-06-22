package com.blargsworkshop.engine.event;

public interface IEventHandler {
	
	enum EventHandlerType {
		DEFAULT,
		FORGE,
		FML
	}

	/**
	 * Returns the bus type that this event handler should be registered on.
	 * @return FORGE for main forge bus. FML for tick events.  Default is the same as FORGE.
	 */
	public IEventHandler.EventHandlerType getBusType();

}
