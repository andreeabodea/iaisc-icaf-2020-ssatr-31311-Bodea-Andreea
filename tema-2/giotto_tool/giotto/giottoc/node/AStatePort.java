/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class AStatePort extends PStatePort
{
    private TIdent _typeName_;
    private TIdent _portName_;
    private TAssign _assign_;
    private TIdent _initializationDriver_;

    public AStatePort()
    {
    }

    public AStatePort(
        TIdent _typeName_,
        TIdent _portName_,
        TAssign _assign_,
        TIdent _initializationDriver_)
    {
        setTypeName(_typeName_);

        setPortName(_portName_);

        setAssign(_assign_);

        setInitializationDriver(_initializationDriver_);

    }
    public Object clone()
    {
        return new AStatePort(
            (TIdent) cloneNode(_typeName_),
            (TIdent) cloneNode(_portName_),
            (TAssign) cloneNode(_assign_),
            (TIdent) cloneNode(_initializationDriver_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStatePort(this);
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

    public TAssign getAssign()
    {
        return _assign_;
    }

    public void setAssign(TAssign node)
    {
        if (_assign_ != null)
        {
            _assign_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _assign_ = node;
    }

    public TIdent getInitializationDriver()
    {
        return _initializationDriver_;
    }

    public void setInitializationDriver(TIdent node)
    {
        if (_initializationDriver_ != null)
        {
            _initializationDriver_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _initializationDriver_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_typeName_)
            + toString(_portName_)
            + toString(_assign_)
            + toString(_initializationDriver_);
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

        if (_assign_ == child)
        {
            _assign_ = null;
            return;
        }

        if (_initializationDriver_ == child)
        {
            _initializationDriver_ = null;
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

        if (_assign_ == oldChild)
        {
            setAssign((TAssign) newChild);
            return;
        }

        if (_initializationDriver_ == oldChild)
        {
            setInitializationDriver((TIdent) newChild);
            return;
        }

    }
}