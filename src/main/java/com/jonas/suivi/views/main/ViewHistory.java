package com.jonas.suivi.views.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jonas.suivi.views.model.Application;

public class ViewHistory implements Serializable {

	SingleView current;
	boolean right;
	ViewHistory parent;
	List<ViewHistory> children = new ArrayList<>(); 
	
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}

	
	
	public SingleView getCurrent() {
		return current;
	}
	public void setCurrent(SingleView current) {
		this.current = current;
	}
	public ViewHistory getParent() {
		return parent;
	}
	public void setParent(ViewHistory parent) {
		this.parent = parent;
	}
	
	public boolean isGridView() {
		return current instanceof GridView;
	}
	public List<ViewHistory> getChildren() {
		return children;
	}
	
	public boolean isOfCtx(Class<? extends Application> ctx) {
		return current.getCtx().equals(ctx);
	}
	
	public boolean containsCtx(Class<? extends Application> ctx) {
		if(!isGridView()) {
			return ((DetailView)current).getGridComponents().stream().anyMatch(gcmp -> gcmp.getContext().equals(ctx));
		}else {
			return false;
		}
	}
	
	
	
	
}
