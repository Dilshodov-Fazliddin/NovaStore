package uz.nova.novastore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.RoleDto;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.repository.UserRoleRepository;

@Service
@RequiredArgsConstructor
public class RoleMapper {
    private final UserRoleRepository userRoleRepository;

    public RoleEntity toEntity(RoleDto roleDto){
        return RoleEntity.builder()
                .name(roleDto.getName())
                .build();
    }
}
