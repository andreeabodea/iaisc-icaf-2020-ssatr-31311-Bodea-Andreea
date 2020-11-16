/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class TColon extends Token
{
    public TColon()
    {
        super.setText(":");
    }

    public TColon(int line, int pos)
    {
        super.setText(":");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TColon(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTColon(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TColon text.");
    }
}
