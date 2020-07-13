package ebj.yujinkun.applauncher.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ebj.yujinkun.applauncher.R;
import ebj.yujinkun.applauncher.data.AppInfo;

public class AppInfoListAdapter extends RecyclerView.Adapter<AppInfoListAdapter.AppInfoListViewHolder> {

    private List<AppInfo> appInfoList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public AppInfoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_app, parent, false);
        return new AppInfoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppInfoListViewHolder holder, int position) {
        final AppInfo appInfo = appInfoList.get(position);
        holder.bind(appInfo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClicked(appInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    public void setAppInfoList(List<AppInfo> appInfoList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AppInfoDiffCallback(this.appInfoList, appInfoList));
        diffResult.dispatchUpdatesTo(this);
        this.appInfoList.clear();
        this.appInfoList.addAll(appInfoList);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(AppInfo appInfo);
    }

    class AppInfoListViewHolder extends RecyclerView.ViewHolder {

        TextView appNameTextView;
        TextView packageNameTextView;
        ImageView iconImageView;

        AppInfoListViewHolder(@NonNull View itemView) {
            super(itemView);
            appNameTextView = itemView.findViewById(R.id.app_name);
            packageNameTextView = itemView.findViewById(R.id.package_name);
            iconImageView = itemView.findViewById(R.id.app_image);
        }

        void bind(AppInfo appInfo) {
            appNameTextView.setText(appInfo.getAppName());
            packageNameTextView.setText(appInfo.getPackageName());
            iconImageView.setImageDrawable(appInfo.getIconDrawable());
        }
    }

    private class AppInfoDiffCallback extends DiffUtil.Callback {

        private final List<AppInfo> oldList;
        private final List<AppInfo> newList;

        public AppInfoDiffCallback(List<AppInfo> oldList, List<AppInfo> newList) {
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
            return oldList.get(oldItemPosition).getPackageName().equals(newList.get(newItemPosition).getPackageName());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}
