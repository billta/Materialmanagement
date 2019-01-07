package com.ld.materialmanagement.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ld.materialmanagement.R;
import com.ld.materialmanagement.adapter.ScanRecyclerViewAdapter;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.ResultBean;
import com.ld.materialmanagement.bean.ScanBean;
import com.ld.materialmanagement.bean.User;
import com.ld.materialmanagement.model.APi;
import com.ld.materialmanagement.model.LoginModel;
import com.ld.materialmanagement.model.Network;
import com.ld.materialmanagement.utils.AesHelper;
import com.ld.materialmanagement.utils.StringUtil;
import com.ld.materialmanagement.utils.UiUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ld.materialmanagement.R.id.flashlight;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate, ScanRecyclerViewAdapter.SlidingItemClickListener {

    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 0x666;
    public static final int ALL = 0;
    public static final int BACKUP = 1;
    public static final int IN_LIB = 2;
    public static final int OUT_LIB = 3;

    @BindView(R.id.zxingview)
    ZXingView mQRCodeView;
    @BindView(R.id.toggle_flashlight)
    ImageView mToggleFlashlight;
    @BindView(R.id.flashlight)
    ImageView flashLight;
    @BindView(R.id.choose_qrcde_from_gallery)
    ImageView mQrcodeFromGallery;
    @BindView(R.id.rv_scan_result)
    RecyclerView mScanResult;
    @BindView(R.id.rl_scan_layout)
    RelativeLayout mRootView;
    @BindView(R.id.tv_submit)
    TextView mSubmit;
    @BindView(R.id.scan_control)
    LinearLayout scanControl;

    private boolean toggleFlash = true;
    ArrayList<ScanBean> mResult;
    private ScanRecyclerViewAdapter adapter;
    Gson gson;
    private int scan;
    private APi api;

    @Override
    protected int getContentView() {
        return R.layout.activity_scan;
    }

    @Override
    protected boolean initBundle(Bundle extras) {
        //得到传回来的值
        scan = extras.getInt("scan");
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        //mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();

    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //初始化控件
        mQRCodeView.setDelegate(this);
        gson = new Gson();
        if (scan == ALL) {

            //隐藏
            scanControl.setVisibility(View.GONE);
            flashLight.setVisibility(View.VISIBLE);

            return;
        }


        api = Network.getInstance().getApi(ScanActivity.this);


        // 扫描结果的
        mResult = new ArrayList<>();
        mScanResult.setItemAnimator(new DefaultItemAnimator());
        mScanResult.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ScanRecyclerViewAdapter(ScanActivity.this);

        mScanResult.setAdapter(adapter);
        adapter.updateData(mResult);
    }

    @OnClick({R.id.toggle_flashlight,
            R.id.choose_qrcde_from_gallery,
            flashlight,
            R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggle_flashlight:
                // 开启或关闭闪光灯
                toggleFlashLight();
                break;
            case flashlight:
                // 开启或关闭闪光灯
                toggleFlashLight();
                break;
            case R.id.choose_qrcde_from_gallery:
                //从相册选中
                startActivityForResult(BGAPhotoPickerActivity.newIntent(this, null, 1, null, false), REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
            case R.id.tv_submit:

                tryLogin();

                break;
        }
    }

    public void showSelectDialog() {
        String[] items = {"物资台账", "创建入库单", "创建出库单"};
        AlertDialog dialog = new AlertDialog.Builder(ScanActivity.this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                submitGoods();
                                break;
                            case 1:
                                submitInData();
                                break;
                            case 2:
                                submitOutData();
                                break;
                        }
                    }
                })
                .create();

        dialog.show();
    }


    /**
     * 尝试登陆
     */
    public void tryLogin() {

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
                        showSelectDialog();
                        break;
                    case BACKUP:
                        submitGoods();
                        break;
                    case IN_LIB:
                        submitInData();
                        break;
                    case OUT_LIB:
                        submitOutData();
                        break;
                }
            }

            @Override
            public void loginFailure(String msg) {
                //登录失败
                UiUtils.showLoginActivity(mContext, null);
                finish();
            }

            @Override
            public void connectFail() {
                //当前网络不是内网
                showMaterialDialog("", "当前网络不是内网,无法提交 ", "确定", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideMaterialDialog();
                    }
                }, null);
            }
        });
    }

    // 提交入库单
    public void submitInData() {

        //尝试登录
        SharedPreferences spHeader = getSharedPreferences(AppConst.Header.FILE, Context.MODE_PRIVATE);
        String cookie = spHeader.getString(AppConst.Header.SET_COOKIE, "");

        showWaitingDialog("提交到入库单");


        Call<ResultBean> inData = api.submitInData(cookie, getData(),"");

           inData.enqueue(new Callback<ResultBean>() {
               @Override
               public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                   hideWaitingDialog();
               }

               @Override
               public void onFailure(Call<ResultBean> call, Throwable t) {
                   hideWaitingDialog();
               }
           });



        // 提交是否成功   如果成功  清空列表
    }

    // 提交出库单
    public void submitOutData() {
    }

    //提交物资到物资台账
    public void submitGoods() {
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
            fields.put("ItemId" + i, scanBean.id);
            //入库的个数
            fields.put("StorageCount" + i, scanBean.selectCount + "");
        }

        return fields;
    }

    public void toggleFlashLight() {
        if (toggleFlash) {
            mQRCodeView.openFlashlight();
            toggleFlash = false;
        } else {
            mQRCodeView.closeFlashlight();
            toggleFlash = true;
        }
        switchLightImage(toggleFlash);
    }

    public void switchLightImage(boolean flag) {
        if (!flag) {
            mToggleFlashlight.setImageResource(R.drawable.bulb_bright);
            flashLight.setImageResource(R.drawable.bulb_bright);
        } else {
            mToggleFlashlight.setImageResource(R.drawable.bulb_dark);
            flashLight.setImageResource(R.drawable.bulb_dark);
        }
    }


    @Override
    public void onScanQRCodeSuccess(String result) {

        if (checkResult(result)) {

            //解析JSON
            ScanBean scanBean = gson.fromJson(result, ScanBean.class);

            if (scan == IN_LIB || scan == OUT_LIB || scan == BACKUP) {
                scanBean.selectCount = 1;
                if (contains(scanBean)) {
                    adapter.updateData(scanBean.goodsId);
                } else {
                    mResult.add(scanBean);
                }
                adapter.notifyDataSetChanged();
            } else {
                showDialog(scanBean.showInDialog());
            }

        } else {
            showDialog(result);
        }
        mQRCodeView.startSpot();
        vibrate();
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

    /**
     * 判断是不是我们自己的二维码
     *
     * @return
     */
    public boolean checkResult(String result) {
        String reg = "\\{.*\\}";
        return result.matches(reg) && result.contains("ld_datatype");
    }


    @Override
    public void onScanQRCodeOpenCameraError() {
        Logger.i("打开相机出错");
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mQRCodeView.showScanRect();

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);

            /*
            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
                }

                @Override
                protected void onPostExecute(String result) {
                    if (TextUtils.isEmpty(result)) {
                        showSnackBar("未发现二维码");
                        return;
                    }

                    if (true) {
                        //TODO 解析数据  如果是我们的二维码添加到RecycleView上面去 如果不是就弹出窗体
                        mResult.add(gson.fromJson(result, ScanBean.class));

                    } else {
                        showDialog(result);
                    }


                }
            }.execute();
        }


    }


    public void showDialog(final String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
        final AlertDialog alertDialog = builder
                .setTitle("扫描结果")
                .setMessage(msg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("复制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 复制
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText(null, msg));

                        mQRCodeView.startSpot();
                        showSnackBar("复制成功 !");
                    }
                })
                .create();

        alertDialog.show();
    }

    public void showSnackBar(String msg) {
        Snackbar.make(mRootView, msg, Snackbar.LENGTH_LONG).show();
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

}
