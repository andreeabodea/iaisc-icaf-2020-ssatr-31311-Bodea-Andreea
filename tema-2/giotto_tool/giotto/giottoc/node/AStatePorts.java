/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class AStatePorts extends PStatePorts
{
    private TLPar _lPar_;
    private PStatePortList _statePortList_;
    private TRPar _rPar_;

    public AStatePorts()
    {
    }

    public AStatePorts(
        TLPar _lPar_,
        PStatePortList _statePortList_,
        TRPar _rPar_)
    {
        setLPar(_lPar_);

        setStatePortList(_statePortList_);

        setRPar(_rPar_);

    }
    public Object clone()
    {
        return new AStatePorts(
            (TLPar) cloneNode(_lPar_),
            (PStatePortList) cloneNode(_statePortList_),
            (TRPar) cloneNode(_rPar_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStatePorts(this);
    }

    public TLPar getLPar()
    {
        return _lPar_;
    }

    public void setLPar(TLPar node)
    {
        if (_lPar_ != null)
        {
            _lPar_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _lPar_ = node;
    }

    public PStatePortList getStatePortList()
    {
        return _statePortList_;
    }

    public void setStatePortList(PStatePortList node)
    {
        if (_statePortList_ != null)
        {
            _statePortList_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _statePortList_ = node;
    }

    public TRPar getRPar()
    {
        return _rPar_;
    }

    public void setRPar(TRPar node)
    {
        if (_rPar_ != null)
        {
            _rPar_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _rPar_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_lPar_)
            + toString(_statePortList_)
            + toString(_rPar_);
    }

    void removeChild(Node child)
    {
        if (_lPar_ == child)
        {
            _lPar_ = null;
            return;
        }

        if (_statePortList_ == child)
        {
            _statePortList_ = null;
            return;
        }

        if (_rPar_ == child)
        {
            _rPar_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
            return;
        }

        if (_statePortList_ == oldChild)
        {
            setStatePortList((PStatePortList) newChild);
            return;
        }

        if (_rPar_ == oldChild)
        {
            setRPar((TRPar) newChild);
            return;
        }

    }
}
