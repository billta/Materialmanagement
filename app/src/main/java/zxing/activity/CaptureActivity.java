/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expr0ess or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zxing.activity;

        import android.Manifest;
        import android.content.ClipData;
        import android.content.ClipboardManager;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Rect;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.annotation.NonNull;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.view.animation.Animation;
        import android.view.animation.TranslateAnimation;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.gson.Gson;
        import com.google.zxing.Result;
        import com.ld.materialmanagement.R;
        import com.ld.materialmanagement.activity.BaseActivity;
        import com.ld.materialmanagement.activity.MMGoodsDetailActivity;
        import com.ld.materialmanagement.adapter.ScanRecyclerViewAdapter;
        import com.ld.materialmanagement.application.AppConst;
        import com.ld.materialmanagement.bean.ResultBean;
        import com.ld.materialmanagement.bean.ScanBean;
        import com.ld.materialmanagement.bean.User;
        import com.ld.materialmanagement.model.APi;
        import com.ld.materialmanagement.model.LoginModel;
        import com.ld.materialmanagement.model.Network;
        import com.ld.materialmanagement.utils.AesHelper;
        import com.ld.materialmanagement.utils.L;
        import com.ld.materialmanagement.utils.StringUtil;
        import com.ld.materialmanagement.utils.UiUtils;
        import com.orhanobut.logger.Logger;

        import java.io.IOException;
        import java.lang.reflect.Field;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import butterknife.BindView;
        import butterknife.OnClick;
        import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
        import pub.devrel.easypermissions.AfterPermissionGranted;
        import pub.devrel.easypermissions.EasyPermissions;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import zxing.camera.CameraManager;
        import zxing.decode.DecodeThread;
        import zxing.utils.BeepManager;
        import zxing.utils.CaptureActivityHandler;
        import zxing.utils.InactivityTimer;


