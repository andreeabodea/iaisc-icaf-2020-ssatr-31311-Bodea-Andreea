/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;


public final class X2PHostTail extends XPHostTail
{
    private PHostTail _pHostTail_;

    public X2PHostTail()
    {
    }

    public X2PHostTail(
        PHostTail _pHostTail_)
    {
        setPHostTail(_pHostTail_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public PHostTail getPHostTail()
    {
        return _pHostTail_;
    }

    public void setPHostTail(PHostTail node)
    {
        if (_pHostTail_ != null)
        {
            _pHostTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pHostTail_ = node;
    }

    void removeChild(Node child)
    {
        if (_pHostTail_ == child)
        {
            _pHostTail_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_pHostTail_);
    }
}
