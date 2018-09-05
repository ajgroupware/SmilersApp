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
 * Encabezado general
 **/
@ApiModel(description = "Encabezado general")
public class GeneralHeader {
  
  @SerializedName("id")
  private Long id = null;
  @SerializedName("title")
  private String title = null;
  @SerializedName("description")
  private String description = null;
  @SerializedName("designOrder")
  private Integer designOrder = null;
  @SerializedName("designColor")
  private String designColor = null;
  @SerializedName("isPublished")
  private Boolean isPublished = null;

  /**
   * Identificador único del encabezado
   **/
  @ApiModelProperty(value = "Identificador único del encabezado")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Titulo del encabezado o bienvenida
   **/
  @ApiModelProperty(required = true, value = "Titulo del encabezado o bienvenida")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Descripción o ayuda del encabezado
   **/
  @ApiModelProperty(value = "Descripción o ayuda del encabezado")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Orden en que se lista el encabezado
   **/
  @ApiModelProperty(value = "Orden en que se lista el encabezado")
  public Integer getDesignOrder() {
    return designOrder;
  }
  public void setDesignOrder(Integer designOrder) {
    this.designOrder = designOrder;
  }

  /**
   * Color del encabezado
   **/
  @ApiModelProperty(value = "Color del encabezado")
  public String getDesignColor() {
    return designColor;
  }
  public void setDesignColor(String designColor) {
    this.designColor = designColor;
  }

  /**
   * Encabezado activo o listo para visualizar
   **/
  @ApiModelProperty(value = "Encabezado activo o listo para visualizar")
  public Boolean getIsPublished() {
    return isPublished;
  }
  public void setIsPublished(Boolean isPublished) {
    this.isPublished = isPublished;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GeneralHeader generalHeader = (GeneralHeader) o;
    return (this.id == null ? generalHeader.id == null : this.id.equals(generalHeader.id)) &&
        (this.title == null ? generalHeader.title == null : this.title.equals(generalHeader.title)) &&
        (this.description == null ? generalHeader.description == null : this.description.equals(generalHeader.description)) &&
        (this.designOrder == null ? generalHeader.designOrder == null : this.designOrder.equals(generalHeader.designOrder)) &&
        (this.designColor == null ? generalHeader.designColor == null : this.designColor.equals(generalHeader.designColor)) &&
        (this.isPublished == null ? generalHeader.isPublished == null : this.isPublished.equals(generalHeader.isPublished));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.title == null ? 0: this.title.hashCode());
    result = 31 * result + (this.description == null ? 0: this.description.hashCode());
    result = 31 * result + (this.designOrder == null ? 0: this.designOrder.hashCode());
    result = 31 * result + (this.designColor == null ? 0: this.designColor.hashCode());
    result = 31 * result + (this.isPublished == null ? 0: this.isPublished.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeneralHeader {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  designOrder: ").append(designOrder).append("\n");
    sb.append("  designColor: ").append(designColor).append("\n");
    sb.append("  isPublished: ").append(isPublished).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
