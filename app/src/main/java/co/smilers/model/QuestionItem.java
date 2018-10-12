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
 * Definición de las preguntas de una campaña
 **/
@ApiModel(description = "Definición de las preguntas de una campaña")
public class QuestionItem {
  
  @SerializedName("code")
  private Long code = null;
  @SerializedName("title")
  private String title = null;
  @SerializedName("description")
  private String description = null;
  @SerializedName("designOrder")
  private Integer designOrder = null;
  @SerializedName("designColor")
  private String designColor = null;
  @SerializedName("minScore")
  private Double minScore = null;
  @SerializedName("isPublished")
  private Boolean isPublished = null;
  @SerializedName("receiveComment")
  private Boolean receiveComment = null;
  @SerializedName("sendSmsNotification")
  private Boolean sendSmsNotification = null;
  @SerializedName("questionType")
  private String questionType = null;

  /**
   * Identificador único de la pregunta
   **/
  @ApiModelProperty(value = "Identificador único de la pregunta")
  public Long getCode() {
    return code;
  }
  public void setCode(Long code) {
    this.code = code;
  }

  /**
   * Pregunta
   **/
  @ApiModelProperty(required = true, value = "Pregunta")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Descripción o soporte de ayuda a la pregunta
   **/
  @ApiModelProperty(value = "Descripción o soporte de ayuda a la pregunta")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Orden en que se listan las preguntas
   **/
  @ApiModelProperty(value = "Orden en que se listan las preguntas")
  public Integer getDesignOrder() {
    return designOrder;
  }
  public void setDesignOrder(Integer designOrder) {
    this.designOrder = designOrder;
  }

  /**
   * Color exadecimal de la pregunta
   **/
  @ApiModelProperty(value = "Color exadecimal de la pregunta")
  public String getDesignColor() {
    return designColor;
  }
  public void setDesignColor(String designColor) {
    this.designColor = designColor;
  }

  /**
   * Calificación mínima recidida por un usuario para generar alerta
   **/
  @ApiModelProperty(value = "Calificación mínima recidida por un usuario para generar alerta")
  public Double getMinScore() {
    return minScore;
  }
  public void setMinScore(Double minScore) {
    this.minScore = minScore;
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

  /**
   * Indica si la pregunta habilita comentario
   **/
  @ApiModelProperty(value = "Indica si la pregunta habilita comentario")
  public Boolean getReceiveComment() {
    return receiveComment;
  }
  public void setReceiveComment(Boolean receiveComment) {
    this.receiveComment = receiveComment;
  }

  /**
   * Indica la posibilidad de enviar notificación de Alerta SMS
   **/
  @ApiModelProperty(value = "Indica la posibilidad de enviar notificación de Alerta SMS")
  public Boolean getSendSmsNotification() {
    return sendSmsNotification;
  }
  public void setSendSmsNotification(Boolean sendSmsNotification) {
    this.sendSmsNotification = sendSmsNotification;
  }

  /**
   * Tipo de pregunta
   **/
  @ApiModelProperty(value = "Tipo de pregunta")
  public String getQuestionType() {
    return questionType;
  }
  public void setQuestionType(String questionType) {
    this.questionType = questionType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuestionItem questionItem = (QuestionItem) o;
    return (this.code == null ? questionItem.code == null : this.code.equals(questionItem.code)) &&
        (this.title == null ? questionItem.title == null : this.title.equals(questionItem.title)) &&
        (this.description == null ? questionItem.description == null : this.description.equals(questionItem.description)) &&
        (this.designOrder == null ? questionItem.designOrder == null : this.designOrder.equals(questionItem.designOrder)) &&
        (this.designColor == null ? questionItem.designColor == null : this.designColor.equals(questionItem.designColor)) &&
        (this.minScore == null ? questionItem.minScore == null : this.minScore.equals(questionItem.minScore)) &&
        (this.isPublished == null ? questionItem.isPublished == null : this.isPublished.equals(questionItem.isPublished)) &&
        (this.receiveComment == null ? questionItem.receiveComment == null : this.receiveComment.equals(questionItem.receiveComment)) &&
        (this.sendSmsNotification == null ? questionItem.sendSmsNotification == null : this.sendSmsNotification.equals(questionItem.sendSmsNotification)) &&
        (this.questionType == null ? questionItem.questionType == null : this.questionType.equals(questionItem.questionType));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.code == null ? 0: this.code.hashCode());
    result = 31 * result + (this.title == null ? 0: this.title.hashCode());
    result = 31 * result + (this.description == null ? 0: this.description.hashCode());
    result = 31 * result + (this.designOrder == null ? 0: this.designOrder.hashCode());
    result = 31 * result + (this.designColor == null ? 0: this.designColor.hashCode());
    result = 31 * result + (this.minScore == null ? 0: this.minScore.hashCode());
    result = 31 * result + (this.isPublished == null ? 0: this.isPublished.hashCode());
    result = 31 * result + (this.receiveComment == null ? 0: this.receiveComment.hashCode());
    result = 31 * result + (this.sendSmsNotification == null ? 0: this.sendSmsNotification.hashCode());
    result = 31 * result + (this.questionType == null ? 0: this.questionType.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class QuestionItem {\n");
    
    sb.append("  code: ").append(code).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  designOrder: ").append(designOrder).append("\n");
    sb.append("  designColor: ").append(designColor).append("\n");
    sb.append("  minScore: ").append(minScore).append("\n");
    sb.append("  isPublished: ").append(isPublished).append("\n");
    sb.append("  receiveComment: ").append(receiveComment).append("\n");
    sb.append("  sendSmsNotification: ").append(sendSmsNotification).append("\n");
    sb.append("  questionType: ").append(questionType).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
