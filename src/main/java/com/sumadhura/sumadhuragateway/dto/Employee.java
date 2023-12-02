/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

/**
 * @author venkat_koniki
 *
 */
//@XmlRootElement
public class Employee implements Serializable {

	private static final long serialVersionUID = -5501601917122900357L;
	private Long id;
	private String name;
	private String sex;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", sex=" + sex + "]";
	}
	
}
