package com.august.run.Controller;

import java.util.Map;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import com.august.run.Request.UserRequest;
import com.august.run.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * Get User Data One
     * 
     * @param request
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<UserRequest> getUserInfo() {
        UserRequest response = userService.getUserInfo();
        System.out.println(response);

        return ResponseEntity.ok(response);
    }





    /**
     * Update User Data
     * 
     * @param request
     * @return
     */
    @PutMapping("{user_id}")
    public ResponseEntity<HttpStatus> update(@RequestBody UserRequest request, @PathVariable String user_id) {
        if (userService.update(request, user_id).equals("Success")) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




    /**
     * Delete User Data
     * 
     * @param user_id
     * @return
     */
    @DeleteMapping("{user_id}")
    public Map<String, Object> delete(@PathVariable String user_id) {
        Map<String, Object> response = new HashMap<>();

        String result = userService.delete(user_id);
        if (result == "Success") response.put("result", "Success");
        if (result == "Fail") {
            response.put("result", "Fail");
            response.put("reason", "일치하는 회원 정보가 없습니다.");
        }

        return response;
    }    
}
