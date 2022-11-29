package cn.master.backend.util;

import cn.master.backend.request.OrderRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author create by 11's papa on 2022-11-22
 */
public class ServiceUtils {
    public static final int ORDER_STEP = 5000;

    public static List<OrderRequest> getDefaultOrder(List<OrderRequest> orders) {
        return getDefaultOrder(null, orders);
    }

    public static List<OrderRequest> getDefaultOrder(String prefix, List<OrderRequest> orders) {
        return getDefaultOrderByField(prefix, orders, "update_time");
    }

    public static List<OrderRequest> getDefaultOrderByField(List<OrderRequest> orders, String field) {
        return getDefaultOrderByField(null, orders, field);
    }

    private static List<OrderRequest> getDefaultOrderByField(String prefix, List<OrderRequest> orders, String field) {
        if (orders == null || orders.size() < 1) {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setName(field);
            orderRequest.setType("desc");
            if (StringUtils.isNotBlank(prefix)) {
                orderRequest.setPrefix(prefix);
            }
            orders = new ArrayList<>();
            orders.add(orderRequest);
            return orders;
        }
        return orders;
    }

    public static Long getNextOrder(String templateId, BiFunction<String, Long, Long> getLastOrderFunc) {
        Long lastOrder = getLastOrderFunc.apply(templateId, null);
        return (lastOrder == null ? 0 : lastOrder) + ServiceUtils.ORDER_STEP;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
