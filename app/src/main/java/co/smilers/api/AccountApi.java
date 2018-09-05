package co.smilers.api;

import android.app.ProgressDialog;
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
import co.smilers.model.Login;

public class AccountApi {

    private static final String TAG = AccountApi.class.getSimpleName();

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

    private Context context;


    /**
     * Login user
     * This can only be done by the logged in user.
     * @param body Created user object
     */
    public void loginUser (Login body, Context mContext, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) {
        Object postBody = body;
        context = mContext;

        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "Verificando usuario...", false);

        // verify the required parameter 'body' is set
        if (body == null) {
            VolleyError error = new VolleyError("Missing the required parameter 'body' when calling loginUser",
                    new ApiException(400, "Missing the required parameter 'body' when calling loginUser"));
        }


        // create path and map variables
        String path = "/account/login";

        // query params
        List<Pair> queryParams = new ArrayList<Pair>();
        // header params
        Map<String, String> headerParams = new HashMap<String, String>();
        // form params
        Map<String, String> formParams = new HashMap<String, String>();



        String[] contentTypes = {

        };
        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";


        String[] authNames = new String[] {  };

        try {
            apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType, authNames, context,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String localVarResponse) {
                            progressDialog.dismiss();
                            responseListener.onResponse(localVarResponse);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            errorListener.onErrorResponse(error);
                        }
                    });
        } catch (ApiException ex) {
            errorListener.onErrorResponse(new VolleyError(ex));
        }
    }

}