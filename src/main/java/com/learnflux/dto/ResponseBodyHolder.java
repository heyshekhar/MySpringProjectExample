package com.learnflux.dto;

//import org.apache.commons.math3.util.Pair;


public class ResponseBodyHolder {
	
	String name;
	Boolean booleanValue;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getBooleanValue() {
		return booleanValue;
	}
	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}
	
	
	
	
	
	

//	private Pair<String, Integer> mapResponse = new Pair<>();
//
//	public Pair<String, Integer> getMapResponse() {
//		return mapResponse;
//	}
//
//	public void setMapResponse(Pair<String, Integer> p) {
//		this.mapResponse = p;
//	}
	
//	class Pair<S, B> 
//	{
//	  S p1;  B p2;
//	  Pair()
//	  {
//	    //default constructor
//	  }
//	  void setValue(S a, B b)
//	  {
//	    this.p1 = a;
//	    this.p2 = b;
//	  }
//	  Pair getValue()
//	  {
//	    return this;
//	  }
//	}
	
}


