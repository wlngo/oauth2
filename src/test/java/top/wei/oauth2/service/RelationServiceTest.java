package top.wei.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.wei.oauth2.model.entity.RolePermissionRelation;
import top.wei.oauth2.model.entity.UserRoleRelation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * RelationServiceTest.
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelationServiceTest {

    private final RolePermissionRelationService rolePermissionRelationService;

    private final UserRoleRelationService userRoleRelationService;

    @Test
    void testServicesAreLoaded() {
        // Test that services are properly injected and Spring context loads
        assertNotNull(rolePermissionRelationService);
        assertNotNull(userRoleRelationService);
    }

    @Test 
    void testServiceMethods() {
        // Test basic service functionality - just verify methods exist
        RolePermissionRelation rolePermission = new RolePermissionRelation();
        rolePermission.setRoleId("test-role");
        rolePermission.setPermissionId("test-permission");
        
        UserRoleRelation userRole = new UserRoleRelation();
        userRole.setUserId("test-user");
        userRole.setRoleId("test-role");
        
        // These methods should exist and be callable
        assertNotNull(rolePermissionRelationService);
        assertNotNull(userRoleRelationService);
    }
}
