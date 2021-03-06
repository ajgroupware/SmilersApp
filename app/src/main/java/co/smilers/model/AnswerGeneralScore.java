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
 * Respuesta y puntaje otorgado por los usuarios a las campañas
 **/
@ApiModel(description = "Respuesta y puntaje otorgado por los usuarios a las campañas")
public class AnswerGeneralScore {
  
  @SerializedName("id")
  private Long id = null;
  @SerializedName("headquarter")
  private Headquarter headquarter = null;
  @SerializedName("zone")
  private Zone zone = null;
  @SerializedName("questionItem")
  private QuestionItem questionItem = null;
  @SerializedName("meterDevice")
  private MeterDevice meterDevice = null;
  @SerializedName("cityCode")
  private Long cityCode = null;
  @SerializedName("cityName")
  private String cityName = null;
  @SerializedName("excellent")
  private Integer excellent = null;
  @SerializedName("good")
  private Integer good = null;
  @SerializedName("moderate")
  private Integer moderate = null;
  @SerializedName("bad")
  private Integer bad = null;
  @SerializedName("poor")
  private Integer poor = null;
  @SerializedName("score")
  private Integer score = null;
  @SerializedName("registrationDate")
  private String registrationDate = null;
  @SerializedName("comment")
  private String comment = null;
  @SerializedName("userId")
  private String userId = null;

