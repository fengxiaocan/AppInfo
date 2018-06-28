package com.evil.appinfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.evil.ioslibs.DialogCancelListener;
import com.evil.ioslibs.IosDialog;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<RecyclerViewAdapterHolder> {
    private List<AppInfo> mAppInfos;

    public void setAppInfos(List<AppInfo> appInfos) {
        mAppInfos = appInfos;
    }

    public synchronized void addAppInfo(AppInfo app) {
        if (mAppInfos == null) {
            mAppInfos = new ArrayList<>();
        }
        mAppInfos.add(app);
    }

    @NonNull
    @Override
    public RecyclerViewAdapterHolder onCreateViewHolder(
            @NonNull ViewGroup parent,int viewType
    )
    {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.holder_app,parent,false);
        return new RecyclerViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterHolder holder,final int position) {
        AppInfo appInfo = mAppInfos.get(position);
        holder.mIvIcon.setImageDrawable(appInfo.getIcon());
        holder.mTvAppName.setText(appInfo.getAppName());
        holder.mTvAppSys.setVisibility(appInfo.isSysApp() ? View.VISIBLE : View.GONE);
        holder.mTvPgkName.setText(appInfo.getPackname());
        holder.mTvVersion.setText(appInfo.getVersionName());
        holder.mTvSha1.setText("SHA1:" + appInfo.getSha1());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                IosDialog dialog = new IosDialog(v.getContext());
                dialog.setTitle("应用信息");
                AppInfo appInfo = mAppInfos.get(position);
                final String msg = appInfo.toString();
                dialog.setMessage(msg);
                dialog.setLeftButton("复制",new DialogCancelListener() {
                    @Override
                    public void onClick(DialogInterface dialog) {
                        ClipboardManager clipboard = (ClipboardManager)v.getContext()
                                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboard.setPrimaryClip(ClipData.newPlainText("text",msg));
                        Toast.makeText(v.getContext(),"已复制到粘贴板",Toast.LENGTH_SHORT).show();
                        super.onClick(dialog);
                    }
                });
                dialog.setRightButton("取消",new DialogCancelListener());
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mAppInfos != null) {
            return mAppInfos.size();
        }
        return 0;
    }
}
