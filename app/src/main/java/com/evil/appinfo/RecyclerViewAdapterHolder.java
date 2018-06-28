package com.evil.appinfo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapterHolder extends RecyclerView.ViewHolder {
    public ImageView mIvIcon;
    public CardView mCdvIcon;
    public TextView mTvAppName;
    public TextView mTvPgkName;
    public TextView mTvVersion;
    public TextView mTvSha1;
    public TextView mTvAppSys;

    public RecyclerViewAdapterHolder(View itemView) {
        super(itemView);
        this.mIvIcon = (ImageView)itemView.findViewById(R.id.iv_icon);
        this.mCdvIcon = (CardView)itemView.findViewById(R.id.cdv_icon);
        this.mTvAppName = (TextView)itemView.findViewById(R.id.tv_app_name);
        this.mTvPgkName = (TextView)itemView.findViewById(R.id.tv_pgk_name);
        this.mTvVersion = (TextView)itemView.findViewById(R.id.tv_version);
        this.mTvSha1 = (TextView)itemView.findViewById(R.id.tv_sha1);
        this.mTvAppSys = (TextView)itemView.findViewById(R.id.tv_app_sys);
    }
}
