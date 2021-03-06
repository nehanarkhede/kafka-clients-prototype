package kafka.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Utils {
	
	/**
	 * Turn the given UTF8 byte array into a string
	 * @param bytes The byte array
	 * @return The string
	 */
	public static String utf8(byte[] bytes) {
		try {
			return new String(bytes, "UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("This shouldn't happen.", e);
		}
	}
	
	/**
	 * Turn a string into a utf8 byte[]
	 * @param string The string
	 * @return The byte[]
	 */
	public static byte[] utf8(String string) {
		try {
			return string.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("This shouldn't happen.", e);
		}
	}
	
	  /**
	   * Read an unsigned integer from the current position in the buffer, 
	   * incrementing the position by 4 bytes
	   * @param buffer The buffer to read from
	   * @return The integer read, as a long to avoid signedness
	   */
	  public static long readUnsignedInt(ByteBuffer buffer) { 
	    return buffer.getInt() & 0xffffffffL;
	  }
	  
	  /**
	   * Read an unsigned integer from the given position without modifying the buffers
	   * position
	   * @param buffer the buffer to read from
	   * @param index the index from which to read the integer
	   * @return The integer read, as a long to avoid signedness
	   */
	  public static long readUnsignedInt(ByteBuffer buffer, int index) {
		  return buffer.getInt(index) & 0xffffffffL;
	  }
	  
	  /**
	   * Write the given long value as a 4 byte unsigned integer. Overflow is ignored.
	   * @param buffer The buffer to write to
	   * @param value The value to write
	   */
	  public static void writetUnsignedInt(ByteBuffer buffer, long value) {
		  buffer.putInt((int) (value & 0xffffffffL));
	  }
	  
	  /**
	   * Write the given long value as a 4 byte unsigned integer. Overflow is ignored.
	   * @param buffer The buffer to write to
	   * @param index The position in the buffer at which to begin writing
	   * @param value The value to write
	   */
	  public static void writeUnsignedInt(ByteBuffer buffer, int index, long value) { 
		  buffer.putInt(index, (int) (value & 0xffffffffL));
	  }
	  
	  /**
	   * Compute the CRC32 of the byte array
	   * @param bytes The array to compute the checksum for
	   * @return The CRC32
	   */
	  public static long crc32(byte[] bytes){
		  return crc32(bytes, 0, bytes.length);
	  }
	  
	  /**
	   * Compute the CRC32 of the segment of the byte array given by the specificed size and offset
	   * @param bytes The bytes to checksum
	   * @param offset the offset at which to begin checksumming
	   * @param size the number of bytes to checksum
	   * @return The CRC32
	   */
	  public static long crc32(byte[] bytes, int offset, int size) {
	    Crc32 crc = new Crc32();
	    crc.update(bytes, offset, size);
	    return crc.getValue();
	  }
}
