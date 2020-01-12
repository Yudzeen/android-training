package ebj.yujinkun.textspantranslate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean s1Clicked = false;
    private boolean s2Clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.text);
        final String s1 = getString(R.string.handsome);
        final String s2 = getString(R.string.strong);
        final String s3 = getString(R.string.i_am_and_sentence, s1, s2);

        final int i1 = s3.indexOf(s1);
        final int i2 = s3.indexOf(s2);

        final SpannableString ss = new SpannableString(capitalizeFirstLetter(s3));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                s1Clicked = true;
                String message = "onClick: " + s1;
                Log.d(TAG, message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                textView.setText(ss);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setTypeface(Typeface.create(ds.getTypeface(), Typeface.ITALIC));
                ds.setUnderlineText(false);
                ds.setColor(Color.BLUE);
            }
        };

        ss.setSpan(clickableSpan, i1, i1+s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                s2Clicked = true;
                String message = "onClick: " + s2;
                Log.d(TAG, message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                textView.setText(ss);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setTypeface(Typeface.create(ds.getTypeface(), Typeface.BOLD));
            }
        };
        ss.setSpan(clickableSpan2, i2, i2+s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "onClick: textview";
                Log.d(TAG, message);
                if (s1Clicked) {
                    Log.d(TAG, "onClick: s1Clicked");
                } else if (s2Clicked) {
                    Log.d(TAG, "onClick: s2Clicked");
                } else {
                    Log.d(TAG, "onClick: non-clickable span");
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                resetStringClicked();
            }
        });
    }

    private String capitalizeFirstLetter(String s1) {
        return s1.substring(0,1).toUpperCase() + s1.substring(1);
    }

    private void resetStringClicked() {
        s1Clicked = false;
        s2Clicked = false;
    }
}
