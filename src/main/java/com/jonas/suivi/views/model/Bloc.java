package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bloc  implements Serializable {
	
	
	List<Line> lines = new ArrayList<>();
	boolean hidden = false;
	boolean collapsed = false;
	
	
	
	public List<Line> getLines() {
		return lines;
	}
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public boolean isCollapsed() {
		return collapsed;
	}
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	
	
	
}
