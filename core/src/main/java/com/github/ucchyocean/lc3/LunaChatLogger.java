package com.github.ucchyocean.lc3;

public interface LunaChatLogger {
    void info(String message);
    void warn(String message);
    void error(String message);
    void error(Throwable e);
}
