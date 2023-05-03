package org.benf.cfr.reader.bytecode.analysis.variables;

import org.benf.cfr.reader.bytecode.analysis.types.MethodPrototype;
import org.benf.cfr.reader.entities.attributes.MethodParameterEntry;
import org.benf.cfr.reader.entities.constantpool.ConstantPool;
import org.benf.cfr.reader.entities.constantpool.ConstantPoolEntryUTF8;
import org.benf.cfr.reader.util.output.Dumper;

import java.util.List;

public class NamedVariableMethodParameter implements NamedVariable {
    private String name;
    private boolean forced = false;
    private final List<MethodParameterEntry> parameters;
    private final ConstantPool cp;

    public NamedVariableMethodParameter(List<MethodParameterEntry> parameters, String name, ConstantPool cp) {
        this.name = name;
        this.parameters = parameters;
        this.cp = cp;
    }

    @Override
    public void forceName(String name) {
        this.name = name;
        forced = true;
    }

    @Override
    public String getStringName() {
        return this.name;
    }

    @Override
    public boolean isGoodName() {
        return true;
    }

    @Override
    public Dumper dump(Dumper d) {
        return dump(d, false);
    }

    @Override
    public Dumper dump(Dumper d, boolean defines) {
        return d.variableName(name, this, defines);
    }

    @Override
    public Dumper dumpParameter(Dumper d, MethodPrototype prototype, int index, boolean defines) {
        if (forced) {
            return d.parameterName(name, this, prototype, index, defines);
        }
        int nameIndex = parameters.get(index).nameIndex;
        if (nameIndex == 0) {
            return d.parameterName(name, this, prototype, index, defines);
        }
        return d.parameterName(((ConstantPoolEntryUTF8)cp.getEntry(nameIndex)).getValue(), this, prototype, index, defines);
    }
}
