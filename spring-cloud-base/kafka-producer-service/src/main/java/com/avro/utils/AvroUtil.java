package com.avro.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class AvroUtil
{
    
    public static GenericRecord bytesRead(byte[] receivedMessage, Schema schema) throws IOException
    {
        DatumReader<GenericRecord> reader = new SpecificDatumReader<>(schema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(receivedMessage, null);
        GenericRecord message = reader.read(null, decoder);
        
        return message;
    }
    
    public static byte[] bytesWrite(Schema schema, GenericRecord genericRecord) throws IOException
    {
        DatumWriter<GenericRecord> writer = new SpecificDatumWriter<>(schema);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(genericRecord, encoder);
        encoder.flush();
        out.close();
        
        return out.toByteArray();
    }
    
}
