package com.example.planeng;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class getTask extends StringRequest {
    private static final String GETTASK_URL = "https://108401.000webhostapp.com/getTask.php";
    private Map<String, String> params;

    public getTask(String dbDate, Response.Listener<String> listener) {
        super(Method.POST, GETTASK_URL, listener, null);
        params = new HashMap<>();
        params.put("date", dbDate);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}