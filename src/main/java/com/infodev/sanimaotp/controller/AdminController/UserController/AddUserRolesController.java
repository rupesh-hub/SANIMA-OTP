package com.infodev.sanimaotp.controller.AdminController.UserController;

import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.UserManagement.AddRolePojo;
import com.infodev.sanimaotp.pojo.UserManagement.AddUserPojo;
import com.infodev.sanimaotp.services.adminService.userRoleManagement.CreateUserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddUserRolesController {

    @Autowired
    private CreateUserRoles createUserRoles;

    @RequestMapping("api/addRole")
    public ResponseEntity<?> addRole(@RequestBody AddRolePojo addRolePojo){
        return new ResponseEntity<>(createUserRoles.createRole(addRolePojo), HttpStatus.OK);
    }

    @RequestMapping("api/addUser")
    public ResponseEntity<GlobalResponse> addUser(@RequestBody AddUserPojo addUserPojo, Authentication authentication){
        GlobalResponse globalResponse= new GlobalResponse();
        try{
            globalResponse.setData(createUserRoles.createUser(addUserPojo, authentication));
            globalResponse.setMessage("User added successfully");
            globalResponse.setStatus(1);
        }catch(Exception e){
            globalResponse.setData(null);
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            globalResponse.setMessage("User addition failed: "+e.getMessage());
            globalResponse.setStatus(0);
        }
        return new ResponseEntity(globalResponse, HttpStatus.OK);
    }

}
