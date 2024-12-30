package com.xczcdjx.word.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Optional;

public class LoggerUtil {
    public static void info(String info, @Nullable Optional<String> infoName){
        Log.i(infoName.orElse("User defined Info"),info);
    }
    public static void error(String err, @Nullable Optional<String> errName){
        Log.e(errName.orElse("Error by User defined"),err);
    }
    // 无需第二个参数的重载方法
    public static void info(String info) {
        info(info, Optional.empty());
    }

    public static void error(String err) {
        error(err, Optional.empty());
    }
}
