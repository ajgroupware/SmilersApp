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

package co.smilers.api;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.com.groupware.quantum.api.ApiException;
import co.com.groupware.quantum.api.ApiInvoker;
import co.com.groupware.quantum.api.Pair;
import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerGeneralScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.RequestAssistance;

public class CampaignApi {
  String basePath = ApiUtil.BASE_PATH;
  ApiInvoker apiInvoker = ApiInvoker.getInstance();

  public void addHeader(String key, String value) {
    getInvoker().addDefaultHeader(key, value);
  }

  public ApiInvoker getInvoker() {
    return apiInvoker;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public String getBasePath() {
    return basePath;
  }


    /**
     * Agregar calificaciones generales
     * Agregar nueva lista de calificaciones generales
     * @param account Cuenta activa y asociada a la sede   * @param answerGeneralScore Objecto de resultado que será agregado
     */
    public void addAnswerGeneralScore (String account, List<AnswerGeneralScore> answerGeneralScore, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = answerGeneralScore;

        // verify the required parameter 'account' is set
        if (account == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'account' when calling addAnswerGeneralScore",
                    new ApiException(400, "Missing the required parameter 'account' when calling addAnswerGeneralScore"));
        }
        // verify the required parameter 'answerGeneralScore' is set
        if (answerGeneralScore == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'answerGeneralScore' when calling addAnswerGeneralScore",
                    new ApiException(400, "Missing the required parameter 'answerGeneralScore' when calling addAnswerGeneralScore"));
        }

