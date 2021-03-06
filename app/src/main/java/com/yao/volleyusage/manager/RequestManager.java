
package com.yao.volleyusage.manager;

import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yao.volleyusage.VolleyUsageApplication;

/**
 * Manager for the queue
 */
public class RequestManager {
    public final String TAG = "RequestManager";

    /**
     * 全局请求队列
     */
    private static RequestQueue mRequestQueue;

    private static RequestManager mInstance;

    public static RequestManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestManager();
        }

        return mInstance;
    }

    /**
     * @return instance of the queue
     */
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(VolleyUsageApplication.getApplication());
        }
        return mRequestQueue;
    }

    /**
     * 添加指定的请求到全局队列,可以指定标签，否则为默认
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {

        req.setRetryPolicy(new DefaultRetryPolicy(30000,0,1));

        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        req.setShouldCache(false);
        getRequestQueue().add(req);
    }

    /**
     * 添加指定的请求到全局队列，使用默认标签
     */
    public <T> void addToRequestQueue(Request<T> req) {

        addToRequestQueue(req, TAG);
    }

    /**
     * 通过指定标签清除未返回的请求
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
