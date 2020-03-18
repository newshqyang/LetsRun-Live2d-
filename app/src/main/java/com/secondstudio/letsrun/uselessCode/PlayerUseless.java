//package com.secondstudio.letsrun.uselessCode;
//
//import android.content.Context;
//import android.os.Looper;
//import android.widget.Toast;
//
//import com.secondstudio.letsrun.model.TaskList;
//import com.secondstudio.letsrun.utils.WebUtils;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class PlayerUseless {
//
//    private static JSONArray mBag;  //玩家背包
//    /**
//     * 加载背包界面所需背包信息，并刷新背包界面
//     * @throws JSONException
//     */
//    public static void Load_BvAndShow(final String account) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (mBag != null) {
//                    mBag = null;
//                }
//                try {
//                    String tmpBag = WebUtils.getInfo("bv" , account);
//                    if (tmpBag == null) {
//                        Looper.prepare();
//                        Toast.makeText(mActivity, "ERROR:0001", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                        return;
//                    }
//                    mBag  = new JSONArray(tmpBag);       //获取信息
//                    Looper.prepare();
//                    Looper.loop();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    public static String tmpInfo = "";
//    //将玩家信息发送至服务器
//    public static void send(Context context) throws JSONException {
//        JSONObject info = new JSONObject();
//        info.put("player_account", mAccount);
//        info.put("player_name", mName);
//        info.put("player_sex", mSex);
//        info.put("player_doTaskDate", mDate);
//        info.put("player_bag", mBag);
//        int result = WebUtils.doPost(info);
//        if (result == 0) {
//            Toast.makeText(context, "Error:0002", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public static JSONArray getBag() {
//        return mBag;
//    }
//    //获取背包内卡牌数量
//    public static int getBagSize() {
//        return mBag.length();
//    }
//    //获取背包中指定编号的卡牌的数量
//    public static int getCardSizeByNo(int cardNo) throws JSONException {
//        for (int i=0;i<mBag.length();i++) {
//            if (mBag.getJSONObject(i).getInt("card_no") == cardNo) {
//                return mBag.getJSONObject(i).getInt("size");
//            }
//        }
//        return 0;
//    }
//    //增加指定编号的卡牌至背包中
//    public static void addCard(int cardNo) throws JSONException {
//        for (int i=0;i<mBag.length();i++) {
//            if (mBag.getJSONObject(i).getInt("card_no") == cardNo) {
//                mBag.getJSONObject(i).put("size", mBag.getJSONObject(i).getInt("size") + 1);
//                return;
//            }
//        }
//        mBag.put(new JSONObject().put("card_no", cardNo).put("size", 1));   //背包中没有，就新建
////        send(mContext);
//    }
//    //删除背包内指定编号的卡牌
//    public static void deleteCard(int cardNo) throws JSONException {
//        for (int i=0;i<mBag.length();i++) {
//            if (mBag.getJSONObject(i).getInt("card_no") == cardNo) {
//                mBag.getJSONObject(i).put("size", mBag.getJSONObject(i).getInt("size") - 1);
//                break;
//            }
//        }
//    }
//
//    /**
//     *  判断是否需要重置任务列表
//     *  storeItemDo用来判断是否是商品操作
//     */
//    public static void DecideToRefresh(int storeItemDo){
//        //获取系统时间
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        String ymd = year+"年"+month+"月"+day+"日";
//        if (!getDate().equals("无")){
//            if (!getDate().equals(ymd) || storeItemDo == 9527){
//                //如果当前日期和数据库日期不一样，就代表另一天了，需要重置任务列表
//                DBMS.getDB().execSQL("UPDATE player_tasks SET done=0");
//                TaskList.refresh();
//            }
//        }else {
//            //如果是第一次登录游戏，就记录第一次的时间
//            mDate = ymd;
//        }
//    }
//}
