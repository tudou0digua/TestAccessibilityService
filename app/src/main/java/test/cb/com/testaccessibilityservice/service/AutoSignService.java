package test.cb.com.testaccessibilityservice.service;

import android.content.Intent;
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
    public static final String TAO_BAO = "com.taobao.taobao";
    public static final String QIAN_DAO = "com.cb.registerbyhand";
    public static final String JAN_DAN = "com.danielstudio.app.wowtu";

    public boolean finishSignYouKu = false;
    public boolean finishSignHangLvZongHeng= false;
    public boolean finishSignBaoZou = false;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        super.onAccessibilityEvent(event);

        if (MI_UI_QUAN_XIAN.equals(event.getPackageName())) {
            printAllView("AAAAA");
        }

        if (findViewByTextEnhance("允许") != null
                && findViewByTextEnhance("拒绝") != null && findViewByTextEnhance("正在尝试") != null) {
            clickViewByTextEnhance("允许", "是否允许");
//            return;
        }

        if (QIANG_QIANG.equals(event.getPackageName())) {
            //抢抢网
//            clickViewByTextEnhance("免费试用");

        } else if (YOU_KU.equals(event.getPackageName())) {
            //优酷
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
            if (finishSignHangLvZongHeng) {
                return;
            }
            //航旅纵横
            clickTextViewByText("旅豆中心");
            AccessibilityNodeInfo info = findViewByTextEnhance("每日抽奖");
            if (info != null) {
                performViewClick(info);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finishSignHangLvZongHeng = true;
                startActivity(BAO_ZOU);
            }
            //lotteryBtn
//            clickViewByTextEnhance("抽   奖");
            clickTextViewByID("lotteryBtn");

        } else if (BAO_ZOU.equals(event.getPackageName())) {
            //暴走漫画
            if (finishSignBaoZou) {
                return;
            }

            if (findViewByID("com.baozoumanhua.android:id/count_tv") != null) {
                clickTextViewByID("com.baozoumanhua.android:id/count_tv");
                return;
            }

            //com.baozoumanhua.android:id/sign_in
//            AccessibilityNodeInfo info = findViewByTextEnhance("领取奖励");
            AccessibilityNodeInfo info = findViewByID("com.baozoumanhua.android:id/sign_in");
            if (info != null) {
                performViewClick(info);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                clickViewByTextEnhance("我的", "我的神作", "我的频道", "我的连载", "我的收藏", "我的订阅", "我的下载");
                if (findViewByTextEnhance("我的收藏") != null) {
                    scrollViewByText("我的收藏", Orientation.UP);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clickViewByTextEnhance("任务奖励");
                    return;
                }
                if (findViewByTextEnhance("任务奖励") != null) {
                    if (findViewByTextEnhance("领取") != null) {
                        clickViewByTextEnhance("领取");
                    } else {
                        clickViewByTextEnhance("签到", "每天签到", "签到的");
                    }
                }
                if (findViewByTextEnhance("本月已签到") != null) {
                    clickViewByTextEnhance("签到", "本月已签到", "签到的");
                    startActivity(TAO_BAO);
//                    finishSignBaoZou = true;
                }
            }
        }
    }

    private void startActivity(String packageName) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        intent.setPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
