package com.august.run.Service;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HexFormat;

import javax.crypto.Cipher;
import java.time.LocalDateTime;
import com.august.run.Model.User;
import javax.crypto.spec.SecretKeySpec;
import java.time.format.DateTimeFormatter;
import com.august.run.Request.UserRequest;
import java.io.UnsupportedEncodingException;
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

    /**
     * Get All User Data
     * 
     * @return List
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(e -> users.add(e));
        
        return users;
    }

    /**
     * Create User Data
     * 
     * @param request
     * @return
     */
    public String signUp(UserRequest request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        userRepository.save(
            User.builder()
                .loginId(request.getLoginId())
                .loginPw(aes_encrypt(request.getLoginPw()))
                .loginName(request.getLoginName())
                .loginGender(request.getLoginGender())
                .loginBirth(LocalDate.parse(request.getLoginBirth(), formatter))
                .createdAt(LocalDateTime.now().withNano(0))
                .updatedAt(LocalDateTime.now().withNano(0))
                .build()
        );
        
        return "Success";
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
    
    
}
