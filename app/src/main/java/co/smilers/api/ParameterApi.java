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

public class ParameterApi {

  private static final String TAG = ParameterApi.class.getSimpleName();

  ApiInvoker apiInvoker = ApiInvoker.getInstance();
  String basePath = ApiUtil.BASE_PATH;
  public void addHeader(String key, String value) {
    getInvoker().addDefaultHeader(key, value);
  }

  public ApiInvoker getInvoker() {
    return apiInvoker;
  }




    /**
     * Lista las sedes de una cuenta
     * Lista las sedes de una cuenta - filtra por diferentes parámetros
     * @param account Cuenta asociada   * @param name Nombre   * @param city Teléfono
     */
    public void listHeadquarter (String account, String name, Integer city, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = null;


        // create path and map variables
        String path = "/parameter/headquarter".replaceAll("\\{format\\}","json");

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();

        queryParams.addAll(ApiInvoker.parameterToPairs("", "account", account));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "name", name));
        queryParams.addAll(ApiInvoker.parameterToPairs("", "city", city));


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
   * Lista las zonas de una cuenta
   * Lista las zona de una cuenta - filtra por diferentes parámetros
   * @param account Cuenta asociada   * @param name Nombre   * @param headquarter Sede
  */
  public void listZone (String account, String name, Integer headquarter, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
    Object postBody = null;


    // create path and map variables
    String path = "/parameter/zone";

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> headerParams = new HashMap<String, String>();
    // form params
    Map<String, String> formParams = new HashMap<String, String>();

    queryParams.addAll(ApiInvoker.parameterToPairs("", "account", account));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "name", name));
    queryParams.addAll(ApiInvoker.parameterToPairs("", "headquarter", headquarter));


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

}