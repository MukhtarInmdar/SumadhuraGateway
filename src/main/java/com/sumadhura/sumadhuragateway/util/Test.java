package com.sumadhura.sumadhuragateway.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Test{
	
	public static void main(String[] args) {
		
		
		
		
		String str = "/src/main/resources/vmtemplate/CandidateStageCompleteEmail.vm";
		String strArr[] = str.split("/");
		String name = strArr[strArr.length-1]; 
		System.out.println(name);
		
		
		
		
	}
}

class Student{
	
	public String name;
	
	public Integer age;
	
	public Student(String name, Integer age) {
		this.name=name;
		this.age=age;
	}
	
	public String toString() {
		return "name:"+name+",age:"+age;
		
	}
}