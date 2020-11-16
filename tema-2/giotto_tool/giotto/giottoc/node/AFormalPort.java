/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class AFormalPort extends PFormalPort
{
    private TIdent _typeName_;
    private TIdent _portName_;

    public AFormalPort()
    {
    }

    public AFormalPort(
        TIdent _typeName_,
        TIdent _portName_)
    {
        setTypeName(_typeName_);

        setPortName(_portName_);

    }
    public Object clone()
    {
        return new AFormalPort(
            (TIdent) cloneNode(_typeName_),
            (TIdent) cloneNode(_portName_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFormalPort(this);
    }

    public TIdent getTypeName()
    {
        return _typeName_;
    }

    public void setTypeName(TIdent node)
    {
        if (_typeName_ != null)
        {
            _typeName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _typeName_ = node;
    }

    public TIdent getPortName()
    {
        return _portName_;
    }

    public void setPortName(TIdent node)
    {
        if (_portName_ != null)
        {
            _portName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _portName_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_typeName_)
            + toString(_portName_);
    }

    void removeChild(Node child)
    {
        if (_typeName_ == child)
        {
            _typeName_ = null;
            return;
        }

        if (_portName_ == child)
        {
            _portName_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_typeName_ == oldChild)
        {
            setTypeName((TIdent) newChild);
            return;
        }

        if (_portName_ == oldChild)
        {
            setPortName((TIdent) newChild);
            return;
        }

    }
}
