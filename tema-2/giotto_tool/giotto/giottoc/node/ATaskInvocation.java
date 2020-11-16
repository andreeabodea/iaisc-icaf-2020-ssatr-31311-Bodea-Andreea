/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class ATaskInvocation extends PTaskInvocation
{
    private TTaskfreq _taskfreq_;
    private TNumber _taskFrequency_;
    private TDo _do_;
    private TIdent _taskName_;
    private TLPar _lPar_;
    private TIdent _driverName_;
    private TRPar _rPar_;
    private PTaskAnnotation _taskAnnotation_;
    private TSemicolon _semicolon_;

    public ATaskInvocation()
    {
    }

    public ATaskInvocation(
        TTaskfreq _taskfreq_,
        TNumber _taskFrequency_,
        TDo _do_,
        TIdent _taskName_,
        TLPar _lPar_,
        TIdent _driverName_,
        TRPar _rPar_,
        PTaskAnnotation _taskAnnotation_,
        TSemicolon _semicolon_)
    {
        setTaskfreq(_taskfreq_);

        setTaskFrequency(_taskFrequency_);

        setDo(_do_);

        setTaskName(_taskName_);

        setLPar(_lPar_);

        setDriverName(_driverName_);

        setRPar(_rPar_);

        setTaskAnnotation(_taskAnnotation_);

        setSemicolon(_semicolon_);

    }
    public Object clone()
    {
        return new ATaskInvocation(
            (TTaskfreq) cloneNode(_taskfreq_),
            (TNumber) cloneNode(_taskFrequency_),
            (TDo) cloneNode(_do_),
            (TIdent) cloneNode(_taskName_),
            (TLPar) cloneNode(_lPar_),
            (TIdent) cloneNode(_driverName_),
            (TRPar) cloneNode(_rPar_),
            (PTaskAnnotation) cloneNode(_taskAnnotation_),
            (TSemicolon) cloneNode(_semicolon_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATaskInvocation(this);
    }

    public TTaskfreq getTaskfreq()
    {
        return _taskfreq_;
    }

    public void setTaskfreq(TTaskfreq node)
    {
        if (_taskfreq_ != null)
        {
            _taskfreq_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _taskfreq_ = node;
    }

    public TNumber getTaskFrequency()
    {
        return _taskFrequency_;
    }

    public void setTaskFrequency(TNumber node)
    {
        if (_taskFrequency_ != null)
        {
            _taskFrequency_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _taskFrequency_ = node;
    }

    public TDo getDo()
    {
        return _do_;
    }

    public void setDo(TDo node)
    {
        if (_do_ != null)
        {
            _do_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _do_ = node;
    }

    public TIdent getTaskName()
    {
        return _taskName_;
    }

    public void setTaskName(TIdent node)
    {
        if (_taskName_ != null)
        {
            _taskName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _taskName_ = node;
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

    public TIdent getDriverName()
    {
        return _driverName_;
    }

    public void setDriverName(TIdent node)
    {
        if (_driverName_ != null)
        {
            _driverName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _driverName_ = node;
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

    public PTaskAnnotation getTaskAnnotation()
    {
        return _taskAnnotation_;
    }

    public void setTaskAnnotation(PTaskAnnotation node)
    {
        if (_taskAnnotation_ != null)
        {
            _taskAnnotation_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _taskAnnotation_ = node;
    }

    public TSemicolon getSemicolon()
    {
        return _semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if (_semicolon_ != null)
        {
            _semicolon_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _semicolon_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_taskfreq_)
            + toString(_taskFrequency_)
            + toString(_do_)
            + toString(_taskName_)
            + toString(_lPar_)
            + toString(_driverName_)
            + toString(_rPar_)
            + toString(_taskAnnotation_)
            + toString(_semicolon_);
    }

    void removeChild(Node child)
    {
        if (_taskfreq_ == child)
        {
            _taskfreq_ = null;
            return;
        }

        if (_taskFrequency_ == child)
        {
            _taskFrequency_ = null;
            return;
        }

        if (_do_ == child)
        {
            _do_ = null;
            return;
        }

        if (_taskName_ == child)
        {
            _taskName_ = null;
            return;
        }

        if (_lPar_ == child)
        {
            _lPar_ = null;
            return;
        }

        if (_driverName_ == child)
        {
            _driverName_ = null;
            return;
        }

        if (_rPar_ == child)
        {
            _rPar_ = null;
            return;
        }

        if (_taskAnnotation_ == child)
        {
            _taskAnnotation_ = null;
            return;
        }

        if (_semicolon_ == child)
        {
            _semicolon_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_taskfreq_ == oldChild)
        {
            setTaskfreq((TTaskfreq) newChild);
            return;
        }

        if (_taskFrequency_ == oldChild)
        {
            setTaskFrequency((TNumber) newChild);
            return;
        }

        if (_do_ == oldChild)
        {
            setDo((TDo) newChild);
            return;
        }

        if (_taskName_ == oldChild)
        {
            setTaskName((TIdent) newChild);
            return;
        }

        if (_lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
            return;
        }

        if (_driverName_ == oldChild)
        {
            setDriverName((TIdent) newChild);
            return;
        }

        if (_rPar_ == oldChild)
        {
            setRPar((TRPar) newChild);
            return;
        }

        if (_taskAnnotation_ == oldChild)
        {
            setTaskAnnotation((PTaskAnnotation) newChild);
            return;
        }

        if (_semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

    }
}
