/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class TPriorities extends Token
{
    public TPriorities()
    {
        super.setText("priorities");
    }

    public TPriorities(int line, int pos)
    {
        super.setText("priorities");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TPriorities(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTPriorities(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TPriorities text.");
    }
}
