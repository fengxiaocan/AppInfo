package com.evil.appinfo;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.evil.appinfo.rx.RxEmitter;
import com.evil.appinfo.rx.RxSubscriber;
import com.evil.appinfo.rx.RxThread;
import com.evil.appinfo.rx.SimpleRxAcceptor;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                                                                     DividerItemDecoration.VERTICAL
        );
        decoration.setDrawable(getResources().getDrawable(R.drawable.shape_decoration));
        mRecyclerView.addItemDecoration(decoration);
        mAdapter = new AppAdapter();
        mRecyclerView.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        RxThread.io().observeOnMain().open().subscriber(new RxSubscriber<AppInfo>() {
            @Override
            public void onSubscribe(RxEmitter<AppInfo> emitter) {
                //获取全部应用：
                PackageManager packageManager = getPackageManager();
                Intent mIntent = new Intent(Intent.ACTION_MAIN,null);
                mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> listAllApps = packageManager.queryIntentActivities(mIntent,0);
                if (listAllApps != null) {
                    for (ResolveInfo appInfo : listAllApps) {
                        AppInfo appInfo1 = new AppInfo();
                        String pkgName = appInfo.activityInfo.packageName;//获取包名
                        appInfo1.setPackname(pkgName);
                        Drawable iconResource = appInfo.loadIcon(packageManager);
                        appInfo1.setIcon(iconResource);
                        PackageInfo mPackageInfo = null;
                        try {
                            //判断是否系统应用：
                            mPackageInfo = packageManager.getPackageInfo(pkgName,0);
                            if ((mPackageInfo.applicationInfo.flags &
                                 ApplicationInfo.FLAG_SYSTEM) <= 0)
                            {
                                //第三方应用
                                appInfo1.setSysApp(false);
                            } else {
                                //系统应用
                                appInfo1.setSysApp(true);
                            }
                            String appName = packageManager.getApplicationLabel(mPackageInfo.applicationInfo)
                                                           .toString();
                            appInfo1.setAppName(appName);
                            String versionName = mPackageInfo.versionName;
                            appInfo1.setVersionName(versionName);
                            String md5 = SignaturesMsg.signatureMD5(mPackageInfo.signatures);
                            appInfo1.setMd5(md5);
                            String sha1 = SignaturesMsg.signatureSHA1(mPackageInfo.signatures);
                            appInfo1.setSha1(sha1);
                            String signatureSHA256 = SignaturesMsg.signatureSHA256(mPackageInfo.signatures);
                            appInfo1.setSha256(signatureSHA256);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        emitter.onNext(appInfo1);
                    }
                }
            }
        }).acceptOnSync().acceptor(new SimpleRxAcceptor<AppInfo>() {
            @Override
            public void onNext(AppInfo appInfo) {
                Log.e("noah",appInfo.getAppName());
                mAdapter.addAppInfo(appInfo);
            }

            @Override
            public void onError(Throwable t) {
                if (t != null) {
                    Log.e("noah","获取应用信息错误=" + t.getMessage());
                }
            }

            @Override
            public void onComplete() {
                Log.e("noah","onComplete");
                mAdapter.notifyDataSetChanged();
            }
        });


    }
}
