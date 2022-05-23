package ConvertTool.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

/**
 * 字节工具类，提供字节转换等方法
 * <p>
 * 二进制位逢二进一
 * 1 1 1 1  1 1 0 1 = 256 + 128 + 64 + 32 + 16 + 8 + 4 + 0 + 1 = 253
 * </p>
 * <p>
 * 这里 | 和 & 均是 二进制运算法
 * 把对应的数字 转换成二进制 在把运算符带入 即是结果
 * 如 8 | 9 | 12 = 0000 1000 | 0000 1001 | 0000 1100 = 0000 1101 = 8 + 4 + 1 = 13
 * 如 8 & 9 & 12 = 0000 1000 & 0000 1001 & 0000 1100 = 0000 1000 = 8
 * <p>
 * 同时 << 和 >> 均为移动符号 左边为乘 右边为除
 * 如: 1 << 3 = 1 * 2 * 2 * 2 = 0000 0001 << 0000 1000 = 8
 * </p>
 *
 * @author wbw
 * @date 2020年8月1日11:13:01
 */
@UtilityClass
public class ByteUtil {
	/**
	 * 补码使用
	 * 0xff = 255 = 1111 1111
	 */
	private final short FRAME = 0xFF;

	/**
	 * 数字 转 bytes
	 *
	 * @param value 值
	 * @param src   源
	 * @param start 开始
	 * @param len   长度
	 */
	public void toBytes(long value, byte[] src, int start, int len) {
		if (ArrayUtil.isEmpty(src) || src.length < len) {
			return;
		}
		for (int i = 0; i < len; i++) {
			src[start + i] = (byte) ((value >> (8 * i)) & FRAME);
		}
	}

	/**
	 * 字段串 转 bytes
	 * 默认 utf-8
	 *
	 * @param value 值
	 * @param src   源
	 * @param start 开始
	 */
	public void toBytes(String value, byte[] src, int start) {
		ByteUtil.toBytes(value, src, start, StandardCharsets.UTF_8);
	}

	/**
	 * 字段串 转 bytes 默认 gbk
	 *
	 * @param value 值
	 * @param src   源
	 * @param start 开始
	 */
	public void toBytesGbk(String value, byte[] src, int start) {
		ByteUtil.toBytes(value, src, start, Charset.forName(CharsetUtil.GBK));
	}

	public void toBytes(String value, byte[] src, int start, Charset charset) {
		if (StrUtil.isEmpty(value) || ArrayUtil.isEmpty(src)) {
			return;
		}
		byte[] valBytes = value.getBytes(charset);
		System.arraycopy(valBytes, 0, src, start, valBytes.length);
	}

	/**
	 * bytes  转 字符串
	 *
	 * @param bytes   源
	 * @param start   开始
	 * @param len     长度
	 * @param charset 编码
	 * @return String
	 */
	public String toString(byte[] bytes, int start, int len, String charset) {
		if (ArrayUtil.isEmpty(bytes) || start + len > bytes.length) {
			return "";
		}
		byte[] res = new byte[len];
		System.arraycopy(bytes, start, res, 0, len);
		return StrUtil.str(res, charset).trim();
	}

	/**
	 * bytes  转 字符串 默认 utf-8
	 *
	 * @param bytes 源
	 * @param start 开始
	 * @param len   长度
	 * @return String
	 */
	public String toString(byte[] bytes, int start, int len) {
		return ByteUtil.toString(bytes, start, len, CharsetUtil.UTF_8);
	}

	/**
	 * 时间特殊处理 yyyy-mm-dd hh:mm:ss
	 *
	 * @param bytes 字节
	 * @param start 起始位置
	 * @return 时间
	 */
	public String toTime(byte[] bytes, int start) {
		List<Integer> time = new LinkedList<>();
		time.add(ByteUtil.toInt(bytes, start, 2));
		time.add(ByteUtil.toInt(bytes, start + 4, 2));
		time.add(ByteUtil.toInt(bytes, start + 6, 2));
		time.add(ByteUtil.toInt(bytes, start + 8, 2));
		time.add(ByteUtil.toInt(bytes, start + 10, 2));
		time.add(ByteUtil.toInt(bytes, start + 12, 2));
		Object[] array = time.stream()
				.map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString())
				.toArray();
		return String.format("%4s-%2s-%2s %2s:%2s:%2s", array);
	}

	/**
	 * bytes  转 字符串 默认 gbk
	 *
	 * @param bytes 源
	 * @param start 开始
	 * @param len   长度
	 * @return String
	 */
	public String toStringGbk(byte[] bytes, int start, int len) {
		return ByteUtil.toString(bytes, start, len, CharsetUtil.GBK);
	}

	public long toLong(byte[] bytes, int start, int len) {
		if (ArrayUtil.isEmpty(bytes) || start + len > bytes.length) {
			return 0L;
		}
		long result = 0;

		for (int i = 0; i < len; i++) {
			result |= (bytes[start + i] & FRAME) << (i * 8);
		}
		return result;
	}

	public final int toInt(byte[] bytes, int start, int len) {
		return Math.toIntExact(ByteUtil.toLong(bytes, start, len));
	}

	public static float toFloat(byte[] b, int start, int len) {
		int l;
		l = b[start + 0];
		l &= 0xff;
		l |= ((long) b[start + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[start + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[start + 3] << 24);
		return Float.intBitsToFloat(l);

	}

	/**
	 * 反的
	 *
	 * @param bytes 源
	 * @param start 开始
	 * @param len   长度
	 * @return int
	 */
	public int toIntReverse(byte[] bytes, int start, int len) {
		if (ArrayUtil.isEmpty(bytes) || start + len > bytes.length) {
			return 0;
		}
		int result = 0;
		for (int i = len - 1, location = 0; i >= 0; i--) {
			result |= (bytes[start + location++] & FRAME) << (i * 8);
		}
		return result;
	}
}