package uz.nova.novastore.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.CreateProductDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.entity.CategoryEntity;
import uz.nova.novastore.entity.ProductEntity;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {
     ResponseEntity<StandardResponse<?>>saveProduct(CreateProductDto productDto, Principal principal);
     ResponseEntity<StandardResponse<?>>deleteProduct(Principal principal, UUID id);
     ResponseEntity<StandardResponse<List<ProductEntity>>>getAll();
     ResponseEntity<StandardResponse<?>>updateProduct(CreateProductDto dto,UUID id);
     ResponseEntity<StandardResponse<List<ProductEntity>>>getProductByCategory(String name,int size,int page);
}
