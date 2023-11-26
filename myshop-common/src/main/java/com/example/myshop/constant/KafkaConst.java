package com.example.myshop.constant;

public class KafkaConst {
    public final static String ORDER_COMPLETED_COUPON_APPLY_GROUP_ID = "order-completed-coupon-apply-group_id";
    public final static String ORDER_COMPLETED_INCREASE_QUANTITY_GROUP_ID = "order-completed-increase-quantity-group-id";
    public final static String ORDER_COMPLETED_GROUP_MESSAGE_GROUP_ID = "order-completed-group_message-group-id";
    public final static String ORDER_CANCELED_COUPON_RESET_GROUP_ID = "order-cancel-coupon-reset-group-id";
    public final static String ORDER_CANCELED_DECREASE_QUANTITY_GROUP_ID = "order-canceled_decrease-quantity-group-id";
    public final static String ORDER_UPDATED_RECEIVER_GROUP_ID = "order-updated-receiver-group-id";


    public final static String ORDER_COMPLETED_TOPIC_NAME = "order-completed-topic";
    public final static String ORDER_CANCELED_TOPIC_NAME = "order-canceled-topic";
    public final static String ORDER_UPDATED_TOPIC_NAME = "order-updated-topic";

}