package uz.nova.novastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.CreateProductDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.domain.request.ProductEntityForFront;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<StandardResponse<List<ProductEntityForFront>>> getProductByCategory(String name, int size, int page) {
        CategoryEntity category = categoryRepository.findByName(name)
                .orElseThrow(()->new DataNotFoundException("Category not found"));
        Sort sort = Sort.by(Sort.Direction.ASC,"price");
        Pageable pageable = PageRequest.of(page,size,sort);
        List<ProductEntity>product=productRepository.searchAllByCategory(category,pageable);
        List<ProductEntityForFront> productEntityForFronts = mapRoles(product);
        return ResponseEntity.ok(StandardResponse.<List<ProductEntityForFront>>builder().data(productEntityForFronts).message("this is products for Pageable").status(200).build());
    }

    @Override
    public List<ProductEntityForFront> mapRoles(List<ProductEntity> forMapping) {
        List<ProductEntityForFront>productEntityForFronts=new ArrayList<>();
        for (ProductEntity product:forMapping){
            productEntityForFronts.add(ProductEntityForFront.builder()
                            .name(product.getName())
                            .brand(product.getBrand())
                            .price(product.getPrice())
                            .category(product.getCategory().getName())
                            .customer(product.getCustomer().getUsername())
                            .description(product.getDescription())
                    .build());
        }
        return productEntityForFronts;
    }

    @Override
    public ResponseEntity<StandardResponse<?>> deleteBadProducts(Principal principal, UUID id) {
        UserEntity user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));
        if (user.getRole().getName().equals("ROLE_ADMIN")){
            productRepository.deleteById(id);
            return ResponseEntity.ok(StandardResponse.builder().data(null).message("Product successfully deleted").status(200).build());
        }
            throw new NotAcceptableException("You are not an admin");
    }

    @Override
    public ResponseEntity<StandardResponse<Integer>> getNumberProducts() {
        return ResponseEntity.ok(StandardResponse.<Integer>builder().status(200).message("this is products number").data(productRepository.countProductEntitiesBy()).build());
    }

}


