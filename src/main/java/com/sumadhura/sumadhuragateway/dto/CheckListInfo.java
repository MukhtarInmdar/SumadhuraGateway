/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author VENKAT
 * DATE 11-MAR-2019
 * TIME 11.11 AM
 */



//check List master table


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CheckListInfo implements Serializable {


   /**
	 * 
	 */
	private static final long serialVersionUID = -1903801904239366519L;
	private Long  checkListId;
   private String  checkListName;
   private String  checkListDiscription;
	
	
}
