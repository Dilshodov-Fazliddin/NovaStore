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
    public ResponseEntity<StandardResponse<RoleEntity>> updateRole(RoleDto roleDto, UUID id) {
        roleRepository.deleteById(id);
        RoleEntity role = roleMapper.toEntity(roleDto);
        role.setName("ROLE_"+role.getName().toUpperCase());
        roleRepository.save(role);
        return ResponseEntity.ok(StandardResponse.<RoleEntity>builder().data(null).status(200).message("role updated").build());
    }

    @Override
    public ResponseEntity<StandardResponse<List<RoleEntity>>> getAllRoles() {
        return ResponseEntity.ok(StandardResponse.<List<RoleEntity>>builder().message("All roles").status(200).list(List.of(roleRepository.findAll())).build());
    }

}
