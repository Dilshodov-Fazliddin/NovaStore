package uz.nova.novastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.CreateProductDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.entity.CategoryEntity;
import uz.nova.novastore.entity.ProductEntity;
import uz.nova.novastore.entity.UserEntity;
import uz.nova.novastore.exception.DataNotFoundException;
import uz.nova.novastore.exception.NotAcceptableException;
import uz.nova.novastore.mapper.ProductMapper;
import uz.nova.novastore.repository.CategoryRepository;
import uz.nova.novastore.repository.ProductRepository;
import uz.nova.novastore.repository.UserRepository;
import uz.nova.novastore.service.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ResponseEntity<StandardResponse<?>> saveProduct(CreateProductDto productDto, Principal principal) {
        productRepository.save(productMapper.toEntity(productDto, principal));
        return ResponseEntity.ok(StandardResponse.builder().data(null).status(200).message("Product successfully saved").build());
    }

    @Override
    public ResponseEntity<StandardResponse<?>> deleteProduct(Principal principal, UUID id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        UserEntity user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));

        if (product.getCustomer().getId().toString().equals(user.getId().toString())) {
            productRepository.delete(product);
            return ResponseEntity.ok(StandardResponse.builder().message("Product successfully deleted").data(null).status(200).build());
        }
        throw new NotAcceptableException("This product does not belong to you");
    }

    @Override
    public ResponseEntity<StandardResponse<List<ProductEntity>>> getAll() {
        return ResponseEntity.ok(StandardResponse.<List<ProductEntity>>builder().status(200).data(productRepository.findAll()).message("Product List").build());
    }

    @Override
    public ResponseEntity<StandardResponse<?>> updateProduct(CreateProductDto dto, UUID id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
            product.setName(dto.getName());
            productRepository.save(product);
            return ResponseEntity.ok(StandardResponse.builder().data(null).message("Product Updated").status(200).build());
        }

    @Override
    public ResponseEntity<StandardResponse<List<ProductEntity>>> getProductByCategory(String name, int size, int page) {
        CategoryEntity category = categoryRepository.searchByName(name).orElseThrow(() -> new DataNotFoundException("Category not found"));
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(StandardResponse.<List<ProductEntity>>builder().data(productRepository.findProductEntitiesByCategory(category,pageable))
                .message("Products found by category").status(200).build());

    }
}


