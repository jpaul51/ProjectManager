package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Detail  implements Serializable {

	public enum DetailType{
		NEW,
		UPDATE
	}
	
	String detailName = "default";
	List<Bloc> blocs = new ArrayList<>();
	DetailType detailType;
	

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public List<Bloc> getBlocs() {
		return blocs;
	}

	public void setBlocs(List<Bloc> blocs) {
		this.blocs = blocs;
	}
	
	

	public DetailType getDetailType() {
		return detailType;
	}

	public void setDetailType(DetailType detailType) {
		this.detailType = detailType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detailName == null) ? 0 : detailName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Detail other = (Detail) obj;
		if (detailName == null) {
			if (other.detailName != null)
				return false;
		} else if (!detailName.equals(other.detailName))
			return false;
		return true;
	}
	
	
	
	
}
