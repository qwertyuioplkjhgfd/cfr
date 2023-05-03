package org.benf.cfr.reader.bytecode.analysis.variables;

import org.benf.cfr.reader.bytecode.analysis.types.MethodPrototype;
import org.benf.cfr.reader.entities.attributes.MethodParameterEntry;
import org.benf.cfr.reader.util.output.Dumper;

import java.util.List;

public class NamedVariableMethodParameter implements NamedVariable {
    private String name;
    private boolean forced = false;
    private List<MethodParameterEntry> parameters;

    public NamedVariableMethodParameter(List<MethodParameterEntry> parameters, String name) {
        this.name = name;
        this.parameters = parameters;
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
        return d.parameterName(forced ? name : parameters.get(index).name.getValue(), this, prototype, index, defines);
    }
}
