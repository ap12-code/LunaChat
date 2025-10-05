package com.github.ucchyocean.lc3.velocity;

import com.github.ucchyocean.lc3.LunaChatLogger;
import org.slf4j.Logger;


public record LunaChatVelocityLogger(Logger logger) implements LunaChatLogger {

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }

    @Override
    public void error(Throwable e) {
        this.logger.error("Exception occurred", e);
    }
}
