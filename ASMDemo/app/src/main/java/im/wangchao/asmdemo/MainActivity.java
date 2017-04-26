package im.wangchao.asmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();

        findViewById(R.id.testBtn).setOnClickListener(new View.OnClickListener() {
            @CostTest(test = "haha-2") @Override public void onClick(View v) {
                Log.e("wcwcwc", "test btn click!");
            }
        });
    }

    @CostTest(test = "haha-1") public void test(){
        Log.e("wcwcwc", "test method");
    }
}
