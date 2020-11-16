/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class ACallDriver extends PCallDriver
{
    private TIf _if_;
    private TIdent _guardImplementation_;
    private PGuardParameters _guardParameters_;
    private TThen _then_;
    private TIdent _driverImplementation_;
    private PActualScopedParameters _driverParameters_;

    public ACallDriver()
    {
    }

    public ACallDriver(
        TIf _if_,
        TIdent _guardImplementation_,
        PGuardParameters _guardParameters_,
        TThen _then_,
        TIdent _driverImplementation_,
        PActualScopedParameters _driverParameters_)
    {
        setIf(_if_);

        setGuardImplementation(_guardImplementation_);

        setGuardParameters(_guardParameters_);

        setThen(_then_);

        setDriverImplementation(_driverImplementation_);

        setDriverParameters(_driverParameters_);

    }
    public Object clone()
    {
        return new ACallDriver(
            (TIf) cloneNode(_if_),
            (TIdent) cloneNode(_guardImplementation_),
            (PGuardParameters) cloneNode(_guardParameters_),
            (TThen) cloneNode(_then_),
            (TIdent) cloneNode(_driverImplementation_),
            (PActualScopedParameters) cloneNode(_driverParameters_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACallDriver(this);
    }

    public TIf getIf()
    {
        return _if_;
    }

    public void setIf(TIf node)
    {
        if (_if_ != null)
        {
            _if_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _if_ = node;
    }

    public TIdent getGuardImplementation()
    {
        return _guardImplementation_;
    }

    public void setGuardImplementation(TIdent node)
    {
        if (_guardImplementation_ != null)
        {
            _guardImplementation_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _guardImplementation_ = node;
    }

    public PGuardParameters getGuardParameters()
    {
        return _guardParameters_;
    }

    public void setGuardParameters(PGuardParameters node)
    {
        if (_guardParameters_ != null)
        {
            _guardParameters_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _guardParameters_ = node;
    }

    public TThen getThen()
    {
        return _then_;
    }

    public void setThen(TThen node)
    {
        if (_then_ != null)
        {
            _then_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _then_ = node;
    }

    public TIdent getDriverImplementation()
    {
        return _driverImplementation_;
    }

    public void setDriverImplementation(TIdent node)
    {
        if (_driverImplementation_ != null)
        {
            _driverImplementation_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _driverImplementation_ = node;
    }

    public PActualScopedParameters getDriverParameters()
    {
        return _driverParameters_;
    }

    public void setDriverParameters(PActualScopedParameters node)
    {
        if (_driverParameters_ != null)
        {
            _driverParameters_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _driverParameters_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_if_)
            + toString(_guardImplementation_)
            + toString(_guardParameters_)
            + toString(_then_)
            + toString(_driverImplementation_)
            + toString(_driverParameters_);
    }

    void removeChild(Node child)
    {
        if (_if_ == child)
        {
            _if_ = null;
            return;
        }

        if (_guardImplementation_ == child)
        {
            _guardImplementation_ = null;
            return;
        }

        if (_guardParameters_ == child)
        {
            _guardParameters_ = null;
            return;
        }

        if (_then_ == child)
        {
            _then_ = null;
            return;
        }

        if (_driverImplementation_ == child)
        {
            _driverImplementation_ = null;
            return;
        }

        if (_driverParameters_ == child)
        {
            _driverParameters_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_if_ == oldChild)
        {
            setIf((TIf) newChild);
            return;
        }

        if (_guardImplementation_ == oldChild)
        {
            setGuardImplementation((TIdent) newChild);
            return;
        }

        if (_guardParameters_ == oldChild)
        {
            setGuardParameters((PGuardParameters) newChild);
            return;
        }

        if (_then_ == oldChild)
        {
            setThen((TThen) newChild);
            return;
        }

        if (_driverImplementation_ == oldChild)
        {
            setDriverImplementation((TIdent) newChild);
            return;
        }

        if (_driverParameters_ == oldChild)
        {
            setDriverParameters((PActualScopedParameters) newChild);
            return;
        }

    }
}
