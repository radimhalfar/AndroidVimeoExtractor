package radim.halfar.vimeo.extractor.Activity;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;

import radim.halfar.vimeo.extractor.Activity.Base.BaseActivity;
import radim.halfar.vimeo.extractor.R;
import radim.halfar.vimeo.extractor.Vimeo.Exceptions.VimeoException;
import radim.halfar.vimeo.extractor.Vimeo.VimeoCallback;
import radim.halfar.vimeo.extractor.Vimeo.VimeoExtractor;
import radim.halfar.vimeo.extractor.Vimeo.VimeoQuality.VimeoQuality;

public class VimeoVideoPlayer extends BaseActivity implements VimeoCallback {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vimeo_video_player);
        VimeoExtractor extr = new VimeoExtractor("58600663", getApplicationContext(), VimeoQuality.mobile, this);
        extr.downloadVideoFile();
        mapElements();
    }

    @Override
    public void mapElements() {
        super.mapElements();
        videoView = (VideoView)findViewById(R.id.videoView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vimeo_video_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void vimeoURLCallback(String callback) {

        Uri uri = Uri.parse(callback);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

    }

    @Override
    public void videoExceptionCallback(VimeoException exceptionCallback) {
        new AlertDialog.Builder(this).setTitle("Error").setMessage(exceptionCallback.getMessage()).setCancelable(true).show();
    }
}
