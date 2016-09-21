package com.easemob.easeui.model;

/**
 * Created by it_huangxin on 2016/1/14.
 */
public class GoodChatRowEventBusEntity {
    public static final int OPERATION_SKILL = 0;
    public static final int OPERATION_NEED = 1;
    public static final int OPERATION_SURE = 2;
    public static final int OPERATION_REFUSE = 3;

    private int operation;
    private int id;

    public GoodChatRowEventBusEntity(int operation) {
        this.operation = operation;
    }

    public GoodChatRowEventBusEntity(int operation, int id) {
        this.operation = operation;
        this.id = id;
    }

    public int getOperation() {
        return operation;
    }

    public int getId() {
        return id;
    }
}