  /**
   * Identificador único de la respuesta
   **/
  @ApiModelProperty(value = "Identificador único de la respuesta")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Headquarter getHeadquarter() {
    return headquarter;
  }
  public void setHeadquarter(Headquarter headquarter) {
    this.headquarter = headquarter;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Zone getZone() {
    return zone;
  }
  public void setZone(Zone zone) {
    this.zone = zone;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public QuestionItem getQuestionItem() {
    return questionItem;
  }
  public void setQuestionItem(QuestionItem questionItem) {
    this.questionItem = questionItem;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public MeterDevice getMeterDevice() {
    return meterDevice;
  }
  public void setMeterDevice(MeterDevice meterDevice) {
    this.meterDevice = meterDevice;
  }

  /**
   * 
   **/
  @ApiModelProperty(value = "")
  public Long getCityCode() {
    return cityCode;
  }
  public void setCityCode(Long cityCode) {
    this.cityCode = cityCode;
  }

  /**
   * 
   **/
  @ApiModelProperty(value = "")
  public String getCityName() {
    return cityName;
  }
  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  /**
   * Calificación excelente 5
   **/
  @ApiModelProperty(value = "Calificación excelente 5")
  public Integer getExcellent() {
    return excellent;
  }
  public void setExcellent(Integer excellent) {
    this.excellent = excellent;
  }

  /**
   * Calificación buena 4
   **/
  @ApiModelProperty(value = "Calificación buena 4")
  public Integer getGood() {
    return good;
  }
  public void setGood(Integer good) {
    this.good = good;
  }

  /**
   * Calificación regular 3
   **/
  @ApiModelProperty(value = "Calificación regular 3")
  public Integer getModerate() {
    return moderate;
  }
  public void setModerate(Integer moderate) {
    this.moderate = moderate;
  }

  /**
   * Calificación mala 2
   **/
  @ApiModelProperty(value = "Calificación mala 2")
  public Integer getBad() {
    return bad;
  }
  public void setBad(Integer bad) {
    this.bad = bad;
  }

  /**
   * Calificación deficiente 1
   **/
  @ApiModelProperty(value = "Calificación deficiente 1")
  public Integer getPoor() {
    return poor;
  }
  public void setPoor(Integer poor) {
    this.poor = poor;
  }

  /**
   * Calificación total
   **/
  @ApiModelProperty(value = "Calificación total")
  public Integer getScore() {
    return score;
  }
  public void setScore(Integer score) {
    this.score = score;
  }

  /**
   * Fecha en que se realizó la calificación
   **/
  @ApiModelProperty(value = "Fecha en que se realizó la calificación")
  public String getRegistrationDate() {
    return registrationDate;
  }
  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  /**
   * Comentario del usuario
   **/
  @ApiModelProperty(value = "Comentario del usuario")
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * Identificación del usuario que califica
   **/
  @ApiModelProperty(value = "Identificación del usuario que califica")
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnswerGeneralScore answerGeneralScore = (AnswerGeneralScore) o;
    return (this.id == null ? answerGeneralScore.id == null : this.id.equals(answerGeneralScore.id)) &&
        (this.headquarter == null ? answerGeneralScore.headquarter == null : this.headquarter.equals(answerGeneralScore.headquarter)) &&
        (this.zone == null ? answerGeneralScore.zone == null : this.zone.equals(answerGeneralScore.zone)) &&
        (this.questionItem == null ? answerGeneralScore.questionItem == null : this.questionItem.equals(answerGeneralScore.questionItem)) &&
        (this.meterDevice == null ? answerGeneralScore.meterDevice == null : this.meterDevice.equals(answerGeneralScore.meterDevice)) &&
        (this.cityCode == null ? answerGeneralScore.cityCode == null : this.cityCode.equals(answerGeneralScore.cityCode)) &&
        (this.cityName == null ? answerGeneralScore.cityName == null : this.cityName.equals(answerGeneralScore.cityName)) &&
        (this.excellent == null ? answerGeneralScore.excellent == null : this.excellent.equals(answerGeneralScore.excellent)) &&
        (this.good == null ? answerGeneralScore.good == null : this.good.equals(answerGeneralScore.good)) &&
        (this.moderate == null ? answerGeneralScore.moderate == null : this.moderate.equals(answerGeneralScore.moderate)) &&
        (this.bad == null ? answerGeneralScore.bad == null : this.bad.equals(answerGeneralScore.bad)) &&
        (this.poor == null ? answerGeneralScore.poor == null : this.poor.equals(answerGeneralScore.poor)) &&
        (this.score == null ? answerGeneralScore.score == null : this.score.equals(answerGeneralScore.score)) &&
        (this.registrationDate == null ? answerGeneralScore.registrationDate == null : this.registrationDate.equals(answerGeneralScore.registrationDate)) &&
        (this.comment == null ? answerGeneralScore.comment == null : this.comment.equals(answerGeneralScore.comment)) &&
        (this.userId == null ? answerGeneralScore.userId == null : this.userId.equals(answerGeneralScore.userId));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.headquarter == null ? 0: this.headquarter.hashCode());
    result = 31 * result + (this.zone == null ? 0: this.zone.hashCode());
    result = 31 * result + (this.questionItem == null ? 0: this.questionItem.hashCode());
    result = 31 * result + (this.meterDevice == null ? 0: this.meterDevice.hashCode());
    result = 31 * result + (this.cityCode == null ? 0: this.cityCode.hashCode());
    result = 31 * result + (this.cityName == null ? 0: this.cityName.hashCode());
    result = 31 * result + (this.excellent == null ? 0: this.excellent.hashCode());
    result = 31 * result + (this.good == null ? 0: this.good.hashCode());
    result = 31 * result + (this.moderate == null ? 0: this.moderate.hashCode());
    result = 31 * result + (this.bad == null ? 0: this.bad.hashCode());
    result = 31 * result + (this.poor == null ? 0: this.poor.hashCode());
    result = 31 * result + (this.score == null ? 0: this.score.hashCode());
    result = 31 * result + (this.registrationDate == null ? 0: this.registrationDate.hashCode());
    result = 31 * result + (this.comment == null ? 0: this.comment.hashCode());
    result = 31 * result + (this.userId == null ? 0: this.userId.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnswerGeneralScore {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  headquarter: ").append(headquarter).append("\n");
    sb.append("  zone: ").append(zone).append("\n");
    sb.append("  questionItem: ").append(questionItem).append("\n");
    sb.append("  meterDevice: ").append(meterDevice).append("\n");
    sb.append("  cityCode: ").append(cityCode).append("\n");
    sb.append("  cityName: ").append(cityName).append("\n");
    sb.append("  excellent: ").append(excellent).append("\n");
    sb.append("  good: ").append(good).append("\n");
    sb.append("  moderate: ").append(moderate).append("\n");
    sb.append("  bad: ").append(bad).append("\n");
    sb.append("  poor: ").append(poor).append("\n");
    sb.append("  score: ").append(score).append("\n");
    sb.append("  registrationDate: ").append(registrationDate).append("\n");
    sb.append("  comment: ").append(comment).append("\n");
    sb.append("  userId: ").append(userId).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
