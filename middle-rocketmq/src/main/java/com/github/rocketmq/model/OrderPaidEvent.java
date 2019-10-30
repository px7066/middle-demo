package com.github.rocketmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>OrderPaidEvent</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaidEvent {
    private String orderId;
    private BigDecimal paidMoney;
}
