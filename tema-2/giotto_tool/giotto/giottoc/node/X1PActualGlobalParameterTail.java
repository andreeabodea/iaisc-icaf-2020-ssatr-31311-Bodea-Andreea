/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;


public final class X1PActualGlobalParameterTail extends XPActualGlobalParameterTail
{
    private XPActualGlobalParameterTail _xPActualGlobalParameterTail_;
    private PActualGlobalParameterTail _pActualGlobalParameterTail_;

    public X1PActualGlobalParameterTail()
    {
    }

    public X1PActualGlobalParameterTail(
        XPActualGlobalParameterTail _xPActualGlobalParameterTail_,
        PActualGlobalParameterTail _pActualGlobalParameterTail_)
    {
        setXPActualGlobalParameterTail(_xPActualGlobalParameterTail_);
        setPActualGlobalParameterTail(_pActualGlobalParameterTail_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public XPActualGlobalParameterTail getXPActualGlobalParameterTail()
    {
        return _xPActualGlobalParameterTail_;
    }

    public void setXPActualGlobalParameterTail(XPActualGlobalParameterTail node)
    {
        if (_xPActualGlobalParameterTail_ != null)
        {
            _xPActualGlobalParameterTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _xPActualGlobalParameterTail_ = node;
    }

    public PActualGlobalParameterTail getPActualGlobalParameterTail()
    {
        return _pActualGlobalParameterTail_;
    }

    public void setPActualGlobalParameterTail(PActualGlobalParameterTail node)
    {
        if (_pActualGlobalParameterTail_ != null)
        {
            _pActualGlobalParameterTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pActualGlobalParameterTail_ = node;
    }

    void removeChild(Node child)
    {
        if (_xPActualGlobalParameterTail_ == child)
        {
            _xPActualGlobalParameterTail_ = null;
        }

        if (_pActualGlobalParameterTail_ == child)
        {
            _pActualGlobalParameterTail_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_xPActualGlobalParameterTail_) +
            toString(_pActualGlobalParameterTail_);
    }
}