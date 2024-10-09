package pl.chudziudgi.paymc.util;

import lombok.Getter;

public enum TimeType {

    TICK(50, 50),
    MILLISECOND(1, 1),
    SECOND(1000, 1000),
    MINUTE(60000, 60),
    HOUR(3600000, 60),
    DAY(86400000, 24),
    WEEK(604800000, 7);

    @Getter
    private final int time;
    private final int timeMulti;

    TimeType(int time, int timeMulti) {
        this.time = time;
        this.timeMulti = timeMulti;
    }

    public int getMulti() {
        return this.timeMulti;
    }

    public int getTick() {
        return this.time / 50;
    }

    public int getTime(int multi) {
        return this.time * multi;
    }

    public int getTick(int multi) {
        return getTick() * multi;
    }
}
