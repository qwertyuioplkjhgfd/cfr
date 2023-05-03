package org.benf.cfr.reader.entities.attributes;

import org.benf.cfr.reader.entities.constantpool.ConstantPool;
import org.benf.cfr.reader.util.bytestream.ByteData;
import org.benf.cfr.reader.util.collections.ListFactory;
import org.benf.cfr.reader.util.output.Dumper;

import java.util.List;

public class AttributeMethodParameters extends Attribute {
    public static final String ATTRIBUTE_NAME = "MethodParameters";

    private static final long OFFSET_OF_ATTRIBUTE_LENGTH = 2;
    private static final long OFFSET_OF_REMAINDER = 6;
    private static final long OFFSET_OF_PARAMETERS_COUNT = 6;
    private static final long OFFSET_OF_PARAMETERS = 7;

    private final int length;
    private final int count;
    private final List<MethodParameterEntry> parameters = ListFactory.newList();

    public AttributeMethodParameters(ByteData raw, ConstantPool cp) {
        this.length = raw.getS4At(OFFSET_OF_ATTRIBUTE_LENGTH);
        this.count = raw.getU1At(OFFSET_OF_PARAMETERS_COUNT);
        long offset = OFFSET_OF_PARAMETERS;
        for (long i = 0; i < count; i++) {
            int nameIndex = raw.getU2At(offset);
            int accessFlags = raw.getU2At(offset + 2);
            parameters.add(new MethodParameterEntry(cp.getEntry(nameIndex), accessFlags));
        }
    }

    public int getParameterCount() {
        return count;
    }

    public List<MethodParameterEntry> getParameters() {
        return parameters;
    }

    @Override
    public Dumper dump(Dumper d) {
        return d.print(ATTRIBUTE_NAME);
    }

    @Override
    public String getRawName() {
        return ATTRIBUTE_NAME;
    }

    @Override
    public long getRawByteLength() {
        return OFFSET_OF_REMAINDER + length;
    }
}
