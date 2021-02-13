package com.jonas.suivi.views.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)		
public @interface Input {
	

	String value() default TEXT_INPUT;

	
	
	public static String TEXT_INPUT="TEXT_INPUT";
	public static String TEXT_AREA="TEXT_AREA";
	public static String TEXT_RICH="TEXT_RICH";
	public static String FILE_IMG="FILE_IMG";
	public static String FILE_PDF="FILE_PDF";
	public static String FILE_CANVAS="FILE_CANVAS";
	public static String DATE_TIME="DATE_TIME";
	public static String DATE_TIME_ONLY="DATE_TIME_ONLY";
	public static String NUM_INTEGER="NUM_INTEGER";
	public static String NUM_CURRENCY="NUM_CURRENCY";
	public static String NUM_FLOAT="NUM_FLOAT";
	public static String COORDINATE_CURRENT="COORDINATE_CURRENT";
	public static String COORDINATE_GEOCODING="COORDINATE_GEOCODING";
	public static String COORDINATE_MAP="COORDINATE_MAP";
	public static String TEXT_INPUT_MULTIPLE = "TEXT_INPUT_MULTIPLE";
	
	public static String SELECT = "SELECT";


}




//
//enum EInputType{
//	
//	DEFAULT(TextType.TEXT_INPUT);
//	
//	InputType type;
//	
//	EInputType(InputType type) {
//		this.type = type;
//	}
//	
//	public void setType(InputType type) {
//		this.type = type;
//	}
//	
//
//}
//
// interface InputType{
//	
//	 default String getType(InputType t) {
//		 return t.toString();
//	 }
//	 
//}
//
//enum TextType implements InputType{
//	TEXT_INPUT,
//	TEXT_AREA,
//	TEXT_RICH,;
//
//
//	
//}
//
//enum FileType implements InputType{
//
//	
//	FILE_IMG,
//	FILE_PDF,
//	FILE_CANVAS,
//	
//}
//
//enum DateType implements InputType{
//	DATE_TIME,
//	DATE_TIME_ONLY,
//}
//
//enum NumType implements InputType{
//	NUM_INTEGER,
//	NUM_CURRENCY,
//	NUM_FLOAT,
//}
//
//enum CoordinateType{
//	COORDINATE_CURRENT,
//	COORDINATE_GEOCODING,
//	COORDINATE_MAP
//}


