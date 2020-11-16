/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class TActuator extends Token
{
    public TActuator()
    {
        super.setText("actuator");
    }

    public TActuator(int line, int pos)
    {
        super.setText("actuator");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TActuator(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTActuator(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TActuator text.");
    }
}
