package uz.nova.novastore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.domain.request.ProductEntityForFront;
import uz.nova.novastore.entity.ProductEntity;
import uz.nova.novastore.service.ProductService;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/nova/market")
public class MarketController {
    private final ProductService productService;
    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @GetMapping("/get-by-category")
    public ResponseEntity<StandardResponse<List<ProductEntityForFront>>> getByCategory(@RequestParam String category, @RequestParam int size, @RequestParam int page){
        return productService.getProductByCategory(category,size,page);
    }
}
