package acsse.csc03a3.BlockChain;

import java.io.Serializable;

public class GunData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5352789100834580596L;

	//firearm details;
	private String FirearmSerielnum;
	private String FirearmType;
	private String FirearmModel;
	
	private String FirearmName;
	private String DateManufactured;
	
	
	
	//manufacturer details;
	private String manufacName;
	private String manufacAddress;
	private String manufacEmail;
	private String manufacLicence;
	
	
	//buyer details;
	private String BuyerFullnames;
	private String BuyerIDNum;
	private String BuyerEmail;
	private String BuyerPhoneNumber;
	private String ReasonsForBuying;
	private String PurchaseDate;
	
	
	//Authorities License details;
	private String status;
	private String LicenseNum;
	private String AuthorityApprovalData;
	
	
	public GunData(String FirearmSerielnum, String FirearmType, String name, String model, String DateManufactured, String manufacName,String 
			manufacAddress, String manufacEmail, String manufacLicence,String status)
	{
		this.FirearmSerielnum=FirearmSerielnum;
		this.FirearmType=FirearmType;
		this.FirearmModel=model;
		this.FirearmName=name;
		this.DateManufactured=DateManufactured;
		this.manufacName=manufacName;
		this.manufacAddress=manufacAddress;
		this.manufacEmail=manufacEmail;
		this.manufacLicence=manufacLicence;
		this.status=status;
	}
	
	
	public String getFirearmSerielnum() {
		return FirearmSerielnum;
	}
	public void setFirearmSerielnum(String firearmSerielnum) {
		FirearmSerielnum = firearmSerielnum;
	}
	
	/**
	 * @return the firearmModel
	 */
	public String getFirearmModel() {
		return FirearmModel;
	}
	/**
	 * @param firearmModel the firearmModel to set
	 */
	public void setFirearmModel(String firearmModel) {
		FirearmModel = firearmModel;
	}
	/**
	 * @return the firearmType
	 */
	public String getFirearmType() {
		return FirearmType;
	}
	/**
	 * @param firearmType the firearmType to set
	 */
	public void setFirearmType(String firearmType) {
		FirearmType = firearmType;
	}
	/**
	 * @return the dateManufactured
	 */
	public String getDateManufactured() {
		return DateManufactured;
	}
	/**
	 * @param dateManufactured the dateManufactured to set
	 */
	public void setDateManufactured(String dateManufactured) {
		DateManufactured = dateManufactured;
	}

	
	/**
	
	/**
	 * @return the manufacName
	 */
	public String getManufacName() {
		return manufacName;
	}
	/**
	 * @param manufacName the manufacName to set
	 */
	public void setManufacName(String manufacName) {
		this.manufacName = manufacName;
	}
	/**
	 * @return the manufacAddress
	 */
	public String getManufacAddress() {
		return manufacAddress;
	}
	/**
	 * @param manufacAddress the manufacAddress to set
	 */
	public void setManufacAddress(String manufacAddress) {
		this.manufacAddress = manufacAddress;
	}
	/**
	 * @return the manufacEmail
	 */
	public String getManufacEmail() {
		return manufacEmail;
	}
	/**
	 * @param manufacEmail the manufacEmail to set
	 */
	public void setManufacEmail(String manufacEmail) {
		this.manufacEmail = manufacEmail;
	}
	/**
	 * @return the manufacLicence
	 */
	public String getManufacLicence() {
		return manufacLicence;
	}
	/**
	 * @param manufacLicence the manufacLicence to set
	 */
	public void setManufacLicence(String manufacLicence) {
		this.manufacLicence = manufacLicence;
	}
	/**
	 * @return the buyerFullnames
	 */
	public String getBuyerFullnames() {
		return BuyerFullnames;
	}
	/**
	 * @param buyerFullnames the buyerFullnames to set
	 */
	public void setBuyerFullnames(String buyerFullnames) {
		BuyerFullnames = buyerFullnames;
	}
	/**
	 * @return the buyerIDNum
	 */
	public String getBuyerIDNum() {
		return BuyerIDNum;
	}
	/**
	 * @param buyerIDNum the buyerIDNum to set
	 */
	public void setBuyerIDNum(String buyerIDNum) {
		BuyerIDNum = buyerIDNum;
	}
	/**
	 * @return the buyerEmail
	 */
	public String getBuyerEmail() {
		return BuyerEmail;
	}
	/**
	 * @param buyerEmail the buyerEmail to set
	 */
	public void setBuyerEmail(String buyerEmail) {
		BuyerEmail = buyerEmail;
	}
	/**
	 * @return the firearmName
	 */
	public String getFirearmName() {
		return FirearmName;
	}


	/**
	 * @param firearmName the firearmName to set
	 */
	public void setFirearmName(String firearmName) {
		FirearmName = firearmName;
	}
	/**
	 * @return the buyerPhoneNumber
	 */
	public String getBuyerPhoneNumber() {
		return BuyerPhoneNumber;
	}
	/**
	 * @param buyerPhoneNumber the buyerPhoneNumber to set
	 */
	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		BuyerPhoneNumber = buyerPhoneNumber;
	}
	/**
	 * @return the reasonsForBuying
	 */
	public String getReasonsForBuying() {
		return ReasonsForBuying;
	}
	/**
	 * @param reasonsForBuying the reasonsForBuying to set
	 */
	public void setReasonsForBuying(String reasonsForBuying) {
		ReasonsForBuying = reasonsForBuying;
	}
	/**
	 * @return the purchaseDate
	 */
	public String getPurchaseDate() {
		return PurchaseDate;
	}
	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(String purchaseDate) {
		PurchaseDate = purchaseDate;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the licenseNum
	 */
	public String getLicenseNum() {
		return LicenseNum;
	}
	/**
	 * @param licenseNum the licenseNum to set
	 */
	public void setLicenseNum(String licenseNum) {
		LicenseNum = licenseNum;
	}
	/**
	 * @return the authorityApprovalData
	 */
	public String getAuthorityApprovalData() {
		return AuthorityApprovalData;
	}
	/**
	 * @param authorityApprovalData the authorityApprovalData to set
	 */
	public void setAuthorityApprovalData(String authorityApprovalData) {
		AuthorityApprovalData = authorityApprovalData;
	}
	
	
	
}
