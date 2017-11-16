package test.cb.com.testaccessibilityservice.service;

import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by chenbin on 2017/11/16.
 */
public class AutoSignService extends BaseAccessibilityService {
    private static final String TAG = "AutoSignService";

    public static final String MI_UI_QUAN_XIAN = "com.android.systemui";
    public static final String QIANG_QIANG = "com.cb.qiangqiang2";
    public static final String YOU_KU = "com.youku.phone";
    public static final String BAO_ZOU = "com.baozoumanhua.android";
    public static final String HANG_LU_ZONG_HENG = "com.umetrip.android.msky.app";

    public boolean finishSignYouKu = false;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        super.onAccessibilityEvent(event);
        if (findViewByTextEnhance("允许") != null && findViewByTextEnhance("拒绝") != null && findViewByTextEnhance("正在尝试开启") != null) {
            Log.e(TAG, "onAccessibilityEvent: " + event.getPackageName());
            clickViewByTextEnhance("允许", "是否允许");
            return;
        }

        if (QIANG_QIANG.equals(event.getPackageName())) {
            clickViewByTextEnhance("免费试用");
        } else if (YOU_KU.equals(event.getPackageName())) {
            clickTextViewByText("我的");
            AccessibilityNodeInfo info = findViewByTextEnhance("我的任务");
            if (info != null && !finishSignYouKu) {
                finishSignYouKu = true;
                performViewClick(info);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(HANG_LU_ZONG_HENG);
            }
        } else if (HANG_LU_ZONG_HENG.equals(event.getPackageName())) {
            clickTextViewByText("旅豆中心");
            clickTextViewByText("每日抽奖");
            //lotteryBtn
//            clickViewByTextEnhance("抽   奖");
            clickTextViewByID("lotteryBtn");
        }
    }

    private void startActivity(String packageName) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        intent.setPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
