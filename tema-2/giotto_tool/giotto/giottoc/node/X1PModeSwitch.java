/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;


public final class X1PModeSwitch extends XPModeSwitch
{
    private XPModeSwitch _xPModeSwitch_;
    private PModeSwitch _pModeSwitch_;

    public X1PModeSwitch()
    {
    }

    public X1PModeSwitch(
        XPModeSwitch _xPModeSwitch_,
        PModeSwitch _pModeSwitch_)
    {
        setXPModeSwitch(_xPModeSwitch_);
        setPModeSwitch(_pModeSwitch_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public XPModeSwitch getXPModeSwitch()
    {
        return _xPModeSwitch_;
    }

    public void setXPModeSwitch(XPModeSwitch node)
    {
        if (_xPModeSwitch_ != null)
        {
            _xPModeSwitch_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _xPModeSwitch_ = node;
    }

    public PModeSwitch getPModeSwitch()
    {
        return _pModeSwitch_;
    }

    public void setPModeSwitch(PModeSwitch node)
    {
        if (_pModeSwitch_ != null)
        {
            _pModeSwitch_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pModeSwitch_ = node;
    }

    void removeChild(Node child)
    {
        if (_xPModeSwitch_ == child)
        {
            _xPModeSwitch_ = null;
        }

        if (_pModeSwitch_ == child)
        {
            _pModeSwitch_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_xPModeSwitch_) +
            toString(_pModeSwitch_);
    }
}
