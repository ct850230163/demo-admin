package cn.vpclub.demo.admin.feign.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 说明：
 * Jwt工具集合
 * BCryptPasswordEncoder加密的优势：
 * 同一字符串每次加密的结果不同，但都可用通过验证。
 * 这样就不需要进行加盐操作
 * 创建人：westfruit
 * 创建时间：2018-1-1
 */
public class JwtUtil {

    /**
     * 获得BCryptPasswordEncoder加密的密码
     *
     * */
    public static String getBCryptPassword(CharSequence rawPassword){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        String hashedPassword =bCryptPasswordEncoder.encode(rawPassword);

        return hashedPassword;
    }

    /**
     * 验证密码是否相等-->第一个参数是明文
     * 同一字符串每次加密的结果不同，但都可用通过验证
     * 验证方法：bCryptPasswordEncoder.matches(password,encodePassword);//未加密的password要放在前面
     * */
    public static boolean isSamePassword(CharSequence password,String encodePassword){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        Boolean result=bCryptPasswordEncoder.matches(password,encodePassword);

        return result;
    }

    public static void main(String[] args) {
        System.out.println(getBCryptPassword("123456"));
    }
}
