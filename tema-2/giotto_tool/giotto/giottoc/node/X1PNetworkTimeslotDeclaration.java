/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;


public final class X1PNetworkTimeslotDeclaration extends XPNetworkTimeslotDeclaration
{
    private XPNetworkTimeslotDeclaration _xPNetworkTimeslotDeclaration_;
    private PNetworkTimeslotDeclaration _pNetworkTimeslotDeclaration_;

    public X1PNetworkTimeslotDeclaration()
    {
    }

    public X1PNetworkTimeslotDeclaration(
        XPNetworkTimeslotDeclaration _xPNetworkTimeslotDeclaration_,
        PNetworkTimeslotDeclaration _pNetworkTimeslotDeclaration_)
    {
        setXPNetworkTimeslotDeclaration(_xPNetworkTimeslotDeclaration_);
        setPNetworkTimeslotDeclaration(_pNetworkTimeslotDeclaration_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public XPNetworkTimeslotDeclaration getXPNetworkTimeslotDeclaration()
    {
        return _xPNetworkTimeslotDeclaration_;
    }

    public void setXPNetworkTimeslotDeclaration(XPNetworkTimeslotDeclaration node)
    {
        if (_xPNetworkTimeslotDeclaration_ != null)
        {
            _xPNetworkTimeslotDeclaration_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _xPNetworkTimeslotDeclaration_ = node;
    }

    public PNetworkTimeslotDeclaration getPNetworkTimeslotDeclaration()
    {
        return _pNetworkTimeslotDeclaration_;
    }

    public void setPNetworkTimeslotDeclaration(PNetworkTimeslotDeclaration node)
    {
        if (_pNetworkTimeslotDeclaration_ != null)
        {
            _pNetworkTimeslotDeclaration_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pNetworkTimeslotDeclaration_ = node;
    }

    void removeChild(Node child)
    {
        if (_xPNetworkTimeslotDeclaration_ == child)
        {
            _xPNetworkTimeslotDeclaration_ = null;
        }

        if (_pNetworkTimeslotDeclaration_ == child)
        {
            _pNetworkTimeslotDeclaration_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_xPNetworkTimeslotDeclaration_) +
            toString(_pNetworkTimeslotDeclaration_);
    }
}
