package cn.lonecloud.cts;

/**
 * Created by lonecloud on 17/4/30.
 * 枚举失败和成功
 */
public enum MessageConstants {
    SUCCESS(200), ERROR(500);

    private int value;

    MessageConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
