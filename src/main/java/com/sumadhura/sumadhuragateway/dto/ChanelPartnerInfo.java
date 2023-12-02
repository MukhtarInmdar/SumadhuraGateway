package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;




/**
 * CustomerAddressPojo class provides ADDRESS Table specific fields.
 * 
 * @author Srivenu
 * @since 30.05.2019
 * @time 11:53AM
 */


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ChanelPartnerInfo implements Serializable{
	
	private static final long serialVersionUID = 3220908561169920128L;
	private Long channelPartnerId;
	private String channelPartnerName;
	private String channelPartnerCompanyName;
	private String channelPartnerCPID;
	private String channelPartnerRERANO;
	
}
