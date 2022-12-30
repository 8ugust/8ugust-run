package com.august.run.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.time.format.DateTimeFormatter;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import lombok.RequiredArgsConstructor;
import javax.crypto.spec.SecretKeySpec;

import com.august.run.Model.User;
import com.august.run.Request.UserRequest;
import com.august.run.Request.TokenRequest;
import com.august.run.Config.JWT.TokenProvider;
import com.august.run.Repository.UserRepository;
import com.august.run.Config.Security.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Service
@Component
@RequiredArgsConstructor
public class UserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Value("${properties.db_secret_key}")
    private String db_secret_key;

    @Value("${properties.jwt_secret_key}")
    private String jwt_secret_key;





    /**
     * User Login
     * 
     * @param request
     * @return
     */
    public TokenRequest login(UserRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.createToken(authentication);
    }





    /**
     * Create User Data
     * 
     * @param request
     * @return
     */
    public String signup(UserRequest request) {
        try {
            log.debug("Request ID : " + request.getId());
            log.debug("Request PW : " + request.getPassword());
            log.debug("Request NM : " + request.getName());
            log.debug("Request PH : " + request.getPhone());
            log.debug("Request GN : " + request.getGender());
            log.debug("Request BR : " + request.getBirth());
            log.debug("Request IS : " + request.getInstagram());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println(request.getName());

            userRepository.save(
                User.builder()
                    .id(request.getId())
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .birth(LocalDate.parse(request.getBirth(), formatter))
                    .gender(request.getGender())
                    .phone(aes_encrypt(request.getPhone()))
                    .instagram(request.getInstagram())
                    .createdAt(LocalDateTime.now().withNano(0))
                    .updatedAt(LocalDateTime.now().withNano(0))
                    .build()
            );
            
            
            System.out.println("End");
            return "success";
        
        } catch (Exception e) {
            System.out.println(e);
            return e.toString();
        }
    }





    /**
     * Get User Info
     * 
     * @param request
     * @return
     */
    public UserRequest getUserInfo() {
        return userRepository.findById(SecurityUtil.getCurrentUserId()).map(UserRequest::userInfo)
            .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }





    /**
     * Udpate User Data
     * 
     * @param password
     * @return
     */
    public String update(UserRequest request, String user_id) {
        User user = userRepository.findById(user_id);

        if (StringUtils.isNotBlank(request.getPassword())) user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
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
        User user = userRepository.findById(user_id);
        user.setDeletedAt(LocalDateTime.now().withNano(0));

        userRepository.save(user);
        return "Success";
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
