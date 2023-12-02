package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties
public class PropertyDetails extends Result implements Serializable {

	private static final long serialVersionUID = -2309112234395977454L;
	private String sbua;
	private String carpetArea;
	private String floorplanImage;
	private Double unitNumber;
	private String floorName;
	private Double perSqftCost;
	private Double plc;
	private Double floorRise;
	private PdfDetails[] pdfDetails;
	private Boolean ispdfDetailsAvailable;
	private Double infraCharges;
	private Double carParking;
	private Double clubHouse;
	private FlatDTO flatDTO;
	private FlatRegistrationDTO flatRegistrationDTO;
	private FlatDetailsDTO flatDetailsDTO;
	private FlatCostDTO flatCostDTO;
	private FloorDetailsDTO floorDetailsDTO;
	private BlockDetailsDTO blockDetailsDTO;
	private BlockDTO blockDTO;
	private FloorDTO floorDTO;
    private List<FlatDetailsInfo> flatDetailsInfoList;
    private List<FlatDetailsInfo> amenitiesCostInfoList;
    private List<FlatModificationCostDTO> flatModificationCostDTOLIST;
    private Boolean isflatModificationCostDTOListAvailable;
    private List<String> propertyUpdatesCarouselList;
	public String getSbua() {
		return sbua;
	}
	public void setSbua(String sbua) {
		this.sbua = sbua;
	}
	public String getCarpetArea() {
		return carpetArea;
	}
	public void setCarpetArea(String carpetArea) {
		this.carpetArea = carpetArea;
	}
	public String getFloorplanImage() {
		return floorplanImage;
	}
	public void setFloorplanImage(String floorplanImage) {
		this.floorplanImage = floorplanImage;
	}
	public Double getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(Double unitNumber) {
		this.unitNumber = unitNumber;
	}
	public String getFloorName() {
		return floorName;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	public Double getPerSqftCost() {
		return perSqftCost;
	}
	public void setPerSqftCost(Double perSqftCost) {
		this.perSqftCost = perSqftCost;
	}
	public Double getPlc() {
		return plc;
	}
	public void setPlc(Double plc) {
		this.plc = plc;
	}
	public Double getFloorRise() {
		return floorRise;
	}
	public void setFloorRise(Double floorRise) {
		this.floorRise = floorRise;
	}
	public PdfDetails[] getPdfDetails() {
		return pdfDetails;
	}
	public void setPdfDetails(PdfDetails[] pdfDetails) {
		this.pdfDetails = pdfDetails;
	}
	public Boolean getIspdfDetailsAvailable() {
		return ispdfDetailsAvailable;
	}
	public void setIspdfDetailsAvailable(Boolean ispdfDetailsAvailable) {
		this.ispdfDetailsAvailable = ispdfDetailsAvailable;
	}
	public Double getInfraCharges() {
		return infraCharges;
	}
	public void setInfraCharges(Double infraCharges) {
		this.infraCharges = infraCharges;
	}
	public Double getCarParking() {
		return carParking;
	}
	public void setCarParking(Double carParking) {
		this.carParking = carParking;
	}
	public Double getClubHouse() {
		return clubHouse;
	}
	public void setClubHouse(Double clubHouse) {
		this.clubHouse = clubHouse;
	}
	public FlatDTO getFlatDTO() {
		return flatDTO;
	}
	public void setFlatDTO(FlatDTO flatDTO) {
		this.flatDTO = flatDTO;
	}
	public FlatRegistrationDTO getFlatRegistrationDTO() {
		return flatRegistrationDTO;
	}
	public void setFlatRegistrationDTO(FlatRegistrationDTO flatRegistrationDTO) {
		this.flatRegistrationDTO = flatRegistrationDTO;
	}
	public FlatDetailsDTO getFlatDetailsDTO() {
		return flatDetailsDTO;
	}
	public void setFlatDetailsDTO(FlatDetailsDTO flatDetailsDTO) {
		this.flatDetailsDTO = flatDetailsDTO;
	}
	public FlatCostDTO getFlatCostDTO() {
		return flatCostDTO;
	}
	public void setFlatCostDTO(FlatCostDTO flatCostDTO) {
		this.flatCostDTO = flatCostDTO;
	}
	public FloorDetailsDTO getFloorDetailsDTO() {
		return floorDetailsDTO;
	}
	public void setFloorDetailsDTO(FloorDetailsDTO floorDetailsDTO) {
		this.floorDetailsDTO = floorDetailsDTO;
	}
	public BlockDetailsDTO getBlockDetailsDTO() {
		return blockDetailsDTO;
	}
	public void setBlockDetailsDTO(BlockDetailsDTO blockDetailsDTO) {
		this.blockDetailsDTO = blockDetailsDTO;
	}
	public BlockDTO getBlockDTO() {
		return blockDTO;
	}
	public void setBlockDTO(BlockDTO blockDTO) {
		this.blockDTO = blockDTO;
	}
	public FloorDTO getFloorDTO() {
		return floorDTO;
	}
	public void setFloorDTO(FloorDTO floorDTO) {
		this.floorDTO = floorDTO;
	}
	public List<FlatDetailsInfo> getFlatDetailsInfoList() {
		return flatDetailsInfoList;
	}
	public void setFlatDetailsInfoList(List<FlatDetailsInfo> flatDetailsInfoList) {
		this.flatDetailsInfoList = flatDetailsInfoList;
	}
	public List<FlatDetailsInfo> getAmenitiesCostInfoList() {
		return amenitiesCostInfoList;
	}
	public void setAmenitiesCostInfoList(List<FlatDetailsInfo> amenitiesCostInfoList) {
		this.amenitiesCostInfoList = amenitiesCostInfoList;
	}
	public List<FlatModificationCostDTO> getFlatModificationCostDTOLIST() {
		return flatModificationCostDTOLIST;
	}
	public void setFlatModificationCostDTOLIST(List<FlatModificationCostDTO> flatModificationCostDTOLIST) {
		this.flatModificationCostDTOLIST = flatModificationCostDTOLIST;
	}
	public Boolean getIsflatModificationCostDTOListAvailable() {
		return isflatModificationCostDTOListAvailable;
	}
	public void setIsflatModificationCostDTOListAvailable(Boolean isflatModificationCostDTOListAvailable) {
		this.isflatModificationCostDTOListAvailable = isflatModificationCostDTOListAvailable;
	}
	public List<String> getPropertyUpdatesCarouselList() {
		return propertyUpdatesCarouselList;
	}
	public void setPropertyUpdatesCarouselList(List<String> propertyUpdatesCarouselList) {
		this.propertyUpdatesCarouselList = propertyUpdatesCarouselList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "PropertyDetails [sbua=" + sbua + ", carpetArea=" + carpetArea + ", floorplanImage=" + floorplanImage
				+ ", unitNumber=" + unitNumber + ", floorName=" + floorName + ", perSqftCost=" + perSqftCost + ", plc="
				+ plc + ", floorRise=" + floorRise + ", pdfDetails=" + Arrays.toString(pdfDetails)
				+ ", ispdfDetailsAvailable=" + ispdfDetailsAvailable + ", infraCharges=" + infraCharges
				+ ", carParking=" + carParking + ", clubHouse=" + clubHouse + ", flatDTO=" + flatDTO
				+ ", flatRegistrationDTO=" + flatRegistrationDTO + ", flatDetailsDTO=" + flatDetailsDTO
				+ ", flatCostDTO=" + flatCostDTO + ", floorDetailsDTO=" + floorDetailsDTO + ", blockDetailsDTO="
				+ blockDetailsDTO + ", blockDTO=" + blockDTO + ", floorDTO=" + floorDTO + ", flatDetailsInfoList="
				+ flatDetailsInfoList + ", amenitiesCostInfoList=" + amenitiesCostInfoList
				+ ", flatModificationCostDTOLIST=" + flatModificationCostDTOLIST
				+ ", isflatModificationCostDTOListAvailable=" + isflatModificationCostDTOListAvailable
				+ ", propertyUpdatesCarouselList=" + propertyUpdatesCarouselList + "]";
	}
	
}
