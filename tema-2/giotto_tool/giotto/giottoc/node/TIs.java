/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class TIs extends Token
{
    public TIs()
    {
        super.setText("is");
    }

    public TIs(int line, int pos)
    {
        super.setText("is");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TIs(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTIs(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TIs text.");
    }
}
