package org.benf.cfr.reader.bytecode.analysis.structured.statement;

import org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement;
import org.benf.cfr.reader.bytecode.analysis.structured.StructuredStatement;
import org.benf.cfr.reader.bytecode.analysis.structured.StructuredStatementTransformer;
import org.benf.cfr.reader.entities.exceptions.ExceptionGroup;
import org.benf.cfr.reader.util.ListFactory;
import org.benf.cfr.reader.util.output.Dumper;

import java.util.List;

/**
 * Created:
 * User: lee
 * Date: 15/05/2012
 */
public class StructuredTry extends AbstractStructuredStatement {
    private final ExceptionGroup exceptionGroup;
    private Op04StructuredStatement tryBlock;
    private List<Op04StructuredStatement> catchBlocks = ListFactory.newList();

    public StructuredTry(ExceptionGroup exceptionGroup, Op04StructuredStatement tryBlock) {
        this.exceptionGroup = exceptionGroup;
        this.tryBlock = tryBlock;
    }

    @Override
    public void dump(Dumper dumper) {
        dumper.print("try ");
        tryBlock.dump(dumper);
        for (Op04StructuredStatement catchBlock : catchBlocks) {
            catchBlock.dump(dumper);
        }
    }

    @Override
    public boolean isProperlyStructured() {
        return true;
    }

    public void addCatch(Op04StructuredStatement catchStatement) {
        catchBlocks.add(catchStatement);
    }

    public void removeFinalJumpsTo(Op04StructuredStatement after) {
        tryBlock.removeLastGoto(after);
    }

    @Override
    public void transformStructuredChildren(StructuredStatementTransformer transformer) {
        tryBlock.transform(transformer);
    }

    @Override
    public void linearizeInto(List<StructuredStatement> out) {
        out.add(this);
        tryBlock.linearizeStatementsInto(out);
        for (Op04StructuredStatement catchBlock : catchBlocks) {
            catchBlock.linearizeStatementsInto(out);
        }

    }

}
