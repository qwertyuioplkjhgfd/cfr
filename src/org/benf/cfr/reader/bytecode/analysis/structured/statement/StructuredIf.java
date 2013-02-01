package org.benf.cfr.reader.bytecode.analysis.structured.statement;

import org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement;
import org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.MatchIterator;
import org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.MatchResultCollector;
import org.benf.cfr.reader.bytecode.analysis.parse.expression.ConditionalExpression;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.BlockIdentifier;
import org.benf.cfr.reader.bytecode.analysis.structured.StructuredStatement;
import org.benf.cfr.reader.bytecode.analysis.structured.StructuredStatementTransformer;
import org.benf.cfr.reader.bytecode.analysis.structured.statement.placeholder.ElseBlock;
import org.benf.cfr.reader.util.output.Dumper;

import java.util.List;
import java.util.Vector;

/**
 * Created:
 * User: lee
 * Date: 15/05/2012
 */
public class StructuredIf extends AbstractStructuredStatement {

    ConditionalExpression conditionalExpression;
    Op04StructuredStatement ifTaken;
    Op04StructuredStatement elseBlock;

    public StructuredIf(ConditionalExpression conditionalExpression, Op04StructuredStatement ifTaken) {
        this(conditionalExpression, ifTaken, null);
    }

    public StructuredIf(ConditionalExpression conditionalExpression, Op04StructuredStatement ifTaken, Op04StructuredStatement elseBlock) {
        this.conditionalExpression = conditionalExpression;
        this.ifTaken = ifTaken;
        this.elseBlock = elseBlock;
    }


    @Override
    public void dump(Dumper dumper) {
        dumper.print("if (" + conditionalExpression + ") ");
        ifTaken.dump(dumper);
        if (elseBlock != null) {
            dumper.removePendingCarriageReturn();
            dumper.print(" else ");
            elseBlock.dump(dumper);
        }
    }

    @Override
    public StructuredStatement informBlockHeirachy(Vector<BlockIdentifier> blockIdentifiers) {
        ifTaken.informBlockMembership(blockIdentifiers);
        if (elseBlock != null) elseBlock.informBlockMembership(blockIdentifiers);
        return null;
    }

    @Override
    public void transformStructuredChildren(StructuredStatementTransformer transformer) {
        ifTaken.transform(transformer);
        if (elseBlock != null) elseBlock.transform(transformer);
    }

    @Override
    public void linearizeInto(List<StructuredStatement> out) {
        out.add(this);
        ifTaken.linearizeStatementsInto(out);
        if (elseBlock != null) {
            out.add(new ElseBlock());
            elseBlock.linearizeStatementsInto(out);
        }
    }

    @Override
    public boolean match(MatchIterator<StructuredStatement> matchIterator, MatchResultCollector matchResultCollector) {
        StructuredStatement o = matchIterator.getCurrent();
        if (!(o instanceof StructuredIf)) return false;
        StructuredIf other = (StructuredIf) o;
        if (!conditionalExpression.equals(other.conditionalExpression)) return false;

        matchIterator.advance();
        return true;
    }
}
