package uz.nova.novastore.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.RoleDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.domain.request.RoleForFront;
import uz.nova.novastore.entity.RoleEntity;

import java.util.List;
import java.util.UUID;

@Service
public interface RoleService {
    ResponseEntity<StandardResponse<RoleEntity>>saveRole(RoleDto roleDto);
    ResponseEntity<StandardResponse<?>>deleteRole(UUID id);
    ResponseEntity<StandardResponse<RoleEntity>>updateRole(RoleDto roleDto,UUID id);
    ResponseEntity<StandardResponse<List<RoleForFront>>> getRoles(int size,int page);
    List<RoleForFront> mapRoles(List<RoleEntity> forMapping);
}
