package uz.nova.novastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.RoleDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.mapper.RoleMapper;
import uz.nova.novastore.repository.UserRoleRepository;
import uz.nova.novastore.service.RoleService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final UserRoleRepository roleRepository;
    private final RoleMapper roleMapper;
    @Override
    public ResponseEntity<StandardResponse<RoleEntity>> saveRole(RoleDto roleDto) {
        roleRepository.save(roleMapper.toEntity(roleDto));
        return ResponseEntity.ok(StandardResponse.<RoleEntity>builder().status(200).message("Role successfully added").data(null).build());
    }

    @Override
    public ResponseEntity<StandardResponse<?>> deleteRole(UUID id) {
        roleRepository.deleteById(id);
        return ResponseEntity.ok(StandardResponse.<RoleEntity>builder().status(200).data(null).message("Role deleted").build());
    }

    @Override
    public ResponseEntity<StandardResponse<RoleEntity>> updateRole(String oldName, String newName) {
        roleRepository.updateByName(oldName,newName);
        return ResponseEntity.ok(StandardResponse.<RoleEntity>builder().message("updated Role").data(null).status(200).build())
    }

    @Override
    public ResponseEntity<StandardResponse<List<RoleEntity>>> getAllRoles() {
        return ResponseEntity.ok(StandardResponse.<List<RoleEntity>>builder().message("All roles").status(200).data(roleRepository.findAll()).build());
    }
}
