package ebj.habinyasuyujin.modulereflection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> startMyLibraryActivity());
    }

    private void startMyLibraryActivity() {
        Intent intent = new Intent(this, ActivityFactory.getMyLibraryActivityClass());
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setClassName(ActivityFactory.MY_LIBRARY_PACKAGE_NAME, ActivityFactory.MY_LIBRARY_ACTIVITY_CLASS_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}