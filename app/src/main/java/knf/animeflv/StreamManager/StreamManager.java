package knf.animeflv.StreamManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;

import knf.animeflv.Utils.FileUtil;

/**
 * Created by Jordy on 04/03/2016.
 */
public class StreamManager {
    public static InternalStream internal(Context context) {
        return new InternalStream(context);
    }

    public static ExternalStream external(Context context) {
        return new ExternalStream(context);
    }

    public static MXStream mx(Context context) {
        return new MXStream(context);
    }

    public static void Play(Context context, String eid) {
        String[] data = eid.replace("E", "").split("_");
        String aid = data[0];
        String semi = eid.replace("E", "");
        File file = new File(Environment.getExternalStorageDirectory() + "/Animeflv/download/" + aid + "/" + semi + ".mp4");
        File sd = new File(FileUtil.getSDPath() + "/Animeflv/download/" + aid + "/" + semi + ".mp4");
        int type = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString("t_video", "0"));
        Log.d("Play type", String.valueOf(type));
        switch (type) {
            case 0:
                if (file.exists()) {
                    StreamManager.internal(context).Play(eid, file);
                } else {
                    if (sd.exists()) {
                        StreamManager.internal(context).Play(eid, sd);
                    }
                }
                break;
            case 1:
                if (file.exists()) {
                    StreamManager.external(context).Play(eid, file);
                } else {
                    if (sd.exists()) {
                        StreamManager.external(context).Play(eid, sd);
                    }
                }
                break;
        }
    }

    public static void Stream(Context context, String eid, String url) {
        int type = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString("t_streaming", "0"));
        Log.d("Streaming", PreferenceManager.getDefaultSharedPreferences(context).getString("t_streaming", "0"));
        switch (type) {
            case 0:
                StreamManager.internal(context).Stream(eid, url);
                break;
            case 1:
                StreamingExtbyURL(context, eid, url);
                break;
        }
    }

    private static void StreamingExtbyURL(Context context, String eid, String url) {
        Intent i = (new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setType("application/mp4"));
        PackageManager pm = context.getPackageManager();
        final ResolveInfo mInfo = pm.resolveActivity(i, 0);
        String id = mInfo.activityInfo.applicationInfo.processName;
        if (id.startsWith("com.mxtech.videoplayer")) {
            StreamManager.mx(context).Stream(eid, url);
        } else {
            StreamManager.external(context).Stream(eid, url);

        }
    }
}