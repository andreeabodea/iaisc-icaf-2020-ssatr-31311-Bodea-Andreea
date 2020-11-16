/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class TActfreq extends Token
{
    public TActfreq()
    {
        super.setText("actfreq");
    }

    public TActfreq(int line, int pos)
    {
        super.setText("actfreq");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TActfreq(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTActfreq(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TActfreq text.");
    }
}
