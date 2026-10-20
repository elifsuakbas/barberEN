package com.barber;
import java.util.*;
import java.security.*;
import java.nio.*;
import java.nio.charset.*;
/** In-memory demo accounts only. Replace with a backend for production. */
public final class AuthService {
    private static final Map<String,byte[]> accounts=new HashMap<>();
    static {char[] initial={'B','a','r','b','e','r','1','2','3','!'};register("demo@barber.app",initial);Arrays.fill(initial,'\0');}
    private AuthService(){}
    private static String key(String email){return email.trim().toLowerCase(Locale.ROOT);}
    public static synchronized boolean register(String email,char[] password){if(accounts.containsKey(key(email)))return false;accounts.put(key(email),digest(password));return true;}
    public static synchronized boolean authenticate(String email,char[] password){byte[] expected=accounts.get(key(email)),actual=digest(password);try{return expected!=null&&MessageDigest.isEqual(expected,actual);}finally{Arrays.fill(actual,(byte)0);}}
    private static byte[] digest(char[] password){ByteBuffer bytes=StandardCharsets.UTF_8.encode(CharBuffer.wrap(password));byte[] input=new byte[bytes.remaining()];bytes.get(input);try{return MessageDigest.getInstance("SHA-256").digest(input);}catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}finally{Arrays.fill(input,(byte)0);if(bytes.hasArray())Arrays.fill(bytes.array(),(byte)0);}}
}
