/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class ADriverDeclaration extends PDriverDeclaration
{
    private TDriver _driver_;
    private TIdent _driverName_;
    private PActualPorts _sourcePorts_;
    private TOutput _output_;
    private PFormalPorts _destinationPorts_;
    private TLBrace _lBrace_;
    private PCallDriver _callDriver_;
    private TRBrace _rBrace_;

    public ADriverDeclaration()
    {
    }

    public ADriverDeclaration(
        TDriver _driver_,
        TIdent _driverName_,
        PActualPorts _sourcePorts_,
        TOutput _output_,
        PFormalPorts _destinationPorts_,
        TLBrace _lBrace_,
        PCallDriver _callDriver_,
        TRBrace _rBrace_)
    {
        setDriver(_driver_);

        setDriverName(_driverName_);

        setSourcePorts(_sourcePorts_);

        setOutput(_output_);

        setDestinationPorts(_destinationPorts_);

        setLBrace(_lBrace_);

        setCallDriver(_callDriver_);

        setRBrace(_rBrace_);

    }
    public Object clone()
    {
        return new ADriverDeclaration(
            (TDriver) cloneNode(_driver_),
            (TIdent) cloneNode(_driverName_),
            (PActualPorts) cloneNode(_sourcePorts_),
            (TOutput) cloneNode(_output_),
            (PFormalPorts) cloneNode(_destinationPorts_),
            (TLBrace) cloneNode(_lBrace_),
            (PCallDriver) cloneNode(_callDriver_),
            (TRBrace) cloneNode(_rBrace_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADriverDeclaration(this);
    }

    public TDriver getDriver()
    {
        return _driver_;
    }

    public void setDriver(TDriver node)
    {
        if (_driver_ != null)
        {
            _driver_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _driver_ = node;
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

    public PActualPorts getSourcePorts()
    {
        return _sourcePorts_;
    }

    public void setSourcePorts(PActualPorts node)
    {
        if (_sourcePorts_ != null)
        {
            _sourcePorts_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _sourcePorts_ = node;
    }

    public TOutput getOutput()
    {
        return _output_;
    }

    public void setOutput(TOutput node)
    {
        if (_output_ != null)
        {
            _output_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _output_ = node;
    }

    public PFormalPorts getDestinationPorts()
    {
        return _destinationPorts_;
    }

    public void setDestinationPorts(PFormalPorts node)
    {
        if (_destinationPorts_ != null)
        {
            _destinationPorts_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _destinationPorts_ = node;
    }

    public TLBrace getLBrace()
    {
        return _lBrace_;
    }

    public void setLBrace(TLBrace node)
    {
        if (_lBrace_ != null)
        {
            _lBrace_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _lBrace_ = node;
    }

    public PCallDriver getCallDriver()
    {
        return _callDriver_;
    }

    public void setCallDriver(PCallDriver node)
    {
        if (_callDriver_ != null)
        {
            _callDriver_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _callDriver_ = node;
    }

    public TRBrace getRBrace()
    {
        return _rBrace_;
    }

    public void setRBrace(TRBrace node)
    {
        if (_rBrace_ != null)
        {
            _rBrace_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _rBrace_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_driver_)
            + toString(_driverName_)
            + toString(_sourcePorts_)
            + toString(_output_)
            + toString(_destinationPorts_)
            + toString(_lBrace_)
            + toString(_callDriver_)
            + toString(_rBrace_);
    }

    void removeChild(Node child)
    {
        if (_driver_ == child)
        {
            _driver_ = null;
            return;
        }

        if (_driverName_ == child)
        {
            _driverName_ = null;
            return;
        }

        if (_sourcePorts_ == child)
        {
            _sourcePorts_ = null;
            return;
        }

        if (_output_ == child)
        {
            _output_ = null;
            return;
        }

        if (_destinationPorts_ == child)
        {
            _destinationPorts_ = null;
            return;
        }

        if (_lBrace_ == child)
        {
            _lBrace_ = null;
            return;
        }

        if (_callDriver_ == child)
        {
            _callDriver_ = null;
            return;
        }

        if (_rBrace_ == child)
        {
            _rBrace_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_driver_ == oldChild)
        {
            setDriver((TDriver) newChild);
            return;
        }

        if (_driverName_ == oldChild)
        {
            setDriverName((TIdent) newChild);
            return;
        }

        if (_sourcePorts_ == oldChild)
        {
            setSourcePorts((PActualPorts) newChild);
            return;
        }

        if (_output_ == oldChild)
        {
            setOutput((TOutput) newChild);
            return;
        }

        if (_destinationPorts_ == oldChild)
        {
            setDestinationPorts((PFormalPorts) newChild);
            return;
        }

        if (_lBrace_ == oldChild)
        {
            setLBrace((TLBrace) newChild);
            return;
        }

        if (_callDriver_ == oldChild)
        {
            setCallDriver((PCallDriver) newChild);
            return;
        }

        if (_rBrace_ == oldChild)
        {
            setRBrace((TRBrace) newChild);
            return;
        }

    }
}