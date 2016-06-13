package com.npc.lottery.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.CharEncoding;

public class Tools {
    
	/** 加密,解密键值(用户名屏蔽使用) */
	private static final String shieldAccountKey = "alexa2091nndgx";
	
	
	public static int getMark() {
        //占成修改时间限制
          String beginDate = "02:30:00";
          String endDate = "08:30:00"; 
          Date afterDate;
          Date beforeDate;
          Date date = new Date();
          DateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
          int rateFlag = 0;
          try {
              afterDate = dateformat.parse(beginDate);
              beforeDate = dateformat.parse(endDate);
              date = dateformat.parse(dateformat.format(date));
              if(date.after(afterDate) && date.before(beforeDate)){
                  rateFlag = 1;
                  return rateFlag;
              }
              else
              {
                  rateFlag = 0;
                  return rateFlag;
              }
          } catch (ParseException e) {
              e.printStackTrace();
          }
        return rateFlag;
    }
	
	private Tools() {
	}

	private static byte[] encrypt(byte[] data, byte[] key) {
		if (data.length == 0) {
			return data;
		}
		return toByteArray(encrypt(toIntArray(data, true), toIntArray(key, false)), false);
	}

	private static byte[] decrypt(byte[] data, byte[] key) {
		if (data.length == 0) {
			return data;
		}
		return toByteArray(decrypt(toIntArray(data, false), toIntArray(key, false)), true);
	}

	private static int[] encrypt(int[] v, int[] k) {
		int n = v.length - 1;

		if (n < 1) {
			return v;
		}
		if (k.length < 4) {
			int[] key = new int[4];

			System.arraycopy(k, 0, key, 0, k.length);
			k = key;
		}
		int z = v[n], y = v[0], delta = 0x9E3779B9, sum = 0, e;
		int p, q = 6 + 52 / (n + 1);

		while (q-- > 0) {
			sum = sum + delta;
			e = sum >>> 2 & 3;
			for (p = 0; p < n; p++) {
				y = v[p + 1];
				z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
			}
			y = v[0];
			z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
		}
		return v;
	}

	private static int[] decrypt(int[] v, int[] k) {
		int n = v.length - 1;

		if (n < 1) {
			return v;
		}
		if (k.length < 4) {
			int[] key = new int[4];

			System.arraycopy(k, 0, key, 0, k.length);
			k = key;
		}
		int z = v[n], y = v[0], delta = 0x9E3779B9, sum, e;
		int p, q = 6 + 52 / (n + 1);

		sum = q * delta;
		while (sum != 0) {
			e = sum >>> 2 & 3;
			for (p = n; p > 0; p--) {
				z = v[p - 1];
				y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
			}
			z = v[n];
			y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
			sum = sum - delta;
		}
		return v;
	}

	private static int[] toIntArray(byte[] data, boolean includeLength) {
		int n = (((data.length & 3) == 0) ? (data.length >>> 2) : ((data.length >>> 2) + 1));
		int[] result;

		if (includeLength) {
			result = new int[n + 1];
			result[n] = data.length;
		} else {
			result = new int[n];
		}
		n = data.length;
		for (int i = 0; i < n; i++) {
			result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
		}
		return result;
	}

	private static byte[] toByteArray(int[] data, boolean includeLength) {
		int n = data.length << 2;

		;
		if (includeLength) {
			int m = data[data.length - 1];

			if (m > n) {
				return null;
			} else {
				n = m;
			}
		}
		byte[] result = new byte[n];

		for (int i = 0; i < n; i++) {
			result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
		}
		return result;
	}

	/**
	 * 进行XXTEA加密
	 * 
	 * @param plaintext
	 *            明文
	 * @return
	 * @create_time 2011-8-29 上午11:14:21
	 */
	public static String encodeWithKey(String plaintext) {
		byte[] result = encrypt(Base64StrUtils.getBytesUtf8(plaintext), shieldAccountKey.getBytes());
		return new String(Base64.encodeBase64URLSafe(result));
	}

	/**
	 * 进行XXTEA解密
	 * 
	 * @param clipertext
	 *            密文
	 * @return
	 * @create_time 2011-8-29 上午11:14:39
	 */
	public static String decodeWithKey(String clipertext) {
		try {
			byte[] cipherByte = Base64.decodeBase64(Base64StrUtils.getBytesUtf8(clipertext));
			byte[] result = decrypt(cipherByte, shieldAccountKey.getBytes());
			return new String(result, CharEncoding.UTF_8);
		} catch (Exception e) {
			return null;
		}
	}

    public   static   String   textToHtml(String   sourcestr)         
    {         
     int   strlen;         
     String   restring="",   destr   =   "";         
     strlen   =   sourcestr.length();         
     for           (int   i=0;   i<strlen;           i++)         
     {         
         char   ch=sourcestr.charAt(i);         
         switch   (ch)         
         {         
         case   '<':         
         destr   =   "<";         
         break;         
         case   '>':         
         destr   =   ">";         
         break;         
         case   '\"':         
         destr   =   "\"";
         break;         
         case   '&':         
         destr   =   "&";         
         break;         
         case   13:         
         destr   =   "<br>";         
         break;         
         case   '\n':         
        	 destr   =   "<br>";         
        	 break;         
         case   32:         
         destr   =   " ";         
         break;         
         default   :         
         destr   =   ""   +   ch;         
         break;         
         }         
         restring   =   restring   +   destr;         
         }         
         return   ""   +   restring;         
     }   
	
	public static void main(String[] args) {
		System.out.println(textToHtml("<script>\ralert(\"sdfsdf\");</script>wa\nng\r\n "));
	}
	
}
