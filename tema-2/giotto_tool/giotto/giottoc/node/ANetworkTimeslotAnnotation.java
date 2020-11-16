/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class ANetworkTimeslotAnnotation extends PNetworkTimeslotAnnotation
{
    private TLBracket _lBracket_;
    private PNetworkTimeslotDeclarationList _networkTimeslotDeclarationList_;
    private TRBracket _rBracket_;

    public ANetworkTimeslotAnnotation()
    {
    }

    public ANetworkTimeslotAnnotation(
        TLBracket _lBracket_,
        PNetworkTimeslotDeclarationList _networkTimeslotDeclarationList_,
        TRBracket _rBracket_)
    {
        setLBracket(_lBracket_);

        setNetworkTimeslotDeclarationList(_networkTimeslotDeclarationList_);

        setRBracket(_rBracket_);

    }
    public Object clone()
    {
        return new ANetworkTimeslotAnnotation(
            (TLBracket) cloneNode(_lBracket_),
            (PNetworkTimeslotDeclarationList) cloneNode(_networkTimeslotDeclarationList_),
            (TRBracket) cloneNode(_rBracket_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANetworkTimeslotAnnotation(this);
    }

    public TLBracket getLBracket()
    {
        return _lBracket_;
    }

    public void setLBracket(TLBracket node)
    {
        if (_lBracket_ != null)
        {
            _lBracket_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _lBracket_ = node;
    }

    public PNetworkTimeslotDeclarationList getNetworkTimeslotDeclarationList()
    {
        return _networkTimeslotDeclarationList_;
    }

    public void setNetworkTimeslotDeclarationList(PNetworkTimeslotDeclarationList node)
    {
        if (_networkTimeslotDeclarationList_ != null)
        {
            _networkTimeslotDeclarationList_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _networkTimeslotDeclarationList_ = node;
    }

    public TRBracket getRBracket()
    {
        return _rBracket_;
    }

    public void setRBracket(TRBracket node)
    {
        if (_rBracket_ != null)
        {
            _rBracket_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _rBracket_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_lBracket_)
            + toString(_networkTimeslotDeclarationList_)
            + toString(_rBracket_);
    }

    void removeChild(Node child)
    {
        if (_lBracket_ == child)
        {
            _lBracket_ = null;
            return;
        }

        if (_networkTimeslotDeclarationList_ == child)
        {
            _networkTimeslotDeclarationList_ = null;
            return;
        }

        if (_rBracket_ == child)
        {
            _rBracket_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_lBracket_ == oldChild)
        {
            setLBracket((TLBracket) newChild);
            return;
        }

        if (_networkTimeslotDeclarationList_ == oldChild)
        {
            setNetworkTimeslotDeclarationList((PNetworkTimeslotDeclarationList) newChild);
            return;
        }

        if (_rBracket_ == oldChild)
        {
            setRBracket((TRBracket) newChild);
            return;
        }

    }
}
