/**
 * Smiller
 * Api de servicios REST para la aplicación [https://www.smilers.co](https://www.smilers.co).
 *
 * OpenAPI spec version: 1.0.0
 * Contact: apps@groupware.com.co
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package co.smilers.model;

import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;

/**
 * Dispositivos medidores asociados a una campaña
 **/
@ApiModel(description = "Dispositivos medidores asociados a una campaña")
public class SmsCellPhone {
  
  @SerializedName("id")
  private Long id = null;
  @SerializedName("cellPhoneNumber")
  private String cellPhoneNumber = null;
  @SerializedName("campaignCode")
  private Long campaignCode = null;
  @SerializedName("headquarterCode")
  private Long headquarterCode = null;
  @SerializedName("zoneCode")
  private Long zoneCode = null;
  @SerializedName("isActive")
  private Boolean isActive = null;

  /**
   * Identificador único
   **/
  @ApiModelProperty(value = "Identificador único")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getCellPhoneNumber() {
    return cellPhoneNumber;
  }
  public void setCellPhoneNumber(String cellPhoneNumber) {
    this.cellPhoneNumber = cellPhoneNumber;
  }

  /**
   * Campaña asociada
   **/
  @ApiModelProperty(value = "Campaña asociada")
  public Long getCampaignCode() {
    return campaignCode;
  }
  public void setCampaignCode(Long campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * Sede asociada
   **/
  @ApiModelProperty(value = "Sede asociada")
  public Long getHeadquarterCode() {
    return headquarterCode;
  }
  public void setHeadquarterCode(Long headquarterCode) {
    this.headquarterCode = headquarterCode;
  }

  /**
   * 
   **/
  @ApiModelProperty(value = "")
  public Long getZoneCode() {
    return zoneCode;
  }
  public void setZoneCode(Long zoneCode) {
    this.zoneCode = zoneCode;
  }

  /**
   * Si está habilitado
   **/
  @ApiModelProperty(value = "Si está habilitado")
  public Boolean getIsActive() {
    return isActive;
  }
  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SmsCellPhone smsCellPhone = (SmsCellPhone) o;
    return (this.id == null ? smsCellPhone.id == null : this.id.equals(smsCellPhone.id)) &&
        (this.cellPhoneNumber == null ? smsCellPhone.cellPhoneNumber == null : this.cellPhoneNumber.equals(smsCellPhone.cellPhoneNumber)) &&
        (this.campaignCode == null ? smsCellPhone.campaignCode == null : this.campaignCode.equals(smsCellPhone.campaignCode)) &&
        (this.headquarterCode == null ? smsCellPhone.headquarterCode == null : this.headquarterCode.equals(smsCellPhone.headquarterCode)) &&
        (this.zoneCode == null ? smsCellPhone.zoneCode == null : this.zoneCode.equals(smsCellPhone.zoneCode)) &&
        (this.isActive == null ? smsCellPhone.isActive == null : this.isActive.equals(smsCellPhone.isActive));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.cellPhoneNumber == null ? 0: this.cellPhoneNumber.hashCode());
    result = 31 * result + (this.campaignCode == null ? 0: this.campaignCode.hashCode());
    result = 31 * result + (this.headquarterCode == null ? 0: this.headquarterCode.hashCode());
    result = 31 * result + (this.zoneCode == null ? 0: this.zoneCode.hashCode());
    result = 31 * result + (this.isActive == null ? 0: this.isActive.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SmsCellPhone {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  cellPhoneNumber: ").append(cellPhoneNumber).append("\n");
    sb.append("  campaignCode: ").append(campaignCode).append("\n");
    sb.append("  headquarterCode: ").append(headquarterCode).append("\n");
    sb.append("  zoneCode: ").append(zoneCode).append("\n");
    sb.append("  isActive: ").append(isActive).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}