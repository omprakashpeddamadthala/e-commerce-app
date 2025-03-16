        package com.ms.dlj.order;

        import com.ms.dlj.product.PurchaseRequest;
        import jakarta.validation.constraints.NotBlank;
        import jakarta.validation.constraints.NotEmpty;
        import jakarta.validation.constraints.NotNull;
        import jakarta.validation.constraints.Positive;

        import java.math.BigDecimal;
        import java.util.List;

        public record OrderRequest(

                Integer id,
                String reference,
                @Positive(message = "Total amount should be positive")
                BigDecimal totalAmount,

                @NotNull(message = "Payment method should be precised")
                PaymentMethod paymentMethod,

                @NotNull(message = "Customer id should be present")
                @NotEmpty(message = "Customer id should be present")
                @NotBlank(message = "Customer id should be present")
                String customerId,

                @NotEmpty(message = "You should have at least one product")
                List<PurchaseRequest> products

        ) {
        }
