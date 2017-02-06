package ly.android.com.liuyang.network;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public abstract class NetworkResponseHandler extends AsyncHttpResponseHandler {
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String result = new String(responseBody);
        onSuccess(result);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        onFail("网络异常");
    }

    public abstract void onFail(String message);

    public abstract void onSuccess(String data);

}
