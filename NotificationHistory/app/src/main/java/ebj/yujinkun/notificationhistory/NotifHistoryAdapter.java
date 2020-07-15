package ebj.yujinkun.notificationhistory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotifHistoryAdapter extends RecyclerView.Adapter<NotifHistoryAdapter.NotifHistoryViewHolder> {

    private static final String TAG = NotifHistoryAdapter.class.getSimpleName();

    private List<NotifHistory> notifHistoryList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public NotifHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notif, parent, false);
        return new NotifHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifHistoryViewHolder holder, int position) {
        NotifHistory notifHistory = notifHistoryList.get(position);
        holder.bind(notifHistory);
        holder.setOnClickListener(onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return notifHistoryList.size();
    }

    public void setNotifHistoryList(List<NotifHistory> notifHistoryList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new NotifHistoryDiffCallback(this.notifHistoryList, notifHistoryList));
        diffResult.dispatchUpdatesTo(this);
        this.notifHistoryList.clear();
        this.notifHistoryList.addAll(notifHistoryList);
    }

    public void addNotifHistory(NotifHistory notifHistory) {
        notifHistoryList.add(0, notifHistory);
        notifyItemInserted(0);
    }

    public NotifHistory get(int position) {
        return notifHistoryList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class NotifHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView notifInfo;
        private NotifHistory notifHistory;

        NotifHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            notifInfo = itemView.findViewById(R.id.notif_info);
        }

        void bind(NotifHistory notifHistory) {
            this.notifHistory = notifHistory;
            notifInfo.setText(notifHistory.toString());
        }

        void setOnClickListener(final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Item clicked: " + notifHistory.toString());
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(notifHistory);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NotifHistory notifHistory);
    }

    private class NotifHistoryDiffCallback extends DiffUtil.Callback {

        private final List<NotifHistory> oldList;
        private final List<NotifHistory> newList;

        public NotifHistoryDiffCallback(List<NotifHistory> oldList, List<NotifHistory> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getTitle().equals(newList.get(newItemPosition).getTitle());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}

