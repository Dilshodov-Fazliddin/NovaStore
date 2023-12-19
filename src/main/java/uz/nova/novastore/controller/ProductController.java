package uz.nova.novastore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nova.novastore.domain.CreateProductDto;
import uz.nova.novastore.domain.StandardResponse;
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

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<?>>save(@Valid @RequestBody CreateProductDto productDto, Principal principal){
        return productService.saveProduct(productDto,principal);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<StandardResponse<?>>delete(@RequestParam UUID productId,Principal principal){
        return productService.deleteProduct(principal,productId);
    }

    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<ProductEntity>>>getAll(){
        return productService.getAll();
    }


    @PutMapping("/change-name")
    public ResponseEntity<StandardResponse<?>>updateName( @RequestBody CreateProductDto productDto,@RequestParam UUID id){
        return productService.updateProduct(productDto,id);
    }
}
