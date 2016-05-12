
package publickeycertificate;

import java.math.BigInteger;
import java.security.SecureRandom;
/**
 *
 * @author Arya
 */
public class PublicKeyCertificate {

    public static BigInteger e, d; //variable public and private exponential 
    public static SecureRandom rand;
    
    public static BigInteger createKey(int bit){ //bit harus diatas 512
        BigInteger totient, N; //N = public key, totient = (p-1).(q-1)
        rand = new SecureRandom();
        BigInteger p = new BigInteger(bit/2,100,rand); //random prime >=256 bit
        BigInteger q = new BigInteger(bit/2,100,rand); //random prime >=256 bit
        N=p.multiply(q); //public key
        totient=(p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        
        //nyari nilai e, pilih terserah, yang penting 1<e<totient, e dan totient koprima
        e = new BigInteger("3");
        while (totient.gcd(e).intValue()>1) {
            e = e.add(new BigInteger("2"));
        }
        
        //d.e = 1 (mod totient)
        d = e.modInverse(totient);
        return N;
    }
    
    public static String encrypt(String plaintext, BigInteger key){
        BigInteger plainBigInt = new BigInteger(plaintext.getBytes());
        plainBigInt = plainBigInt.modPow(e, key);
        String cipher = plainBigInt.toString();
        return cipher;
    }
    
    public static String decrypt(String ciphertext,BigInteger key) {
        BigInteger cipherBigInt = new BigInteger(ciphertext);
        cipherBigInt = cipherBigInt.modPow(d, key);
        String plain = new String(cipherBigInt.toByteArray());
        return plain;
    }
    
    public static void main(String[] args) {
        BigInteger key = createKey(1024); //bit harus diatas 512
        
        String plaintext = "I Gusti Ngurah Arya Bawanta IGN Arya Bawanta DLL DLL";
        System.out.println("Plaintext: " + plaintext);

        String ciphertext = encrypt(plaintext,key);
        System.out.println("Ciphertext: " + ciphertext);
        plaintext = decrypt(ciphertext, key);

        System.out.println("Plaintext: " + plaintext);
    }
    
}
