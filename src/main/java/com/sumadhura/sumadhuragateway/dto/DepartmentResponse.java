/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DepartmentResponse extends Result{
	
	List<Department> deptList;

	public List<Department> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Department> deptList) {
		this.deptList = deptList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DepartmentResponse [deptList=" + deptList + "]";
	}
	
}
