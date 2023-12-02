/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ChangeTicketTypeResponce bean class provides ChangeTicketType Response specific properties.
 * 
 * @author Venkat_Koniki
 * @since 04.06.2020
 * @time 04:19PM
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class ChangeTicketType extends TicketResponse{
private static final long serialVersionUID = 7995567562925300966L;
   @NotEmpty(message = "Please provide proper value for to key")
   @NotNull(message = "Please provide value for  to  key")
   private String to;
   @NotEmpty(message = "Please provide proper value for cc key")
   @NotNull(message = "Please provide value for cc key")
   private String cc;
   @NotEmpty(message = "Please provide proper value for subject key")
   @NotNull(message = "Please provide value for subject key")
   private String subject;
   @NotEmpty(message = "Please provide proper value for messageBody key")
   @NotNull(message = "Please provide value for messageBody key")
   private String messageBody;
   @NotEmpty(message = "Please provide proper value for employeeName key")
   @NotNull(message = "Please provide value for employeeName key")
   private String employeeName;
   @NotEmpty(message = "Please provide proper value for raisedUnderCategory key")
   @NotNull(message = "Please provide value for raisedUnderCategory key")
   private String raisedUnderCategory;
   @NotEmpty(message = "Please provide proper value for categoryToBeChanged key")
   @NotNull(message = "Please provide value for categoryToBeChanged key ")
   private String categoryToBeChanged;
   @NotEmpty(message = "Please provide proper value for sessionKey key")
   @NotNull(message = "Please provide value for sessionKey key ")
   private String sessionKey;
   private Long changedTicketTypeId;
   private String changeTicketTypeAction;
   private Long employeeId;

}
