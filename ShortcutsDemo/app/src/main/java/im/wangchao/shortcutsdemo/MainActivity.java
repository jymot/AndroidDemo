package im.wangchao.shortcutsdemo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @TargetApi(Build.VERSION_CODES.N_MR1) private void set(){
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id1")
                .setShortLabel("Web site")
                .setLongLabel("Open the web site")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_launcher_round))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.mysite.example.com/")))
                .build();

        shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));

    }
}
