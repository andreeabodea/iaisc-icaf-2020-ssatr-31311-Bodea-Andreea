/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class TTaskfreq extends Token
{
    public TTaskfreq()
    {
        super.setText("taskfreq");
    }

    public TTaskfreq(int line, int pos)
    {
        super.setText("taskfreq");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TTaskfreq(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTaskfreq(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TTaskfreq text.");
    }
}
