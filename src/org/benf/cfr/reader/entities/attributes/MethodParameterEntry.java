package org.benf.cfr.reader.entities.attributes;

import org.benf.cfr.reader.entities.constantpool.ConstantPoolEntry;
import org.benf.cfr.reader.entities.constantpool.ConstantPoolEntryUTF8;

public class MethodParameterEntry {
    public final ConstantPoolEntryUTF8 name;
    public final int accessFlags;

    public MethodParameterEntry(ConstantPoolEntry name, int accessFlags) {
        this.name = (ConstantPoolEntryUTF8) name;
        this.accessFlags = accessFlags;
    }
}
