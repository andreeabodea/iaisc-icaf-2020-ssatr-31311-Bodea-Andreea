/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class ATaskTimingAnnotation extends PTaskTimingAnnotation
{
    private TLBracket _lBracket_;
    private TNumber _start_;
    private TLessOrEqual _lessOrEqual_;
    private TNumber _end_;
    private TRBracket _rBracket_;

    public ATaskTimingAnnotation()
    {
    }

    public ATaskTimingAnnotation(
        TLBracket _lBracket_,
        TNumber _start_,
        TLessOrEqual _lessOrEqual_,
        TNumber _end_,
        TRBracket _rBracket_)
    {
        setLBracket(_lBracket_);

        setStart(_start_);

        setLessOrEqual(_lessOrEqual_);

        setEnd(_end_);

        setRBracket(_rBracket_);

    }
    public Object clone()
    {
        return new ATaskTimingAnnotation(
            (TLBracket) cloneNode(_lBracket_),
            (TNumber) cloneNode(_start_),
            (TLessOrEqual) cloneNode(_lessOrEqual_),
            (TNumber) cloneNode(_end_),
            (TRBracket) cloneNode(_rBracket_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATaskTimingAnnotation(this);
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

    public TNumber getStart()
    {
        return _start_;
    }

    public void setStart(TNumber node)
    {
        if (_start_ != null)
        {
            _start_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _start_ = node;
    }

    public TLessOrEqual getLessOrEqual()
    {
        return _lessOrEqual_;
    }

    public void setLessOrEqual(TLessOrEqual node)
    {
        if (_lessOrEqual_ != null)
        {
            _lessOrEqual_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _lessOrEqual_ = node;
    }

    public TNumber getEnd()
    {
        return _end_;
    }

    public void setEnd(TNumber node)
    {
        if (_end_ != null)
        {
            _end_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _end_ = node;
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
            + toString(_start_)
            + toString(_lessOrEqual_)
            + toString(_end_)
            + toString(_rBracket_);
    }

    void removeChild(Node child)
    {
        if (_lBracket_ == child)
        {
            _lBracket_ = null;
            return;
        }

        if (_start_ == child)
        {
            _start_ = null;
            return;
        }

        if (_lessOrEqual_ == child)
        {
            _lessOrEqual_ = null;
            return;
        }

        if (_end_ == child)
        {
            _end_ = null;
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

        if (_start_ == oldChild)
        {
            setStart((TNumber) newChild);
            return;
        }

        if (_lessOrEqual_ == oldChild)
        {
            setLessOrEqual((TLessOrEqual) newChild);
            return;
        }

        if (_end_ == oldChild)
        {
            setEnd((TNumber) newChild);
            return;
        }

        if (_rBracket_ == oldChild)
        {
            setRBracket((TRBracket) newChild);
            return;
        }

    }
}