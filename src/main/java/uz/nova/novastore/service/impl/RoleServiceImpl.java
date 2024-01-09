package uz.nova.novastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.RoleDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.domain.request.RoleForFront;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.exception.DataNotFoundException;
import uz.nova.novastore.exception.NotAcceptableException;
import uz.nova.novastore.mapper.RoleMapper;
import uz.nova.novastore.repository.RoleRepository;
import uz.nova.novastore.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
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
        RoleEntity role=roleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Role not found"));
        if (!roleDto.getName().isBlank()) {
            role.setName("ROLE_" + roleDto.getName().toUpperCase());
            roleRepository.save(role);
            return ResponseEntity.ok(StandardResponse.<RoleEntity>builder().data(null).status(200).message("role updated").build());
        }
        throw new NotAcceptableException("Something went wrong, please try again");
    }



    @Override
    public ResponseEntity<StandardResponse<List<RoleForFront>>> getRoles(int size,int page) {
        Sort sort = Sort.by(Sort.Direction.ASC,"name");
        Pageable pageable= PageRequest.of(page,size,sort);
        List<RoleEntity>all=roleRepository.searchAllBy(pageable);
        List<RoleForFront> roleForFronts = mapRoles(all);
        return ResponseEntity.ok(StandardResponse.<List<RoleForFront>>builder().data(roleForFronts).message("All roles").data(roleForFronts).build());
    }

    @Override
    public List<RoleForFront> mapRoles(List<RoleEntity> forMapping) {
        List<RoleForFront>roleForFronts=new ArrayList<>();
        for (RoleEntity role:forMapping){
            roleForFronts.add(RoleForFront.builder()
                    .name(role.getName())
                    .build());
        }
        return roleForFronts;
    }


}
