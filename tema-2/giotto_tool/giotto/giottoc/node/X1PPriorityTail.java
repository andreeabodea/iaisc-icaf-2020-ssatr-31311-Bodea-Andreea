/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;


public final class X1PPriorityTail extends XPPriorityTail
{
    private XPPriorityTail _xPPriorityTail_;
    private PPriorityTail _pPriorityTail_;

    public X1PPriorityTail()
    {
    }

    public X1PPriorityTail(
        XPPriorityTail _xPPriorityTail_,
        PPriorityTail _pPriorityTail_)
    {
        setXPPriorityTail(_xPPriorityTail_);
        setPPriorityTail(_pPriorityTail_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public XPPriorityTail getXPPriorityTail()
    {
        return _xPPriorityTail_;
    }

    public void setXPPriorityTail(XPPriorityTail node)
    {
        if (_xPPriorityTail_ != null)
        {
            _xPPriorityTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _xPPriorityTail_ = node;
    }

    public PPriorityTail getPPriorityTail()
    {
        return _pPriorityTail_;
    }

    public void setPPriorityTail(PPriorityTail node)
    {
        if (_pPriorityTail_ != null)
        {
            _pPriorityTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pPriorityTail_ = node;
    }

    void removeChild(Node child)
    {
        if (_xPPriorityTail_ == child)
        {
            _xPPriorityTail_ = null;
        }

        if (_pPriorityTail_ == child)
        {
            _pPriorityTail_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_xPPriorityTail_) +
            toString(_pPriorityTail_);
    }
}
