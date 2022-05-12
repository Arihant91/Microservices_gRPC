// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: order.proto

package com.shopping.stubs.order;

public interface OrderOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.shopping.stubs.order.Order)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 user_id = 1;</code>
   * @return The userId.
   */
  int getUserId();

  /**
   * <code>int32 order_id = 2;</code>
   * @return The orderId.
   */
  int getOrderId();

  /**
   * <code>int32 no_of_items = 3;</code>
   * @return The noOfItems.
   */
  int getNoOfItems();

  /**
   * <code>double total_amount = 4;</code>
   * @return The totalAmount.
   */
  double getTotalAmount();

  /**
   * <code>.google.protobuf.Timestamp order_date = 5;</code>
   * @return Whether the orderDate field is set.
   */
  boolean hasOrderDate();
  /**
   * <code>.google.protobuf.Timestamp order_date = 5;</code>
   * @return The orderDate.
   */
  com.google.protobuf.Timestamp getOrderDate();
  /**
   * <code>.google.protobuf.Timestamp order_date = 5;</code>
   */
  com.google.protobuf.TimestampOrBuilder getOrderDateOrBuilder();
}
