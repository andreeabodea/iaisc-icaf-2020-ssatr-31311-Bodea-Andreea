/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class ATimeslotDeclaration extends PTimeslotDeclaration
{
    private TIdent _timeslotName_;
    private TLPar _lPar_;
    private TNumber _startTime_;
    private TComma _comma_;
    private TNumber _endTime_;
    private TRPar _rPar_;

    public ATimeslotDeclaration()
    {
    }

    public ATimeslotDeclaration(
        TIdent _timeslotName_,
        TLPar _lPar_,
        TNumber _startTime_,
        TComma _comma_,
        TNumber _endTime_,
        TRPar _rPar_)
    {
        setTimeslotName(_timeslotName_);

        setLPar(_lPar_);

        setStartTime(_startTime_);

        setComma(_comma_);

        setEndTime(_endTime_);

        setRPar(_rPar_);

    }
    public Object clone()
    {
        return new ATimeslotDeclaration(
            (TIdent) cloneNode(_timeslotName_),
            (TLPar) cloneNode(_lPar_),
            (TNumber) cloneNode(_startTime_),
            (TComma) cloneNode(_comma_),
            (TNumber) cloneNode(_endTime_),
            (TRPar) cloneNode(_rPar_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATimeslotDeclaration(this);
    }

    public TIdent getTimeslotName()
    {
        return _timeslotName_;
    }

    public void setTimeslotName(TIdent node)
    {
        if (_timeslotName_ != null)
        {
            _timeslotName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _timeslotName_ = node;
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

    public TNumber getStartTime()
    {
        return _startTime_;
    }

    public void setStartTime(TNumber node)
    {
        if (_startTime_ != null)
        {
            _startTime_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _startTime_ = node;
    }

    public TComma getComma()
    {
        return _comma_;
    }

    public void setComma(TComma node)
    {
        if (_comma_ != null)
        {
            _comma_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _comma_ = node;
    }

    public TNumber getEndTime()
    {
        return _endTime_;
    }

    public void setEndTime(TNumber node)
    {
        if (_endTime_ != null)
        {
            _endTime_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _endTime_ = node;
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
            + toString(_timeslotName_)
            + toString(_lPar_)
            + toString(_startTime_)
            + toString(_comma_)
            + toString(_endTime_)
            + toString(_rPar_);
    }

    void removeChild(Node child)
    {
        if (_timeslotName_ == child)
        {
            _timeslotName_ = null;
            return;
        }

        if (_lPar_ == child)
        {
            _lPar_ = null;
            return;
        }

        if (_startTime_ == child)
        {
            _startTime_ = null;
            return;
        }

        if (_comma_ == child)
        {
            _comma_ = null;
            return;
        }

        if (_endTime_ == child)
        {
            _endTime_ = null;
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
        if (_timeslotName_ == oldChild)
        {
            setTimeslotName((TIdent) newChild);
            return;
        }

        if (_lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
            return;
        }

        if (_startTime_ == oldChild)
        {
            setStartTime((TNumber) newChild);
            return;
        }

        if (_comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if (_endTime_ == oldChild)
        {
            setEndTime((TNumber) newChild);
            return;
        }

        if (_rPar_ == oldChild)
        {
            setRPar((TRPar) newChild);
            return;
        }

    }
}