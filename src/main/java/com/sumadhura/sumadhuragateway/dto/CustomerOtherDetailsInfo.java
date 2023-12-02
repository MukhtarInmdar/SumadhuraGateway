package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;




/**
 * 
 * 
 * @author Srivenu Aare
 * @since 30.05.2019
 * @time 11:54AM
 */


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CustomerOtherDetailsInfo implements Serializable {
	
	private static final long serialVersionUID = 6950314153563230237L;
	private String purposeofPurchase;
	private String currentResidentialStatus;
	private String referenceId;
	private String referenceName;
	private String referenceStatus;
	private String haveYouOwnedSumadhuraHome;
	private String haveYouOwnedSumadhuraHomeIfYesProjectName;
	private String haveYouOwnedSumadhuraHomeIfYesUnitNo;
	private POADetailsInfo poadetailsInfo;
	private ReferencesFriendInfo referencesFriend;
	private ReferencesCustomerInfo referencesCustomer;
	private ReferencesMappingInfo referencesMappingInfo;
	private ReferenceMaster referenceMaster;
	private ChanelPartnerInfo channelPartnerInfo;
	private String applicationNumber;
	private Long empIdOfSTM;
	private String empNameOfSTM;
	private String reasonForNoKYC;
	private Long existedFlatBookId;
	private Long id;
	
}
