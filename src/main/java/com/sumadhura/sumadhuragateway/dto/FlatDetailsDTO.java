package com.sumadhura.sumadhuragateway.dto;

import java.util.Date;
import java.util.List;
/**
 * @author VENKAT
 * DATE 07-FEB-2019
 * TIME 05.30 PM
 */

public class FlatDetailsDTO {

	private Long flatDetailsId;
	private Long flatId;
	private String flatNo;
	private Long statusId;
	private Date createdDate;
	private String imageLocation;
	private Long floorDetailsId;
	private Integer bhkId;
	private Integer fourWheelerParking;
	private Integer twoWheelerParking;
	private Date updatedDate;
	private String facing;
	private Double carpetArea;
	private Double balconyArea;
	private Double sbua;
	private Double uds;
	private Double livingArea;
	private String propertyUpdatesCarousel;
	private String uploadedDocs;
	private List<String> propertyUpdatesCarouselList;
	
	/**
	 * @return the carpetArea
	 */
	public Double getCarpetArea() {
		return carpetArea;
	}
	/**
	 * @param carpetArea the carpetArea to set
	 */
	public void setCarpetArea(Double carpetArea) {
		this.carpetArea = carpetArea;
	}
	/**
	 * @return the sbua
	 */
	public Double getSbua() {
		return sbua;
	}
	/**
	 * @param sbua the sbua to set
	 */
	public void setSbua(Double sbua) {
		this.sbua = sbua;
	}
	
	
	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the flatDetailsId
	 */
	public Long getFlatDetailsId() {
		return flatDetailsId;
	}

	/**
	 * @param flatDetailsId the flatDetailsId to set
	 */
	public void setFlatDetailsId(Long flatDetailsId) {
		this.flatDetailsId = flatDetailsId;
	}

	/**
	 * @return the flatId
	 */
	public Long getFlatId() {
		return flatId;
	}

	/**
	 * @param flatId the flatId to set
	 */
	public void setFlatId(Long flatId) {
		this.flatId = flatId;
	}

	/**
	 * @return the flatNo
	 */
	public String getFlatNo() {
		return flatNo;
	}

	/**
	 * @param flatNo the flatNo to set
	 */
	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the imageLocation
	 */
	public String getImageLocation() {
		return imageLocation;
	}

	/**
	 * @param imageLocation the imageLocation to set
	 */
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	/**
	 * @return the floorDetailsId
	 */
	public Long getFloorDetailsId() {
		return floorDetailsId;
	}

	/**
	 * @param floorDetailsId the floorDetailsId to set
	 */
	public void setFloorDetailsId(Long floorDetailsId) {
		this.floorDetailsId = floorDetailsId;
	}

	/**
	 * @return the bhkId
	 */
	public Integer getBhkId() {
		return bhkId;
	}

	/**
	 * @param bhkId the bhkId to set
	 */
	public void setBhkId(Integer bhkId) {
		this.bhkId = bhkId;
	}

	/**
	 * @return the fourWheelerParking
	 */
	public Integer getFourWheelerParking() {
		return fourWheelerParking;
	}

	/**
	 * @param fourWheelerParking the fourWheelerParking to set
	 */
	public void setFourWheelerParking(Integer fourWheelerParking) {
		this.fourWheelerParking = fourWheelerParking;
	}

	/**
	 * @return the twoWheelerParking
	 */
	public Integer getTwoWheelerParking() {
		return twoWheelerParking;
	}

	/**
	 * @param twoWheelerParking the twoWheelerParking to set
	 */
	public void setTwoWheelerParking(Integer twoWheelerParking) {
		this.twoWheelerParking = twoWheelerParking;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the facing
	 */
	public String getFacing() {
		return facing;
	}

	/**
	 * @param facing the facing to set
	 */
	public void setFacing(String facing) {
		this.facing = facing;
	}
	
	/**
	 * @return the balconyArea
	 */
	public Double getBalconyArea() {
		return balconyArea;
	}

	/**
	 * @param balconyArea the balconyArea to set
	 */
	public void setBalconyArea(Double balconyArea) {
		this.balconyArea = balconyArea;
	}
	
	/**
	 * @return the uds
	 */
	public Double getUds() {
		return uds;
	}

	/**
	 * @param uds the uds to set
	 */
	public void setUds(Double uds) {
		this.uds = uds;
	}

	/**
	 * @return the livingArea
	 */
	public Double getLivingArea() {
		return livingArea;
	}

	/**
	 * @param livingArea the livingArea to set
	 */
	public void setLivingArea(Double livingArea) {
		this.livingArea = livingArea;
	}

	/**
	 * @return the propertyUpdatesCarousel
	 */
	public String getPropertyUpdatesCarousel() {
		return propertyUpdatesCarousel;
	}
	/**
	 * @param propertyUpdatesCarousel the propertyUpdatesCarousel to set
	 */
	public void setPropertyUpdatesCarousel(String propertyUpdatesCarousel) {
		this.propertyUpdatesCarousel = propertyUpdatesCarousel;
	}
	/**
	 * @return the uploadedDocs
	 */
	public String getUploadedDocs() {
		return uploadedDocs;
	}
	/**
	 * @param uploadedDocs the uploadedDocs to set
	 */
	public void setUploadedDocs(String uploadedDocs) {
		this.uploadedDocs = uploadedDocs;
	}
	
	/**
	 * @return the propertyUpdatesCarouselList
	 */
	public List<String> getPropertyUpdatesCarouselList() {
		return propertyUpdatesCarouselList;
	}
	/**
	 * @param propertyUpdatesCarouselList the propertyUpdatesCarouselList to set
	 */
	public void setPropertyUpdatesCarouselList(List<String> propertyUpdatesCarouselList) {
		this.propertyUpdatesCarouselList = propertyUpdatesCarouselList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlatDetailsDTO [flatDetailsId=" + flatDetailsId + ", flatId=" + flatId + ", flatNo=" + flatNo
				+ ", statusId=" + statusId + ", createdDate=" + createdDate + ", imageLocation=" + imageLocation
				+ ", floorDetailsId=" + floorDetailsId + ", bhkId=" + bhkId + ", fourWheelerParking="
				+ fourWheelerParking + ", twoWheelerParking=" + twoWheelerParking + ", updatedDate=" + updatedDate
				+ ", facing=" + facing + ", carpetArea=" + carpetArea + ", balconyArea=" + balconyArea + ", sbua="
				+ sbua + ", uds=" + uds + ", livingArea=" + livingArea + ", propertyUpdatesCarousel="
				+ propertyUpdatesCarousel + ", uploadedDocs=" + uploadedDocs + "]";
	}
	
}
