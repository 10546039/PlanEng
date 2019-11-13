package com.example.planeng;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;



import java.util.HashMap;
import java.util.Map;

public class getReview extends StringRequest {
    private static final String GETTASK_URL = "https://108401.000webhostapp.com/getReview.php";
    private Map<String, String> params;

    public getReview(String m_id, Response.Listener<String> listener) {
        super(Method.POST, GETTASK_URL, listener,null);
        params = new HashMap<>();
        params.put("m_id", m_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
