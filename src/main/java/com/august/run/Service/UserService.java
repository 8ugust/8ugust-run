package com.august.run.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import java.time.LocalDateTime;

import com.august.run.Model.User;

import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import java.time.format.DateTimeFormatter;
import com.august.run.Request.UserRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import com.august.run.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;


@Service
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Value("${properties.db_secret_key}")
    private String db_secret_key;

    @Value("${properties.jwt_secret_key}")
    private String jwt_secret_key;


    /**
     * Get All User Data
     * 
     * @return List
     */
    public List<User> getUserAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(e -> users.add(e));
        
        return users;
    }





    /**
     * 
     * 
     * @param request
     * @return
     */
    public List<User> getUserOne(String user_id) {
        List<User> user = new ArrayList<>();
        userRepository.findByUserId(user_id).map(e -> user.add(e));

        return user;
    }





    /**
     * Create User Data
     * 
     * @param request
     * @return
     */
    public String save(UserRequest request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        userRepository.save(
            User.builder()
                .userId(request.getUserId())
                .userPw(sha_encrypt(request.getUserPw()))
                .name(request.getName())
                .gender(request.getGender())
                .phone(aes_encrypt(request.getPhone()))
                .birth(LocalDate.parse(request.getBirth(), formatter))
                .createdAt(LocalDateTime.now().withNano(0))
                .updatedAt(LocalDateTime.now().withNano(0))
                .build()
        );
        
        return "Success";
    }





    /**
     * Udpate User Data
     * 
     * @param password
     * @return
     */
    public String update(UserRequest request, String user_id) {
        Optional<User> oUser = userRepository.findByUserId(user_id);
        if (!oUser.isPresent()) return "Fail";

        User user = oUser.get();
        if (StringUtils.isNotBlank(request.getUserPw())) user.setUserPw(aes_encrypt(request.getUserPw()));
        if (StringUtils.isNotBlank(request.getPhone())) user.setPhone(aes_encrypt(request.getPhone()));
        user.setUpdatedAt(LocalDateTime.now().withNano(0));

        userRepository.save(user);
        return "Success";
    }





    /**
     * Delete User Data
     * 
     * @param password
     * @return
     */
    public String delete(String user_id) {
        Optional<User> oUser = userRepository.findByUserId(user_id);
        if (!oUser.isPresent()) return "Fail";

        User user = oUser.get();
        user.setDeletedAt(LocalDateTime.now().withNano(0));

        userRepository.save(user);
        return "Success";
    }





    /**
     * User Login
     * 
     * @param user_id
     * @param user_pw
     * @return
     */
    public Map<String, Object> login(String user_id, String user_pw) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> oUser = userRepository.findByUserId(user_id);
        if (!oUser.isPresent()) {
            response.put("result", "Fail");
            response.put("reason", "ID를 확인해주세요.");
            return response;
        }

        String password = oUser.get().getUserPw();
        String input_pw = sha_encrypt(user_pw);
        if (!input_pw.equals(password)) {
            response.put("result", "Fail");
            response.put("reason", "Password를 확인해주세요.");
            return response;
        }

        byte[] keyBytes = Decoders.BASE64.decode(jwt_secret_key);
        Key byte_secret_key = Keys.hmacShaKeyFor(keyBytes);
        
        Date date = new Date();
        Long now = date.getTime();
        Date tokenExpiration = new Date(now + (30 * 60 * 1000L));

        String auth = Jwts.builder()
            .setSubject(oUser.get().getName())
            .claim("auth", "auth_value")
            .signWith(byte_secret_key, SignatureAlgorithm.HS256)
            .setExpiration(tokenExpiration)
            .compact();
        
        response.put("result", "Success");
        response.put("auth", auth);
        
        return response;
    }





    // SHA256 Encrypt
    public String sha_encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passByte = password.getBytes(); md.reset();
            byte[] digested = md.digest(passByte);

            StringBuilder sb = new StringBuilder();
            for (int i=0; i<digested.length; i++) {
                sb.append(Integer.toString((digested[i]&0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // AES Encrypt
    public String aes_encrypt(String password) {
        try {
            final Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, generatedMySQLAESKey(db_secret_key, "UTF-8"));
            byte[] encrypt = encryptCipher.doFinal(password.getBytes("UTF-8"));
            return byteArrToHex(encrypt);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // AES Decrypt
    public String aes_decrypt(String hex) {
        try {
            final Cipher decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, generatedMySQLAESKey(db_secret_key, "UTF-8"));
            byte[] decrypt = hexToByteArr(hex);
            return new String(decryptCipher.doFinal(decrypt));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Encrypt User Password To MySQL AES Algorithm
    private static SecretKeySpec generatedMySQLAESKey(final String key, final String encoding) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for (byte b : key.getBytes(encoding)) {
                finalKey[i++%16] ^= b;
            }
    
            return new SecretKeySpec(finalKey, "AES");

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // Byte to Hex
    public static String byteArrToHex(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        String hexNumber;
        StringBuffer sb = new StringBuffer(data.length * 2);
        for (int i=0; i<data.length; i++) {
            hexNumber = "0" + Integer.toHexString(0xff & data[i]);
            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }

        return sb.toString();
    }

    // Hex to Byte
    public static byte[] hexToByteArr(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        byte[] data = new byte[hex.length() / 2];
        for (int i=0; i<data.length; i++) {
            data[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }

        return data;
    }
    
    
}
