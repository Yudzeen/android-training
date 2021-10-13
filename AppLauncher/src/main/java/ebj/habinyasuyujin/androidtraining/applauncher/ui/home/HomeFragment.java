package ebj.habinyasuyujin.androidtraining.applauncher.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ebj.habinyasuyujin.androidtraining.applauncher.R;
import ebj.habinyasuyujin.androidtraining.applauncher.data.AppInfo;
import ebj.habinyasuyujin.androidtraining.applauncher.util.AppUtil;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;
    private AppInfoListAdapter appInfoListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.app_list_view);
        appInfoListAdapter = new AppInfoListAdapter();
        recyclerView.setAdapter(appInfoListAdapter);
        appInfoListAdapter.setOnItemClickListener(new AppInfoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(AppInfo appInfo) {
                startActivity(appInfo.getLaunchIntent());
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        homeViewModel.getAppInfoList().observe(getViewLifecycleOwner(), new Observer<List<AppInfo>>() {
            @Override
            public void onChanged(List<AppInfo> appInfoList) {
                Log.d(TAG, "List: " + appInfoList.toString());
                appInfoListAdapter.setAppInfoList(AppUtil.sortByAppNameIgnoreCase(appInfoList));
            }
        });
    }
}