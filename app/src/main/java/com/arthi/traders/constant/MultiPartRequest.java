package com.arthi.traders.constant;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;


public class MultiPartRequest extends Request<String> {

    private Response.Listener<String> mListener;
    private HttpEntity mHttpEntity;

    public MultiPartRequest(Response.ErrorListener errorListener, Response.Listener listener, ArrayList<File> file, int numberOfFiles, String amount, String transactionid, String transactionmode, String user_id) {
        super(Method.POST, Helper.payment_url, errorListener);
        mListener = listener;
        mHttpEntity = buildMultipartEntity(file, numberOfFiles,amount,transactionid,transactionmode,user_id);
    }

    private HttpEntity buildMultipartEntity(ArrayList<File> file, int numberOffiles, String amount, String transactionid, String transactionmode,   String user_id) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        try {
            Log.e("amount ",amount.toString()+"..");
            Log.e("transactionid ",transactionid.toString()+"..");
            Log.e("transactionmode ",transactionmode.toString()+"..");
            Log.e("user_id ",user_id.toString()+"..");
            Log.e("numberOffiles ",numberOffiles+"..");
            Log.e("file ",file.size()+"..");

            for(int i=0; i < file.size();i++){
                FileBody fileBody = new FileBody(file.get(i));
                builder.addPart(Template.Query.KEY_IMAGE.concat(String.valueOf(i)), fileBody);
            }
            builder.addTextBody(Template.Query.KEY_DIRECTORY, Template.Query.VALUE_DIRECTORY);
            builder.addTextBody("numberOfFiles", String.valueOf(numberOffiles));

            builder.addTextBody("amount", amount);
            builder.addTextBody("transactionid", transactionid);
            builder.addTextBody("mode", transactionmode);
            builder.addTextBody("userid", user_id);
            builder.addTextBody("emp","");
            Log.e("builder ",builder.toString()+"..");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            mHttpEntity.writeTo(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            VolleyLog.e("" + e);
            return null;
        } catch (OutOfMemoryError e){
            VolleyLog.e("" + e);
            return null;
        }

    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new String(response.data, "UTF-8"),
                    getCacheEntry());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.success(new String(response.data),
                    getCacheEntry());
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }


}
