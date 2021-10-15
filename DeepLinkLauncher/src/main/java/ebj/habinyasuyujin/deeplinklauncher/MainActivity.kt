package ebj.habinyasuyujin.deeplinklauncher

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.editText)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val deepLink = editText.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLink))
            startActivity(intent)
        }
    }
}