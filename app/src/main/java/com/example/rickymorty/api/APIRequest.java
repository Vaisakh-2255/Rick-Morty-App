package com.example.rickymorty.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rickymorty.utils.Constants;
import com.example.rickymorty.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIRequest {

    private String jsonURL;
    private final Context context;
    private final RequestQueue requestQueue;
    private final Map<String, String> header;
    private JSONObject parameters;
    private int method = Request.Method.GET;
    private String path = "";

    public APIRequest(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        this.header = new HashMap<>();
        this.parameters = new JSONObject();
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCustomUrl(String url) {
        this.jsonURL = url;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void addParam(String key, String value) {
        try {
            parameters.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void executeRequest(final VolleyCallback callback) {
        if (Utils.isNetworkAvailable(context)) {
            // Construct the full URL
            String url = Constants.BASE_URL + path;
            Utils.log("APIRequest", "--------------------------URL----------------------------");
            Utils.log("APIRequest", url);

            try {
                Utils.log("APIRequest", "--------------------------Params----------------------------");
                Utils.log("APIRequest", parameters.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add headers
            header.put("Content-Type", "application/json");
            if (Utils.getSharedPreference().contains("token")) {
                header.put("Authorization", "Bearer " + Utils.getSharedPreference().getString("token", ""));
            }

            // Clear cache
            requestQueue.getCache().clear();

            // Create and add request
            JsonObjectRequest jsonRequest = new JsonObjectRequest(method, url, parameters,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Utils.log("APIRequest", "--------------------------Response----------------------------");
                                Utils.log("APIRequest", response.toString());
                                callback.getSuccessResponse(response, "Success");
                            } catch (Exception e) {
                                callback.getErrorResponse(e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Utils.log("APIRequest", "--------------------------Response Error----------------------------");
                                Utils.log("APIRequest", error.getMessage());
                                callback.getErrorResponse(error.getMessage());
                            } catch (Exception e) {
                                Utils.log("APIRequest", "Error " + e.getMessage());
                                callback.getErrorResponse("Something Went Wrong!");
                            }
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Utils.log("APIRequest", "--------------------------Header----------------------------");
                    Utils.log("APIRequest", header.toString());
                    return header;
                }
            };

            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonRequest);

        } else {
            callback.getErrorResponse("Network connectivity issue.");
        }
    }

    public interface VolleyCallback {
        void getSuccessResponse(JSONObject response, String message);
        void getErrorResponse(String error);
    }
}
