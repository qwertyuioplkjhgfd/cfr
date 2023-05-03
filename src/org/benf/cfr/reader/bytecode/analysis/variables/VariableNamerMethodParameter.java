package org.benf.cfr.reader.bytecode.analysis.variables;

import org.benf.cfr.reader.entities.attributes.LocalVariableEntry;
import org.benf.cfr.reader.entities.attributes.MethodParameterEntry;
import org.benf.cfr.reader.util.collections.MapFactory;

import java.util.List;
import java.util.Map;

public class VariableNamerMethodParameter implements VariableNamer {
    private final VariableNamer missingNamer = new VariableNamerDefault();
    private final List<MethodParameterEntry> parameters;
    private final Map<LocalVariableEntry, NamedVariable> cache = MapFactory.newMap();

    public VariableNamerMethodParameter(List<MethodParameterEntry> parameters) {
        this.parameters = parameters;
    }

    @Override
    public NamedVariable getName(int originalRawOffset, Ident ident, long stackPosition, boolean clashed) {
        return new NamedVariableMethodParameter(parameters, null);
    }

    @Override
    public List<NamedVariable> getNamedVariables() {
        return missingNamer.getNamedVariables();
    }

    @Override
    public void mutatingRenameUnClash(NamedVariable toRename) {
        missingNamer.mutatingRenameUnClash(toRename);
    }

    @Override
    public void forceName(Ident ident, long stackPosition, String name) {
        missingNamer.forceName(ident, stackPosition, name);
    }
}
