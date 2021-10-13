package ebj.yujinkun.pagingsample;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CheeseAdapter extends PagedListAdapter<Cheese, CheeseAdapter.CheeseViewHolder> {

    private static final String TAG = CheeseAdapter.class.getSimpleName();

    public CheeseAdapter() {
        super(new CheeseDiffItemCallback());
    }

    @NonNull
    @Override
    public CheeseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cheese, parent, false);
        return new CheeseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheeseViewHolder holder, int position) {
        Cheese cheese = getItem(position);
        if (cheese != null) {
            holder.bindTo(cheese);
        } else {
            Log.w(TAG, "Cheese is null at position " + position);
        }
    }

    public static class CheeseViewHolder extends RecyclerView.ViewHolder {

        private Cheese cheese;
        private TextView nameView;

        public CheeseViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
        }

        void bindTo(Cheese cheese) {
            this.cheese = cheese;
            nameView.setText(cheese.name);
        }

        public Cheese getCheese() {
            return cheese;
        }
    }

    private static class CheeseDiffItemCallback extends DiffUtil.ItemCallback<Cheese> {

        @Override
        public boolean areItemsTheSame(@NonNull Cheese oldItem, @NonNull Cheese newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Cheese oldItem, @NonNull Cheese newItem) {
            return oldItem.equals(newItem);
        }
    }
}
