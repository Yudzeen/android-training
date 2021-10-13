package ebj.yujinkun.pagingsample;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private CheeseViewModel cheeseViewModel;
    private CheeseAdapter cheeseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cheeseAdapter = new CheeseAdapter();
        cheeseViewModel = new ViewModelProvider(this, new CheeseViewModelFactory(getApplication()))
                .get(CheeseViewModel.class);
        cheeseViewModel.getCheeseList().observe(this, cheeses -> cheeseAdapter.submitList(cheeses));

        RecyclerView recyclerView = findViewById(R.id.cheeseList);
        recyclerView.setAdapter(cheeseAdapter);
        initSwipeToDelete(recyclerView);

        initAddListeners();
    }

    private void initSwipeToDelete(RecyclerView recyclerView) {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder instanceof CheeseAdapter.CheeseViewHolder) {
                    Cheese cheese = ((CheeseAdapter.CheeseViewHolder) viewHolder).getCheese();
                    cheeseViewModel.remove(cheese);
                } else {
                    Log.w(TAG, "Unknown instance of ViewHolder");
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void addCheese() {
        EditText editText = findViewById(R.id.inputText);
        String name = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            cheeseViewModel.insert(name);
        } else {
            Toast.makeText(this, "Empty name", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "Empty name");
        }
    }

    private void initAddListeners() {
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            addCheese();
        });

        EditText editText = findViewById(R.id.inputText);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCheese();
                return true;
            }
            return false;
        });

        editText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCheese();
                return true;
            }
            return false;
        });
    }

}