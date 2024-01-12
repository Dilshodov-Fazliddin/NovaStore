package uz.nova.novastore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nova.novastore.domain.CreateProductDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.entity.CategoryEntity;
import uz.nova.novastore.entity.ProductEntity;
import uz.nova.novastore.service.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nova/product-service")
public class ProductController {
    private final ProductService productService;

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @PostMapping("/save")
    public ResponseEntity<StandardResponse<?>>save(@Valid @RequestBody CreateProductDto productDto, Principal principal){
        return productService.saveProduct(productDto,principal);
    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @DeleteMapping("/delete")
    public ResponseEntity<StandardResponse<?>>deleteById(@RequestParam UUID productId,Principal principal){
        return productService.deleteProduct(principal,productId);
    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<ProductEntity>>>getAll(){
        return productService.getAll();
    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))

    @PutMapping("/change-name")
    public ResponseEntity<StandardResponse<?>>updateName( @RequestBody CreateProductDto productDto,@RequestParam UUID id){
        return productService.updateProduct(productDto,id);
    }

    @DeleteMapping("/delete-bad-products")
    public ResponseEntity<StandardResponse<?>>deleteBadProduct(@RequestParam UUID id,Principal principal){
        return productService.deleteBadProducts(principal,id);
    }

}
