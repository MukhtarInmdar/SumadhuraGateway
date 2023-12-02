/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author VENKAT
 * DATE 07-FEB-2019
 * TIME 05.30 PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FlatCostInfo implements Serializable {

	private static final long serialVersionUID = 4708566066929745091L;
		private Long flatCostId;
		private Long flatId;
		private Double unitNumber;
		private Double sqftCost;
		private Double subaSqft;
		private Double carpetAreaSqft;
		private Double perSqftCost;
		private Double plc;
		private Double floorRise;
		private Double basicFlatCost;
		private Double amenitiesFlatCost;
		private Double gstCost;
		private Double gstPercentage;
		private Double totalCost;
		private Double extraChanges;
		private Double fourWheelerParking;
		private Double twoWheelerParking;
        private Double clubHouse;
        private Double infra;
        private Double modificationCost;
      //  private Double flatCost;
        private double flatCost;
		private Date createdDate;
		private Date updatedDate;
		private Long statusId;
		private Long createdBy;
		private Long updatedBy;
		//ACP
		private Double totalCostExcludeGst;
		private Double taxesPerSft;
		private Double soldBasePrice;
		private Double actualPricePerSft;
		private Double overallPricePerSft;
		private String unitGroup;
		private Double quotedBasePrice;
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "FlatCostDTO [flatCostId=" + flatCostId + ", flatId=" + flatId + ", unitNumber=" + unitNumber
					+ ", sqftCost=" + sqftCost + ", subaSqft=" + subaSqft + ", carpetAreaSqft=" + carpetAreaSqft
					+ ", perSqftCost=" + perSqftCost + ", plc=" + plc + ", floorRise=" + floorRise + ", basicFlatCost="
					+ basicFlatCost + ", , gstCost=" + gstCost + ", gstPercentage="
					+ gstPercentage + ", totalCost=" + totalCost + ", extraChanges=" + extraChanges
					+ ", fourWheelerParking=" + fourWheelerParking + ", twoWheelerParking=" + twoWheelerParking
					+ ", clubHouse=" + clubHouse + ", infra=" + infra + ", modificationCost=" + modificationCost
					+ ", flatCost=" + flatCost + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
					+ ", statusId=" + statusId + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
		}
		
		
	}

