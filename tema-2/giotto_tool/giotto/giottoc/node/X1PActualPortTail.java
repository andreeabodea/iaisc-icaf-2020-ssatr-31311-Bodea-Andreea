/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;


public final class X1PActualPortTail extends XPActualPortTail
{
    private XPActualPortTail _xPActualPortTail_;
    private PActualPortTail _pActualPortTail_;

    public X1PActualPortTail()
    {
    }

    public X1PActualPortTail(
        XPActualPortTail _xPActualPortTail_,
        PActualPortTail _pActualPortTail_)
    {
        setXPActualPortTail(_xPActualPortTail_);
        setPActualPortTail(_pActualPortTail_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public XPActualPortTail getXPActualPortTail()
    {
        return _xPActualPortTail_;
    }

    public void setXPActualPortTail(XPActualPortTail node)
    {
        if (_xPActualPortTail_ != null)
        {
            _xPActualPortTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _xPActualPortTail_ = node;
    }

    public PActualPortTail getPActualPortTail()
    {
        return _pActualPortTail_;
    }

    public void setPActualPortTail(PActualPortTail node)
    {
        if (_pActualPortTail_ != null)
        {
            _pActualPortTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pActualPortTail_ = node;
    }

    void removeChild(Node child)
    {
        if (_xPActualPortTail_ == child)
        {
            _xPActualPortTail_ = null;
        }

        if (_pActualPortTail_ == child)
        {
            _pActualPortTail_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_xPActualPortTail_) +
            toString(_pActualPortTail_);
    }
}