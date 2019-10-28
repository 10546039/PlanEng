package com.example.planeng.Book;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class addBooklist extends StringRequest {
    private static final String ADDBOOKLIST_URL = "https://108401.000webhostapp.com/addBookList.php";
    private Map<String, String> params;

    public addBooklist(String m_id, String bookname, String startDate,String endDate,String totalChap,String chapName, Response.Listener<String> listener) {
        super(Method.POST, ADDBOOKLIST_URL, listener, null);
        params = new HashMap<>();
        params.put("m_id", m_id);
        params.put("bookname", bookname);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("totalChap", totalChap);
        params.put("chapName", chapName);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}