/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class TLessOrEqual extends Token
{
    public TLessOrEqual()
    {
        super.setText("<=");
    }

    public TLessOrEqual(int line, int pos)
    {
        super.setText("<=");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TLessOrEqual(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTLessOrEqual(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TLessOrEqual text.");
    }
}
