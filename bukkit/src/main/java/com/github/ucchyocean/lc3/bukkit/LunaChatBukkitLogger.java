package com.github.ucchyocean.lc3.bukkit;

import com.github.ucchyocean.lc3.LunaChatLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LunaChatBukkitLogger implements LunaChatLogger {
    private final Logger logger;

    public LunaChatBukkitLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void warn(String message) {
        this.logger.warning(message);
    }

    @Override
    public void error(String message) {
        this.logger.severe(message);
    }

    @Override
    public void error(Throwable e) {
        this.logger.log(Level.SEVERE, "Exception occurred.", e);
    }
}
