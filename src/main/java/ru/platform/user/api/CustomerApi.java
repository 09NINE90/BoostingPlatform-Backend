package ru.platform.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.user.dto.response.CustomerProfileRsDto;
import ru.platform.user.service.ICustomerService;

import static ru.platform.LocalConstants.Api.CUSTOMER_TAG_DESCRIPTION;
import static ru.platform.LocalConstants.Api.CUSTOMER_TAG_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Tag(name = CUSTOMER_TAG_NAME, description = CUSTOMER_TAG_DESCRIPTION)
public class CustomerApi {

    private final ICustomerService customerService;

    @GetMapping("/me/profile")
    @Operation(summary = "Получить профиль заказчика")
    public ResponseEntity<CustomerProfileRsDto> getUserProfileData() {
        CustomerProfileRsDto result = customerService.getCustomerProfileData();
        return ResponseEntity.ok(result);
    }

}
