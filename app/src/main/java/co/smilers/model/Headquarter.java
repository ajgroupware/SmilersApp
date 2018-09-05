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
 * Sedes que puede tener una cuenta
 **/
@ApiModel(description = "Sedes que puede tener una cuenta")
public class Headquarter {
  
  @SerializedName("code")
  private Long code = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("city")
  private City city = null;
  @SerializedName("account")
  private String account = null;

  /**
   * Identificador único de la sede
   **/
  @ApiModelProperty(value = "Identificador único de la sede")
  public Long getCode() {
    return code;
  }
  public void setCode(Long code) {
    this.code = code;
  }

  /**
   * Nombre de la sede
   **/
  @ApiModelProperty(required = true, value = "Nombre de la sede")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public City getCity() {
    return city;
  }
  public void setCity(City city) {
    this.city = city;
  }

  /**
   * Cuenta asociada a la sede
   **/
  @ApiModelProperty(value = "Cuenta asociada a la sede")
  public String getAccount() {
    return account;
  }
  public void setAccount(String account) {
    this.account = account;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Headquarter headquarter = (Headquarter) o;
    return (this.code == null ? headquarter.code == null : this.code.equals(headquarter.code)) &&
        (this.name == null ? headquarter.name == null : this.name.equals(headquarter.name)) &&
        (this.city == null ? headquarter.city == null : this.city.equals(headquarter.city)) &&
        (this.account == null ? headquarter.account == null : this.account.equals(headquarter.account));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.code == null ? 0: this.code.hashCode());
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.city == null ? 0: this.city.hashCode());
    result = 31 * result + (this.account == null ? 0: this.account.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Headquarter {\n");
    
    sb.append("  code: ").append(code).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  city: ").append(city).append("\n");
    sb.append("  account: ").append(account).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}