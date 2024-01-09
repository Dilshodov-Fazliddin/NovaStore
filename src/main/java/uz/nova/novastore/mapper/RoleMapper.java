package uz.nova.novastore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.RoleDto;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleMapper {
    private final RoleRepository userRoleRepository;

    public RoleEntity toEntity(RoleDto roleDto){
        return RoleEntity.builder()
                .name("ROLE_"+roleDto.getName().toUpperCase())
                .build();
    }
}
