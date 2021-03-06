package kafka.common.protocol.types;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * The schema for a compound record definition
 */
public class Schema extends Type {
	
	private final Field[] fields;
	private final Map<String, Field> fieldsByName;
	
	public Schema(Field...fs) {
		this.fields = new Field[fs.length];
		this.fieldsByName = new HashMap<String, Field>();
		for(int i = 0; i < this.fields.length; i++) {
			Field field = fs[i];
			if(fieldsByName.containsKey(field.name))
				throw new IllegalArgumentException("Schema contains a duplicate field: " + field.name);
			this.fields[i] = new Field(i, field.name, field.type, field.doc);
			this.fieldsByName.put(fs[i].name, this.fields[i]);
		}
	}
    
	public void write(ByteBuffer buffer, Object o) {
    	Record r = (Record) o;
    	for(int i = 0; i < fields.length; i++) {
    		Field f = fields[i];
    		f.type.write(buffer, r.get(f));
    	}
    }
	
	public Object read(ByteBuffer buffer) {
    	Object[] objects = new Object[fields.length];
    	for(int i = 0; i < fields.length; i++)
    		objects[i] = fields[i].type.read(buffer);
    	return new Record(this, objects);
    }
	
	/**
	 * The size of the given record
	 */
	public int sizeOf(Object o) {
		int size = 0;
		Record r = (Record) o;
		for(int i = 0; i < fields.length; i++)
			size += fields[i].type.sizeOf(r.get(fields[i]));
		return size;
	}
	
	/**
	 * The number of fields in this schema
	 */
	public int numFields() {
		return this.fields.length;
	}

	/**
	 * Get a field by its slot in the record array
	 * @param slot The slot at which this field sits
	 * @return The field
	 */
	public Field get(int slot) {
		return this.fields[slot];
	}
	
	/**
	 * Get a field by its name
	 * @param name The name of the field
	 * @return The field 
	 */
	public Field get(String name) {
		return this.fieldsByName.get(name);
	}
	
}