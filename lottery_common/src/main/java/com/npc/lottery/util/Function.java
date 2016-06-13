package com.npc.lottery.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Function {

	public Function() {
	}

	public static String BlankNull(String str, String def) {
		if (str == null)
			return def;
		return str;
	}

	public static void main(String arg[]) {
		Function fun = new Function();

		byte[] message = fun.stringToBcd("88888");
		StringBuffer msg = new StringBuffer();
		for (int i = 0; message != null && i < message.length; i++) {
			String tmps = Integer.toHexString(message[i] & 0xff);
			if (tmps.length() == 1) {
				tmps = "0" + tmps;
			}
			msg.append(tmps + " ");
			if (i != 0 && (i + 1) % 16 == 0) {
				msg.append("\n");
			}
		}
		System.out.println(new String(msg));

	}

	public static int getInt(String str, int value) {
		int result = value;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
		}
		return result;
	}

	public static String getCurrentTime() {
		String theFormatString = "yyyy-MM-dd HH:mm:ss";
		GregorianCalendar todaysdate = new GregorianCalendar();
		Date theDate = todaysdate.getTime();
		if (theDate == null || theFormatString == null) {
			return "";
		}
		String theDateString = "";
		try {
			SimpleDateFormat theDateFormater = new SimpleDateFormat(
					theFormatString);
			theDateString = theDateFormater.format(theDate);
		} catch (IllegalArgumentException theException) {
			//
		}
		return theDateString;
	}

	public static String getCurrentTime(String theFormatString) {
		return getCurrentTime(theFormatString, 0);
	}

	public static String getCurrentTime(String theFormatString, int days) {
		// String theFormatString = "yyyy-MM-dd HH:mm:ss";
		GregorianCalendar todaysdate = new GregorianCalendar();
		todaysdate.add(Calendar.DAY_OF_MONTH, days);// 
		Date theDate = todaysdate.getTime();
		if (theDate == null || theFormatString == null) {
			return "";
		}
		String theDateString = "";
		try {
			SimpleDateFormat theDateFormater = new SimpleDateFormat(
					theFormatString);
			theDateString = theDateFormater.format(theDate);
		} catch (IllegalArgumentException theException) {
			//
		}
		return theDateString;
	}

	public byte[] stringToBcd(String str) {
		byte[] result = new byte[(str.length() + 1) / 2];
		try {
			if (str == null || str.length() == 0) {
				return new byte[0];
			}
			for (int i = 0; i < result.length; i++) {
				result[i] = (byte) 0xff;
				if (i * 2 < str.length()) {
					char c = str.charAt(i * 2);
					result[i] = getStringBcd(c);
				}
				if (i * 2 + 1 < str.length()) {
					char c = str.charAt(i * 2 + 1);
					result[i] = (byte) ((((int) result[i]) << 4 & 0xf0) + ((int) getStringBcd(c) & 0xf));
				} else {
					result[i] = (byte) ((((int) result[i]) << 4 & 0xf0) + (int) 0xf);
				}
			}
		} catch (Exception e) {
		}
		return result;
	}

	public String bcdToString(byte[] bcd) {
		String result = "";
		for (int i = 0; bcd != null && i < bcd.length; i++) {
			int tmp = bcd[i];
			String c = getBcdString(tmp >> 4 & 0xf);
			if (c == null) {
				break;
			}
			result += c;
			c = getBcdString(tmp & 0xf);
			if (c == null) {
				break;
			}
			result += c;
		}
		return result;
	}

	private byte getStringBcd(char i) {
		Character c = new Character(i);
		if (c.compareTo(new Character('0')) == 0) {
			return (byte) 0;
		}
		if (c.compareTo(new Character('1')) == 0) {
			return (byte) 1;
		}
		if (c.compareTo(new Character('2')) == 0) {
			return (byte) 2;
		}
		if (c.compareTo(new Character('3')) == 0) {
			return (byte) 3;
		}
		if (c.compareTo(new Character('4')) == 0) {
			return (byte) 4;
		}
		if (c.compareTo(new Character('5')) == 0) {
			return (byte) 5;
		}
		if (c.compareTo(new Character('6')) == 0) {
			return (byte) 6;
		}
		if (c.compareTo(new Character('7')) == 0) {
			return (byte) 7;
		}
		if (c.compareTo(new Character('8')) == 0) {
			return (byte) 8;
		}
		if (c.compareTo(new Character('9')) == 0) {
			return (byte) 9;
		}
		if (c.compareTo(new Character('*')) == 0) {
			return (byte) 0x0b;
		}
		if (c.compareTo(new Character('#')) == 0) {
			return (byte) 0x0C;
		}
		return (byte) 0xff;
	}

	private String getBcdString(int i) {
		if (i < 10) {
			return i + "";
		}
		switch (i) {
		case 0x0A:
			return "";
		case 0x0B:
			return "*";
		case 0x0c:
			return "#";
		case 0x0d:
			return "";
		case 0x0e:
			return "";
		case 0x0f:
			return null;
		}
		return "";
	}

	/***************************************************************************
	 * �ж��Ƿ�IP�˿ں�"ip:port"
	 * 
	 */
	public boolean isIpPort(String ipPort) {
		if (ipPort == null) {
			return false;
		}
		String[] num = ipPort.split(":");
		if (num.length != 2) {
			return false;
		}
		if (!isIp(num[0])) {
			return false;
		}
		if (!isPort(num[1])) {
			return false;
		}
		return true;
	}

	public boolean isIp(String ip) {
		if (ip == null) {
			return false;
		}
		try {
			String[] num = new String[4];
			num[0] = ip.substring(0, ip.indexOf("."));
			ip = ip.replaceFirst(num[0] + ".", "");
			num[1] = ip.substring(0, ip.indexOf("."));
			ip = ip.replaceFirst(num[1] + ".", "");
			num[2] = ip.substring(0, ip.indexOf("."));
			ip = ip.replaceFirst(num[2] + ".", "");
			num[3] = ip;

			if (num.length != 4) {
				return false;
			}
			if (!isInt(num[0])) {
				return false;
			}
			if (!isInt(num[1])) {
				return false;
			}
			if (!isInt(num[2])) {
				return false;
			}
			if (!isInt(num[3])) {
				return false;
			}
			if (Integer.parseInt(num[0]) > 255 || Integer.parseInt(num[0]) < 0) {
				return false;
			}
			if (Integer.parseInt(num[1]) > 255 || Integer.parseInt(num[1]) < 0) {
				return false;
			}
			if (Integer.parseInt(num[2]) > 255 || Integer.parseInt(num[2]) < 0) {
				return false;
			}
			if (Integer.parseInt(num[3]) > 255 || Integer.parseInt(num[3]) < 0) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean isPort(String port) {
		if (!isInt(port)) {
			return false;
		}
		if (Integer.parseInt(port) > 0xffff || Integer.parseInt(port) <= 0) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * xml�ļ����ַ��ת��
	 * 
	 */
	public static Document string2Xml(String xml) {
		Document doc = null;
		try {
			SAXBuilder sb = new SAXBuilder();
			InputStream in = new ByteArrayInputStream(xml.getBytes());
			doc = sb.build(in);
		} catch (Exception e) {
			System.out
					.println("com.shareinfo.integration.util.function.string2Xml():"
							+ e);
			System.out.println(xml);
		}
		return doc;
	}

	public static String xml2String(Document doc) {
		String xml = "";
		Format format = Format.getCompactFormat();
		format.setEncoding("GB2312");
		format.setOmitEncoding(false);
		format.setIndent("  ");
		format.setLineSeparator("\n");
		XMLOutputter outp = new XMLOutputter(format);
		xml = outp.outputString(doc);
		return xml;
	}

	/***************************************************************************
	 * 
	 * ��ȡ���Ե����� 0�����ͣ�1���ַ��ͣ�2�������� -1:��ʾ��ز�֧�ֵ�����
	 */
	public int getAttributeType(String type) {
		if ("Integer".equals(type)) {
			return 0;
		}
		if ("int".equals(type)) {
			return 0;
		}
		if ("java.lang.String".equals(type)) {
			return 1;
		}
		if ("String".equals(type)) {
			return 1;
		}
		if ("float".equals(type)) {
			return 2;
		}

		return -1;
	}

	/***************************************************************************
	 * ��ȡsocket���ܵ�������� ��ʽ��len(4 byte)+type(1 byte)+msg "type" �� ���յ�����������
	 * "result" �� ���ս��true��false "msg" �� ���յ������
	 * 
	 */
	public HashMap socketReceive(Socket socket) {
		HashMap result = new HashMap();
		DataInputStream dataRec = null;
		int len = 0;
		int type = 0;
		byte[] body = new byte[0];
		synchronized (socket) {
			try {
				dataRec = new DataInputStream(socket.getInputStream());
			} catch (IOException ioexception) { // IO����
				return result;
			}
			try {
				len = dataRec.readInt();
				/*
				 * byte[] l=new byte[4]; l[0]=dataRec.readByte();
				 * l[1]=dataRec.readByte(); l[2]=dataRec.readByte();
				 * l[3]=dataRec.readByte(); len=byte2Int(l);
				 */
				// len=dataRec.readInt();
				type = dataRec.readByte();

				System.out.println("len=" + len);
				System.out.println("type=" + type);

				body = new byte[len - 5];
				for (int readBytes = 0; readBytes < len - 5;) {
					readBytes += dataRec.read(body, readBytes, len - 5
							- readBytes);
				}
			} catch (InterruptedIOException e) { // ��ʱ
				return result;
			} catch (IOException ioexception) { // IO����
				return result;
			} catch (Exception ioexception) { // �������
				return result;
			}
		}
		String msg = new String(body);
		// System.out.println("msg="+msg);
		result.put("result", "true");
		result.put("type", type + "");
		result.put("msg", msg);
		return result;
	}

	/***************************************************************************
	 * ����socket��� ��ʽ��len(4 byte)+type(1 byte)+msg
	 */
	public boolean socketSend(Socket socket, int type, String msg) {
		byte[] body = msg.getBytes();
		int len = body.length + 5;
		/*
		 * byte[] head=new byte[5]; byte[] l=int2Byte(len); head[0]=l[0];
		 * head[1]=l[1]; head[2]=l[2]; head[3]=l[3]; head[4]=(byte)type;
		 */
		synchronized (socket) {
			try {
				DataOutputStream dataSend = new DataOutputStream(socket
						.getOutputStream());
				// dataSend.write(head, 0, 5);
				dataSend.writeInt(len);
				dataSend.writeByte(type);
				if (body != null) {
					dataSend.write(body, 0, body.length);
				}
				dataSend.flush();
			} catch (Exception e) {
				System.out.println("util.function.scoketSend() error:" + e);
				return false;
			}
		}
		return true;
	}

	/***************************************************************************
	 * 
	 * ��Stringת���ɹ̶����ȵ�byte����
	 */
	public byte[] getBytes(String src, int len) {
		byte[] result = new byte[len];
		try {
			byte[] temp = src.getBytes();
			System.arraycopy(temp, 0, result, 0, temp.length);

		} catch (IndexOutOfBoundsException e) {
			System.out.println("submitStruct String2Byte error:" + e);
		} catch (ArrayStoreException e) {
			System.out.println("submitStruct String2Byte error:" + e);
		} catch (Exception e) {
			System.out.println("submitStruct String2Byte error:" + e);
		}
		return result;
	}

	public byte[] getBytes(String[] src, int len) {
		byte[] result = new byte[len * src.length];
		for (int i = 0; i < src.length; i++) {
			try {
				byte[] temp = src[i].getBytes("GB2312");
				// byte[] temp = src[i].getBytes();
				System.arraycopy(temp, 0, result, i * len, temp.length);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("submitStruct String2Byte error:" + e);
			} catch (ArrayStoreException e) {
				System.out.println("submitStruct String2Byte error:" + e);
			} catch (Exception e) {
				System.out.println("submitStruct String2Byte error:" + e);
			}
		}
		return result;
	}

	/***************************************************************************
	 * ��ݳ�ʼ��
	 * 
	 */

	public void memset(byte abyte0[], int i) {
		for (int j = 0; j < i; j++) {
			abyte0[j] = 0;

		}
	}

	/***************************************************************************
	 * 
	 * ���鸴�ƣ���System.arraycopy()һ��
	 */
	public int strcpy(byte abyte0[], byte abyte1[], int i, int j) {
		int k;
		for (k = 0; k < j; k++) {
			abyte0[k + i] = abyte1[k];

		}
		abyte0[k + i] = 0;
		return k;
	}

	/***************************************************************************
	 * 
	 * MD5����
	 * 
	 */

	public static byte[] getMD5(byte[] b, int len) {
		MessageDigest digest;
		String algorithm = "MD5";
		try {
			digest = MessageDigest.getInstance(algorithm);
			digest.update(b, 0, len);
			return digest.digest();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}

	/***************************************************************************
	 * 
	 * ��ȡ��ǰʱ�䣬��ʽMMDDHHMMSS
	 * 
	 */
	public String getCurrentTimeStamp() {
		// return "994573501";

		GregorianCalendar todaysdate = new GregorianCalendar();
		int month = todaysdate.get(Calendar.MONTH) + 1;
		int day = todaysdate.get(Calendar.DAY_OF_MONTH);
		int hour = todaysdate.get(Calendar.HOUR_OF_DAY);
		int minute = todaysdate.get(Calendar.MINUTE);
		int second = todaysdate.get(Calendar.SECOND);
		String strMonth = Integer.toString(month);
		String strDay = Integer.toString(day);
		String strHour = Integer.toString(hour);
		String strMinute = Integer.toString(minute);
		String strSecond = Integer.toString(second);
		if (day < 10) {
			strDay = "0" + strDay;
		}
		if (month < 10) {
			strMonth = "0" + strMonth;
		}
		if (hour < 10) {
			strHour = "0" + strHour;
		}
		if (minute < 10) {
			strMinute = "0" + strMinute;
		}
		if (second < 10) {
			strSecond = "0" + strSecond;
		}
		return strMonth + strDay + strHour + strMinute + strSecond;
	}

	/***************************************************************************
	 * null to 0
	 * 
	 */
	public String nullTo0(String str) {
		if (str == null) {
			return "0";
		}
		return str;
	}

	public String nullToEmpty(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	/***************************************************************************
	 * ����url����ȡ���ص����
	 * 
	 */
	public String getUrlContent(String url) {
		// System.out.println("url="+url);
		String result = "";
		try {
			URL server = new URL(url);
			URLConnection con = server.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con
					.getInputStream()));
			String dat = "";
			while ((dat = in.readLine()) != null) {
				if (result.length() != 0) {
					result += "\r";
				}
				result += dat;
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Function.getUrl(String url):" + e);
		}

		return result;
	}

	/***************************************************************************
	 * ��ȡstart�굽����ʱ����������
	 * 
	 */

	public String[] getYear(int start) {
		int end = Integer.parseInt(getCurrentYear());
		if (start > end) {
			return new String[0];
		}
		String[] result = new String[end - start + 1];
		int temp = start;
		for (int i = 0; i < result.length; i++) {
			result[result.length - i - 1] = temp + "";
			temp++;
		}
		return result;
	}

	/***************************************************************************
	 * 
	 * ��ȡ���£���ʽ200308
	 * 
	 */
	public String getYearMonth(String date) {
		if (date == null || date.length() < 10) {
			return "";
		}
		String result = date.substring(0, 7);
		result = replace(result, "-", "");
		return result;
	}

	/***************************************************************************
	 * 
	 * �ж��Ƿ����ֻ��
	 * 
	 */
	public boolean isMobile(String mobile) {
		if (mobile == null || mobile.length() < 11) {
			return false;
		}
		// �����ж�.....
		return true;
	}

	/***************************************************************************
	 * 
	 * ��һ����Ϣ�и�ɼ������Ϣ
	 * 
	 */
	public String[] splitShortMessage(String content) {
		if (content == null || "".equals(content)) {
			return new String[0];
		}
		int size = 70;
		int len = content.length();
		String[] result = new String[1];
		if (len <= size) {
			result[0] = content;
			return result;
		}
		// int num=(len+size-1)/size;
		int num = (len + size - 6) / (size - 5); // �ָ�ʱǰ����ϣ�2-1��2-2��֮���ʶ��
		result = new String[num];
		size = size - 5;
		for (int i = 0; i < len; i += size) {
			int no = i / size + 1;
			if (i < len - size) {
				result[i / size] = "(" + num + "-" + no + ")"
						+ content.substring(i, i + size);
			} else {
				result[i / size] = "(" + num + "-" + no + ")"
						+ content.substring(i, len);
			}
		}

		return result;
	}

	/***************************************************************************
	 * 
	 * ��С��ͨ��һ����Ϣ�и�ɼ������Ϣ
	 * 
	 */
	public String[] splitShortMessageSms(String content) {
		if (content == null || "".equals(content)) {
			return new String[0];
		}
		int size = 45;
		int len = content.length();
		String[] result = new String[1];
		if (len <= size) {
			result[0] = content;
			return result;
		}
		// int num=(len+size-1)/size;
		int num = (len + size - 6) / (size - 5); // �ָ�ʱǰ����ϣ�2-1��2-2��֮���ʶ��
		result = new String[num];
		size = size - 5;
		for (int i = 0; i < len; i += size) {
			int no = i / size + 1;
			if (i < len - size) {
				result[i / size] = "(" + num + "-" + no + ")"
						+ content.substring(i, i + size);
			} else {
				result[i / size] = "(" + num + "-" + no + ")"
						+ content.substring(i, len);
			}
		}

		return result;
	}

	/***************************************************************************
	 * 
	 * ��ȡһ�����ʱ��Ķ� start:��ʼʱ��� end������ʱ���
	 * 
	 */
	/*
	 * public String[] getYear(int start) { int
	 * end=Integer.parseInt(getCurrentYear()); if(start>end) return new
	 * String[0]; String[] result=new String[end-start+1]; int temp=start;
	 * for(int i=0;i<result.length;i++) { result[i]=temp+""; temp++; } return
	 * result; }
	 */
	/***************************************************************************
	 * 
	 * ��ȡһ��û���ظ����������
	 * 
	 * size:����Ĵ�С len:���ٸ������
	 * 
	 */
	public Vector getRandom(Vector size, int num) {
		if (size.size() == 0) {
			return new Vector();
		}
		Vector result = new Vector();
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			if (size.size() == 0) {
				break;
			}
			int index = r.nextInt(size.size());
			// System.out.println("size="+size.size()+" random="+index);
			result.add(size.get(index));
			size.remove(index);
			if (size.size() == 1 && i < num - 1) {
				result.add(size.get(0));
				break;
			}
		}
		return result;
	}

	/***************************************************************************
	 * 
	 * �����ַ��ʽת��
	 * 
	 **************************************************************************/

	public String setToDB(String str) {
		if (str == null) {
			return "";
		}
		return codeConver(str, "ISO8859_1", "GBK");
	}

	public static String getFromDB(String str) {
		if (str == null) {
			return "";
		}
		// return codeConver(str,"GB2312","ISO8859_1");
		return str;
		// return codeConver(str,"ISO8859_1","GB2312");
	}

	/***************************************************************************
	 * 
	 * �����������,��С type: big--��С small����С����
	 **************************************************************************/
	public int[] sort(int[] data, String type) {
		boolean blnChange = true;
		while (blnChange) {
			blnChange = false;
			for (int i = 0; i < data.length - 1; i++) {
				if ("big".equals(type)) {
					if (data[i + 1] > data[i]) {
						int temp = data[i + 1];
						data[i + 1] = data[i];
						data[i] = temp;
						blnChange = true;
					}
				}
				if ("small".equals(type)) {
					if (data[i + 1] < data[i]) {
						int temp = data[i + 1];
						data[i + 1] = data[i];
						data[i] = temp;
						blnChange = true;
					}
				}
			}
		}
		return data;
	}

	/***************************************************************************
	 * 
	 * ��ȡһ���������ַ�
	 * 
	 **************************************************************************/
	/*
	public String getRandom(int len) {
		Random r = new Random();
		String result = "";
		for (int i = 0; i < len; i++) {
			result += r.nextInt(9);
		}
		return result;
	}
	*/

	/***************************************************************************
	 * 
	 * ������תΪʱ�ֵĸ�ʽ 19:00
	 * 
	 **************************************************************************/
	public String getTime(String t) {
		if (!isInt(t)) {
			return "00:00";
		}
		int i = Integer.parseInt(t);
		String[] s = new String[2];
		s[0] = Integer.toString(i / 60);
		int j = i % 60;
		if (j < 10) {
			s[1] = "0" + j;
		} else {
			s[1] = j + "";
		}
		return s[0] + ":" + s[1];
	}

	/***************************************************************************
	 * 
	 * ��ȡ��ֵ�����ֱ�ʾ��
	 * 
	 **************************************************************************/
	public String getSize(int size) {
		String strSize = "0Byte";
		if (size > 1024 * 1024 * 2) {
			strSize = Integer.toString(size / 1024 / 1024) + "M";
		} else if (size > 1024 * 2) {
			strSize = Integer.toString(size / 1024) + "K";
		} else {
			strSize = size + "Byte";
		}
		return strSize;
	}

	/***************************************************************************
	 * 
	 * �����ڣ���ʽ��2002��08��08��ɾ�������ת��Ϊ��ֵ��20020808��
	 * 
	 **************************************************************************/
	public static int getDateValue(String day) {
		if (day == null || day.length() < 10)
			return 20050101;
		return Integer.parseInt(day.substring(0, 4) + day.substring(5, 7)
				+ day.substring(8, 10));
	}

	/***************************************************************************
	 * 
	 * ����һ�����ڼ��ϼ����º��ֵ
	 * 
	 **************************************************************************/
	public String getNextDate(String start, int num) {
		String nextDay = "";
		int intYear = Integer.parseInt(start.substring(0, 4));
		int intMonth = Integer.parseInt(start.substring(5, 7));
		String strDay = start.substring(8, 10);
		intMonth += num;
		intYear += (intMonth - 1) / 12;
		intMonth = (intMonth - 1) % 12 + 1;
		nextDay = intYear + "-" + intMonth + "-" + strDay;
		if (intMonth < 10) {
			nextDay = intYear + "-0" + intMonth + "-" + strDay;
		}
		return nextDay;
	}

	/***************************************************************************
	 * 
	 * �ж��������ڵ�׼ȷ�ԣ�����һ���ĸ�ʽ��� mode: "CN" -- xx��xx��xx�� "EN" -- xx-xx-xx
	 **************************************************************************/
	public String getNoteDate(String year, String month, String day, String mode) {
		String strResult = "";
		// check year
		if (!isInt(year) || year.length() != 4) {
			year = getCurrentYear();
			// check month
		}
		if (!isInt(month) || month.length() > 2 || Integer.parseInt(month) > 12) {
			month = getCurrentMonth();
			// check day
		}
		if (!isInt(day) || day.length() > 2 || Integer.parseInt(day) > 31) {
			month = getCurrentDay();
		}
		if ("CN".equals(mode)) {
			strResult = year + "��" + Integer.parseInt(month) + "��"
					+ Integer.parseInt(day) + "��";
		} else { // "EN" mode
			if (month.length() == 1) {
				month = "0" + month;
			}
			if (day.length() == 1) {
				day = "0" + day;
			}
			strResult = year + "-" + month + "-" + day;
		}
		return strResult;
	}

	/***************************************************************************
	 * 
	 * ��ȡ���ĵ����ڱ�ʶ��ʽ
	 * 
	 **************************************************************************/
	public String getWeekDay(String id) {
		String result = "";
		if ("0".equals(id)) {
			result = "������";
		}
		if ("1".equals(id)) {
			result = "����һ";
		}
		if ("2".equals(id)) {
			result = "���ڶ�";
		}
		if ("3".equals(id)) {
			result = "������";
		}
		if ("4".equals(id)) {
			result = "������";
		}
		if ("5".equals(id)) {
			result = "������";
		}
		if ("6".equals(id)) {
			result = "������";
		}
		return result;
	}

	/***************************************************************************
	 * ��ȡһ��ʱ�ε��������ڣ���(start��end start��ʽ��2002-08-08 end��ʽ��2002-08-08 ����ֵ��
	 * ������0�������ڣ���ʽΪ��2002-08-08 ������1�������ڼ��������գ�0 ������6
	 **************************************************************************/
	public String[][] getSomeDate(String start, String end) {
		// ��ݺϷ��Լ��
		if (!isDate(start)) {
			return new String[0][0];
		}
		if (!isDate(end)) {
			return new String[0][0];
		}
		String end1 = replace(end, "-", "");
		String start1 = replace(start, "-", "");
		if (Integer.parseInt(end1) < Integer.parseInt(start1)) {
			return new String[0][0];
		}
		// ʱ��ͳ��
		int i = 0;
		String[] temp;
		for (i = 0;;) {
			i++;
			temp = getDate(start, i);
			start1 = replace(temp[0], "-", "");
			if (Integer.parseInt(end1) <= Integer.parseInt(start1)) {
				break;
			}
			// if(temp[0].equals(end)) break;
		}
		String[][] result = new String[i + 1][2];
		for (i = 0;;) {

			temp = getDate(start, i);
			result[i] = temp;
			i++;
			start1 = replace(temp[0], "-", "");
			if (Integer.parseInt(end1) <= Integer.parseInt(start1)) {
				break;
			}

			// if(temp[0].equals(end)) break;
		}
		return result;
	}

	/***************************************************************************
	 * 
	 * �ж�����Ƿ��ǺϷ������ڸ�ʽ��2002-08-08
	 * 
	 **************************************************************************/
	public boolean isDate(String date) {
		if (date == null || date.length() != 10) {
			return false;
		}
		String date1 = replace(date, "-", "");
		if (!isInt(date1) || date1.length() != 8) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * 
	 * �����������ĳ��ʱ���day��������
	 * 
	 * result[0]�����ڣ���ʽ��2002-02-02 result[1]�����µ�1�������ڼ� ( �����գ�0 ������6 )
	 * 
	 **************************************************************************/
	public String[] getDate(String start, int day) {
		String[] result = { "", "" };
		// ��ݺϷ��Լ��
		if (!isDate(start)) {
			start = getCurrentDate();
		}
		int intYear = Integer.parseInt(start.substring(0, 4));
		int intMonth = Integer.parseInt(start.substring(5, 7));
		int intDay = Integer.parseInt(start.substring(8, 10));
		GregorianCalendar cal = new GregorianCalendar(intYear, intMonth - 1,
				intDay);
		cal.add(Calendar.DAY_OF_MONTH, day);
		intYear = cal.get(Calendar.YEAR);
		intMonth = cal.get(Calendar.MONTH) + 1;
		intDay = cal.get(Calendar.DAY_OF_MONTH);
		result[0] = intYear + "-";
		if (intMonth > 9) {
			result[0] += intMonth + "-";
		} else {
			result[0] += "0" + intMonth + "-";
		}
		if (intDay > 9) {
			result[0] += intDay + "";
		} else {
			result[0] += "0" + intDay + "";
		}
		if (Calendar.MONDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[1] = "1";
		}
		if (Calendar.TUESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[1] = "2";
		}
		if (Calendar.WEDNESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[1] = "3";
		}
		if (Calendar.THURSDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[1] = "4";
		}
		if (Calendar.FRIDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[1] = "5";
		}
		if (Calendar.SATURDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[1] = "6";
		}
		if (Calendar.SUNDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[1] = "0";
		}
		return result;
	}

	/***************************************************************************
	 * 
	 * ���������ȡ���µ������µ�1�������ڼ�
	 * 
	 * result[0]����� 2002 2003 result[1]���·� 1 2 3 result[2]�����µ�����
	 * result[3]�����µ�1�������ڼ� ( �����գ�0 ������6 )
	 * 
	 **************************************************************************/
	public String[] calendar(String year, String month) {
		// year : 2002 2003 ...
		// month: 1 2 3 ....
		String[] result = new String[4];
		if (year == null || year.length() != 4 || !isInt(year)) {
			year = getCurrentYear();
		}
		if (month == null || month.length() > 2 || !isInt(month)) {
			month = getCurrentMonth();
		}
		GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(year),
				Integer.parseInt(month) - 1, 1);
		result[0] = year;
		result[1] = month;
		result[2] = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "";
		if (Calendar.MONDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[3] = "1";
		}
		if (Calendar.TUESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[3] = "2";
		}
		if (Calendar.WEDNESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[3] = "3";
		}
		if (Calendar.THURSDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[3] = "4";
		}
		if (Calendar.FRIDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[3] = "5";
		}
		if (Calendar.SATURDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[3] = "6";
		}
		if (Calendar.SUNDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			result[3] = "0";
		}
		return result;
	}

	public boolean runCommand(String strCommand) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = null;
			// System.out.println(strCommand);
			p = rt.exec(strCommand);
			java.io.InputStream in = p.getInputStream();
			java.io.BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String input = reader.readLine();
			if (input == null) {
				return true;
			} else {
				while (input != null) {
					System.out.println("interface error:" + input);
					input = reader.readLine();
				}

				return false;
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
		return false;
	}

	public String cmd(String strCommand) {
		String input = null;
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = null;
			// System.out.println(strCommand);
			p = rt.exec(strCommand);
			java.io.InputStream in = p.getInputStream();
			java.io.BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			input = reader.readLine();
			// p.waitFor();
			// Thread.sleep(20000);
			// while(input==null||input.equals(""))
			// input=reader.readLine();
			// System.out.println("interface error:"+input);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return input;
	}

	public String command(String strCommand) {
		String input = "";
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = null;
			// System.out.println(strCommand);
			p = rt.exec(strCommand);
			java.io.InputStream in = p.getInputStream();
			java.io.BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			input = reader.readLine();
			System.out.println("interface error:" + input);
			while (input != null) {
				System.out.println("interface error:" + input);
				input += reader.readLine() + "";
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
		return input;
	}

	public void log(String s) {
		try {
			FileOutputStream out = new FileOutputStream("c:\\debug.txt", true);
			out.write((s + "\r").getBytes("ISO8859_1"));
			out.close();
		} catch (IOException e) {
		}
	}

	public boolean writeFile(String filename, String content, boolean append) {
		boolean blnResult = true;
		try {
			FileOutputStream out = new FileOutputStream(filename, append);
			out.write((content + "\r").getBytes());
			out.close();
		} catch (IOException e) {
			blnResult = false;
		}
		return blnResult;
	}

	// ���ַ���StandardFrom�����ʽת��ΪStandardTo����ĸ�ʽ
	public String codeConver(String Input, String StandardFrom,
			String StandardTo) {
		byte[] bytes;
		String conResult = new String();
		try {
			bytes = Input.getBytes(StandardFrom);
			conResult = new String(bytes, StandardTo);
		}

		catch (Exception e) {
			// debugging begins here
			System.out.println(e);
			// debugging ends here
			return ("null");
		}
		return conResult;
	}

	// ���ַ���StandardFrom�����ʽת���ޱ����ʽ���ַ�
	public String clearConver(String Input, String StandardFrom) {
		byte[] bytes;
		String conResult = new String();
		try {
			bytes = Input.getBytes(StandardFrom);
			conResult = new String(bytes);
		}

		catch (Exception e) {
			// debugging begins here
			System.out.println(e);
			// debugging ends here
			return ("null");
		}
		return conResult;
	}

	// ���ޱ����ʽ���ַ�ת��ΪStandardFrom�����ʽ�ַ�
	public String codeConver(String Input, String StandardTo) {
		byte[] bytes;
		String conResult = new String();
		try {
			bytes = Input.getBytes();
			conResult = new String(bytes, StandardTo);
		}

		catch (Exception e) {
			// debugging begins here
			System.out.println(e);
			// debugging ends here
			return ("null");
		}
		return conResult;
	}

	public String getUrlIp(String url) {
		InetAddress myServer = null;
		try {
			myServer = InetAddress.getByName(url);
		} catch (UnknownHostException e) {
			System.out.println("function. getUrlIp:" + e);
		}
		return myServer.getHostAddress();
	}

	public String getDate(java.util.Date d) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(d);
		String date = "";
		date = g.get(Calendar.YEAR) + "-"
				+ Integer.toString(g.get(Calendar.MONTH) + 1) + "-"
				+ g.get(Calendar.DATE) + " " + g.get(Calendar.HOUR_OF_DAY)
				+ ":" + g.get(Calendar.MINUTE);
		return date;
	}

	public String[] split(String string, String splitChar) {
		String[] s = null;
		if (string == null || string.equals("")) {
			s = new String[0];
			return s;
		}
		String temp = string;
		int i = 0;
		for (i = 0; temp.indexOf(splitChar) != -1; i++) {
			temp = temp.substring(temp.indexOf(splitChar) + 1, temp.length());
		}
		s = new String[i + 1];
		if (s.length == 1) {
			s[0] = temp;
		} else {
			for (i = 0; true; i++) {
				s[i] = string.substring(0, string.indexOf(splitChar));
				string = string.substring(string.indexOf(splitChar) + 1, string
						.length());
				if (string.indexOf(splitChar) == -1) {
					s[i + 1] = string.substring(0);
					break;
				}
			}
		}
		return s;
	}

	public String[] getPageUrl(String url1, String num, int total) {
		// num should be from big to small
		// url[0] -- next
		// url[1] -- previous
		String[] url = new String[2];
		int intNum = 0;
		url[0] = "#";
		url[1] = "#";
		if (isInt(num)) {
			intNum = Integer.parseInt(num);
		}
		if (total > intNum) {
			url[1] = url1 + Integer.toString(intNum + 1);
		}
		if (intNum > 1) {
			url[0] = url1 + Integer.toString(intNum - 1);
		}
		return url;
	}

	public String[] getPageUrl(String url, String page, int total, int pagesize) {
		// 0:first page
		// 1:previous page
		// 2:next page
		// 3:last page
		// 4:current page
		// 5:total pages
		// 6:total records
		String[] s = new String[7];
		int intPage = 1; // current page;
		int intTotalPage = (total + pagesize - 1) / pagesize;
		int temp = 0;
		if (isInt(page)) {
			intPage = Integer.parseInt(page);
		}
		if (intPage > intTotalPage) {
			intPage = intTotalPage;
		}
		s[0] = url + "1";
		if (intPage == 1) {
			s[0] = "#";
		}
		temp = 1;
		if (intPage > 2) {
			temp = intPage - 1;
		}
		s[1] = url + temp;
		if (temp == intPage) {
			s[1] = "#";
		}
		temp = intTotalPage;
		if (intPage < intTotalPage) {
			temp = intPage + 1;
		}
		s[2] = url + temp;
		if (temp == intPage) {
			s[2] = "#";
		}
		s[3] = url + intTotalPage;
		if (intTotalPage == intPage) {
			s[3] = "#";
		}
		s[4] = "" + intPage;
		s[5] = "" + intTotalPage;
		s[6] = total + "";
		return s;
	}

	public int getPageStart(String page, int PageSize) {
		int intPage = 1;
		if (isInt(page)) {
			intPage = Integer.parseInt(page);
		}
		if (intPage < 1) {
			intPage = 1;
		}
		return (intPage - 1) * PageSize;
	}

	public String ChangeSize(int size) {
		String strSize = "";
		if (size < 1024) {
			strSize = size + "Byte";
		}
		if (size > 1024) {
			strSize = Integer.toString((size + 512) / 1024) + "KB";
		}
		return strSize;
	}

	public String CheckStringForHtml(String strString) {

		String strResult = "";
		int intI = 0;
		if (strString == null) {
			strString = "";
		}
		for (int i = 0; i < strString.length(); i++) {

			switch (strString.charAt(i)) {
			// case '\r':
			// result=result+"<br>";
			case '\r':
				intI = 0;
				strResult = strResult + "<br>";
				break;
			// break;
			case ' ':
				if (intI == 2) {
					strResult = strResult + "&nbsp;";
					intI = 2;
				}
				if (intI == 1) {
					strResult = strResult + "&nbsp;&nbsp;";
					intI = 2;
				}
				if (intI == 0) {
					strResult = strResult + " ";
					intI = 1;
				}

				/*
				 * if(intI==1) { strResult=strResult+"&nbsp;"; intI=2; } else {
				 * strResult=strResult+" "; intI=1; }
				 */
				break;
			case '\"':
				intI = 0;
				strResult = strResult + "&quot;";
				break;
			case '<':
				intI = 0;
				strResult = strResult + "&lt;";
				break;
			case '>':
				intI = 0;
				strResult = strResult + "&gt;";
				break;
			default:
				intI = 0;
				strResult = strResult + strString.charAt(i);
			}
		}
		return strResult;
	}

	// �ʼ������ݿ�����HTML�ĸ�ʽ�����Բ����q�html�ı�ǩ��ֻ�ܴ���һЩjava�Ļ�������
	public String CheckContentForHtml(String strString) {

		String strResult = "";
		int intI = 0;
		if (strString == null) {
			strString = "";
		}
		for (int i = 0; i < strString.length(); i++) {
			switch (strString.charAt(i)) {
			case '\r':
				intI = 0;
				strResult = strResult + "<br>";
				break;
			case ' ':
				if (intI == 2) {
					strResult = strResult + "&nbsp;";
					intI = 2;
				}
				if (intI == 1) {
					strResult = strResult + "&nbsp;&nbsp;";
					intI = 2;
				}
				if (intI == 0) {
					strResult = strResult + " ";
					intI = 1;
				}

				/*
				 * if(intI==1) { strResult=strResult+"&nbsp;"; intI=2; } else {
				 * strResult=strResult+" "; intI=1; }
				 */
				break;
			default:
				intI = 0;
				strResult = strResult + strString.charAt(i);
			}
		}
		return strResult;
	}

	//预警消息模板内容HTML格式特殊处理���方法
	public static String ContentHtml(String strString) {
		String strResult = "";
		if (strString == null) {
			strString = "";
		}
		for (int i = 0; i < strString.length(); i++) {
			switch (strString.charAt(i)) {
			case '\r':
				//strResult = strResult + "<br>";
				break;
			case ' ':
				strResult = strResult + "&nbsp;&nbsp;";
				break;
			case 160: //特殊处理
				strResult = strResult + "&nbsp;";
				break;
			default:
				strResult = strResult + strString.charAt(i);
			}
		}
		return strResult;
	}

	public String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null) {
			return "";
		}
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;

		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;
		return strDest;
		// return other.ChangeToISO8859(strDest);
	}

	public static String CheckStringForDB(String strString) {

		String strResult = "";
		if (strString == null) {
			strString = "";

			// strString=setToDB(strString);

		}
		for (int i = 0; i < strString.length(); i++) {
			switch (strString.charAt(i)) {
			case '\'':
				strResult = strResult + "''";
				break;
			case '\\':
				strResult = strResult + "\\";
			default:
				strResult = strResult + strString.charAt(i);
			}
		}
		return strResult;
	}

	public boolean isInt(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		str = str.trim();
		try {
			Integer int_num = new Integer(0);
			int int_out = int_num.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean isAllInt(String str) // ����ĸ������
	{
		String Letters = "0123456789";
		char CheckChar;
		for (int i = 0; i < str.length(); i++) {
			CheckChar = str.charAt(i);
			if (Letters.indexOf(CheckChar) == -1) {
				return false;
			}
		}
		return true;
	}

	public boolean isHexInt(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		str = str.trim();
		try {
			Integer int_num = new Integer(0);
			int int_out = int_num.parseInt(str, 16);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/*
	 * public String getTomorrow() { java.util.Date date=ComputeDate(new
	 * java.util.Date(),Calendar.DAY_OF_MONTH,1); GregorianCalendar todaysdate =
	 * new GregorianCalendar(); todaysdate.setGregorianChange(date); int year =
	 * todaysdate.get(Calendar.YEAR); int month = todaysdate.get(Calendar.MONTH) +
	 * 1; int day = todaysdate.get(Calendar.DAY_OF_MONTH); String strYear =
	 * Integer.toString(year); String strMonth = Integer.toString(month); String
	 * strDay = Integer.toString(day); if (day < 10) strDay = "0" + strDay; if
	 * (month < 10) strMonth = "0" + strMonth; return strYear + "-" + strMonth +
	 * "-" + strDay; }
	 */

	public String getCurrentDate() {
		GregorianCalendar todaysdate = new GregorianCalendar();
		int year = todaysdate.get(Calendar.YEAR);
		int month = todaysdate.get(Calendar.MONTH) + 1;
		int day = todaysdate.get(Calendar.DAY_OF_MONTH);
		String strYear = Integer.toString(year);
		String strMonth = Integer.toString(month);
		String strDay = Integer.toString(day);
		if (day < 10) {
			strDay = "0" + strDay;
		}
		if (month < 10) {
			strMonth = "0" + strMonth;
		}
		return strYear + "-" + strMonth + "-" + strDay;
	}

	/***************************************************************************
	 * ��ʽ��20040909
	 * 
	 */

	public static String getToday() {
		GregorianCalendar todaysdate = new GregorianCalendar();
		int year = todaysdate.get(Calendar.YEAR);
		int month = todaysdate.get(Calendar.MONTH) + 1;
		int day = todaysdate.get(Calendar.DAY_OF_MONTH);
		String strYear = Integer.toString(year);
		String strMonth = Integer.toString(month);
		String strDay = Integer.toString(day);
		if (day < 10) {
			strDay = "0" + strDay;
		}
		if (month < 10) {
			strMonth = "0" + strMonth;
		}
		return strYear + strMonth + strDay;
	}

	public String getCurrentYear() {
		GregorianCalendar todaysdate = new GregorianCalendar();
		int year = todaysdate.get(Calendar.YEAR);
		int month = todaysdate.get(Calendar.MONTH) + 1;
		int day = todaysdate.get(Calendar.DAY_OF_MONTH);
		String strYear = Integer.toString(year);
		String strMonth = Integer.toString(month);
		String strDay = Integer.toString(day);
		if (day < 10) {
			strDay = "0" + strDay;
		}
		if (month < 10) {
			strMonth = "0" + strMonth;
		}
		return strYear;
	}

	public String getCurrentMonth() {
		GregorianCalendar todaysdate = new GregorianCalendar();
		int year = todaysdate.get(Calendar.YEAR);
		int month = todaysdate.get(Calendar.MONTH) + 1;
		int day = todaysdate.get(Calendar.DAY_OF_MONTH);
		String strYear = Integer.toString(year);
		String strMonth = Integer.toString(month);
		String strDay = Integer.toString(day);
		if (day < 10) {
			strDay = "0" + strDay;
		}
		if (month < 10) {
			strMonth = "0" + strMonth;
		}
		return strMonth;
	}

	public String getCurrentDay() {
		GregorianCalendar todaysdate = new GregorianCalendar();
		int year = todaysdate.get(Calendar.YEAR);
		int month = todaysdate.get(Calendar.MONTH) + 1;
		int day = todaysdate.get(Calendar.DAY_OF_MONTH);
		String strYear = Integer.toString(year);
		String strMonth = Integer.toString(month);
		String strDay = Integer.toString(day);
		if (day < 10) {
			strDay = "0" + strDay;
		}
		if (month < 10) {
			strMonth = "0" + strMonth;
		}
		return strDay;
	}

	/***************************************************************************
	 * Author:xuzengli Date:2004/04/02 Function��ҳ�淭ҳ���ܣ������һҳ��ҳ�롣 Parameters��
	 * CurrentPage:��ǰ��ʾ��ҳ�롣 PageCount:��ҳ�� PageType:��ҳ�����ͣ�0����ҳ��1����һҳ��2����һҳ��3��βҳ��
	 * Return����Ҫ��ʾ��ҳ�롣
	 **************************************************************************/
	public int GetNextPage(int CurrentPage, int PageCount, String PageType) {
		if (PageType == null) {

		} else if ("0".equals(PageType)) {
			CurrentPage = 0;
		} else if ("1".equals(PageType)) {
			if ((CurrentPage - 1) > 0) {
				// LastPage = CurrentPage;
				CurrentPage = CurrentPage - 1;
			} else {
				CurrentPage = 0;
			}
		} else if ("2".equals(PageType)) {
			if ((CurrentPage + 1) < PageCount) {
				CurrentPage = CurrentPage + 1;
				// LastPage = CurrentPage + RowCount;
			} else {
				CurrentPage = PageCount - 1;
				// LastPage = superRecord.size();
			}
		} else if ("3".equals(PageType)) {
			// LastPage = superRecord.size();
			CurrentPage = PageCount - 1;
			// superRecord.size() - (superRecord.size() % RowCount);
		}
		if (CurrentPage < 0) {
			CurrentPage = 0;

		}
		return CurrentPage;
	}

	public static String getCurrentDatetime() {
		GregorianCalendar todaysdate = new GregorianCalendar();
		int year = todaysdate.get(Calendar.YEAR);
		int month = todaysdate.get(Calendar.MONTH) + 1;
		int day = todaysdate.get(Calendar.DAY_OF_MONTH);
		int hour = todaysdate.get(Calendar.HOUR_OF_DAY);
		int minute = todaysdate.get(Calendar.MINUTE);
		int second = todaysdate.get(Calendar.SECOND);
		String strYear = Integer.toString(year);
		String strMonth = Integer.toString(month);
		String strDay = Integer.toString(day);
		String strHour = Integer.toString(hour);
		String strMinute = Integer.toString(minute);
		String strSecond = Integer.toString(second);
		if (day < 10) {
			strDay = "0" + strDay;
		}
		if (month < 10) {
			strMonth = "0" + strMonth;
		}
		if (hour < 10) {
			strHour = "0" + strHour;
		}
		if (minute < 10) {
			strMinute = "0" + strMinute;
		}
		if (second < 10) {
			strSecond = "0" + strSecond;
		}

		return strYear + "-" + strMonth + "-" + strDay + " " + strHour + ":"
				+ strMinute + ":" + second;
	}

	public String ChangeToGB2312(String str) {
		byte[] temp = null;
		String strResult = null;
		if (str == null) {
			return "";
		}
		try {
			temp = str.getBytes("GB2312");
			strResult = new String(temp);
		} catch (UnsupportedEncodingException e) {
		}
		return strResult;
	}

	public String ChangeToISO8859(String str) {
		byte[] temp = null;
		if (str == null) {
			return "";
		}
		String strResult = null;
		try {
			temp = str.getBytes("ISO8859_1");
			strResult = new String(temp);
		} catch (UnsupportedEncodingException e) {
		}
		return strResult;
	}

	public String ChangeDateFormat(Calendar c) { // error
		String date = "";
		date = c.YEAR + "-" + c.MONTH + "-" + c.DATE + " " + c.HOUR + ":"
				+ c.MINUTE;
		return date;
	}

	/**
	 * ����ָ���ĸ�ʽ����������ڵ��ַ�
	 * <p>
	 * ʾ��<br>
	 * ���� theDate Ϊ 2003-10-23 15:39:00.0<br>
	 * ���� theFormatString Ϊ "yyyy.MM.dd"<br>
	 * ��ô ���Ϊ�� "2003.10.23"
	 * 
	 * </p>
	 * 
	 * @param theDate
	 *            ĳ��ʱ��
	 * @param theFormatString
	 *            ָ���ĸ�ʽ�����ڸ�ʽ������μ� java.text.SimpleDateFormat��
	 * @return ���ո�ʽ�������ڵ��ַ�
	 */
	public String formatDate(java.util.Date theDate, String theFormatString) {
		if (theDate == null || theFormatString == null) {
			return "";
		}
		String theDateString = "";
		try {
			SimpleDateFormat theDateFormater = new SimpleDateFormat(
					theFormatString);
			theDateString = theDateFormater.format(theDate);
		} catch (IllegalArgumentException theException) {
			//
		}
		return theDateString;
	}

	/***************************************************************************
	 * yyyy-MM-dd ����2004-05-12
	 * 
	 */

	public String getTomorrowDate(String format) {
		return formatDate(getTomorrowDate(new java.util.Date()), format);
	}

	/**
	 * ��ݻ�׼ʱ����������������
	 * 
	 * @param theBaseDate
	 *            ��׼ʱ�䣻���Ϊ null�����ʾ��ǰʱ��
	 * @return
	 */
	public java.util.Date getYesterdayDate(java.util.Date theBaseDate) {
		return computeDate(theBaseDate, Calendar.DAY_OF_MONTH, -1); // �����ڡ��� 1
		// -> ����
	}

	/**
	 * ��ݻ�׼ʱ����������������
	 * 
	 * @param theBaseDate
	 *            ��׼ʱ�䣻���Ϊ null�����ʾ��ǰʱ��
	 * @return
	 */
	public java.util.Date getTomorrowDate(java.util.Date theBaseDate) {
		return computeDate(theBaseDate, Calendar.DAY_OF_MONTH, +1); // �����ڡ��� 1
		// -> ����
	}

	/**
	 * ��ݻ�׼ʱ��������ϸ��µ�����
	 * 
	 * @param theBaseDate
	 *            ��׼ʱ�䣻���Ϊ null�����ʾ��ǰʱ��
	 * @return
	 */
	public java.util.Date getPreviousMonthDate(java.util.Date theBaseDate) {
		return computeDate(theBaseDate, Calendar.MONTH, -1); // ���·ݡ��� 1 ->
		// �ϸ���
	}

	/**
	 * ��ݻ�׼ʱ��������¸��µ�����
	 * 
	 * @param theBaseDate
	 *            ��׼ʱ�䣻���Ϊ null�����ʾ��ǰʱ��
	 * @return
	 */
	public java.util.Date getNextMonthDate(java.util.Date theBaseDate) {
		return computeDate(theBaseDate, Calendar.MONTH, +1); // ���·ݡ��� 1 ->
		// �¸���
	}

	/**
	 * ͳһ�ĸ�ݻ�׼ʱ�䡢�������Ԫ/��/��/��/ʱ/��/��/���룩��ƫ��4����ʱ��ķ���
	 * 
	 * @param theBaseDate
	 *            ��׼ʱ�䣻 ���Ϊ null�����ʾ��ǰʱ��
	 * @param CalendarItem
	 *            ����� ��Ԫ/��/��/��/ʱ/��/��/���� �ȵȣ�����Ϊ�Ϸ��� java.util.Calendar �ĳ���
	 * @param Offset
	 *            ƫ��
	 * @return ������ʱ��
	 */
	public java.util.Date computeDate(java.util.Date theBaseDate,
			int CalendarItem, int Offset) {
		GregorianCalendar theNewDate = new GregorianCalendar();
		if (theBaseDate == null) {
			theNewDate.setTime(new java.util.Date());
		} else {
			theNewDate.setTime(theBaseDate);
		}
		theNewDate.add(CalendarItem, Offset);
		return theNewDate.getTime();
	}

	/***************************************************************************
	 * �����ַ�ض���ʾ
	 * 
	 * 
	 */
	public static String cutStr(String content, int len) {
		if (content == null) {
			return "";
		} else if (content.length() <= len) {
			return content;
		} else {
			return content.substring(0, len) + "...";
		}

	}

	/***************************************************************************
	 * �����Ҫ���뵽��ݿ���е��ֶ�ֵ�Ƿ��'���
	 **************************************************************************/
	public static boolean checkstrToDb(String str) {
		for (int i = 0; i < str.length(); i++) {
			if ("'".equals(str.substring(i, i + 1)))
				return false;
		}
		return true;
	}

	/**
	 * ��������ڸ�ʽ���
	 * @param endDate	
	 * 		��������ڸ�ʽ�ַ�
	 * @param format
	 * 		��Ҫת���ĸ�ʽ���磺yyyyMMddHHmmssSSS,��yyyy-MM-dd
	 * @return
	 * @auther YanBinghuang
	 * @since 2007-8-20
	 */
	public static boolean checkDateFormat(String endDate, String format) {
		if (endDate != null && endDate.length() == format.length()) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				java.util.Date d = sdf.parse(endDate);
				return true;
			} catch (Exception e) {
				// todo
			}
		}
		return false;
	}
}
