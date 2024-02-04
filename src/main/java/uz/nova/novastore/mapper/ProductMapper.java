package uz.nova.novastore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.CreateProductDto;
import uz.nova.novastore.entity.ProductEntity;
import uz.nova.novastore.repository.CategoryRepository;
import uz.nova.novastore.repository.UserRepository;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ProductMapper {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductEntity toEntity(CreateProductDto dto, Principal principal){
        return ProductEntity.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .color(dto.getColor())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .category(categoryRepository.searchByName(dto.getCategory()))
                .customer(userRepository.searchByEmail(principal.getName()))
                .build();
    }
}