        // create path and map variables
        String path = "/campaign/answerGeneralScore/{account}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "account" + "\\}", apiInvoker.escapeString(account.toString()));

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();



        String[] contentTypes = {
                "application/json; charset=utf-8","application/xml; charset=utf-8"
        };
        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


        String[] authNames = new String[] { };

        try {
            apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String localVarResponse) {

                                responseListener.onResponse(localVarResponse);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            errorListener.onErrorResponse(error);
                        }
                    });
        } catch (ApiException ex) {
            errorListener.onErrorResponse(new VolleyError(ex));
        }
    }

    /**
     * Agregar calificaciones
     * Agregar nueva lista de calificaciones
     * @param account Cuenta activa y asociada a la sede   * @param answerScore Objecto de resultado que será agregado
     */
    public void addAnswerScore (String account, List<AnswerScore> answerScore, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = answerScore;

        // verify the required parameter 'account' is set
        if (account == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'account' when calling addAnswerScore",
                    new ApiException(400, "Missing the required parameter 'account' when calling addAnswerScore"));
        }
        // verify the required parameter 'answerScore' is set
        if (answerScore == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'answerScore' when calling addAnswerScore",
                    new ApiException(400, "Missing the required parameter 'answerScore' when calling addAnswerScore"));
        }

        // create path and map variables
        String path = "/campaign/answerScore/{account}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "account" + "\\}", apiInvoker.escapeString(account.toString()));

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();



        String[] contentTypes = {
                "application/json; charset=utf-8","application/xml; charset=utf-8"
        };
        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

        String[] authNames = new String[] { };

        try {
            apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String localVarResponse) {

                                responseListener.onResponse(localVarResponse);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            errorListener.onErrorResponse(error);
                        }
                    });
        } catch (ApiException ex) {
            errorListener.onErrorResponse(new VolleyError(ex));
        }
    }

      /**
   * Lista las campañas
   * Lista las campañas
   * @param user Usuario asociado a la cuenta de la campaña   * @param title Título   * @param description Descripción   * @param startDate Fecha de inicio de la campaña yyyy-MM-dd HH:mm:ss   * @param endDate Fecha fin de la campaña yyyy-MM-dd HH:mm:ss   * @param isPublished Campañas publicadas   * @param zone Zona
  */
  public void listCampaign (String user, String title, String description, String startDate, String endDate, Boolean isPublished, Integer zone, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
    Object postBody = null;

    // verify the required parameter 'user' is set
    if (user == null) {
      VolleyError error = new VolleyError("Missing the required parameter 'user' when calling listCampaign",
        new ApiException(400, "Missing the required parameter 'user' when calling listCampaign"));
    }

    // create path and map variables
    String path = "/campaign".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();

    queryParams.addAll(ApiInvoker.parameterToPairs("", "user", user));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "title", title));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "description", description));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "startDate", startDate));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "endDate", endDate));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "isPublished", isPublished));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "zone", zone));


    String[] contentTypes = {
      
    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


    String[] authNames = new String[] { };

    try {
      apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String localVarResponse) {

              responseListener.onResponse(localVarResponse);

          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            errorListener.onErrorResponse(error);
          }
      });
    } catch (ApiException ex) {
      errorListener.onErrorResponse(new VolleyError(ex));
    }
  }

      /**
   * Lista encabezado general
   * Lista los encabezados generales
   * @param user Usuario asociado a la cuenta de la campaña   * @param title Título   * @param description Descripción   * @param isPublished Encabezado publicado
  */
  public void listGeneralHeader (String user, String title, String description, Boolean isPublished, final Response.Listener<String> responseListener, Context mContext, final Response.ErrorListener errorListener) {
    Object postBody = null;

    // verify the required parameter 'user' is set
    if (user == null) {
      VolleyError error = new VolleyError("Missing the required parameter 'user' when calling listGeneralHeader",
        new ApiException(400, "Missing the required parameter 'user' when calling listGeneralHeader"));
    }

    // create path and map variables
    String path = "/campaign/generalHeader".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();

    queryParams.addAll(ApiInvoker.parameterToPairs("", "user", user));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "title", title));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "description", description));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "isPublished", isPublished));


    String[] contentTypes = {
      
    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


    String[] authNames = new String[] { };

    try {
      apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String localVarResponse) {

              responseListener.onResponse(localVarResponse);

          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            errorListener.onErrorResponse(error);
          }
      });
    } catch (ApiException ex) {
      errorListener.onErrorResponse(new VolleyError(ex));
    }
  }

      /**
   * Lista encabezado general
   * Lista los encabezados generales
   * @param user Usuario asociado a la cuenta   * @param title Título   * @param description Descripción   * @param isPublished Pregunta publicada
  */
  public void listGeneralQuestion (String user, String title, String description, Boolean isPublished, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
    Object postBody = null;

    // verify the required parameter 'user' is set
    if (user == null) {
      VolleyError error = new VolleyError("Missing the required parameter 'user' when calling listGeneralQuestion",
        new ApiException(400, "Missing the required parameter 'user' when calling listGeneralQuestion"));
    }

    // create path and map variables
    String path = "/campaign/generalQuestion".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();

    queryParams.addAll(ApiInvoker.parameterToPairs("", "user", user));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "title", title));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "description", description));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "isPublished", isPublished));


    String[] contentTypes = {
      
    };
    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


    String[] authNames = new String[] { };

    try {
      apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String localVarResponse) {

              responseListener.onResponse(localVarResponse);

          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            errorListener.onErrorResponse(error);
          }
      });
    } catch (ApiException ex) {
      errorListener.onErrorResponse(new VolleyError(ex));
    }
  }

    /**
     * Lista celulares
     * Lista celulares
     * @param account Cuenta activa y asociada a la sede   * @param zone Zona   * @param campaign Campaña   * @param headquaeter Sede
     */
    public void listSmsCellPhone (String account, Integer zone, Integer campaign, Integer headquaeter, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = null;

        // verify the required parameter 'account' is set
        if (account == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'account' when calling listSmsCellPhone",
                    new ApiException(400, "Missing the required parameter 'account' when calling listSmsCellPhone"));
        }

        // create path and map variables
        String path = "/campaign/smsCellPhone/{account}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "account" + "\\}", apiInvoker.escapeString(account.toString()));

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();

        queryParams.addAll(ApiInvoker.parameterToPairs("", "zone", zone));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "campaign", campaign));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "headquaeter", headquaeter));


        String[] contentTypes = {

        };
        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


        String[] authNames = new String[] { };

        try {
            apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String localVarResponse) {

                                responseListener.onResponse(localVarResponse);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            errorListener.onErrorResponse(error);
                        }
                    });
        } catch (ApiException ex) {
            errorListener.onErrorResponse(new VolleyError(ex));
        }
    }

    /**
     * Lista campañas en zonas
     * Lista campañas en zonas
     * @param account Cuenta activa y asociada a la sede   * @param zone Zona   * @param campaign Campaña
     */
    public void listTargetZone (String account, Integer zone, Integer campaign, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = null;

        // verify the required parameter 'account' is set
        if (account == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'account' when calling listTargetZone",
                    new ApiException(400, "Missing the required parameter 'account' when calling listTargetZone"));
        }

        // create path and map variables
        String path = "/campaign/targetZone/{account}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "account" + "\\}", apiInvoker.escapeString(account.toString()));

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();

        queryParams.addAll(ApiInvoker.parameterToPairs("", "zone", zone));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "campaign", campaign));


        String[] contentTypes = {

        };
        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


        String[] authNames = new String[] { };

        try {
            apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String localVarResponse) {

                                responseListener.onResponse(localVarResponse);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            errorListener.onErrorResponse(error);
                        }
                    });
        } catch (ApiException ex) {
            errorListener.onErrorResponse(new VolleyError(ex));
        }
    }

    /**
     * Lista píe de página
     * Lista los píe de página de una campaña
     * @param account Usuario asociado a la cuenta de la campaña   * @param title Título   * @param description Descripción   * @param isPublished Encabezado publicado   * @param campaign Campaña
     */
    public void listCampaignFooter (String account, String title, String description, Boolean isPublished, Integer campaign, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = null;

        // verify the required parameter 'account' is set
        if (account == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'account' when calling listCampaignFooter",
                    new ApiException(400, "Missing the required parameter 'account' when calling listCampaignFooter"));
        }

        // create path and map variables
        String path = "/campaign/campaignFooter".replaceAll("\\{format\\}","json");

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();

        queryParams.addAll(ApiInvoker.parameterToPairs("", "account", account));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "title", title));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "description", description));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "isPublished", isPublished));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "campaign", campaign));


        String[] contentTypes = {

        };
        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


        String[] authNames = new String[] {  };

        try {
            apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String localVarResponse) {

                                responseListener.onResponse(localVarResponse);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            errorListener.onErrorResponse(error);
                        }
                    });
        } catch (ApiException ex) {
            errorListener.onErrorResponse(new VolleyError(ex));
        }
    }

    /**
     * Agregar solicitud de asistencia
     * Agregar nueva solicitud de asistencia
     * @param account Cuenta activa y asociada a la sede   * @param requestAssistance Objecto de solicitud
     */
    public void addRequestAssistance (String account, List<RequestAssistance> requestAssistance, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = requestAssistance;

        // verify the required parameter 'account' is set
        if (account == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'account' when calling addRequestAssistance",
                    new ApiException(400, "Missing the required parameter 'account' when calling addRequestAssistance"));
        }
        // verify the required parameter 'requestAssistance' is set
        if (requestAssistance == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'requestAssistance' when calling addRequestAssistance",
                    new ApiException(400, "Missing the required parameter 'requestAssistance' when calling addRequestAssistance"));
        }

        // create path and map variables
        String path = "/campaign/requestAssistance/{account}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "account" + "\\}", apiInvoker.escapeString(account.toString()));

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();



        String[] contentTypes = {
                "application/json; charset=utf-8","application/xml; charset=utf-8"
        };
        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


        String[] authNames = new String[] { };

        try {
            apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String localVarResponse) {

                                responseListener.onResponse(localVarResponse);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            errorListener.onErrorResponse(error);
                        }
                    });
        } catch (ApiException ex) {
            errorListener.onErrorResponse(new VolleyError(ex));
        }
    }

    /**
     * Agregar calificaciones SI/NO
     * Agregar nueva lista de calificaciones SI/NO
     * @param account Cuenta activa y asociada a la sede   * @param answerBooleanScore Objecto de resultado que será agregado
     */
    public void addAnswerBooleanScore (String account, List<AnswerBooleanScore> answerBooleanScore, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = answerBooleanScore;

        // verify the required parameter 'account' is set
        if (account == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'account' when calling addAnswerBooleanScore",
                    new ApiException(400, "Missing the required parameter 'account' when calling addAnswerBooleanScore"));
        }
        // verify the required parameter 'answerBooleanScore' is set
        if (answerBooleanScore == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'answerBooleanScore' when calling addAnswerBooleanScore",
                    new ApiException(400, "Missing the required parameter 'answerBooleanScore' when calling addAnswerBooleanScore"));
        }

        // create path and map variables
        String path = "/campaign/answerBooleanScore/{account}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "account" + "\\}", apiInvoker.escapeString(account.toString()));

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();



        String[] contentTypes = {
                "application/json; charset=utf-8","application/xml; charset=utf-8"
        };
        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


        String[] authNames = new String[] { };

        try {
            apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType, authNames, mContext,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String localVarResponse) {

                            responseListener.onResponse(localVarResponse);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            errorListener.onErrorResponse(error);
                        }
                    });
        } catch (ApiException ex) {
            errorListener.onErrorResponse(new VolleyError(ex));
        }
    }
}
