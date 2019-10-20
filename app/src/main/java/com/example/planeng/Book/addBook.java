package com.example.planeng.Book;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class addBook extends StringRequest {
    private static final String SAVEBOOK_REQUEST_URL = "https://108401.000webhostapp.com/addBook.php";
    private Map<String, String> params;

    public addBook(String m_id, String bookname, String date,String chap, Response.Listener<String> listener) {
        super(Method.POST, SAVEBOOK_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("m_id", m_id);
        params.put("bookname", bookname);
        params.put("date", date);
        params.put("chap", chap);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}