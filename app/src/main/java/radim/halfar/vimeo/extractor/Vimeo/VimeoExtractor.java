package radim.halfar.vimeo.extractor.Vimeo;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import radim.halfar.vimeo.extractor.Vimeo.Exceptions.VimeoException;
import radim.halfar.vimeo.extractor.Vimeo.Exceptions.VimeoExceptionEnumType;
import radim.halfar.vimeo.extractor.Vimeo.VimeoQuality.VimeoQuality;

/**
 * Created by radimhalfar on 03.09.15.
 */
public class VimeoExtractor {

    private static String VIMEO_URL_CONFIG = "https://player.vimeo.com/video/--/config";

    private String vimeoURL;
    private VimeoQuality vimeoQuality;
    private RequestQueue requestQueque;
    private VimeoCallback callback;

    /*
    Public constructor of vimeo Extractor
     */
    public VimeoExtractor(String vimeoURLID, Context context, VimeoQuality vimeoQuality, VimeoCallback callback) {
        this.vimeoURL = VIMEO_URL_CONFIG.replace("--", vimeoURLID);
        requestQueque = Volley.newRequestQueue(context);
        this.vimeoQuality = vimeoQuality;
        this.callback = callback;
    }

    public void downloadVideoFile() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, vimeoURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject filesInfo = null;
                try {
                     filesInfo = response.getJSONObject("request").getJSONObject("files").getJSONObject("h264");
                } catch (JSONException e) {
                    callback.videoExceptionCallback(new VimeoException("Video not found", VimeoExceptionEnumType.VIMEO_FILE_NOT_FOUND));
                }
                JSONObject videoInfo = null;
                try {
                    videoInfo = filesInfo.getJSONObject(vimeoQuality.toString());
                } catch (Exception e) {
                    callback.videoExceptionCallback(new VimeoException("Video not found", VimeoExceptionEnumType.VIMEO_FILE_NOT_FOUND));
                }
                String url = null;
                try {
                    url = videoInfo.getString("url");
                } catch (Exception e) {
                    callback.videoExceptionCallback(new VimeoException("Video not found", VimeoExceptionEnumType.VIMEO_FILE_NOT_FOUND));
                }

                callback.vimeoURLCallback(url);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.videoExceptionCallback(new VimeoException("Connection error", VimeoExceptionEnumType.VIMEO_CONNECTION_ERROR));
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        requestQueque.add(jsObjRequest);
    }
}
