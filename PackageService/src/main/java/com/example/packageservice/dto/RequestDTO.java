package com.example.packageservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    @NotBlank(message="Sender location cannot be blank")
    private String senderLocation;
    @NotBlank(message="Receiver location cannot be blank")
    private String receiverLocation;
    @NotBlank(message = "Product name cannot be null")
    private String product;
    @Positive(message="Weight must be greater than zero")
    private Double totalWeight;
    @Positive(message="Quantity must be greater than zero")
    private Integer qty;

}
/*notes: Spring stores all validation errors inside something called a BindingResult.
MethodArgumentNotValidException
        │
        ▼
BindingResult
        │
        ▼
FieldErrors
        │
        ▼
@NotBlank message
So eventually you'll extract

"Sender location cannot be blank"

instead of the long Spring message.
 */