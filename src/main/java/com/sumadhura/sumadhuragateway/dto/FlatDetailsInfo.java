package com.sumadhura.sumadhuragateway.dto;

/**
 * @author VENKAT 
 * DATE 12-MAR-2019 
 * TIME 04.29 PM
 */


public class FlatDetailsInfo {

	private String name;
	private String value;
	private String imgLoc;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the imgLoc
	 */
	public String getImgLoc() {
		return imgLoc;
	}
	/**
	 * @param imgLoc the imgLoc to set
	 */
	public void setImgLoc(String imgLoc) {
		this.imgLoc = imgLoc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlatDetailsInfo [name=" + name + ", value=" + value + ", imgLoc=" + imgLoc + "]";
	}
	
}
