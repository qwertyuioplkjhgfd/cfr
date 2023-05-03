package org.benf.cfr.reader.entities.attributes;

import org.benf.cfr.reader.entities.constantpool.ConstantPoolEntryUTF8;

public class MethodParameterEntry {
    public final int nameIndex;
    public final int accessFlags;

    public MethodParameterEntry(int nameIndex, int accessFlags) {
        this.nameIndex = nameIndex;
        this.accessFlags = accessFlags;
    }
}
