/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package org.example.model.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class MeasurementAggregate extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -7940188569771058518L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"MeasurementAggregate\",\"namespace\":\"org.example.model.avro\",\"fields\":[{\"name\":\"timestamp\",\"type\":\"string\",\"doc\":\"something\"},{\"name\":\"deviceA\",\"type\":{\"type\":\"record\",\"name\":\"DeviceInfo\",\"fields\":[{\"name\":\"value\",\"type\":\"double\"},{\"name\":\"device\",\"type\":\"string\"},{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"avgFreq\",\"type\":\"double\",\"default\":0.0}]}},{\"name\":\"deviceB\",\"type\":\"DeviceInfo\"},{\"name\":\"deviceC\",\"type\":\"DeviceInfo\"}],\"version\":\"1\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<MeasurementAggregate> ENCODER =
      new BinaryMessageEncoder<MeasurementAggregate>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<MeasurementAggregate> DECODER =
      new BinaryMessageDecoder<MeasurementAggregate>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<MeasurementAggregate> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<MeasurementAggregate> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<MeasurementAggregate>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this MeasurementAggregate to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a MeasurementAggregate from a ByteBuffer. */
  public static MeasurementAggregate fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  /** something */
  @Deprecated public java.lang.CharSequence timestamp;
  @Deprecated public org.example.model.avro.DeviceInfo deviceA;
  @Deprecated public org.example.model.avro.DeviceInfo deviceB;
  @Deprecated public org.example.model.avro.DeviceInfo deviceC;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public MeasurementAggregate() {}

  /**
   * All-args constructor.
   * @param timestamp something
   * @param deviceA The new value for deviceA
   * @param deviceB The new value for deviceB
   * @param deviceC The new value for deviceC
   */
  public MeasurementAggregate(java.lang.CharSequence timestamp, org.example.model.avro.DeviceInfo deviceA, org.example.model.avro.DeviceInfo deviceB, org.example.model.avro.DeviceInfo deviceC) {
    this.timestamp = timestamp;
    this.deviceA = deviceA;
    this.deviceB = deviceB;
    this.deviceC = deviceC;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return timestamp;
    case 1: return deviceA;
    case 2: return deviceB;
    case 3: return deviceC;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: timestamp = (java.lang.CharSequence)value$; break;
    case 1: deviceA = (org.example.model.avro.DeviceInfo)value$; break;
    case 2: deviceB = (org.example.model.avro.DeviceInfo)value$; break;
    case 3: deviceC = (org.example.model.avro.DeviceInfo)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'timestamp' field.
   * @return something
   */
  public java.lang.CharSequence getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the value of the 'timestamp' field.
   * something
   * @param value the value to set.
   */
  public void setTimestamp(java.lang.CharSequence value) {
    this.timestamp = value;
  }

  /**
   * Gets the value of the 'deviceA' field.
   * @return The value of the 'deviceA' field.
   */
  public org.example.model.avro.DeviceInfo getDeviceA() {
    return deviceA;
  }

  /**
   * Sets the value of the 'deviceA' field.
   * @param value the value to set.
   */
  public void setDeviceA(org.example.model.avro.DeviceInfo value) {
    this.deviceA = value;
  }

  /**
   * Gets the value of the 'deviceB' field.
   * @return The value of the 'deviceB' field.
   */
  public org.example.model.avro.DeviceInfo getDeviceB() {
    return deviceB;
  }

  /**
   * Sets the value of the 'deviceB' field.
   * @param value the value to set.
   */
  public void setDeviceB(org.example.model.avro.DeviceInfo value) {
    this.deviceB = value;
  }

  /**
   * Gets the value of the 'deviceC' field.
   * @return The value of the 'deviceC' field.
   */
  public org.example.model.avro.DeviceInfo getDeviceC() {
    return deviceC;
  }

  /**
   * Sets the value of the 'deviceC' field.
   * @param value the value to set.
   */
  public void setDeviceC(org.example.model.avro.DeviceInfo value) {
    this.deviceC = value;
  }

  /**
   * Creates a new MeasurementAggregate RecordBuilder.
   * @return A new MeasurementAggregate RecordBuilder
   */
  public static org.example.model.avro.MeasurementAggregate.Builder newBuilder() {
    return new org.example.model.avro.MeasurementAggregate.Builder();
  }

  /**
   * Creates a new MeasurementAggregate RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new MeasurementAggregate RecordBuilder
   */
  public static org.example.model.avro.MeasurementAggregate.Builder newBuilder(org.example.model.avro.MeasurementAggregate.Builder other) {
    return new org.example.model.avro.MeasurementAggregate.Builder(other);
  }

  /**
   * Creates a new MeasurementAggregate RecordBuilder by copying an existing MeasurementAggregate instance.
   * @param other The existing instance to copy.
   * @return A new MeasurementAggregate RecordBuilder
   */
  public static org.example.model.avro.MeasurementAggregate.Builder newBuilder(org.example.model.avro.MeasurementAggregate other) {
    return new org.example.model.avro.MeasurementAggregate.Builder(other);
  }

  /**
   * RecordBuilder for MeasurementAggregate instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<MeasurementAggregate>
    implements org.apache.avro.data.RecordBuilder<MeasurementAggregate> {

    /** something */
    private java.lang.CharSequence timestamp;
    private org.example.model.avro.DeviceInfo deviceA;
    private org.example.model.avro.DeviceInfo.Builder deviceABuilder;
    private org.example.model.avro.DeviceInfo deviceB;
    private org.example.model.avro.DeviceInfo.Builder deviceBBuilder;
    private org.example.model.avro.DeviceInfo deviceC;
    private org.example.model.avro.DeviceInfo.Builder deviceCBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(org.example.model.avro.MeasurementAggregate.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[0].schema(), other.timestamp);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.deviceA)) {
        this.deviceA = data().deepCopy(fields()[1].schema(), other.deviceA);
        fieldSetFlags()[1] = true;
      }
      if (other.hasDeviceABuilder()) {
        this.deviceABuilder = org.example.model.avro.DeviceInfo.newBuilder(other.getDeviceABuilder());
      }
      if (isValidValue(fields()[2], other.deviceB)) {
        this.deviceB = data().deepCopy(fields()[2].schema(), other.deviceB);
        fieldSetFlags()[2] = true;
      }
      if (other.hasDeviceBBuilder()) {
        this.deviceBBuilder = org.example.model.avro.DeviceInfo.newBuilder(other.getDeviceBBuilder());
      }
      if (isValidValue(fields()[3], other.deviceC)) {
        this.deviceC = data().deepCopy(fields()[3].schema(), other.deviceC);
        fieldSetFlags()[3] = true;
      }
      if (other.hasDeviceCBuilder()) {
        this.deviceCBuilder = org.example.model.avro.DeviceInfo.newBuilder(other.getDeviceCBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing MeasurementAggregate instance
     * @param other The existing instance to copy.
     */
    private Builder(org.example.model.avro.MeasurementAggregate other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[0].schema(), other.timestamp);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.deviceA)) {
        this.deviceA = data().deepCopy(fields()[1].schema(), other.deviceA);
        fieldSetFlags()[1] = true;
      }
      this.deviceABuilder = null;
      if (isValidValue(fields()[2], other.deviceB)) {
        this.deviceB = data().deepCopy(fields()[2].schema(), other.deviceB);
        fieldSetFlags()[2] = true;
      }
      this.deviceBBuilder = null;
      if (isValidValue(fields()[3], other.deviceC)) {
        this.deviceC = data().deepCopy(fields()[3].schema(), other.deviceC);
        fieldSetFlags()[3] = true;
      }
      this.deviceCBuilder = null;
    }

    /**
      * Gets the value of the 'timestamp' field.
      * something
      * @return The value.
      */
    public java.lang.CharSequence getTimestamp() {
      return timestamp;
    }

    /**
      * Sets the value of the 'timestamp' field.
      * something
      * @param value The value of 'timestamp'.
      * @return This builder.
      */
    public org.example.model.avro.MeasurementAggregate.Builder setTimestamp(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.timestamp = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'timestamp' field has been set.
      * something
      * @return True if the 'timestamp' field has been set, false otherwise.
      */
    public boolean hasTimestamp() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'timestamp' field.
      * something
      * @return This builder.
      */
    public org.example.model.avro.MeasurementAggregate.Builder clearTimestamp() {
      timestamp = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'deviceA' field.
      * @return The value.
      */
    public org.example.model.avro.DeviceInfo getDeviceA() {
      return deviceA;
    }

    /**
      * Sets the value of the 'deviceA' field.
      * @param value The value of 'deviceA'.
      * @return This builder.
      */
    public org.example.model.avro.MeasurementAggregate.Builder setDeviceA(org.example.model.avro.DeviceInfo value) {
      validate(fields()[1], value);
      this.deviceABuilder = null;
      this.deviceA = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'deviceA' field has been set.
      * @return True if the 'deviceA' field has been set, false otherwise.
      */
    public boolean hasDeviceA() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'deviceA' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public org.example.model.avro.DeviceInfo.Builder getDeviceABuilder() {
      if (deviceABuilder == null) {
        if (hasDeviceA()) {
          setDeviceABuilder(org.example.model.avro.DeviceInfo.newBuilder(deviceA));
        } else {
          setDeviceABuilder(org.example.model.avro.DeviceInfo.newBuilder());
        }
      }
      return deviceABuilder;
    }

    /**
     * Sets the Builder instance for the 'deviceA' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public org.example.model.avro.MeasurementAggregate.Builder setDeviceABuilder(org.example.model.avro.DeviceInfo.Builder value) {
      clearDeviceA();
      deviceABuilder = value;
      return this;
    }

    /**
     * Checks whether the 'deviceA' field has an active Builder instance
     * @return True if the 'deviceA' field has an active Builder instance
     */
    public boolean hasDeviceABuilder() {
      return deviceABuilder != null;
    }

    /**
      * Clears the value of the 'deviceA' field.
      * @return This builder.
      */
    public org.example.model.avro.MeasurementAggregate.Builder clearDeviceA() {
      deviceA = null;
      deviceABuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'deviceB' field.
      * @return The value.
      */
    public org.example.model.avro.DeviceInfo getDeviceB() {
      return deviceB;
    }

    /**
      * Sets the value of the 'deviceB' field.
      * @param value The value of 'deviceB'.
      * @return This builder.
      */
    public org.example.model.avro.MeasurementAggregate.Builder setDeviceB(org.example.model.avro.DeviceInfo value) {
      validate(fields()[2], value);
      this.deviceBBuilder = null;
      this.deviceB = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'deviceB' field has been set.
      * @return True if the 'deviceB' field has been set, false otherwise.
      */
    public boolean hasDeviceB() {
      return fieldSetFlags()[2];
    }

    /**
     * Gets the Builder instance for the 'deviceB' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public org.example.model.avro.DeviceInfo.Builder getDeviceBBuilder() {
      if (deviceBBuilder == null) {
        if (hasDeviceB()) {
          setDeviceBBuilder(org.example.model.avro.DeviceInfo.newBuilder(deviceB));
        } else {
          setDeviceBBuilder(org.example.model.avro.DeviceInfo.newBuilder());
        }
      }
      return deviceBBuilder;
    }

    /**
     * Sets the Builder instance for the 'deviceB' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public org.example.model.avro.MeasurementAggregate.Builder setDeviceBBuilder(org.example.model.avro.DeviceInfo.Builder value) {
      clearDeviceB();
      deviceBBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'deviceB' field has an active Builder instance
     * @return True if the 'deviceB' field has an active Builder instance
     */
    public boolean hasDeviceBBuilder() {
      return deviceBBuilder != null;
    }

    /**
      * Clears the value of the 'deviceB' field.
      * @return This builder.
      */
    public org.example.model.avro.MeasurementAggregate.Builder clearDeviceB() {
      deviceB = null;
      deviceBBuilder = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'deviceC' field.
      * @return The value.
      */
    public org.example.model.avro.DeviceInfo getDeviceC() {
      return deviceC;
    }

    /**
      * Sets the value of the 'deviceC' field.
      * @param value The value of 'deviceC'.
      * @return This builder.
      */
    public org.example.model.avro.MeasurementAggregate.Builder setDeviceC(org.example.model.avro.DeviceInfo value) {
      validate(fields()[3], value);
      this.deviceCBuilder = null;
      this.deviceC = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'deviceC' field has been set.
      * @return True if the 'deviceC' field has been set, false otherwise.
      */
    public boolean hasDeviceC() {
      return fieldSetFlags()[3];
    }

    /**
     * Gets the Builder instance for the 'deviceC' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public org.example.model.avro.DeviceInfo.Builder getDeviceCBuilder() {
      if (deviceCBuilder == null) {
        if (hasDeviceC()) {
          setDeviceCBuilder(org.example.model.avro.DeviceInfo.newBuilder(deviceC));
        } else {
          setDeviceCBuilder(org.example.model.avro.DeviceInfo.newBuilder());
        }
      }
      return deviceCBuilder;
    }

    /**
     * Sets the Builder instance for the 'deviceC' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public org.example.model.avro.MeasurementAggregate.Builder setDeviceCBuilder(org.example.model.avro.DeviceInfo.Builder value) {
      clearDeviceC();
      deviceCBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'deviceC' field has an active Builder instance
     * @return True if the 'deviceC' field has an active Builder instance
     */
    public boolean hasDeviceCBuilder() {
      return deviceCBuilder != null;
    }

    /**
      * Clears the value of the 'deviceC' field.
      * @return This builder.
      */
    public org.example.model.avro.MeasurementAggregate.Builder clearDeviceC() {
      deviceC = null;
      deviceCBuilder = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MeasurementAggregate build() {
      try {
        MeasurementAggregate record = new MeasurementAggregate();
        record.timestamp = fieldSetFlags()[0] ? this.timestamp : (java.lang.CharSequence) defaultValue(fields()[0]);
        if (deviceABuilder != null) {
          record.deviceA = this.deviceABuilder.build();
        } else {
          record.deviceA = fieldSetFlags()[1] ? this.deviceA : (org.example.model.avro.DeviceInfo) defaultValue(fields()[1]);
        }
        if (deviceBBuilder != null) {
          record.deviceB = this.deviceBBuilder.build();
        } else {
          record.deviceB = fieldSetFlags()[2] ? this.deviceB : (org.example.model.avro.DeviceInfo) defaultValue(fields()[2]);
        }
        if (deviceCBuilder != null) {
          record.deviceC = this.deviceCBuilder.build();
        } else {
          record.deviceC = fieldSetFlags()[3] ? this.deviceC : (org.example.model.avro.DeviceInfo) defaultValue(fields()[3]);
        }
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<MeasurementAggregate>
    WRITER$ = (org.apache.avro.io.DatumWriter<MeasurementAggregate>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<MeasurementAggregate>
    READER$ = (org.apache.avro.io.DatumReader<MeasurementAggregate>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
