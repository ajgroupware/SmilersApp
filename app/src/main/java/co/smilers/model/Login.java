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

@ApiModel(description = "")
public class Login {
  
  @SerializedName("userName")
  private String userName = null;
  @SerializedName("password")
  private String password = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Login login = (Login) o;
    return (this.userName == null ? login.userName == null : this.userName.equals(login.userName)) &&
        (this.password == null ? login.password == null : this.password.equals(login.password));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.userName == null ? 0: this.userName.hashCode());
    result = 31 * result + (this.password == null ? 0: this.password.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Login {\n");
    
    sb.append("  userName: ").append(userName).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