/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends BaseActivity implements
        SurfaceHolder.Callback, EasyPermissions.PermissionCallbacks, ScanRecyclerViewAdapter.SlidingItemClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private static final int REQUEST_CODE_CHOOSE_QR_CODE_FROM_GALLERY = 0x666;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    @BindView(R.id.capture_container)
    public RelativeLayout scanContainer;
    @BindView(R.id.capture_crop_view)
    public RelativeLayout scanCropView;
    @BindView(R.id.capture_flash)
    public ImageView mFlash;
    @BindView(R.id.rl_root_view)
    RelativeLayout mRootView;

    private Rect mCropRect = null;

    public static final int ALL = 0;
    public static final int BACKUP = 1;
    public static final int IN_LIB = 2;
    public static final int OUT_LIB = 3;
    public static final int LOAN_IN = 4;
    public static final int LOAN_OUT = 5;



    public static final String FLAG_IN_LIB = "入库";
    public static final String FLAG_OUT_LIB = "出库";
    public static final String FLAG_LOAN_IN = "归还";
    public static final String FLAG_LOAN_OUT = "借出";



    @BindView(R.id.toggle_flashlight)
    ImageView flashLight;
    @BindView(R.id.choose_qrcde_from_gallery)
    ImageView mQrcodeFromGallery;
    @BindView(R.id.rv_scan_result)
    RecyclerView mScanResult;

    @BindView(R.id.tv_submit)
    TextView mSubmit;
    @BindView(R.id.scan_control)
    LinearLayout scanControl;


    ArrayList<ScanBean> mResult;
    private ScanRecyclerViewAdapter adapter;
    Gson gson;
    private int scan;
    private APi api;
    SharedPreferences sp;
    String checked;


    @Override
    protected boolean initBundle(Bundle extras) {
        //得到传回来的值
        scan = extras.getInt(AppConst.FLAG_SCAN);
        sp=getSharedPreferences(AppConst.FLAG_CHECKED,MODE_PRIVATE);
        checked=sp.getString(AppConst.FLAG_CHECKED,null);
        L.e("6666666666+="+checked);//null是给的默认值，没有获取到就是null

        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_qr_scan;
    }

    @Override
    protected void initView() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void initWidget() {
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        // Install the callback and wait for surfaceCreated() to init the
        // camera.
        scanPreview.getHolder().addCallback(this);

        cameraTask();


        gson = new Gson();
        if (scan == ALL || scan == BACKUP) {
            //隐藏
            scanControl.setVisibility(View.GONE);
            mFlash.setVisibility(View.VISIBLE);
            return;
        }

        api = Network.getInstance().getApi(CaptureActivity.this);

        // 扫描结果的
        mResult = new ArrayList<>();
        mScanResult.setItemAnimator(new DefaultItemAnimator());
        mScanResult.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ScanRecyclerViewAdapter(CaptureActivity.this);

        mScanResult.setAdapter(adapter);
        adapter.updateData(mResult);






   /*     if (checked.equals("归还")){

            APi api = Network.getInstance().getApi(CaptureActivity.this);
            Call<LoanBeans<Loan>> userBack = api.getUserBack("821");
            userBack.enqueue(new Callback<LoanBeans<Loan>>() {
                @Override
                public void onResponse(Call<LoanBeans<Loan>> call, Response<LoanBeans<Loan>> response) {
                    LoanBeans<Loan> body = response.body();
                    L.e("body0000000000" + body.data);
                    myAdapter.updateData(body.data);

                }

                @Override
                public void onFailure(Call<LoanBeans<Loan>> call, Throwable t) {
                    String message = t.getMessage();
                    L.e("message"+message);
                }
            });
        }*/



    }

    @Override
    protected void onResume() {
        if (scanPreview != null) {
            handler = null;
            if (isHasSurface) {
                // The activity was paused but not stopped, so the surface still
                // exists. Therefore
                // surfaceCreated() won't be called, so init the camera here.
                initCamera(scanPreview.getHolder());
            }
        }
        if (inactivityTimer != null) {
            inactivityTimer.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        if (inactivityTimer != null) {
            inactivityTimer.onPause();
        }
        if (beepManager != null) {
            beepManager.close();
        }
        if (cameraManager != null) {
            cameraManager.closeDriver();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (inactivityTimer != null) {
            inactivityTimer.shutdown();
        }
        if (scanPreview != null) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onDestroy();
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isHasSurface = false;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        isHasSurface = true;
        initCamera(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Doing
    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(final Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();

        beepManager.playBeepSoundAndVibrate();

        // 通过这种方式可以获取到扫描的图片
        //	bundle.putInt("width", mCropRect.width());
        //	bundle.putInt("height", mCropRect.height());
        //	bundle.putString("result", rawResult.getText());
        //
        //	startActivity(new Intent(CaptureActivity.this, ResultActivity.class)
        //		.putExtras(bundle));

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                handleText(rawResult.getText());
            }
        }, 200);
    }



    private void handleText(String result) {
        //判断文本
        if (checkResult(result)) {
            //解析JSON
            ScanBean scanBean = gson.fromJson(result, ScanBean.class);


            if ((scan == IN_LIB) && scanBean.dataType.equals("备件")) {
                scanBean.selectCount = 1;
                if (contains(scanBean)) {
                    adapter.updateData(scanBean.goodsId);
                } else {
                    mResult.add(scanBean);
                }
                adapter.notifyDataSetChanged();
                //继续扫描
                restartPreviewAfterDelay(1000);
            }else if((scan == OUT_LIB) && scanBean.dataType.equals("工具")){
                scanBean.selectCount = 1;
                if (contains(scanBean)) {
                    adapter.updateData(scanBean.goodsId);
                } else {
                    mResult.add(scanBean);
                }
                adapter.notifyDataSetChanged();
                //继续扫描
                restartPreviewAfterDelay(1000);
            }else if (scan == BACKUP) {
                //跳转到详情页
                Intent intent = new Intent(mContext, MMGoodsDetailActivity.class);
                intent.putExtra("id", scanBean.goodsId);

                mContext.startActivity(intent);
            } else if ((scan == LOAN_IN || scan == LOAN_OUT) && scanBean.dataType.equals("工具")) {

                scanBean.selectCount = 1;
                if (contains(scanBean)) {
                    adapter.updateData(scanBean.goodsId);
                } else {
                    mResult.add(scanBean);
                }
                adapter.notifyDataSetChanged();
                //继续扫描
                restartPreviewAfterDelay(1000);

            } else {
                showDialog(scanBean.showInDialog());
            }


        } else {
            showDialog(result);
        }

    }

    public void showDialog(final String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CaptureActivity.this);
        final AlertDialog alertDialog = builder
                .setTitle("扫描结果")
                .setMessage(msg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //继续扫描
                        restartPreviewAfterDelay(1000);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("复制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 复制
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText(null, msg));

                        //继续扫描
                        restartPreviewAfterDelay(1000);
                        showSnackBar("复制成功 !");
                    }
                })
                .create();

        alertDialog.show();
    }


    public boolean checkResult(String result) {
        String reg = "\\{.*\\}";
        String reg2 = "^\\{.*";

        // 判断是否符合基本的json格式
        return result.matches(reg) && result.contains("ld_datatype");
    }

    public boolean contains(ScanBean bean) {
        if (mResult != null) {
            for (ScanBean sean : mResult) {
                if (sean.goodsId == bean.goodsId) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (cameraManager == null)
            return;

        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager,
                        DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException | RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        //Toast.makeText(this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "初始化相机失败请检查权限", Toast.LENGTH_LONG).show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @OnClick({R.id.toggle_flashlight,
            R.id.choose_qrcde_from_gallery,
            R.id.capture_flash,
            R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggle_flashlight:
                // 开启或关闭闪光灯
                light();
                break;
            case R.id.capture_flash:
                light();
                break;
            case R.id.choose_qrcde_from_gallery:
                //从相册选中
                startActivityForResult(BGAPhotoPickerActivity.newIntent(this, null, 1, null, false), REQUEST_CODE_CHOOSE_QR_CODE_FROM_GALLERY);
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    private void tryLogin() {
        showWaitingDialog("正在提交...");

        //获取本地保存的账号密码 并解密 如果获取不到就跳转登陆界面
        SharedPreferences userSp = getSharedPreferences(AppConst.User.FILE, Context.MODE_PRIVATE);

        String id = AesHelper.decrypt(userSp.getString(AppConst.User.KEY_USER_ID, ""), StringUtil.getString());
        String pswd = AesHelper.decrypt(userSp.getString(AppConst.User.KEY_USER_PW, ""), StringUtil.getString());

        LoginModel.login(mContext, id, pswd, new LoginModel.loginState() {
            @Override
            public void loginSuccess(User user) {
                //登录成功
                switch (scan) {
                    case ALL:
                        //说明是首页
                        //showSelectDialog();
                        break;
                    case BACKUP:
                        //备件
                        break;
                    case IN_LIB:
                        //入库
                        if (checked.equals("入库")){
                            submitInData(checked);
                        }else if (checked.equals("出库"))
                            submitOutData(checked);
                        break;
                    case OUT_LIB:
                        //出库
                        if (checked.equals("归还")){
                            submitInData(checked);
                        }else if (checked.equals("借出")){
                            submitOutData(checked);
                        }

                        break;
                    case LOAN_IN:

                        //归还工具
                        submitInData(FLAG_LOAN_IN);
                        break;
                    case LOAN_OUT:
                        //借出工具
                        submitOutData(FLAG_LOAN_OUT);

                        break;
                }
            }

            @Override
            public void loginFailure(String msg) {
                //登录失败
                hideWaitingDialog();
                UiUtils.showLoginActivity(mContext, null);
                finish();
            }

            @Override
            public void connectFail() {
                hideWaitingDialog();
                //当前网络不是内网
                showMaterialDialog("", "网络连接失败,无法提交 ", "确定", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideMaterialDialog();
                        restartPreviewAfterDelay(1000);
                    }
                }, null);
            }
        });

    }


    // 提交入库单和归还
    public void submitInData(final String flag) {

        SharedPreferences spHeader = getSharedPreferences(AppConst.Header.FILE, Context.MODE_PRIVATE);
        String cookie = spHeader.getString(AppConst.Header.SET_COOKIE, "");

        Call<ResultBean> inData = api.submitInData(cookie, getData(), flag);

        inData.enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                hideWaitingDialog();

                ResultBean body = response.body();
                if (body.done) {
                    showSnackBar(flag + "单创建成功");
                    clearList();
                } else {
                    showSnackBar(body.msg + "");
                }
                Logger.i(response.toString());
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                hideWaitingDialog();
                showSnackBar("网络连接失败");
            }
        });

    }

    // 提交出库单 和借出
    public void submitOutData(final String flagOutLib) {
        //尝试登录
        SharedPreferences spHeader = getSharedPreferences(AppConst.Header.FILE, Context.MODE_PRIVATE);
        String cookie = spHeader.getString(AppConst.Header.SET_COOKIE, "");

        // 提交是否成功   如果成功  清空列表
        Call<ResultBean> submitOutData = api.submitOutData(cookie, getOutData(), flagOutLib);
        submitOutData.enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                hideWaitingDialog();
                ResultBean body = response.body();
                if (body.done) {
                    showSnackBar(flagOutLib + "单创建成功");
                    //清空列表
                    clearList();
                } else {
                    showSnackBar(body.msg + "");
                }
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                hideWaitingDialog();
                showSnackBar("网络连接失败");
            }
        });
    }


    public void clearList() {
        adapter.clearList();
    }

    private void submit() {
        if (getData().size() > 0) {
            tryLogin();
        } else {
            showSnackBar("先扫码再试试...");
        }
    }

    private boolean mIsLight;

    private void light() {
        try {
            if (mIsLight) {
                // 关闪光灯
                cameraManager.offLight();
                mFlash.setBackgroundResource(R.mipmap.flash_default);
                mIsLight = false;
            } else {
                // 开闪光灯
                cameraManager.openLight();
                mFlash.setBackgroundResource(R.mipmap.flash_open);
                mIsLight = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initCamera() {

        ImageView scanLine = (ImageView) findViewById(R.id.capture_scan_line);

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);

        cameraManager = new CameraManager(getApplication());
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms != null && perms.size() == 2) {
            initCamera();
        } else {
            displayFrameworkBugMessageAndExit();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        displayFrameworkBugMessageAndExit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private static final int CAMERA_PERM = 1;

    @AfterPermissionGranted(CAMERA_PERM)
    private void cameraTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.VIBRATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            initCamera();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, "请求获取相机权限",
                    CAMERA_PERM, perms);
        }
    }


    public Map<String, String> getData() {
        //从adapter中得到数据
        List<ScanBean> data = adapter.getData();
        Map<String, String> fields = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            ScanBean scanBean = data.get(i);
            //时间
            fields.put("InDate", "\\/Date(" + System.currentTimeMillis() + ")\\/");
            //合同编号
            fields.put("ContractCode", "9527");
            //备件的id
            fields.put("ItemId" + i, scanBean.goodsId + "");
            //入库的个数
            fields.put("StorageCount" + i, scanBean.selectCount + "");
        }

        return fields;
    }

    public Map<String, String> getOutData() {
        //从adapter中得到数据
        List<ScanBean> data = adapter.getData();
        Map<String, String> fields = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            ScanBean scanBean = data.get(i);
            //时间
            fields.put("InDate", "\\/Date(" + System.currentTimeMillis() + ")\\/");
            //合同编号
            fields.put("ContractCode", "9527");
            //备件的id
            fields.put("ItemId" + i, scanBean.goodsId + "");
            //申请的个数
            fields.put("ADCount" + i, scanBean.selectCount + "");
            //申请的个数
            fields.put("ODCount" + i, scanBean.selectCount + "");
        }

        return fields;
    }


    @Override
    public void onItemClick(View view, int position) {
        //点击扫描条目的时候
        showSnackBar("左滑删除");
    }

    @Override
    public void onDeleteBtnClick(View view, int position) {
        showSnackBar("已删除");
        adapter.removeData(position);
    }

    public void showSnackBar(String msg) {
        Snackbar.make(mRootView, msg, Snackbar.LENGTH_LONG).show();
    }
}
