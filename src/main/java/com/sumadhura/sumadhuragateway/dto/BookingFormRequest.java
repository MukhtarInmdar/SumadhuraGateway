package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * EmployeeTicketRequest bean class provides EmployeeTicketRequest specific properties.
 * 
 * @author Srivenu
 * @since 21.05.2019
 * @time 01:10PM
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class BookingFormRequest extends Result implements Serializable {

	private static final long serialVersionUID = 2389958727172702205L;
	
	private String agreementType;
	private Long portNumber;
	private Long customerId;
	private Long flatBookingId;
	private Long applicantId;
	private Long flatId;
	private Long flatCostId;
	private Long floorId;
	private Long floorDetId;
	private Long blockId;
	private String floorName;
	private String blockName;
	private Long blockDetId;
	private Long siteId;
	private Long deptId;
	private Long customerAddressId;
	private Long customerProffisionalId;
	private Long proffisionalId;
	private Long custOtherDetailsId;
	private Long applicantAddressId;
	private Long applicantProffisionalId;
	private Long checkListRegistrationId;
	private Long checkListLegalOfficierId;
	private Long checkListCrmId;
	private Long checkListSalesHeadId;
	private Long checkListDeptMapId;
	private Long checkListId;
	private Long checkListVerfiId;
	private Long applicantCheckListVerfiId;
	private Long statusId;
	private String requestUrl;
	private Long metadataId;
	private Long custBookInfoId;
	private Long coAppBookInfoId;
	private Long aminititesInfraSiteWiseId;
	private Long aminititesInfraFlatWiseId;
	private Long aminititesInfraId;
	private String aminititesInfraName;
	private Long aminititesInfraCostId;
	private String siteName;
	private String flatNo;
	private String actionStr;
	private Long empId;
	private Long menuId;
	private Long empDeptId;
	private List<Long> empSiteId;
	private List<CustomerBookingFormInfo> customerBookingFormsInfos;
	private String comments;
	private String employeeName;
	private Timestamp bookingformCanceledDate;
	private CustomerBookingFormInfo customerBookingFormsInfo;
	private String merchantId;
	private List<BookingFormApproveRequest> bookingFormApproveRequest;
	@JsonProperty("BookingId")
    private String bookingId;
	private String showStatusKey;
	private Long stateId;
}
