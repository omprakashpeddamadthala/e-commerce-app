package com.ms.dlj.order;

import com.ms.dlj.customer.CustomerClient;
import com.ms.dlj.customer.CustomerResponse;
import com.ms.dlj.exceptions.BusinessException;
import com.ms.dlj.kafka.OrderProducer;
import com.ms.dlj.product.ProductClient;
import com.ms.dlj.product.PurchaseRequest;
import com.ms.dlj.product.PurchaseResponse;
import com.ms.dlj.orderline.OrderLineRequest;
import com.ms.dlj.orderline.OrderLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;

    /**
     * Creates a new order based on the provided order request
     * @param orderRequest The request containing order details
     * @return The ID of the newly created order
     * @throws BusinessException if customer is not found or other business rules are violated
     */
    public Integer createOrder(OrderRequest orderRequest) {
        log.info("Starting order creation for reference: {}", orderRequest.reference());

        CustomerResponse customer = getCustomer(orderRequest);
        List<PurchaseResponse> purchasedProducts = purchaseProducts(orderRequest);
        Order order = saveOrder(orderRequest);
        saveOrderLines(orderRequest, order);
        sendOrderConfirmation(orderRequest, customer, purchasedProducts);

        log.info("Order successfully created with ID: {}", order.getId());
        return order.getId();
    }

    /**
     * Retrieves customer information for the order
     */
    private CustomerResponse getCustomer(OrderRequest orderRequest) {
        log.debug("Fetching customer with ID: {}", orderRequest.customerId());
        return customerClient.getCustomerById(orderRequest.customerId())
                .orElseThrow(() -> {
                    String message = "Cannot create order :: No customer found with provided ID :: " + orderRequest.customerId();
                    log.error(message);
                    return new BusinessException(message);
                });
    }

    /**
     * Processes product purchases for the order
     */
    private List<PurchaseResponse> purchaseProducts(OrderRequest orderRequest) {
        log.debug("Purchasing products for order reference: {}", orderRequest.reference());
        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(orderRequest.products());
        log.debug("Successfully purchased {} products", purchasedProducts.size());
        return purchasedProducts;
    }

    /**
     * Saves the order to the repository
     */
    private Order saveOrder(OrderRequest orderRequest) {
        log.debug("Saving order for reference: {}", orderRequest.reference());
        Order order = orderRepository.save(orderMapper.toOrder(orderRequest));
        log.debug("Order saved with ID: {}", order.getId());
        return order;
    }

    /**
     * Saves order line items
     */
    private void saveOrderLines(OrderRequest orderRequest, Order order) {
        log.debug("Saving order lines for order ID: {}", order.getId());
        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(new OrderLineRequest(
                    null,
                    orderRequest.id(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
            ));
        }
        log.debug("Successfully saved {} order lines", orderRequest.products().size());
    }

    /**
     * Sends order confirmation message
     */
    private void sendOrderConfirmation(OrderRequest orderRequest,
                                       CustomerResponse customer,
                                       List<PurchaseResponse> purchasedProducts) {
        log.debug("Sending order confirmation for reference: {}", orderRequest.reference());
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.totalAmount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        log.debug("Order confirmation sent successfully");
    }

    public List<OrderResponse> getAllOrders() {
        log.info( "Received GET request to get all orders " );
        return orderRepository.findAll()
                .stream()
                .map( orderMapper::fromOrder )
                .collect( Collectors.toList() );

    }

    public OrderResponse getOrderById(String orderId) {
        log.info( "Received GET request to get order by id " + orderId );
        return orderRepository.findById( Integer.parseInt( orderId ) )
                .map( orderMapper::fromOrder )
                .orElseThrow( () -> new BusinessException( "Order not found with id: " + orderId ) );
    }
}
