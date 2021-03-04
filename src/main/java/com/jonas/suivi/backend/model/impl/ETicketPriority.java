package com.jonas.suivi.backend.model.impl;

public enum ETicketPriority {
	
	URGENT(1), HIGH(2), MEDIUM(3), LOW(4), VERY_LOW(5);
	
	int priority;
	
	ETicketPriority(int p) {
		priority = p;
	}
	
}
