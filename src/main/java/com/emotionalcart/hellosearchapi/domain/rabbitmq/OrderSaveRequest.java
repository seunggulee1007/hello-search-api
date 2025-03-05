package com.emotionalcart.hellosearchapi.domain.rabbitmq;

import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticOrder;
import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticOrderItem;
import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticOrderItemOption;
import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticOrderRecipient;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class OrderSaveRequest implements Serializable {

    private Long id;
    private double totalPrice;
    private LocalDateTime orderAt;

    private Long orderMemberId;

    private String orderMemberName;

    private String orderStatus;

    private List<OrderItem> items;

    private OrderRecipient recipient;

    @Getter
    @ToString
    public static class OrderItem implements Serializable {

        private Long id;

        private Long productId;

        private Long categoryId;

        private String productName;

        private String categoryName;

        private double price;

        private Integer quantity;

        private List<OrderItemOption> itemOptions;

    }

    @Getter
    @ToString
    public static class OrderItemOption implements Serializable {

        private Long id;
        private Long optionId;
        private String optionName;
        private Long optionDetailId;
        private String optionDetailName;

    }

    @Getter
    @ToString
    public static class OrderRecipient implements Serializable {

        private Long id;
        private String recipientName;
        private String recipientPhone;
        private String postalCode;
        private String defaultAddress;
        private String detailAddress;
        private String orderRequirement;
        private String deliveryRequirement;

    }

    public ElasticOrder mapToElasticOrder() {
        return ElasticOrder.builder()
            .id(id)
            .totalPrice(totalPrice)
            .orderAt(orderAt)
            .orderMemberId(orderMemberId)
            .orderMemberName(orderMemberName)
            .orderStatus(orderStatus)
            .items(items.stream().map(item -> ElasticOrderItem.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .categoryId(item.getCategoryId())
                .productName(item.getProductName())
                .categoryName(item.getCategoryName())
                .price(item.getPrice())
                .itemOptions(item.getItemOptions().stream().map(option -> ElasticOrderItemOption.builder()
                    .id(option.getId())
                    .optionId(option.getOptionId())
                    .optionName(option.getOptionName())
                    .optionDetailId(option.getOptionDetailId())
                    .optionDetailName(option.getOptionDetailName())
                    .build()).toList())
                .build()).toList())
            .recipient(ElasticOrderRecipient.builder()
                           .id(recipient.getId())
                           .recipientName(recipient.getRecipientName())
                           .recipientPhone(recipient.getRecipientPhone())
                           .postalCode(recipient.getPostalCode())
                           .defaultAddress(recipient.getDefaultAddress())
                           .detailAddress(recipient.getDetailAddress())
                           .orderRequirement(recipient.getOrderRequirement())
                           .deliveryRequirement(recipient.getDeliveryRequirement())
                           .build())
            .build();
    }

}
