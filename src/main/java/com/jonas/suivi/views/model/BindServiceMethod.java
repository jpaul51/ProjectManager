package com.jonas.suivi.views.model;

import java.util.function.BiConsumer;

import com.jonas.suivi.backend.services.DisplayableService;


public class BindServiceMethod<METH extends BiConsumer<DisplayableService, METH>> implements BiConsumer<DisplayableService, METH> {

	
	
	public METH methodToCall;
	
	public BindServiceMethod(METH methodToCall) {
		this.methodToCall = methodToCall;
	}
	
	@Override
	public void accept(DisplayableService t, METH u) {

		u.accept(t, u);
		
	}

	 
	
}
